package com.yx.test.db;

import java.sql.*;

/**
 * 调用存储过程，返回多结果集
 * CREATE PROCEDURE test_procedure_multi_select () BEGIN
 * SELECT * FROM teacher where id = 3;
 * SELECT * FROM student;
 * END;
 */
public class MySqlProduceTest {
    public static void main(String[] args) {
        Connection connection = null;
        CallableStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///test", "root", "root");
            String sql = "{call test_procedure_multi_select()}";
            statement = connection.prepareCall(sql);
            boolean hadResults = statement.execute();
            int i = 0;
            while (hadResults) {
                System.out.println("result No:----" + (++i));
                rs = statement.getResultSet();
                while (rs != null && rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    System.out.println(id + ":" + name);
                }
                hadResults = statement.getMoreResults(); // 检查是否存在更多结果集
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
