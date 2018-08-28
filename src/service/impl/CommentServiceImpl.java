package service.impl;

import java.util.List;
import java.util.Map;

import bean.Comments;
import dao.CommentDao;
import factory.DaoFactory;
import service.CommentService;

public class CommentServiceImpl implements CommentService{

	CommentDao commentDao = (CommentDao)DaoFactory.getInstance("CommentDao");
	
	@Override
	public Comments getCommentByID(long id) {

		return commentDao.getCommentByID(id);
	}

	@Override
	public Long getCommentNum() {
		return commentDao.getCommentNum();
	}

	@Override
	public List<Comments> listCommentsByPostID(long postid, int page, int limit) {
		
		return commentDao.listCommentsByPostID(postid, page, limit);
	}

	@Override
	public List<Comments> listComments(int page, int limit) {
		return commentDao.listComments(page, limit);
	}

	@Override
	public List<Map<Object, Object>> listCommentsByPid(long pid, int page, int limit) {
		return commentDao.listCommentsByPid(pid, page, limit);
	}

	@Override
	public List<Map<Object, Object>> listCommentsByCid(long cid, int page, int limit) {
		return commentDao.listCommentsByCid(cid, page, limit);
	}

	@Override
	public List<Comments> listCommentsNotApproved(int page, int limit) {
		
		return commentDao.listCommentsNotApproved(page, limit);
	}

	@Override
	public boolean updateCommentsByID(Comments comment) {
		
		return commentDao.updateCommentsByID(comment);
	}

	@Override
	public boolean updateCommentsApproved(long cid, int approved) {
		return commentDao.updateCommentsApproved(cid, approved);
	}

	@Override
	public boolean removeCommentsByID(long id) {
		
		return commentDao.removeCommentsByID(id);
	}

	@Override
	public long getcommentCountByID(String commentPostId) {
		
		return commentDao.getcommentCountByID(commentPostId);
	}

	@Override
	public long getcommentCountByApproved() {
		
		return commentDao.getcommentCountByApproved();
	}

	@Override
	public long getcommentCountByPid(long pid) {
		return commentDao.getcommentCountByPid(pid);
	}

	@Override
	public boolean saveComment(Comments comment, long pid) {
		return commentDao.saveComment(comment, pid);
	}


}
