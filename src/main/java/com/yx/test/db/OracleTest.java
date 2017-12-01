package com.yx.test.db;

import java.sql.*;

public class OracleTest {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "ccb", "ccb");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
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
