package com.softwareArchitecture;

import java.sql.*;

/**
 * @author zdd
 * @date 2018-11-23 18:12
 */
public class DBTool {

    private String driver = "com.mysql.jdbc.Driver";
    private String dbName = "fenbushi";
    private String userName = "root";
    private String password = "123456";
    private String url = "jdbc:mysql://localhost:3306/" + dbName;
    private PreparedStatement ps;
    private ResultSet rs;
    private Connection conn;
    public ResultSet query()
    {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName,
                    password);
            String querySql = "select id,port,status from tb_test";
            ps = conn.prepareStatement(querySql);
            rs = ps.executeQuery();
/*            while (rs.next()) {
                System.out.println("id : " + rs.getInt(1) + " ip : "
                        + rs.getString(2) + " status : " + rs.getString(3));
            }*/
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return rs;
        }
    }

    public void insertPort(int port, int status)
    {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName,
                    password);
            String sql="insert into tb_test(port,status) values(?,?)";
            ps=conn.prepareStatement(sql);//创建一个Statement对象
            ps.setInt(1,port);//为sql语句第三个问号赋值
            ps.setInt(2,status);//为sql语句的第四个问号赋值
            ps.executeUpdate();//执行sql语句
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
    }

    public void updatePort(int port, int status)
    {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName,
                    password);
            String sql="UPDATE tb_test SET status = ? WHERE port = ? ";
            ps=conn.prepareStatement(sql);//创建一个Statement对象
            ps.setInt(1,status);//为sql语句第三个问号赋值
            ps.setInt(2,port);//为sql语句的第四个问号赋值
            ps.executeUpdate();//执行sql语句
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
    }

    public void close()
    {
        // 关闭记录集
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭声明
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭链接对象
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
