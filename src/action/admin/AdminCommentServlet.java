package action.admin;

import action.BaseServlet;
import bean.Comments;
import com.alibaba.fastjson.JSON;
import factory.ServiceFactory;
import service.CommentService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-08-04
 * Time: 下午9:33
 */
@WebServlet(name = "AdminCommentServlet", urlPatterns = {"/admincomment"})
public class AdminCommentServlet extends BaseServlet {
    private CommentService commentService = ServiceFactory.getInstance("CommentService");

    public Object getNums(HttpServletRequest request, HttpServletResponse response){
        Long commentNum = commentService.getCommentNum();
        try {
            response.getWriter().write(commentNum.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object getComments(HttpServletRequest request, HttpServletResponse response){
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        List<Comments> comments = commentService.listComments(Integer.parseInt(page), Integer.parseInt(limit));
        String s = JSON.toJSONString(comments);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getCommentsByID(HttpServletRequest request, HttpServletResponse response){
        String cid = request.getParameter("cid");
        Comments comment = commentService.getCommentByID(Long.parseLong(cid));
        String s = JSON.toJSONString(comment);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object changeApproved(HttpServletRequest request, HttpServletResponse response){
        String cid = request.getParameter("cid");
        String approved = request.getParameter("approved");

        boolean b = commentService.updateCommentsApproved(Long.parseLong(cid), Integer.parseInt(approved));
        try {
            if(b)
                response.getWriter().write("success");
            else
                response.getWriter().write("error");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object deleteComment(HttpServletRequest request, HttpServletResponse response){
        String cid = request.getParameter("cid");
        boolean b = commentService.removeCommentsByID(Long.parseLong(cid));
        try {
            if(b)
                response.getWriter().write("success");
            else
                response.getWriter().write("error");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
