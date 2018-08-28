package filter;

import exception.InvalidReqException;
import utils.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-26
 * Time: 下午9:04
 */
@WebFilter(filterName = "ExceptionFiler",
        urlPatterns = {"/*"},
        initParams = {@WebInitParam(name= "errorPage", value="/index.html")}
        )
public class ExceptionFiler implements Filter {
    private String errorPage;//跳转的错误信息页面

    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        //捕获你抛出的业务异常
        try {
            chain.doFilter(req, res);
        } catch (RuntimeException e) {
            if(e instanceof InvalidReqException){//如果是你定义的业务异常
                //request.setAttribute("BsException", e);//
                String errmsg = ((InvalidReqException) e).getUserErrMsg() +"\n 异常代码： "+ ((InvalidReqException) e).getErrorCode();
                Log.error("发生异常： " + errmsg);
                request.setAttribute("errmsg", errmsg);
                request.getRequestDispatcher(errorPage).forward(request, response);//跳转到信息提示页面！！
            }
            e.printStackTrace();
        }
    }
    //初始化读取你配置的提示页面路径
    public void init(FilterConfig config) throws ServletException {
        //读取错误信息提示页面路径
        errorPage = config.getInitParameter("errorPage");
        if(null==errorPage || "".equals(errorPage)){
            throw new RuntimeException("没有配置错误信息跳转页面");
            //System.out.println("没有配置错误信息跳转页面");
        }
    }

}
