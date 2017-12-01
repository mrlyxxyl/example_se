package com.yx.test.db;

import java.sql.*;

public class OracleFieldExport {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "select * from TENDERBULLETIN where guid = 'ef96909e-6438-4c23-b7fa-02aaed7b7780'";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@112.112.12.76:1521:zloracle", "ljggfwpt_DEP", "zhulong");
            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            String columnName;
            int columnCount;
            String classType;
            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                columnCount = metaData.getColumnCount();
                String str;
                for (int i = 1; i <= columnCount; i++) {
                    columnName = metaData.getColumnName(i);
                    classType = metaData.getColumnClassName(i);
                    System.out.println(columnName + "----------------" + rs.getObject(i));


//                    entityField(columnName, classType);
//                    field(columnName, classType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void entityField(String columnName, String classType) {
        if (classType.equals("oracle.sql.CLOB")) {
            return;
        }
        String str = "<field column=\"" + columnName + "\" name=\"" + columnName + "\"/>";
        System.out.println(str);
    }

    public static void field(String columnName, String classType) {
        String str = "";
        if (classType.equals("oracle.sql.CLOB")) {
            return;
        } else if (classType.equals("java.math.BigDecimal")) {
            str = "<field name=\"" + columnName + "\" type=\"float\" indexed=\"false\" stored=\"true\"/>";
        } else if (classType.equals("java.lang.String")) {
            str = "<field name=\"" + columnName + "\" type=\"text_general\" indexed=\"false\" stored=\"true\"/>";
        } else {
            System.out.println(classType);
        }
        System.out.println(str);
    }

}
