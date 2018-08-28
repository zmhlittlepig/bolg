package utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: panyiwen
 * Date: 2018-07-24
 * Time: 上午10:12
 */

public class DBUtil {
    private static DBUtil instance = new DBUtil();
    private static DataSource ds = new ComboPooledDataSource();
    /**
     * 为空表示未开启事务。不为空表示开启事务。
     */
    private static ThreadLocal<Connection> TransactionConn = new ThreadLocal<>();


    public static void startTransaction() {
        Connection conn = TransactionConn.get();
        if (conn != null) {
            return;
        }

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            TransactionConn.set(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void commitSession() {
        Connection conn = TransactionConn.get();

        if (conn == null) return;

        try {
            conn.commit();

            conn.close();
            conn = null;
            TransactionConn.remove();
        } catch (SQLException e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     *
     * @throws SQLException
     */
    public static void rollbackTransaction() {
        Connection con = TransactionConn.get();//获取当前线程的事务连接
        if (con == null) return;
        try {
            con.rollback();

            con.close();
            con = null;
            TransactionConn.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放Connection
     *
     * @param connection
     * @throws SQLException
     */
    public static void releaseConnection(Connection connection) {
        Connection con = TransactionConn.get();//获取当前线程的事务连接
        if (connection != con) {//如果参数连接，与当前事务连接不同，说明这个连接不是当前事务，可以关闭！
            try {
                if (connection != null && !connection.isClosed()) {//如果参数连接没有关闭，关闭之！
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private DBUtil() {

    }

    public static DBUtil getInstance() {
        return instance;
    }

    /**
     * 对外提供了获取连接的方法
     */
    public static Connection getConnection() {
        Connection conn = TransactionConn.get();
        try {
            if(conn == null)
                conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * 封装数据库表的增、删、改操作
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static boolean update(String sql, Object... params) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        boolean flag = false;
        toLog(sql, params);
        try {
            ps = conn.prepareStatement(sql);
            //判断参数是否为空
            if (params != null && params.length > 0) {
                ps.clearParameters();//清除当前的参数
                for (int i = 0; i < params.length; i++) {
                    ps.setObject((i + 1), params[i]);
                }
            }
            int result = ps.executeUpdate();
            flag = result > 0 ? true : false;
        } catch (SQLException e) {
            if(TransactionConn.get() != null){
                rollbackTransaction();
            }
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return flag;
    }


    /**
     * 获取单个对象 可用于登录注册验证
     */
    public static <T> T findBySingleObject(String sql, Class<T> cls, Object... params) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        T singleObject = null;
        toLog(sql, params);
        try {
            ps = conn.prepareStatement(sql);

            if (params != null && params.length > 0) {
                ps.clearParameters();//清除当前的参数
                for (int i = 0; i < params.length; i++) {
                    ps.setObject((i + 1), params[i]);
                }
            }
            rs = ps.executeQuery();
            //获取元数据对象
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取字段的个数
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                singleObject = cls.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);//根据索引得到字段的名字
                    Object columnValue = rs.getObject(columnName);//获取字段对应的值
                    Field field = cls.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(singleObject, columnValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(TransactionConn.get() != null){
                rollbackTransaction();
            }
        } finally {
            close(conn, ps);
        }
        return singleObject;

    }

    /**
     * 列表查询
     */
    public static <T> List<T> queryListExecute(String sql, Class<T> cls, Object... params) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        T singleObject = null;
        List<T> list = new ArrayList<T>();
        toLog(sql, params);
        try {
            ps = conn.prepareStatement(sql);

            if (params != null && params.length > 0) {
                ps.clearParameters();
                for (int i = 0; i < params.length; i++) {
                    ps.setObject((i + 1), params[i]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                singleObject = cls.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object columdValue = rs.getObject(columnName);
                    Field field = cls.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(singleObject, columdValue);
                }
                list.add(singleObject);
            }
        } catch (Exception e) {
            if(TransactionConn.get() != null){
                rollbackTransaction();
            }
            e.printStackTrace();
        }finally {
            close(conn, ps);
        }
        return list;
    }


    /**
     * 列表查询返回map
     */
    public static List<Map<Object, Object>> queryListMap(String sql, Object... params) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Map<Object, Object>> list = new ArrayList<>();
        toLog(sql, params);
        try {
            ps = conn.prepareStatement(sql);

            if (params != null && params.length > 0) {
                ps.clearParameters();
                for (int i = 0; i < params.length; i++) {
                    ps.setObject((i + 1), params[i]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Map<Object, Object> map = null;
            while (rs.next()) {
                map = new HashMap<>();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object columdValue = rs.getObject(columnName);
                    map.put(columnName, columdValue);

                }
                list.add(map);
            }
        } catch (SQLException e) {
            if(TransactionConn.get() != null){
                rollbackTransaction();
            }
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return list;
    }

    public static long queryNums(String sql, Object... params) {
        long num = 0;
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        toLog(sql, params);
        try {
            ps = conn.prepareStatement(sql);

            if (params != null && params.length > 0) {
                ps.clearParameters();//清除当前的参数
                for (int i = 0; i < params.length; i++) {
                    ps.setObject((i + 1), params[i]);
                }
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                num = rs.getLong(1);
            }
        } catch (SQLException e) {
            if(TransactionConn.get() != null){
                rollbackTransaction();
            }
            e.printStackTrace();
        }finally {
            close(conn, ps);
        }
        return num;
    }


    /**
     * 释放资源
     */
    public static void close(Connection conn, PreparedStatement pst) {
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            releaseConnection(conn);
        }
    }

    /**
     * close方法进行重载
     *
     * @param conn
     * @param pst
     * @param rs
     */
    public static void close(Connection conn, PreparedStatement pst,
                             ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            releaseConnection(conn);
        }
    }

    public static void toLog(String sql, Object... parms){
        sql += " ---> ";
        for (Object o : parms) {
            sql += " "+o+ " ";
        }
        Log.info(sql);
    }

}