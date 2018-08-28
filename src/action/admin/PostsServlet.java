package action.admin;

import action.BaseServlet;
import bean.Posts;
import com.alibaba.fastjson.JSON;
import dao.PostDao;
import factory.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-25
 * Time: 下午8:17
 */
//@WebServlet(name = "PostsServlet", urlPatterns = {"/posts"})
public class PostsServlet extends BaseServlet {
    private PostDao postDao = (PostDao)DaoFactory.getInstance("PostDao");

    public Object getPosts(HttpServletRequest request, HttpServletResponse response){
        Object uri = null;
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        System.out.println(page +",    " + limit);



        List<Map<Object, Object>> maps = postDao.listPostsInfo(Integer.parseInt(page), Integer.parseInt(limit));
        String s = JSON.toJSONString(maps);
        try {
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }


    public Object getPostNums(HttpServletRequest request, HttpServletResponse response){
        Object uri = null;
        Long Nums = postDao.getpostCount();
        //request.setAttribute("nums", Nums);
        try {
            PrintWriter writer = response.getWriter();
            writer.write(Nums.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }



}
