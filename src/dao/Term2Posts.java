package dao;

import bean.Posts;
import bean.TermRelationships;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-24
 * Time: 下午3:36
 */
public interface Term2Posts {
    /**
     * 通过类别id 查询文章
     * @param id
     * @return
     */
    public List<Posts> listPostsByTermID(long id) ;

    /**
     * 将文章加入到类别中
     * @param postid
     * @param termid
     * @return
     */
    public boolean saveTermRelationships(long postid, long termid) ;

    /**
     * 修改文章类别
     * @param postid
     * @param termid
     * @return
     */
    public boolean updateTermRelationships(long postid, long termid) ;

    /**
     * 删除文章类别
     * @param postid
     * @return
     */
    public boolean removeTermRelationships(long postid) ;
}
