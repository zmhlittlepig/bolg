package service;

import java.util.List;

import bean.Users;

public interface UserService {

    /**
     * 通过id查询用户
     *
     * @param id 用户id
     * @return 用户实体类
     * @description 用来查询用户
     */
    public Users getUserById(long id);

    /**
     * 通过帐号密码查询用户
     *
     * @param login
     * @param passwd
     * @return
     * @description 用来登录
     */
    public Users getUserByLoginAndPass(String login, String passwd);

    /**
     * 通过用户名模糊查询用户。
     *
     * @param name
     * @param page
     * @param limit
     * @return
     * @description 搜索功能
     */
    public List<Users> listUsersByName(String name, int page, int limit);

    /**得到所有用户信息
     * @param page
     * @param limit
     * @return
     */
    public List<Users> listUsers(int page, int limit);

    /**
     * 修改用户基本信息
     *
     * @param user
     * @return
     */
    public boolean updateUser(Users user);


    /**
     * 修改密码
     *
     * @param login
     * @param oldpasswd
     * @param newpasswd
     * @return
     */
    public boolean updateUserPasswd(String login, String oldpasswd, String newpasswd);

    /**
     * 修改用户权限
     * @param status
     * @param id
     * @return
     */
    public boolean updateUserStatus(int status, long id);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    public boolean saveUser(Users user);


    /**
     * 下架用户
     *
     * @param id
     * @return
     * @description 假删除
     */
    public boolean removeUserByID(long id);

    /**
     * 查询所有的用户总数
     */
    public long getuserCount();


    /**
     * 通过用户名模糊查询用户总数。
     *
     * @param name
     * @return
     * @description 搜索功能
     */
    public long getuserCountByName(String name);

    /**
     * 查询用户名是否重复
     *
     * @param name
     * @return
     * @description 搜索功能
     */
    public boolean checkUserName(String name);

    /**
     * 修改用户头像
     *
     * @param name
     * @return
     * @description 搜索功能
     */
    public boolean updateUserPic(String pic, String name);

    /**
     * 通过邮箱修改密码
     * @return
     * @description 搜索功能
     */
    public boolean updatePwdByEmail(String email, String newpassword);

}
