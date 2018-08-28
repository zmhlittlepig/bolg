package dao.impl;

import java.util.List;
import java.util.Map;

import bean.Posts;
import dao.PostDao;
import utils.DBUtil;

public class PostDaoImpl implements PostDao {

    @Override
    public Posts getPostsByID(long ID) {

        String sql = "select * from posts where ID = ?";

        return DBUtil.findBySingleObject(sql, Posts.class, ID);

    }

    @Override
    public Map<Object, Object> getmorePostInfoByID(long id) {
        String sql = "SELECT a.postTitle, a.postContent, a.postDate, a.commentCount, b.`name`, c.userNicename " +
                " FROM posts AS a INNER JOIN terms as b INNER JOIN users as c " +
                " INNER JOIN termRelationships as e " +
                " INNER JOIN termTaxonomy as f " +
                " WHERE a.ID = e.objectId and e.termTaxonomyId = f.termTaxonomyId " +
                " and b.termGroup = 0 and f.termId = b.termId and a.postAuthor = c.ID " +
                " and a.ID = ?;";
        List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, id);
        if(maps.size() > 0) return maps.get(0);
        return null;
    }

    @Override
    public List<Map<Object, Object>> listPostTags(long id){
        String sql = "SELECT b.`name`"
                + " FROM posts AS a INNER JOIN terms as b"
                + " INNER JOIN users as c"
                + " INNER JOIN termRelationships as e"
                + " INNER JOIN termTaxonomy as f"
                + " WHERE a.ID = e.objectId and"
                + " e.termTaxonomyId = f.termTaxonomyId"
                + " and b.termGroup = 1 and f.termId = b.termId and a.postAuthor = c.ID"
                + " and a.ID = ?;";
        List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, id);
        return maps;
    }

    @Override
    public Map<Object, Object> getPostsInfoByID(long id) {

        String sql = "SELECT e.userNicename, a.postDate, a.postTitle, a.postExcerpt, a.ID, a.commentCount,a.postContent, d.`name`, d.termId"
                + " from posts as a INNER JOIN termRelationships as b "
                + "INNER JOIN termTaxonomy as c INNER JOIN terms as d inner join users as e "
                + "WHERE a.ID =? and a.ID = b.objectId and b.termTaxonomyId = c.termTaxonomyId "
                + "and c.termId = d.termId and d.termGroup = 0 and a.postAuthor = e.ID;";
        List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, id);
        if (maps.size() > 0) {
            return maps.get(0);
        }
        return null;
    }

    @Override
    public List<Posts> listPosts(int page, int limit) {
        String sql = "select * from posts limit ? , ?";
        return DBUtil.queryListExecute(sql, Posts.class, (page - 1) * limit, limit);
    }

    @Override
    public List<Map<Object, Object>> listPostsInfo(int page, int limit) {
        String sql = "select a.ID,a.postTitle, a.`postAuthor`, d.name, a.`commentCount`, a.`postModified` from posts as a inner join termRelationships as b inner join `termTaxonomy` as c inner JOIN terms as d where a.ID = b.ObjectID and b.`termTaxonomyId` = c.`termTaxonomyId` and d.`termId` = c.`termId` and d.`termGroup` = 0 order by a.`postModified` desc limit ?, ?";
        List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, (page - 1) * limit, limit);

        return maps;
    }

    @Override
    public List<Map<Object, Object>> listPostsInfostage(int page, int limit) {

        String sql = "SELECT e.userNicename, a.postDate, a.postTitle, a.postExcerpt,a.postPic, a.ID, "
                + "a.commentCount, d.`name` from posts as a INNER JOIN termRelationships as b "
                + "INNER JOIN termTaxonomy as c INNER JOIN terms as d inner join users as e WHERE a.ID = b.objectId "
                + "and b.termTaxonomyId = c.termTaxonomyId and c.termId = d.termId and"
                + " d.termGroup = 0 and a.postAuthor = e.ID ORDER BY a.postDate limit ?,?";

        List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, (page - 1) * limit, limit);

        return maps;
    }


    @Override
    public List<Map<Object, Object>> listPostsInfostageByword(String words, int page, int limit) {

        String sql = "SELECT e.userNicename, a.postDate, a.postTitle, a.postExcerpt,a.postPic, a.ID, "
                + "a.commentCount, d.`name` from posts as a INNER JOIN termRelationships as b "
                + "INNER JOIN termTaxonomy as c INNER JOIN terms as d inner join users as e WHERE a.ID = b.objectId "
                + "and b.termTaxonomyId = c.termTaxonomyId and c.termId = d.termId and"
                + " d.termGroup = 0 and a.postAuthor = e.ID and (a.postTitle like ? or e.userNicename like ?) ORDER BY a.postDate limit ?,?";

        List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, "%" + words + "%", "%" + words + "%", (page - 1) * limit, limit);

        return maps;
    }

    @Override
    public List<Posts> listPostsByName(String postName, int page, int limit) {

        String sql = "select * from posts where postName like ? limit ? , ?";

        return DBUtil.queryListExecute(sql, Posts.class, "%" + postName + "%", (page - 1) * limit, limit);

    }


    @Override
    public List<Posts> listPostsByAuthor(String author, int page, int limit) {

        String sql = "select * from posts where postAuthor like ? limit ? , ?";

        return DBUtil.queryListExecute(sql, Posts.class, "%" + author + "%", (page - 1) * limit, limit);

    }


    @Override
    public boolean updatePosts(Posts post) {

        String sql = "update posts set postContent = ?, postTitle = ?, postExcerpt = ?, postStatus = ?, "
                + " commentStatus = ?, postPic = ?, postModified = ? where ID = ?";

        DBUtil.update(sql, post.getPostContent(), post.getPostTitle(), post.getPostExcerpt(),
                post.getPostStatus(), post.getCommentStatus(), post.getPostPic(),
                post.getPostModified(), post.getID());

        return true;
    }


    @Override
    public long savePosts(Posts post, long termid) {
        boolean b1 = false;
        boolean b2 = false;
        String sql = "insert into posts(postAuthor, postDate, postContent, postTitle, postExcerpt,postStatus,commentStatus,postPic,postModified) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBUtil.startTransaction();
        b1 = DBUtil.update(sql, post.getPostAuthor(), post.getPostDate(), post.getPostContent(), post.getPostTitle(),
                post.getPostExcerpt(), post.getPostStatus(), post.getCommentStatus(), post.getPostPic(), post.getPostDate());

        long l = -1;
        if (b1) {
            l = DBUtil.queryNums("SELECT LAST_INSERT_ID()");
            System.out.println(l);
            String sql1 = "insert into termRelationships values(?, ?)";
            String sql2 = "SELECT termTaxonomyId FROM termTaxonomy WHERE termId = ?";
            long id = DBUtil.queryNums(sql2, termid);
            b2 = DBUtil.update(sql1, l, id);
        }
        String sql3 = "update termTaxonomy set count = count+1 where termId = ?";
        DBUtil.update(sql3, termid);

        DBUtil.commitSession();

        return l;
    }


    @Override
    public boolean removePostsByID(long ID) {


        String sql01 = "delete from posts where ID = ?";
        String sql = "UPDATE `termTaxonomy` set `count` = `count` - 1 where `termTaxonomyId` in (SELECT `termTaxonomyId` from `termRelationships` where objectId = ?);";
        String sql02 = "delete from termRelationships where objectId = ?";

        DBUtil.startTransaction();
        boolean b1 = DBUtil.update(sql02, ID);
        boolean b3 = DBUtil.update(sql, ID);
        boolean b2 = DBUtil.update(sql01, ID);
        DBUtil.commitSession();

        return b1 && b2 && b3;
    }

    @Override
    public long getpostCount() {

        String sql = "select count(ID) from posts";

        return DBUtil.queryNums(sql);
    }

    @Override
    public long getpostCountByNameAndAuthor(String postTitle, String Author) {

        String sql = "select count(a.ID) from posts as a inner join users as b where a.postAuthor = b.ID and (a.postTitle like ? or b.userNicename like ?)";

        return DBUtil.queryNums(sql, "%" + postTitle + "%", "%" + Author + "%");
    }

    @Override
    public long getpostCountByTermID(String TermID) {

        String sql = "select count from termTaxonomy where termId = ?";

        return DBUtil.queryNums(sql, TermID);
    }


    @Override
    public List<Map<Object, Object>> listPostsInfostageByTermID(String TermID, int page, int limit) {

        String sql = "SELECT e.userNicename, a.postDate, a.postTitle, a.postExcerpt, a.postPic, a.ID, "
                + "a.commentCount, d.`name` from posts as a INNER JOIN termRelationships as b "
                + "INNER JOIN termTaxonomy as c INNER JOIN terms as d inner join users as e WHERE a.ID = b.objectId "
                + "and b.termTaxonomyId = c.termTaxonomyId and c.termId = d.termId and"
                + " a.postAuthor = e.ID and d.termId = ? ORDER BY a.postDate desc limit ?,?";

        List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, TermID, (page - 1) * limit, limit);

        return maps;
    }

    @Override
    public List<Posts> listPostsByComment() {

        String sql = "select postTitle, postPic, postExcerpt, ID from posts order by commentCount desc limit 5 ";

        List<Posts> list = DBUtil.queryListExecute(sql, Posts.class);

        return list;
    }

}
