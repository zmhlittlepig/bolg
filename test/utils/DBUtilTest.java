package utils;

import bean.Posts;
import bean.Terms;
import com.alibaba.fastjson.JSON;
import dao.PostDao;
import dao.impl.PostDaoImpl;
import factory.DaoFactory;
import factory.ServiceFactory;
import org.junit.Test;
import service.PostService;
import service.TermService;
import service.impl.TermServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-24
 * Time: 上午10:17
 */
public class DBUtilTest {

    @Test
    public void test2() {
        PostService postService = ServiceFactory.getInstance("PostService");
        long l = postService.getpostCountByTermID("4");
        System.out.println(l);
    }


}