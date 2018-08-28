package action.admin;

import action.BaseServlet;
import com.alibaba.fastjson.JSON;
import exception.InvalidReqException;
import factory.ServiceFactory;
import service.Term2PostsService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-31
 * Time: 下午4:03
 */
@WebServlet(name = "AdminT2PServlet", urlPatterns = {"/t2p"})
public class AdminT2PServlet extends BaseServlet {
    private Term2PostsService t2pService =  ServiceFactory.getInstance("Term2PostsService");

    public Object getTermsByPid(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        List<Map<Object, Object>> maps = t2pService.listTermByPostID(Long.parseLong(pid));
        String s = JSON.toJSONString(maps);
        System.out.println(s);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            new InvalidReqException(500, "标签未查询到");
        }
        return null;
    }
}
