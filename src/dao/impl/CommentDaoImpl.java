package dao.impl;

import java.util.List;
import java.util.Map;

import bean.Comments;
import dao.CommentDao;
import service.CommentService;
import utils.DBUtil;

public class CommentDaoImpl implements CommentDao {


    @Override
    public Comments getCommentByID(long commentId) {

        String sql = "select * from comments where commentId = ?";

        return DBUtil.findBySingleObject(sql, Comments.class, commentId);
    }

    @Override
    public Long getCommentNum() {
        String sql = "select count(*) from comments;";
        return DBUtil.queryNums(sql);
    }


    @Override
    public List<Comments> listCommentsByPostID(long postid, int page, int limit) {

        String sql = "select * from comments where commentPostId = ? limit ?, ?";

        return DBUtil.queryListExecute(sql, Comments.class, postid, (page - 1) * limit, limit);
    }

    @Override
    public List<Comments> listComments(int page, int limit) {
        String sql = "select * from comments order by `commentApproved` asc,commentDate desc limit ?, ?;";
        return DBUtil.queryListExecute(sql, Comments.class, (page - 1) * limit, limit);
    }


    @Override
    public List<Comments> listCommentsNotApproved(int page, int limit) {

        String sql = "select * from comments where commentApproved = ? limit ?,?";

        return DBUtil.queryListExecute(sql, Comments.class, 0, (page - 1) * limit, limit);

    }

    @Override
    public List<Map<Object, Object>> listCommentsByPid(long pid, int page, int limit) {
        String sql = "SELECT b.commentId, b.commentAuthor, b.commentDate, b.commentContent, c.userNicename, c.pic"
                + " from posts as a INNER JOIN comments as b INNER JOIN users as c"
                + " WHERE a.ID = b.commentPostId and b.userId = c.ID and b.commentApproved=1 and a.ID = ? order by b.commentDate desc limit ?, ?;";
        return DBUtil.queryListMap(sql, pid, (page - 1) * limit, limit);
    }

    @Override
    public List<Map<Object, Object>> listCommentsByCid(long cid, int page, int limit) {
        String sql = "SELECT b.commentAuthor, b.commentDate, b.commentContent, c.userNicename, c.pic"
                + " from posts as a INNER JOIN comments as b INNER JOIN users as c"
                + " WHERE a.ID = b.commentPostId and b.userId = c.ID and b.commentParent=?;";

        return DBUtil.queryListMap(sql, cid, (page-1)*limit, limit);
    }

    @Override
    public boolean updateCommentsApproved(long cid, int approved) {
        String sql = "update comments set commentApproved = ? where commentId = ?;";
        return DBUtil.update(sql, approved, cid);
    }


    @Override
    public boolean updateCommentsByID(Comments comment) {

        String sql = "update comments set commentDate = ?, commentContent = ?,"
                + " commentType = ? where commentId = ? ";

        return DBUtil.update(sql, comment.getCommentDate(), comment.getCommentContent(),
                comment.getCommentType(), comment.getCommentId());

    }


    @Override
    public boolean removeCommentsByID(long commentId) {

        String sql01 = "delete from comments where commentId = ?";
        String sql02 = "update posts set commentCount = commentCount - 1 where ID in"
                + " (select commentPostId from comments where commentId = ?)";
        DBUtil.startTransaction();
        boolean b1 = DBUtil.update(sql02, commentId);
        boolean b2 = DBUtil.update(sql01, commentId);
        DBUtil.commitSession();
        return b1 && b2;
    }

    @Override
    public long getcommentCountByID(String commentPostId) {

        String sql = "select count(commentId) from comments where commentPostId = ?";

        return DBUtil.queryNums(sql, commentPostId);
    }


    @Override
    public long getcommentCountByApproved() {

        String sql = "select count(commentId) from comments where commentApproved = 0";

        return DBUtil.queryNums(sql);

    }

    @Override
    public long getcommentCountByPid(long pid) {
        String sql = "select count(commentId) from comments where commentApproved = 1 and commentPostId = ?";
        return DBUtil.queryNums(sql, pid);
    }

    @Override
    public boolean saveComment(Comments comment, long pid) {
        String sql = "INSERT INTO comments(`commentPostId`, `commentAuthor`, `commentAuthorEmail`, `commentDate`, `commentContent`, `commentType`, `commentParent`, userId) values(?,?,?,?,?,?,?,?);";
        String sql1 = "UPDATE posts set `commentCount` = `commentCount` + 1 where `ID` = ?;";

        DBUtil.startTransaction();
        boolean b1 = DBUtil.update(sql, comment.getCommentPostId(), comment.getCommentAuthor(), comment.getCommentAuthorEmail(), comment.getCommentDate(), comment.getCommentContent(),comment.getCommentType(), comment.getCommentParent(), comment.getUserId());
        boolean b2 = DBUtil.update(sql1, pid);
        DBUtil.commitSession();
        return b1 && b2;
    }


}
