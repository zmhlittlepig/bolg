package action.admin;

import action.BaseServlet;
import bean.TermTaxonomy;
import bean.Terms;
import com.alibaba.fastjson.JSON;
import exception.InvalidReqException;
import factory.ServiceFactory;
import service.PostService;
import service.TermService;

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
 * Time: 上午9:42
 */
@WebServlet(name = "AdminTermServlet", urlPatterns = {"/adminterm"})
public class AdminTermServlet extends BaseServlet {
    private TermService termService = (TermService) ServiceFactory.getInstance("TermService");

    public Object getAllTerm(HttpServletRequest request, HttpServletResponse response) {
        Object uri = null;
        List<Terms> terms = termService.listTerms(0);
        String s = JSON.toJSONString(terms);
        System.out.println(s);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            throw new InvalidReqException(500, "分类内容未取到");
        }
        return uri;
    }

    public Object getNav(HttpServletRequest request, HttpServletResponse response) {
        Object uri = null;
        List<List<Map<Object, Object>>> lists = termService.listNavTerms();
        String s = JSON.toJSONString(lists);
        System.out.println(s);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            throw new InvalidReqException(500, "获取导航失败");
        }
        return uri;
    }

    public Object addTerm(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String slug = request.getParameter("slug");
        String father = request.getParameter("father");
        String taxonomy = request.getParameter("taxonomy");
        String description = request.getParameter("description");

        Terms term = new Terms();
        term.setName(name);
        term.setSlug(slug);
        term.setTermGroup(0);

        TermTaxonomy termTaxonomy = new TermTaxonomy();
        termTaxonomy.setParent(Long.parseLong(father)); termTaxonomy.setDescription(description); termTaxonomy.setTaxonomy(taxonomy);
        long l = termService.saveTerms(term, termTaxonomy);

        try {
            if(l != 0)
                response.getWriter().write("success");
            else
                response.getWriter().write("error");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object removeTerms(HttpServletRequest request, HttpServletResponse response){
        String termId = request.getParameter("termId");
        boolean b = termService.removeTerms(Long.parseLong(termId));

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

    public Object getTerm(HttpServletRequest request, HttpServletResponse response){
        String tid = request.getParameter("tid");
        Map<Object, Object> terms = termService.getTermsInfoByID(Long.parseLong(tid));
        String s = JSON.toJSONString(terms);

        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object updateTerm(HttpServletRequest request, HttpServletResponse response){
        String tid = request.getParameter("tid");
        String name = request.getParameter("name");
        String slug = request.getParameter("slug");
        String father = request.getParameter("father");
        String taxonomy = request.getParameter("taxonomy");
        String description = request.getParameter("description");

        Terms term = new Terms();
        term.setTermId(Long.parseLong(tid));term.setName(name);term.setSlug(slug);term.setTermGroup(0);

        TermTaxonomy termTaxonomy = new TermTaxonomy();
        termTaxonomy.setTermId(Long.parseLong(tid)); termTaxonomy.setParent(Long.parseLong(father)); termTaxonomy.setDescription(description); termTaxonomy.setTaxonomy(taxonomy);
        boolean b = termService.updateTermInfo(term, termTaxonomy);

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
