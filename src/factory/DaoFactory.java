package factory;

import aspect.MyAspect;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-24
 * Time: 下午7:14
 */
public class DaoFactory{
    private static volatile DaoFactory daoFactory = null;
    private static Properties pro = new Properties();

    static {
        try {
            pro.load(DaoFactory.class.getClassLoader().getResourceAsStream("daoconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DaoFactory() {
    }

    public static DaoFactory getDaoFactory() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new DaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public static <T> T getInstance(String key) {
        String property = pro.getProperty(key);
        T t = null;
        try {
            Class clazz = Class.forName(property);
            t = (T) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return t;
    }
}
