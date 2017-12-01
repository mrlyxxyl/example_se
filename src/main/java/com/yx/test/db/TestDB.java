package com.yx.test.db;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDB {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "select t.id,t.name,t.age,t.sex,t.height,t.birthday,t.weight,t.score,t.btime,t.bchar from people t";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "yuexin", "yuexin");
            genDaoFromDB("PEOPLE", con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    public static List getBeanFromDB(Class clazz, ResultSet rs) throws IntrospectionException, SQLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (rs == null) {
            throw new RuntimeException("no result!");
        }
        List list = new ArrayList();
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        String columnName;
        while (rs.next()) {
            Object obj = clazz.newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnName = metaData.getColumnName(i);
                String jp, sp;
                for (PropertyDescriptor pd : pds) {
                    jp = pd.getPropertyType().getName();
                    sp = metaData.getColumnClassName(i);
                    if (pd.getName().equalsIgnoreCase(columnName)) {
                        if (jp.equals(sp) || sp.equals("java.sql.Timestamp")) {
                            pd.getWriteMethod().invoke(obj, rs.getObject(pd.getName()));
                        } else if (sp.equals("java.math.BigDecimal")) {
                            if (pd.getPropertyType().getName().equals("int")) {
                                pd.getWriteMethod().invoke(obj, rs.getInt(pd.getName()));
                            } else if (pd.getPropertyType().getName().equals("double")) {
                                pd.getWriteMethod().invoke(obj, rs.getDouble(pd.getName()));
                            } else if (pd.getPropertyType().getName().equals("long")) {
                                pd.getWriteMethod().invoke(obj, rs.getLong(pd.getName()));
                            }
                        }
                        break;
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }


    public static void genDaoFromDB(String tableName, Connection conn) throws ClassNotFoundException, SQLException, IOException {
        PreparedStatement pstmt;
        ResultSet rs;
        String destFilePath = "commontest/src/model/";
        String enterStr = "\r\n";
        File destFile = new File(destFilePath);
        if (!destFile.exists()) {
            destFile.mkdirs();
        }
        FileWriter fw = new FileWriter(destFile.getAbsolutePath() + "/" + tableName.toUpperCase() + ".java");
        fw.write("package model;" + enterStr + "import java.util.Date;" + enterStr + enterStr);
        fw.write("public class " + tableName.toUpperCase() + " {" + enterStr + enterStr);

        String colsql = "select t.COLUMN_NAME,t.DATA_TYPE,t.DATA_SCALE from user_tab_columns t where table_name='" + tableName.toUpperCase() + "'";
        pstmt = conn.prepareStatement(colsql);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME").toLowerCase();
            String columnClassName = rs.getString("DATA_TYPE");
            int dataScale = rs.getInt("DATA_SCALE");

            if (columnClassName.equals("CHAR") || columnClassName.equals("VARCHAR2")) {
                fw.write("    private String " + columnName + ";" + enterStr);
            } else if (columnClassName.equals("NUMBER")) {
                if (dataScale == 0) {
                    fw.write("    private int " + columnName + ";" + enterStr);
                } else if (dataScale > 0) {
                    fw.write("    private double " + columnName + ";" + enterStr);
                }
            } else if (columnClassName.equals("DATE")) {
                fw.write("    private Date " + columnName + ";" + enterStr);
            }
        }

        fw.write(enterStr);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME").toLowerCase();
            String columnClassName = rs.getString("DATA_TYPE");
            int dataScale = rs.getInt("DATA_SCALE");

            if (columnClassName.equals("CHAR") || columnClassName.equals("VARCHAR2")) {
                fw.write("    public void set" + firstToUpperCase(columnName) + "(String " + columnName + ") {" + enterStr);
                fw.write("        this." + columnName + " = " + columnName + ";" + enterStr + "    }" + enterStr);
                fw.write("    public String get" + firstToUpperCase(columnName) + "() {" + enterStr);
                fw.write("        return " + columnName + ";" + enterStr + "    }" + enterStr + enterStr);
            } else if (columnClassName.equals("NUMBER")) {
                if (dataScale == 0) {
                    fw.write("    public void set" + firstToUpperCase(columnName) + "(int " + columnName + ") {" + enterStr);
                    fw.write("        this." + columnName + " = " + columnName + ";" + enterStr + "    }" + enterStr);
                    fw.write("    public int get" + firstToUpperCase(columnName) + "() {" + enterStr);
                    fw.write("        return " + columnName + ";" + enterStr + "    }" + enterStr);
                } else if (dataScale > 0) {
                    fw.write("    public void set" + firstToUpperCase(columnName) + "(double " + columnName + ") {" + enterStr);
                    fw.write("        this." + columnName + " = " + columnName + ";" + enterStr + "    }" + enterStr + enterStr);
                    fw.write("    public double get" + firstToUpperCase(columnName) + "() {" + enterStr);
                    fw.write("        return " + columnName + ";" + enterStr + "    }" + enterStr + enterStr);
                }
            } else if (columnClassName.equals("DATE")) {
                fw.write("    public void set" + firstToUpperCase(columnName) + "(Date " + columnName + ") {" + enterStr);
                fw.write("        this." + columnName + " = " + columnName + ";" + enterStr + "    }" + enterStr + enterStr);
                fw.write("    public Date get" + firstToUpperCase(columnName) + "() {" + enterStr);
                fw.write("        return " + columnName + ";" + enterStr + "    }" + enterStr + enterStr);
            }
        }
        fw.write("}");
        fw.close();
    }

    public static String firstToUpperCase(String str) {
        String firstLetter = str.charAt(0) + "";
        str = firstLetter.toUpperCase() + str.substring(1);
        return str;
    }
}
