package dao.impl;

import java.util.List;
import java.util.Map;

import bean.Posts;
import bean.TermRelationships;
import bean.TermTaxonomy;
import dao.Term2PostsDao;
import utils.DBUtil;

public class Term2PostsDaoImpl implements Term2PostsDao {


	@Override
	public boolean getT2P(long postid, long termID) {
		String sql0 = "select termTaxonomyId from termTaxonomy where termId = ?";
        String sql ="select count(*) from termRelationships where `objectId` = ? and termTaxonomyId = ?";
		DBUtil.startTransaction();
        long l = DBUtil.queryNums(sql0, termID);
        long l1 = DBUtil.queryNums(sql, postid, l);
        DBUtil.commitSession();

		return l1 > 0 ? true : false;
	}

	@Override
	public List<Map<Object, Object>> listTermByPostID(long id) {
		String sql = "SELECT terms.* from terms where `termGroup` = 1 and termId in  "
				   + "(SELECT termId from `termTaxonomy` where `termTaxonomyId` in "
				   + "(SELECT `termTaxonomyId` from `termRelationships` where `objectId` = ?));";
        List<Map<Object, Object>> maps = DBUtil.queryListMap(sql, id);
        return maps;
	}

	@Override
	public List<Posts> listPostsByTermID(long id) {

		String sql = "SELECT * from posts where ID in "
				+ "(select objectId from termRelationships where termTaxonomyId in "
				+ "(SELECT termTaxonomyId from termTaxonomy where termId=?))";

		return DBUtil.queryListExecute(sql, Posts.class, id);

	}




	@Override
	public boolean saveTermRelationships(long postid, long termid) {
		String sql = "SELECT termTaxonomyId FROM termTaxonomy WHERE termId = ?";
		String sql01 = "INSERT into termRelationships VALUES(?, ?);";
		String sql02 = "update termTaxonomy set count = count + 1 where termId = ?";

		DBUtil.startTransaction();
		long l = DBUtil.queryNums(sql, termid);
		boolean b1 = DBUtil.update(sql01, postid, l);
		boolean b2 = DBUtil.update(sql02, termid);
		DBUtil.commitSession();
		return b1 && b2;
	}


	@Override
	public boolean updateTermRelationships(long postid, long termid, long oldtermid) {

		String sql01 = "update termTaxonomy set count = count - 1 where termId = ?";

		String sql02 = "update termTaxonomy set count = count + 1 where termId = ?";

		String sql03 = "UPDATE termRelationships SET termTaxonomyId = "
				+ "(SELECT termTaxonomyId FROM termTaxonomy WHERE termId = ?) WHERE objectId = ? and termTaxonomyId = (SELECT termTaxonomyId FROM termTaxonomy WHERE termId = ?)";

		DBUtil.startTransaction();
		boolean b1 = DBUtil.update(sql01, oldtermid);
		boolean b2 = DBUtil.update(sql02, termid);
		boolean b3 = DBUtil.update(sql03, termid, postid, oldtermid);
		DBUtil.commitSession();

		return b1 && b2 && b3;
	}




	@Override
	public boolean removeTermRelationships(long postid, long termTaxonomyId) {

		String sql01 = "update termTaxonomy set count = count - 1 where termTaxonomyId = "
				+ "(select termTaxonomyId from termRelationships where objectId = ? and termTaxonomyId = ?)";

		String sql02 = "update termTaxonomy set count = count + 1 where termTaxonomyId = 0";

		String sql03 = "update termRelationships set termTaxonomyId = 0 where objectId = ? and termTaxonomyId = ?";

		DBUtil.startTransaction();
		boolean b1 = DBUtil.update(sql01, postid, termTaxonomyId);
		boolean b2 = DBUtil.update(sql02);
		boolean b3 = DBUtil.update(sql03, postid, termTaxonomyId);
		DBUtil.commitSession();

		return b1 && b2 && b3;
	}

    @Override
    public boolean removeTermRelationshipsTags(long postid, long termID) {
	    String sql = "delete from termRelationships where objectId = ? and termTaxonomyId = (select termTaxonomyId from termTaxonomy where termId = ?)";
        boolean b = DBUtil.update(sql, postid, termID);
        return b;
    }

}