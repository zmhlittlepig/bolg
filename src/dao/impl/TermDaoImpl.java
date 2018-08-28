package dao.impl;

import java.util.List;
import java.util.Map;

import bean.TermTaxonomy;
import bean.Terms;
import dao.TermDao;
import utils.DBUtil;

public class TermDaoImpl implements TermDao {

	@Override
	public Terms getTermsByID(long termId) {
		
		String sql = "select * from terms where termId = ?";
		
		return DBUtil.findBySingleObject(sql, Terms.class, termId);
	}

	@Override
	public Map<Object, Object> getTermsInfoByID(long tid) {
		String sql = "SELECT a.*, b.parent, b.taxonomy, b.description  from terms as a inner join `termTaxonomy` as b where a.`termId` = ? and a.`termId` = b.`termId`";
		List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, tid);
		if (maps.size() > 0)
			return maps.get(0);
		return null;
	}

	@Override
	public Terms getTermsByName(String name, int flag) {
		String sql = "select * from terms where name = ? and termGroup = ?;";
		return DBUtil.findBySingleObject(sql, Terms.class, name, flag);
	}

	@Override
	public TermTaxonomy getTermTaxonomyByTermID(long termId) {
		String sql = "select * from termTaxonomy where termId = ?";
		return DBUtil.findBySingleObject(sql, TermTaxonomy.class, termId);
	}

    @Override
    public long getTermTaxonomyParentBytid(long termId) {
	    String sql = "select parent from termTaxonomy where termId = ?;";
        return DBUtil.queryNums(sql, termId);
    }


    @Override
	public List<Terms> listTerms(int flag) {
		
		String sql = "select * from terms where termGroup = ?";
		
		return DBUtil.queryListExecute(sql, Terms.class, flag);
		
	}

	@Override
	public List<Terms> listFirstTerms() {
		String sql = "select a.*, b.count from terms as a inner join termTaxonomy as b where termGroup=0 and a.termId = b.termId and b.parent = -1";
		return DBUtil.queryListExecute(sql, Terms.class);
	}


	@Override
	public List<Map<Object, Object>> listTermsByID(long patentid) {
		
		String sql = "select a.*, b.count from terms as a inner join termTaxonomy as b where termGroup=0 and a.termId = b.termId and b.parent = ?";

		return DBUtil.queryListMap(sql, patentid);
		
	}

	@Override
	public boolean updateTermInfo(Terms term, TermTaxonomy termTaxonomy) {
		String sql = "UPDATE terms set name = ?, slug = ? where `termId` = ?;";
		String sql1 = "UPDATE `termTaxonomy` set description = ?, parent = ?, taxonomy = ? where termId = ?;";
		DBUtil.startTransaction();
		boolean b1 = DBUtil.update(sql, term.getName(), term.getSlug(), term.getTermId());
		boolean b2 = DBUtil.update(sql1, termTaxonomy.getDescription(), termTaxonomy.getParent(), termTaxonomy.getTaxonomy(), termTaxonomy.getTermId());
		DBUtil.commitSession();
		return b1 && b2;
	}


	@Override
	public boolean updateTermName(String name, long termId) {
		
		String sql = "update terms set name = ? where termId = ?";
		
		return DBUtil.update(sql, name, termId);
	}
	
	
	

	@Override
	public boolean updateTermParent(long termId, long patentid) {
		
		String sql = "update termTaxonomy set parent = ? where termId = ?";
		
		return DBUtil.update(sql, patentid, termId);
		
	}

    @Override
    public boolean updateTermCounts(long id) {
	    String sql ="update termTaxonomy set count = count+1 where termTaxonomyId = ?";
        return DBUtil.update(sql, id);
    }


    @Override
	public long saveTerms(Terms terms, TermTaxonomy termTaxonomy) {
	    long l = -1;
		String sql = "insert into terms(name, slug, termGroup) values(?, ?, ?)";

		DBUtil.startTransaction();
		boolean b1 = DBUtil.update(sql, terms.getName(), terms.getSlug(), terms.getTermGroup());
        l = DBUtil.queryNums("SELECT LAST_INSERT_ID()");
        String sql1 = "insert into termTaxonomy(termId, taxonomy, description, parent) values(?, ?, ?, ?)";
        boolean b2 = DBUtil.update(sql1, l, termTaxonomy.getTaxonomy(), termTaxonomy.getDescription(), termTaxonomy.getParent());
        DBUtil.commitSession();
        return l;
	}


	@Override
	public boolean removeTerms(long termId) {


		String sql01 = "update termRelationships set termTaxonomyId = 0 where termTaxonomyId in "
				+ "(select termTaxonomyId from termTaxonomy where termId = ?)";
		
		String sql02 = "delete from terms where termId = ?";
		String sql03 = "delete from termTaxonomy where termId = ?";
		DBUtil.startTransaction();
        boolean b1 = DBUtil.update(sql01, termId);
        boolean b2 = DBUtil.update(sql03, termId);
        boolean b3 = DBUtil.update(sql02, termId);
		DBUtil.commitSession();

		return b2 && b3;
	}

}
