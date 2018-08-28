package dao;

import bean.TermTaxonomy;
import bean.Terms;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-24
 * Time: 下午3:15
 */
public interface TermDao {

    /**
     * 通过id查询类别
     * @param id
     * @return
     */
    public Terms getTermsByID(long id) ;

    public Map<Object, Object> getTermsInfoByID(long tid);

    /**
     * 通过名称查询标签
     * @param name
     * @return
     */
    public Terms getTermsByName(String name, int flag);

    /**
     * 通过termid查询Termaxonomy
     * @param termId
     * @return
     */
    public TermTaxonomy getTermTaxonomyByTermID(long termId);

    /**
     * 查询父类id
     * @param termId
     * @return
     */
    public long getTermTaxonomyParentBytid(long termId);

    /**
     * 查询所有类别
     * @return
     */
    public List<Terms> listTerms(int flag) ;

    /**
     * 查询所有顶级类别
     * @return
     */
    public List<Terms> listFirstTerms();

    /**
     * 查询子类别
     * @return
     */
    public List<Map<Object, Object>> listTermsByID(long id) ;

    public boolean updateTermInfo(Terms term, TermTaxonomy termTaxonomy);

    /**
     * 修改类别名称
     * @param name
     * @return
     */
    public boolean updateTermName(String name, long termId) ;

    /**
     * 修改类别所属父类
     * @param id
     * @param patentid
     * @return
     */
    public boolean updateTermParent(long id, long patentid) ;

    /**
     * 修改类别下的文章数目+1
     * @param id
     * @return
     */
    public boolean updateTermCounts(long id);

    /**
     * 添加类别
     * @param terms
     * @return
     */
    public long saveTerms(Terms terms, TermTaxonomy termTaxonomy) ;

    /**
     * 删除类别
     * @param id
     * @return
     */
    public boolean removeTerms(long id) ;
}
