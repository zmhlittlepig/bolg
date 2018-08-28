package service.impl;

import java.util.List;

import bean.Users;
import dao.UserDao;
import factory.DaoFactory;
import service.UserService;

public class UserServiceImpl implements UserService{
	UserDao userDao = (UserDao)DaoFactory.getInstance("userDao");

	@Override
	public Users getUserById(long id) {
		
		return userDao.getUserById(id);
	}

	@Override
	public Users getUserByLoginAndPass(String login, String passwd) {
		
		return userDao.getUserByLoginAndPass(login, passwd);
	}

	@Override
	public List<Users> listUsersByName(String name, int page, int limit) {
		
		return userDao.listUsersByName(name, page, limit);
	}

	@Override
	public List<Users> listUsers(int page, int limit) {

		return userDao.listUsers(page, limit);
	}

	@Override
	public boolean updateUser(Users user) {
		
		return userDao.updateUser(user);
	}

	@Override
	public boolean updateUserPasswd(String login, String oldpasswd, String newpasswd) {
		
		return userDao.updateUserPasswd(login, oldpasswd, newpasswd);
	}

	@Override
	public boolean updateUserStatus(int status, long id) {

		return userDao.updateUserStatus(status, id);
	}

	@Override
	public boolean saveUser(Users user) {
		
		return userDao.saveUser(user);
	}

	@Override
	public boolean removeUserByID(long id) {
		
		return userDao.removeUserByID(id);
	}

	@Override
	public long getuserCount() {
		
		return userDao.getuserCount();
	}

	@Override
	public long getuserCountByName(String name) {
		
		return userDao.getuserCountByName(name);
	}

	@Override
	public boolean checkUserName(String name) {
		
		return userDao.checkUserName(name);
	}

	@Override
	public boolean updateUserPic(String pic, String name) {
		
		return userDao.updateUserPic(pic, name);
	}

	@Override
	public boolean updatePwdByEmail(String email, String newpassword) {
		// TODO Auto-generated method stub
		return userDao.updatePwdByEmail(email, newpassword);
	}


}
