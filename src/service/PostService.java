package service;

import java.util.List;
import java.util.Map;

import bean.Posts;

public interface PostService {

    /**
     * 通过id得到文章
     * @param id
     * @return
     */
    public Posts getPostsByID(long id);

    /**
     * 通过id得到文章详细信息
     * @param id
     * @return
     */
    public Map<Object, Object> getPostsInfoByID(long id);

    /**
     * 通过文章id得到文章全面的信息
     * @return
     */
    public Map<Object, Object> getmorePostInfoByID(long id);

    /**
     * 通过文章id查询类别
     * @param id
     * @return
     */
    public List<Map<Object, Object>> listPostTags(long id);

    /**
     * 查询一页文章
     * @param page
     * @param limit
     * @return
     */
    public List<Posts> listPosts(int page, int limit);

    /**
     * 查询post的部分重要信息
     * @param page
     * @param limit
     * @return
     */
    public List<Map<Object, Object>> listPostsInfo(int page, int limit);

    /**
     * 查询post的部分重要信息(前台)
     * @param page
     * @param limit
     * @return
     */
    public List<Map<Object, Object>> listPostsInfostage(int page, int limit);

    /**
     * 通过名称模糊查询文章
     * @param name
     * @param page
     * @param limit
     * @return
     */
    public List<Posts> listPostsByName(String name, int page, int limit);

    /**
     * 通过作者模糊查询文章
     * @param author
     * @param page
     * @param limit
     * @return
     */
    public List<Posts> listPostsByAuthor(String author, int page, int limit);


    /**
     * 修改文章
     * @param post
     * @return
     */
    public boolean updatePosts(Posts post);

    /**
     * 新增文章
     * @param post
     * @return
     */
    public long savePosts(Posts post, long termID);

    /**
     * 通过文章ID删除文章
     * @param ID
     * @return
     */
    public boolean removePostsByID(long ID);

    /**
     * 查询文章总数
     * @param
     * @param
     * @return
     */
    public long getpostCount();


    /**
     * 按关键字模糊查询文章内容
     * @param
     * @param
     * @return
     */
    public List<Map<Object, Object>> SearchPost(String words, int page, int limit);

    /**
     * 按关键字模糊查询文章总数
     * @param
     * @param
     * @return
     */
    public long getpostCountByNameAndAuthor(String postTitle, String Author);

    /**
     * 按类别和标签ID查询文章总数
     * @param
     * @param
     * @return
     */
    public long getpostCountByTermID(String TermID);

    /**
     * 根据类别和标签查询post的部分重要信息(前台)
     * @parm TermID
     * @param page
     * @param limit
     * @return
     */
    public List<Map<Object, Object>> listPostsInfostageByTermID(String TermID, int page, int limit);


    /**
     * 查询评论数前五的文章
     * @return
     */
    public List<Posts> listPostsByComment();
}