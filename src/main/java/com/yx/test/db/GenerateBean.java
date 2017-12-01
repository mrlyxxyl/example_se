package com.yx.test.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据mysql数据库直接生成Bean.java文件
 * User: LiWenC
 * Date: 17-5-10
 */
public class GenerateBean {
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet rs = null;
    static String genPath = "D:/bean/";
    static String oneEnter = "\r\n";
    static String twoEnter = "\r\n\r\n";
    static String oneTabStr = "\t";
    static String twoTabStr = "\t\t";


    static {
        try {
            File file = new File(genPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///test", "root", "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateBean(String dbName) throws SQLException, IOException {
        List<String> tables = getTables(dbName);

        if (tables.size() > 0) {
            String sql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT FROM information_schema.`COLUMNS` WHERE TABLE_NAME = '%s'";
            String columnName;
            String columnClassName;
            String columnComment;
            String fileName;
            FileWriter fileWriter;
            String columnField;
            String columnClass;
            String attributeType = "";
            for (String table : tables) {
                fileName = camelFormat(table, true);


                fileWriter = new FileWriter(genPath + fileName + ".java");

                rs = statement.executeQuery("SELECT COUNT(COLUMN_TYPE) AS c FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA = '" + dbName + "' AND TABLE_NAME = '" + table + "' AND DATA_TYPE = 'decimal'");
                if (rs.first()) {
                    if (rs.getInt("c") > 0) {
                        fileWriter.write("import java.math.BigDecimal;" + twoEnter);
                    }
                }
                fileWriter.write("public class " + fileName + " {" + twoEnter);

                statement = connection.createStatement();
                rs = statement.executeQuery(String.format(sql, table));
                while (rs.next()) {
                    columnName = camelFormat(rs.getString("COLUMN_NAME").toLowerCase(), false);
                    columnClassName = rs.getString("DATA_TYPE");
                    columnComment = rs.getString("COLUMN_COMMENT");

                    if ("varchar".equals(columnClassName) || "mediumtext".equals(columnClassName)) {
                        attributeType = "String";
                    } else if ("int".equals(columnClassName) || "bigint".equals(columnClassName)) {
                        attributeType = "int";
                    } else if ("decimal".equals(columnClassName)) {
                        attributeType = "BigDecimal";
                    }
                    fileWriter.write(oneTabStr + "/**" + oneEnter);
                    fileWriter.write(oneTabStr + "* " + columnComment + oneEnter);
                    fileWriter.write(oneTabStr + "*/" + oneEnter);
                    fileWriter.write(oneTabStr + "private " + attributeType + " " + columnName + ";" + twoEnter);
                }

                rs = statement.executeQuery(String.format(sql, table));

                while (rs.next()) {
                    columnName = rs.getString("COLUMN_NAME").toLowerCase();
                    columnClassName = rs.getString("DATA_TYPE");
                    columnField = camelFormat(columnName, false);
                    columnClass = camelFormat(columnName, true);

                    if ("varchar".equals(columnClassName) || "mediumtext".equals(columnClassName)) {
                        attributeType = "String";
                    } else if ("int".equals(columnClassName) || "bigint".equals(columnClassName)) {
                        attributeType = "int";
                    } else if ("decimal".equals(columnClassName)) {
                        attributeType = "BigDecimal";
                    }

                    fileWriter.write(oneTabStr + "public void set" + columnClass + "(" + attributeType + " " + columnField + ") {" + oneEnter);
                    fileWriter.write(twoTabStr + "this." + columnField + " = " + columnField + ";" + oneEnter + "    }" + twoEnter);
                    fileWriter.write(oneTabStr + "public " + attributeType + " get" + columnClass + "() {" + oneEnter);
                    fileWriter.write(twoTabStr + "return this." + columnField + ";" + oneEnter + "    }" + twoEnter);
                }
                fileWriter.write("}");
                fileWriter.close();
            }
        }
    }


    /**
     * 获取表名
     *
     * @param dbName
     * @return
     * @throws SQLException
     */
    public static List<String> getTables(String dbName) throws SQLException {
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA. TABLES WHERE TABLE_SCHEMA = '" + dbName + "'";
        statement = connection.createStatement();
        rs = statement.executeQuery(sql);
        List<String> tables = new ArrayList<String>();
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String firstToUpperCase(String str) {
        String firstLetter = str.charAt(0) + "";
        str = firstLetter.toUpperCase() + str.substring(1);
        return str;
    }

    /**
     * 驼峰标识
     *
     * @param resource
     * @param isClass  是否为类名
     * @return
     */
    public static String camelFormat(String resource, boolean isClass) {
        if (resource != null && resource.trim().length() > 0) {
            String[] strings = resource.split("_");
            if (strings.length > 1) {
                StringBuffer sb = new StringBuffer();
                if (isClass) {
                    sb.append(firstToUpperCase(strings[0]));
                } else {
                    sb.append(strings[0]);
                }
                for (int i = 1; i < strings.length; i++) {
                    sb.append(firstToUpperCase(strings[i]));
                }
                return sb.toString();
            } else {
                if (isClass) {
                    return firstToUpperCase(strings[0]);
                } else {
                    return strings[0];
                }
            }
        }
        return "";
    }

    public static void main(String[] args) throws SQLException, IOException {
        generateBean("test");
    }
}
