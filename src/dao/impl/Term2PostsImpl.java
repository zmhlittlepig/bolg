package dao.impl;

import java.sql.SQLException;
import java.util.List;

import bean.Posts;
import bean.TermRelationships;
import bean.TermTaxonomy;
import dao.Term2Posts;
import utils.DBUtil;

public class Term2PostsImpl implements Term2Posts {

	@Override
	public List<Posts> listPostsByTermID(long id) {

		String sql = "SELECT * from posts where ID in "
				+ "(select objectId from termrelationships where termTaxonomyId in "
				+ "(SELECT termTaxonomyId from termtaxonomy where termId=?))";
		
		return DBUtil.queryListExecute(sql, Posts.class, id);
		
	}
	
	
	

	@Override
	public boolean saveTermRelationships(long postid, long termid) {
		
		String sql = "INSERT into termrelationships VALUES(?, "
				+ "(SELECT termTaxonomyId FROM termtaxonomy WHERE termId = ?)) ;";
		
		return DBUtil.update(sql, postid, termid);
	}

	
	
	
	@Override
	public boolean updateTermRelationships(long postid, long termid) {
		
		String sql = "UPDATE termrelationships SET termTaxonomyId = "
				+ "(SELECT termTaxonomyId FROM termtaxonomy WHERE termId = ?) WHERE objectId = ?";
		
		return DBUtil.update(sql, termid, postid);
	}

	
	
	
	@Override
	public boolean removeTermRelationships(long postid) {
		
		String sql = "update termrelationships set termTaxonomyId = 0 where objectId = ?";
		
		return DBUtil.update(sql, postid);
	}

}
