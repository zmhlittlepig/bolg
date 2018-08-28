package action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import bean.Posts;
import factory.ServiceFactory;
import service.PostService;


@WebServlet(urlPatterns = {"/Posts"})
public class PostServlet extends BaseServlet {

	private PostService postService = (PostService) ServiceFactory.getInstance("PostService");
	
	public Object GetPostMessageStage(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		System.out.println(postService);
		try {
			
			String currPage = request.getParameter("currPage");
			String pageSize = request.getParameter("pageSize");
			int page = Integer.parseInt(currPage);
			int limit = Integer.parseInt(pageSize);
			
			List<Map<Object, Object>> list = postService.listPostsInfostage(page, limit);
			System.out.println(list);
			String s = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");

			response.getWriter().write(s);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return uri;
	}
	
	
	public Object GetPostByCommentCount(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		
		try {
			
			List<Posts> list = postService.listPostsByComment();
			System.out.println(list);
			String s = JSON.toJSONString(list);
			System.out.println(s);
			response.getWriter().write(s);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return uri;
	}
	
	
	public Object SearchPostBywords(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		
		try {
			
			String words = request.getParameter("words");
			String currPage = request.getParameter("currPage");
			String pageSize = request.getParameter("pageSize");
			int page = Integer.parseInt(currPage);
			int limit = Integer.parseInt(pageSize);
			
			List<Map<Object, Object>> list = postService.SearchPost(words, page, limit);
			System.out.println(list);
			String s = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			
			response.getWriter().write(s);
			
			
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return uri;
	}
	
	
	public Object SearchPostByTermID(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		
		try {
			
			String TermID = request.getParameter("termId");
			String currPage = request.getParameter("currPage");
			String pageSize = request.getParameter("pageSize");
			int page = Integer.parseInt(currPage);
			int limit = Integer.parseInt(pageSize);
			
			List<Map<Object, Object>> list = postService.listPostsInfostageByTermID(TermID, page, limit);
			
			String s = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			
			response.getWriter().write(s);
			
			
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return uri;
	}
	
	
	public Object getpostCount(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		
		try {
			
			Long Count = postService.getpostCount();
			response.getWriter().write(Count.toString());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		return uri;
	}
	
	
	public Object getpostCountByWord(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		
		try {
			
			String words = request.getParameter("words");
			System.out.println("******"+words);
			Long Count = postService.getpostCountByNameAndAuthor(words,words);
			response.getWriter().write( Count.toString());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return uri;
	}
	
	
	public Object getpostCountByTermID(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		
		try {
			
			String TermID = request.getParameter("termId");
			Long Count = postService.getpostCountByTermID(TermID);
			response.getWriter().write( Count.toString());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return uri;
	}
	
	
	public Object getPostsInfo(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		String pid = request.getParameter("pid");
		Map<Object, Object> map = postService.getmorePostInfoByID(Long.parseLong(pid));
		String s = JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss");

		try {
			response.getWriter().write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return uri;
	}

	public Object getPoststags(HttpServletRequest request, HttpServletResponse response) {
		String pid = request.getParameter("pid");
		List<Map<Object, Object>> maps = postService.listPostTags(Long.parseLong(pid));
		String s = JSON.toJSONString(maps);
		try {
			response.getWriter().write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
