package action;

import exception.InvalidReqException;
import utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-24
 * Time: 下午3:44
 */
public class BaseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // (保存跳转的资源)  方法返回值
        Object returnValue = null;
        // 1、根据请求获取请求的方法;  【约定 >  操作类型的值，必须对应servlet中的方法名称】
        String methodName=request.getParameter("method");

        try {
            // 2、通过反射获得当前运行类中指定方法,形式参数
            Method m=this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
            // 3. 执行方法
            returnValue=m.invoke(this, request, response);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            returnValue = "/error.jsp";
            e.printStackTrace();
            throw new InvalidReqException(404, "error");
        }
        //4、跳转页面
        if(returnValue != null)
            WebUtils.goTo(request, response, returnValue);
    }
}
