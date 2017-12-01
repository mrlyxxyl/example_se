package com.yx.bean;

import org.msgpack.MessagePack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: LiWenC
 * Date: 16-9-20
 */
public class TestBean {
    protected static final MessagePack msgPack = new MessagePack();

    public static void main(String[] args) throws IOException {
//        msgPack.register(Teacher.class);//等同于在bean上添加@Message注解
        Teacher teacher = new Teacher(1, "t");
        Student s1 = new Student(1, "s1");
        Student s2 = new Student(2, "s2");
        Student s3 = new Student(3, "s3");
    /*    List<Student> studentList = new ArrayList<Student>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        teacher.setStudents(studentList);*/

        byte[] bytes = msgPack.write(teacher);//序列化
        Teacher teacher1 = msgPack.read(bytes, Teacher.class);//反序列化
        System.out.println(teacher1);
    }
}
