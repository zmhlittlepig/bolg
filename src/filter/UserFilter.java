package filter;

import bean.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-15
 * Time: 下午7:54
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {"/admin/*"})
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        if (uri.contains(".js")) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        if (user == null || user.getUserStatus() != 1) {
            request.setAttribute("errmsg", "404 找不到页面");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        } else {
            chain.doFilter(request, response);
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
