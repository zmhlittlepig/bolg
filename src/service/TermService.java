package service;

import java.util.List;
import java.util.Map;

import bean.TermTaxonomy;
import bean.Terms;

public interface TermService {

	/**
     * 通过id查询类别
     * @param id
     * @return
     */
    public Terms getTermsByID(long id);

    public Map<Object, Object> getTermsInfoByID(long tid);

    /**
     * 通过名称查询标签
     * @param name
     * @param flag
     * @return
     */
    public Terms getTermByName(String name, int flag);

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
     * 类别导航
     * @return
     */
    public List<List<Map<Object, Object>>> listNavTerms();

    /**
     * 查询所有类别
     * @return
     */
    public List<Terms> listTerms(int flag);

    public boolean updateTermInfo(Terms term, TermTaxonomy termTaxonomy);

    /**
     * 修改类别名称
     * @param name
     * @return
     */
    public boolean updateTermName(String name, long termId);

    /**
     * 修改类别所属父类
     * @param id
     * @param patentid
     * @return
     */
    public boolean updateTermParent(long id, long patentid);

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
    public long saveTerms(Terms terms, TermTaxonomy termTaxonomy);

    /**
     * 删除类别
     * @param id
     * @return
     */
    public boolean removeTerms(long id);
}
