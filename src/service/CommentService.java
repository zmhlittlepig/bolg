package service;

import java.util.List;
import java.util.Map;

import bean.Comments;

public interface CommentService {

	/**
     * 通过评论id查询评论
     * @param id
     * @return
     */
    public Comments getCommentByID(long id);

    /**
     * 查询所有评论数量
     * @return
     */
    public Long getCommentNum();

    /**
     * 通过文章id查询评论
     * @param postid
     * @param page
     * @param limit
     * @return
     * @description  查询该文章下面的所有评论
     */
    public List<Comments> listCommentsByPostID(long postid, int page, int limit);

    /**
     * 查询所有评论
     * @param page
     * @param limit
     * @return
     */
    public List<Comments> listComments(int page, int limit);

    /**
     * 查询文章评论
     * @param pid
     * @param page
     * @param limit
     * @return
     */
    public List<Map<Object, Object>> listCommentsByPid(long pid, int page, int limit);

    /**
     * 查询评论的子评论
     * @param cid
     * @param page
     * @param limit
     * @return
     */
    public List<Map<Object, Object>> listCommentsByCid(long cid, int page, int limit);


    /**
     * 查询未审核的文章评论
     * @param page
     * @param limit
     * @return
     * @description 查询未审核的文章评论
     */
    public List<Comments> listCommentsNotApproved(int page, int limit);

    /**
     * 修改评论
     * @param comment
     * @return
     */
    public boolean updateCommentsByID(Comments comment);

    /**
     * 修改评论状态
     * @param cid
     * @param approved
     * @return
     */
    public boolean updateCommentsApproved(long cid, int approved);

    /**
     * 删除评论
     * @param id
     * @return
     */
    public boolean removeCommentsByID(long id);

    /**
     * 通过文章id查询评论总数
     * @param commentPostId
     * @return
     * @description  查询该文章下面的所有评论
     */
    public long getcommentCountByID(String commentPostId);

    /**
     * 查询未审核的文章评论总数
     * @return
     * @description 查询未审核的文章评论
     */
    public long getcommentCountByApproved();

    public long getcommentCountByPid(long pid);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    public boolean saveComment(Comments comment, long pid);
}
