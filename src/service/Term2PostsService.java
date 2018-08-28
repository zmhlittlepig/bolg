package service;

import java.util.List;
import java.util.Map;

import bean.Posts;

public interface Term2PostsService {

    /**
     * 查询关系是否存在
     * @param postid
     * @param termID
     * @return
     */
    public boolean getT2P(long postid, long termID);

    /**
     * 通过文章id 查询标签
     * @param id
     * @return
     */
    public List<Map<Object, Object>> listTermByPostID(long id);


    /**
     * 通过类别id 查询文章
     * @param id
     * @return
     */
    public List<Posts> listPostsByTermID(long id);

    /**
     * 将文章加入到类别中
     * @param postid
     * @param termTaxonomyId
     * @return
     */
    public boolean saveTermRelationships(long postid, long termTaxonomyId);

    /**
     * 修改文章类别
     * @param postid
     * @param termid
     * @return
     */
    public boolean updateTermRelationships(long postid, long termid, long oldtermid);

    /**
     * 删除文章类别
     * @param postid
     * @param termTaxonomyId
     * @return
     */
    public boolean removeTermRelationships(long postid, long termTaxonomyId);

    /**
     * 删除文章标签关系
     * @param postid
     * @param termID
     * @return
     */
    public boolean removeTags(long postid, long termID);
}
