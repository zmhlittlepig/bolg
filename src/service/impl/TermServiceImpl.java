package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.TermTaxonomy;
import bean.Terms;
import dao.TermDao;
import factory.DaoFactory;
import service.TermService;

public class TermServiceImpl implements TermService {
	TermDao termDao = (TermDao)DaoFactory.getInstance("TermDao");
	
	@Override
	public Terms getTermsByID(long id) {

		return termDao.getTermsByID(id);
	}

	@Override
	public Map<Object, Object> getTermsInfoByID(long tid) {
		return termDao.getTermsInfoByID(tid);
	}

	@Override
	public Terms getTermByName(String name, int flag) {
		return termDao.getTermsByName(name, flag);
	}

	@Override
	public TermTaxonomy getTermTaxonomyByTermID(long termId) {
		return termDao.getTermTaxonomyByTermID(termId);
	}

	@Override
	public long getTermTaxonomyParentBytid(long termId) {
		return termDao.getTermTaxonomyParentBytid(termId);
	}

	@Override
	public List<List<Map<Object, Object>>> listNavTerms() {
		List<Map<Object, Object>> maps = termDao.listTermsByID(-1);
		List<List<Map<Object, Object>>> list = new ArrayList<>();

		for (Map<Object, Object> m : maps) {
			List<Map<Object, Object>> sonterms = termDao.listTermsByID(Long.parseLong(m.get("termId").toString()));
			sonterms.add(0, m);
			list.add(sonterms);
		}
		return list;
	}

	@Override
	public List<Terms> listTerms(int flag) {
		
		return termDao.listTerms(flag);
	}

	@Override
	public boolean updateTermInfo(Terms term, TermTaxonomy termTaxonomy) {
		return termDao.updateTermInfo(term, termTaxonomy);
	}

	@Override
	public boolean updateTermName(String name, long termId) {
		
		return termDao.updateTermName(name, termId);
	}

	@Override
	public boolean updateTermParent(long id, long patentid) {
		
		return termDao.updateTermParent(id, patentid);
	}

	@Override
	public boolean updateTermCounts(long id) {
		return termDao.updateTermCounts(id);
	}

	@Override
	public long saveTerms(Terms terms, TermTaxonomy termTaxonomy) {
		
		return termDao.saveTerms(terms, termTaxonomy);
	}

	@Override
	public boolean removeTerms(long id) {
		List<Map<Object, Object>> maps = termDao.listTermsByID(id);
		for (Map<Object, Object> m : maps) {
			Long termId = (Long) m.get("termId");
			termDao.removeTerms(termId);
		}
		return termDao.removeTerms(id);
	}

}
