package action.admin;

import action.BaseServlet;
import bean.Posts;
import bean.TermTaxonomy;
import bean.Terms;
import bean.Users;
import com.alibaba.fastjson.JSON;
import exception.InvalidReqException;
import factory.ServiceFactory;
import service.PostService;
import service.Term2PostsService;
import service.TermService;

import java.io.File;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileFilter;
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
@WebServlet(name = "AdminPostsServlet", urlPatterns = {"/adminposts"})
public class AdminPostsServlet extends BaseServlet {
    private PostService postService = (PostService) ServiceFactory.getInstance("PostService");

    public Object getPosts(HttpServletRequest request, HttpServletResponse response) {

        Object uri = null;
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        System.out.println(page + ",    " + limit);

        List<Map<Object, Object>> maps = postService.listPostsInfo(Integer.parseInt(page), Integer.parseInt(limit));
        //String s = JSON.toJSONString(maps);
        String s = JSON.toJSONStringWithDateFormat(maps, "yyyy-MM-dd HH:mm:ss");
        System.out.println(s);
        try {
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } catch (IOException e) {
            throw new InvalidReqException(500, "数据未取到, 请稍候重试！");
        }
        return uri;
    }


    public Object getPostNums(HttpServletRequest request, HttpServletResponse response) {
        Object uri = null;
        Long Nums = postService.getpostCount();
        //request.setAttribute("nums", Nums);
        try {
            PrintWriter writer = response.getWriter();
            writer.write(Nums.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidReqException(500, "数据未取到, 请稍候重试！");
        }
        return uri;
    }

    public Object removePosts(HttpServletRequest request, HttpServletResponse response) {
        Object uri = null;
        try {
            String pid = request.getParameter("pid");
            System.out.println(pid);
            Boolean b = postService.removePostsByID(Integer.parseInt(pid));
            response.getWriter().write(b.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidReqException(500, "系统错误");
        }
        return uri;
    }

    public Object modifyPost(HttpServletRequest request, HttpServletResponse response) {
        Object uri = null;
        String pid = request.getParameter("postid");
        System.out.println(pid);
        Map<Object, Object> post = postService.getPostsInfoByID(Long.parseLong(pid));

        String s = JSON.toJSONStringWithDateFormat(post, "yyyy-MM-dd HH:mm:ss");

        System.out.println(s);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidReqException(500, "获取数据失败!");
        }
        return uri;
    }

    public Object addPost(HttpServletRequest request, HttpServletResponse response) {
        TermService termService = ServiceFactory.getInstance("TermService");
        Term2PostsService t2pService = ServiceFactory.getInstance("Term2PostsService");

        String title = request.getParameter("title");
        String context = request.getParameter("context");
        String termID = request.getParameter("termID");
        String tags = request.getParameter("tags");
        String titlepic = request.getParameter("titlepic");
        String description = request.getParameter("description");
        Users user = (Users) request.getSession().getAttribute("user");
        System.out.println(user.getId());
        //post ptR   terms termTaxg
        Posts post = new Posts();
        post.setPostTitle(title);
        post.setPostContent(context);
        post.setPostAuthor(user.getId());
        post.setPostDate(new Date());
        post.setPostExcerpt(description);
        post.setPostPic(titlepic);
        System.out.println(post);
        System.out.println(termID);
//        long ptid = termService.getTermTaxonomyParentBytid(Long.parseLong(termID));

        long l = postService.savePosts(post, Long.parseLong(termID));
//        System.out.println("pid     - --- > " + ptid);
//        if (ptid != -1) {
//            t2pService.saveTermRelationships(l, ptid);
//        }
        long l1 = 0;
        String[] split = tags.split(",");
        for (String s : split) {
            s = s.trim();
            if(s.length() == 0) break;
            Terms t = termService.getTermByName(s, 1);
            if (t == null) {
                t = new Terms();
                t.setName(s);
                t.setTermGroup(1);
                TermTaxonomy termTaxonomy = new TermTaxonomy();
                termTaxonomy.setCount(1);
                termTaxonomy.setParent(-1);
                long ll = termService.saveTerms(t, termTaxonomy);
                t.setTermId(ll);
            }
            t2pService.saveTermRelationships(l, t.getTermId());
        }


        try {
            if (l != -1)
                response.getWriter().write("success");
            else
                response.getWriter().write("error");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object updatePost(HttpServletRequest request, HttpServletResponse response) {
        try {
            TermService termService = ServiceFactory.getInstance("TermService");
            Term2PostsService t2pService = ServiceFactory.getInstance("Term2PostsService");
            String postID = request.getParameter("postID");
            String title = request.getParameter("title");
            String context = request.getParameter("context");
            String termID = request.getParameter("termID");
            String oldtermID = request.getParameter("oldtermID");
            String tags = request.getParameter("tags");
            String titlepic = request.getParameter("titlepic");
            String description = request.getParameter("description");
            Users user = (Users) request.getSession().getAttribute("user");
            System.out.println(user.getId());
            //post ptR   terms termTaxg
            Posts post = new Posts();
            post.setID(Long.parseLong(postID));
            post.setPostTitle(title);
            post.setPostContent(context);
            post.setPostAuthor(user.getId());
            post.setPostModified(new Date());
            post.setPostExcerpt(description);
            post.setPostPic(titlepic);
            System.out.println(post);
            System.out.println(termID);
            long tid = Long.parseLong(termID);
            List<Map<Object, Object>> maps = t2pService.listTermByPostID(post.getID());
            for (Map<Object, Object> m : maps) {
                Long termId = (Long) m.get("termId");
                t2pService.removeTags(post.getID(), termId);
            }
            String[] split = tags.split(",");
            for (String s : split) {
                s = s.trim();
                if(s.length() == 0) break;
                Terms t = termService.getTermByName(s, 1);
                if (t == null) {
                    t = new Terms();
                    t.setName(s);
                    t.setTermGroup(1);
                    TermTaxonomy termTaxonomy = new TermTaxonomy();
                    termTaxonomy.setCount(1);
                    termTaxonomy.setParent(-1);
                    long l = termService.saveTerms(t, termTaxonomy);
                    t2pService.saveTermRelationships(post.getID(), l);
                }else{
                    if (!t2pService.getT2P(post.getID(), t.getTermId())) {
                        t2pService.saveTermRelationships(post.getID(), t.getTermId());
                    }
                }

            }

            if (!t2pService.getT2P(post.getID(), tid)) {
                t2pService.updateTermRelationships(post.getID(), tid, Long.parseLong(oldtermID));
//                long ptid = termService.getTermTaxonomyParentBytid(tid);
//                if (ptid != -1) {
//                    t2pService.saveTermRelationships(post.getID(), ptid);
//                }
            }
            Boolean b = postService.updatePosts(post);
            if(b)
                response.getWriter().write("success");
            else
                response.getWriter().write("error");
        } catch (Exception e) {
            throw new InvalidReqException(500, "修改文章失败异常: "+e.getMessage());
        }
        return null;
    }


}
