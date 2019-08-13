package com.zhou.jstack;

import java.sql.*;
import java.util.Arrays;

public class DeadLockDemo implements Runnable {

    private String sql;

    private boolean isCommit = false;



    private static String driver = "oracle.jdbc.OracleDriver"; //驱动

    private static String url = "jdbc:oracle:thin:@//10.10.108.5:1521/coretest"; //连接字符串

    private static String username = "coretest"; //用户名

    private static String password = "UL13_T5st_db"; //密码

    // 获得连接对象
    private synchronized Connection getConn(){
        //数据库连接对象
        Connection conn = null;
        if(conn == null){
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    //执行语句
    public void executeSql() throws SQLException{
        System.out.println(Thread.currentThread().getName()+" DeadLockDemo.executeSql begin");
        PreparedStatement pstmt;
//        Statement stmt;
        Connection conn = null;
        try {
            conn = getConn();
//            System.out.println(conn);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            int updateCnt = pstmt.executeUpdate();
            System.out.println(Thread.currentThread().getName()+" "+sql);
            pstmt.executeUpdate(sql);
            if (isCommit){
                conn.commit();
            }
            pstmt.close();
            System.out.println(Thread.currentThread().getName()+" DeadLockDemo.executeSql complete ");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("rollback");
        } finally {
            if (isCommit && conn != null) {
                conn.close();
            }
        }
    }

    public DeadLockDemo(String sql,boolean isCommit) {
        this.sql = sql;
        this.isCommit = isCommit;
    }

    @Override
    public void run() {
        //update AA2 t set b='peng' where a='abc'
        try {
            executeSql();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.out.println("DeadLockDemo.main begin");
//        System.out.println(Arrays.asList(args));
        boolean isCommit = false;
        if (args.length>0){
            try {
                isCommit = Boolean.valueOf(args[0]);
            }catch (Exception e){
                isCommit = false;
            }
        }
        DeadLockDemo demo1 = new DeadLockDemo("update AA2 t set b='peng2' where a='abc'",isCommit);
        DeadLockDemo demo2 = new DeadLockDemo("update AA2 t set b='haha' where a='abc'",isCommit);
        Thread thread1 = new Thread(demo1);
        Thread thread2 = new Thread(demo2);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DeadLockDemo.main finish ");
    }
}
