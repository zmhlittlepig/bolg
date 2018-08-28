package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.Terms;
import factory.ServiceFactory;
import service.TermService;

@WebServlet(urlPatterns = {"/Terms"})
public class TermServlet extends BaseServlet {

	private TermService termService = ServiceFactory.getInstance("TermService");
	
	public Object getAllLable(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		try {
			
			List<Terms> list = termService.listTerms(1);
			System.out.println(list);
			String s = JSON.toJSONString(list);
			System.out.println(s);
			response.getWriter().write(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return uri;
	}
	
}
