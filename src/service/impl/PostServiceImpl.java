package service.impl;

import java.util.List;
import java.util.Map;

import bean.Posts;
import bean.TermRelationships;
import dao.PostDao;
import dao.TermDao;
import factory.DaoFactory;
import service.PostService;

public class PostServiceImpl implements PostService {

	PostDao postDao = (PostDao)DaoFactory.getInstance("PostDao");

	@Override
	public Posts getPostsByID(long id) {
		return postDao.getPostsByID(id);
	}

	@Override
	public Map<Object, Object> getPostsInfoByID(long id) {
        return postDao.getPostsInfoByID(id);
	}

	@Override
	public Map<Object, Object> getmorePostInfoByID(long id) {
		return postDao.getmorePostInfoByID(id);
	}

	@Override
	public List<Map<Object, Object>> listPostTags(long id) {
		return postDao.listPostTags(id);
	}

	@Override
	public List<Posts> listPosts(int page, int limit) {
		return postDao.listPosts(page, limit);
	}

	@Override
	public List<Map<Object, Object>> listPostsInfo(int page, int limit) {
		return postDao.listPostsInfo(page, limit);
	}

	@Override
	public List<Map<Object, Object>> listPostsInfostage(int page, int limit) {
		return postDao.listPostsInfostage(page, limit);
	}

	@Override
	public List<Posts> listPostsByName(String name, int page, int limit) {
		return postDao.listPostsByName(name, page, limit);
	}

	@Override
	public List<Posts> listPostsByAuthor(String author, int page, int limit) {
		return postDao.listPostsByAuthor(author, page, limit);
	}

	@Override
	public boolean updatePosts(Posts post) {
		return postDao.updatePosts(post);
	}

	@Override
	public long savePosts(Posts post, long termID) {
		return postDao.savePosts(post, termID);
	}

	@Override
	public boolean removePostsByID(long ID) {
		return postDao.removePostsByID(ID);
	}

	@Override
	public long getpostCount() {
		long l = postDao.getpostCount();
		return l;
	}


	@Override
	public List<Map<Object, Object>> SearchPost(String words, int page, int limit) {

		return postDao.listPostsInfostageByword(words, page, limit);
	}

	public long getpostCountByNameAndAuthor(String postTitle, String Author) {

		return postDao.getpostCountByNameAndAuthor(postTitle, Author);
	}

	@Override
	public long getpostCountByTermID(String TermID) {
		return postDao.getpostCountByTermID(TermID);
	}

	@Override
	public List<Map<Object, Object>> listPostsInfostageByTermID(String TermID, int page, int limit) {
		return postDao.listPostsInfostageByTermID(TermID, page, limit);
	}

	@Override
	public List<Posts> listPostsByComment() {
		return postDao.listPostsByComment();
	}

}