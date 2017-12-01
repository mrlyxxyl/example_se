package com.yx.test.fileOper;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadExcel {
    public static void main(String[] args) {
        String path = ReadExcel.class.getClassLoader().getResource("template.xls").getPath();
        readData(path);
    }


    public static void readData(String path) {
        try {
            InputStream input = new FileInputStream(path);
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rowLen = sheet.getLastRowNum();
            HSSFCell idCell;
            HSSFCell nameCell;
            HSSFCell ageCell;
            HSSFCell sexCell;
            HSSFCell addressCell;

            List<Person> persons = new ArrayList<Person>();

            for (int i = 1; i <= rowLen; i++) {//第一行为标题

                idCell = sheet.getRow(i).getCell(0);
                int id = (int) idCell.getNumericCellValue();

                nameCell = sheet.getRow(i).getCell(1);
                String name = nameCell.getStringCellValue().trim();

                ageCell = sheet.getRow(i).getCell(2);
                int age = (int) ageCell.getNumericCellValue();

                sexCell = sheet.getRow(i).getCell(3);
                int sex = (int) sexCell.getNumericCellValue();

                addressCell = sheet.getRow(i).getCell(4);
                String address = addressCell.getStringCellValue().trim();

                persons.add(new Person(id, name, age, sex, address));
            }

            for (Person person : persons) {
                System.out.println(person);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Person {
    private int id;
    private String name;
    private int age;
    private int sex;
    private String address;

    Person(int id, String name, int age, int sex, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                '}';
    }
}