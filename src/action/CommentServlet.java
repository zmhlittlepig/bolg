package action;

import bean.Comments;
import com.alibaba.fastjson.JSON;
import factory.ServiceFactory;
import service.CommentService;
import service.PostService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-08-06
 * Time: 下午4:14
 */
@WebServlet(urlPatterns = {"/comments"})
public class CommentServlet extends BaseServlet{
    private CommentService commentService = ServiceFactory.getInstance("CommentService");

    public Object getComments(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");

        List<Map<Object, Object>> comments = commentService.listCommentsByPid(Long.parseLong(pid), Integer.parseInt(page), Integer.parseInt(limit));
        String s = JSON.toJSONStringWithDateFormat(comments, "yyyy:MM:dd HH:mm:ss");
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getCommentsNum(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        Long commentNum = commentService.getcommentCountByPid(Long.parseLong(pid));
        try {
            response.getWriter().write(commentNum.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object saveComment(HttpServletRequest requset, HttpServletResponse response){

        String pid = requset.getParameter("postid");
        String uid = requset.getParameter("uid");
        String uname = requset.getParameter("uname");
        String email = requset.getParameter("email");
        String comment = requset.getParameter("comment");
        String parentid = requset.getParameter("parentid");

        Comments comments = new Comments();
        comments.setUserId(Long.parseLong(uid)); comments.setCommentAuthor(uname); comments.setCommentAuthorEmail(email);
        comments.setCommentContent(comment); comments.setCommentParent(Long.parseLong(parentid));comments.setCommentPostId(Long.parseLong(pid));
        comments.setCommentDate(new Date());
        System.out.println(comments);
        boolean b = commentService.saveComment(comments, Long.parseLong(pid));

            try {
                if(b) {
                    response.getWriter().write("评论成功发出,等待管理员审核");
                }else {
                    response.getWriter().write("评论发送失败, 请重试.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
    }

}
