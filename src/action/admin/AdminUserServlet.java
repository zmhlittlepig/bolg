package action.admin;

import action.BaseServlet;
import bean.Posts;
import bean.TermTaxonomy;
import bean.Terms;
import bean.Users;
import com.alibaba.fastjson.JSON;
import com.sun.corba.se.spi.ior.ObjectKey;
import exception.InvalidReqException;
import factory.ServiceFactory;
import service.PostService;
import service.Term2PostsService;
import service.TermService;
import service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-25
 * Time: 下午8:17
 */
@WebServlet(name = "AdminUserServlet", urlPatterns = {"/adminusers"})
public class AdminUserServlet extends BaseServlet {
    private UserService userService = ServiceFactory.getInstance("UserService");

    public Object getUserInfoByID(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid");
        Users user = userService.getUserById(Long.parseLong(uid));
        String s = JSON.toJSONString(user);
        System.out.println(s);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            throw new InvalidReqException(500, "获取用户信息失败!");
        }

        return null;
    }

    public Object getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Users user = (Users) request.getSession().getAttribute("user");
            System.out.println(user);
            if (user.getUserStatus() != 1) {
                throw new InvalidReqException(500, "没有权限");
            }
            String s = JSON.toJSONString(user);
            System.out.println(s);
            response.getWriter().write(s);
        } catch (IOException e) {
            throw new InvalidReqException(500, "获取用户失败" + e.getMessage());
        }
        return null;
    }

    public Object getUsers(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");

        List<Users> users = userService.listUsers(Integer.parseInt(page), Integer.parseInt(limit));
        String s = JSON.toJSONString(users);
        System.out.println(s);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getUserNums(HttpServletRequest request, HttpServletResponse response) {
        Long l = userService.getuserCount();
        try {
            response.getWriter().write(l.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object updateUserStatus(HttpServletRequest request, HttpServletResponse response) {
        String status = request.getParameter("status");
        String uid = request.getParameter("uid");

        boolean b = userService.updateUserStatus(Integer.parseInt(status), Long.parseLong(uid));
        try {
            if (b) {
                response.getWriter().write("修改成功");
            } else {
                response.getWriter().write("修改失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object addUser(HttpServletRequest request, HttpServletResponse response) {
        String uid = request.getParameter("uid");
        String uname = request.getParameter("uname");
        String uemail = request.getParameter("uemail");
        String upass = request.getParameter("upass");

        Users users = new Users();
        users.setUserLogin(uid);
        users.setUserNicename(uname);
        users.setUserEmail(uemail);
        users.setUserPass(upass);
        users.setUserRegistered(new Date());

        System.out.println(users);

        boolean b = userService.saveUser(users);

        try {
            if (b) {
                response.getWriter().write("success");
            }else {
                response.getWriter().write("error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Object updateUserInfo(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String uid = request.getParameter("uid");
        String uname = request.getParameter("uname");
        String uemail = request.getParameter("uemail");
        String utext = request.getParameter("text");

        Users users = new Users();
        users.setId(Long.parseLong(id));
        users.setUserLogin(uid);
        users.setUserNicename(uname);
        users.setUserEmail(uemail);
        users.setDisplayName(utext);

        System.out.println(users);

        boolean b = userService.updateUser(users);

        try {
            if (b) {
                response.getWriter().write("success");
            }else {
                response.getWriter().write("error");
            }
        } catch (IOException e) {
            throw new InvalidReqException(500, "修改用户失败");
        }

        return null;
    }


}
