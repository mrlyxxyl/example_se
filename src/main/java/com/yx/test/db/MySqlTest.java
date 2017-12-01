package com.yx.test.db;

import java.sql.*;

public class MySqlTest {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///test", "root", "root");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from person where id = 1");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getBoolean("is_encrypt"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
