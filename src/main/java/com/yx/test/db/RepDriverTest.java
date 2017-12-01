package com.yx.test.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: LiWenC
 * Date: 16-9-12
 */
public class RepDriverTest {
    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.ReplicationDriver";
        String url = "jdbc:mysql:replication://localhost:3306,localhost:3307/test";
        String username = "root";
        String password = "root";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            conn.setReadOnly(false);
            conn.createStatement().executeUpdate("insert into person (name) values ('hello')");
            conn.setReadOnly(true);
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM person");
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
