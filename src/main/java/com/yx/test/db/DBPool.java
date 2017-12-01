package com.yx.test.db;

import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

/**
 * @Description:编写数据库连接池
 */
public class DBPool {

    private static LinkedList<Connection> listConnections = new LinkedList<Connection>();

    static {
        InputStream in = DBPool.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            int initPoolSize = Integer.parseInt(prop.getProperty("initPoolSize"));
            Class.forName(driver);
            for (int i = 0; i < initPoolSize; i++) {
                Connection conn = DriverManager.getConnection(url, username, password);
                listConnections.add(conn);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (listConnections.size() > 0) {
            return listConnections.removeFirst();
        } else {
            throw new RuntimeException("对不起，数据库忙");
        }
    }

    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
                listConnections.addLast(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            DBPool pool = new DBPool();
            Connection connection = pool.getConnection();
            release(connection, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}