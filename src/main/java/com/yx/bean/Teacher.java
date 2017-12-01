package com.yx.bean;

import org.msgpack.annotation.Message;
import org.msgpack.annotation.Optional;

import java.io.Serializable;
import java.util.List;

/**
 * User: LiWenC
 * Date: 16-9-20
 */
@Message
public class Teacher implements Serializable {
    private int id;
    private String name;
    List<Student> students;

    public Teacher() {
    }

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
