package factory;

import aspect.MyAspect;
import exception.InvalidReqException;
import service.PostService;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-26
 * Time: 下午8:14
 */
public class ServiceFactory implements InvocationHandler{
    private static volatile ServiceFactory serviceFactory = null;
    private static Map<Object, Object> map = new HashMap<>();
    private MyAspect myAspect = new MyAspect();

    private static Properties pro = new Properties();

    static {
        try {
            pro.load(DaoFactory.class.getClassLoader().getResourceAsStream("serviceconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServiceFactory() {
    }

    public static ServiceFactory getServiceFactory() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactory();
                }
            }
        }
        return serviceFactory;
    }

    public Object bind(Object target) {
        //obj = target;
        //通过反射机制，创建一个代理类对象实例并返回。用户进行方法调用时使用
        //创建代理对象时，需要传递该业务类的类加载器（用来获取业务实现类的元数据，在包装方法是调用真正的业务方法）、接口、handler实现类
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    /**
     * 包装调用方法：进行预处理、调用后处理
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result=null;
        String name = proxy.getClass().getName();

        myAspect.before(map.get(name).getClass().getName(),method.getName());
        try {
            result = method.invoke(map.get(name), args);
        }catch (Exception e){
            myAspect.DOException(e);
            throw e.getCause();
        }
        myAspect.after();
        return result;
    }


    public static <T> T getInstance(String key) {
        String property = pro.getProperty(key);
        T t = null;
        try {
            Class clazz = Class.forName(property);
            t = (T) clazz.newInstance();
            System.out.println(t);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        Object bind = getServiceFactory().bind(t);
        if(bind != null){
            map.put(bind.getClass().getName(), t);
        }else{
            throw new InvalidReqException(500, "动态代理失败异常");
        }
        return (T)bind;
    }
}
