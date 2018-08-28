package aspect;

import utils.Log;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-26
 * Time: 下午5:19
 */
public class MyAspect {

    public void before(String className, String methodName){
        Log.info("开启事务 "+className + "--->执行方法： "+methodName);
    }

    public void after(){
        Log.info("事务结束");
    }

    public void DOException(Exception e) throws Exception {
        Log.error("出现异常: "+ e.getMessage());
    }
}
