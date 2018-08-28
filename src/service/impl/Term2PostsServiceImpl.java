package service.impl;

import java.util.List;
import java.util.Map;

import bean.Posts;
import dao.Term2PostsDao;
import factory.DaoFactory;
import service.Term2PostsService;

public class Term2PostsServiceImpl implements Term2PostsService {
	
	Term2PostsDao TPDao = (Term2PostsDao)DaoFactory.getInstance("Term2Posts");


	@Override
	public boolean getT2P(long postid, long termID) {
		return TPDao.getT2P(postid, termID);
	}

	@Override
	public List<Map<Object, Object>> listTermByPostID(long id) {
		return TPDao.listTermByPostID(id);
	}

	@Override
	public List<Posts> listPostsByTermID(long id) {
		return TPDao.listPostsByTermID(id);
	}

	@Override
	public boolean saveTermRelationships(long postid, long termTaxonomyId) {
		return TPDao.saveTermRelationships(postid, termTaxonomyId);
	}

	@Override
	public boolean updateTermRelationships(long postid, long termid, long oldtermid) {
		return TPDao.updateTermRelationships(postid, termid, oldtermid);
	}

	@Override
	public boolean removeTermRelationships(long postid, long termTaxonomyId) {
		return TPDao.removeTermRelationships(postid, termTaxonomyId);
	}

	@Override
	public boolean removeTags(long postid, long termID) {
		return TPDao.removeTermRelationshipsTags(postid, termID);
	}

}
