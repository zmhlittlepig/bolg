package dao.impl;

import java.sql.SQLException;
import java.util.List;

import bean.Users;
import dao.UserDao;
import utils.DBUtil;

public class UserDaoImpl implements UserDao {


    @Override
    public Users getUserById(long id) {

        String sql = "select ID, userLogin, userNicename, userEmail, userRegistered, userStatus, displayName,pic from users where id = ?";

        return DBUtil.findBySingleObject(sql, Users.class, id);

    }


    @Override
    public Users getUserByLoginAndPass(String login, String passwd) {

        String sql = "select ID, userLogin, userNicename, userEmail, userRegistered, userStatus, displayName,pic from users where userLogin = ? and userPass = ?";

        return DBUtil.findBySingleObject(sql, Users.class, login, passwd);

    }

    @Override
    public List<Users> listUsers(int page, int limit) {
        int start = (page-1)*limit;
        String sql = "select ID, userLogin, userNicename, userEmail, userRegistered, userStatus, displayName,pic from users limit ?, ?";
        List<Users> users = DBUtil.queryListExecute(sql, Users.class, start, limit);
        return users;
    }


    @Override
    public List<Users> listUsersByName(String name, int page, int limit) {

        String sql = "select ID, userLogin, userNicename, userEmail, userRegistered, userStatus, displayName,pic from users where userLogin like ? limit ?,?";

        return DBUtil.queryListExecute(sql, Users.class, "%" + name + "%", (page - 1) * limit, limit);

    }


    @Override
    public boolean updateUser(Users user) {

        String sql = "update users set userNicename=?, userEmail=?,"
                + "displayName=? where userLogin = ?";

        return DBUtil.update(sql, user.getUserNicename(), user.getUserEmail(),
                user.getDisplayName(), user.getUserLogin());
    }


    @Override
    public boolean updateUserPasswd(String login, String oldpasswd, String newpasswd) {

        String sql = "update users set userPass = ? where userLogin = ? and userPass = ?";


        return DBUtil.update(sql, newpasswd, login, oldpasswd);

    }

    @Override
    public boolean updateUserStatus(int status, long id) {
        String sql = "update users set userStatus = ? where ID = ?";
        return DBUtil.update(sql, status, id);
    }


    @Override
    public boolean saveUser(Users user) {

        String sql = "insert into users(userLogin, userPass, userNicename, userEmail, userRegistered, displayName) values(?, ?, ?, ?, ?, ?)";

        return DBUtil.update(sql, user.getUserLogin(), user.getUserPass(), user.getUserNicename(),
                user.getUserEmail(), user.getUserRegistered(), user.getDisplayName());
    }


    @Override
    public boolean removeUserByID(long id) {

        String sql = "update users set userStatus = ? where ID = ?";

        return DBUtil.update(sql, -1, id);
    }


    @Override
    public long getuserCount() {

        String sql = "select count(ID) from users";
        return DBUtil.queryNums(sql);

    }


    @Override
    public long getuserCountByName(String name) {

        String sql = "select count(ID) from users where userLogin like ?";

        return DBUtil.queryNums(sql, "%" + name + "%");

    }

    @Override
    public boolean checkUserName(String name) {

        String sql = "select count(ID) from users where userLogin = ?";

        return DBUtil.queryNums(sql, name) > 0;
    }

    @Override
    public boolean updateUserPic(String pic, String name) {

        String sql = "update users set pic = ? where userLogin = ?";

        return DBUtil.update(sql, pic, name);
    }

    @Override
    public boolean updatePwdByEmail(String email, String newpassword) {

        String sql = "update users set userPass = ? where userEmail = ?";

        return DBUtil.update(sql, newpassword, email);

    }

}
