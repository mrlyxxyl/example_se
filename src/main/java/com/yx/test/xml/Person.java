package com.yx.test.xml;

import javax.xml.bind.annotation.*;

/**
 * Hobby: NMY
 * Date: 16-8-29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "person")
public class Person {

    @XmlAttribute
    private int id;

    @XmlElement
    private String name;

    @XmlElement
    private int age;

    @XmlElement(name = "id_number")
    private String idNumber;

    @XmlElement(name = "hobbies")
    private Hobbies hobbies;

    public Person() {
    }

    public Person(int id, String name, int age, String idNumber, Hobbies hobbies) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.idNumber = idNumber;
        this.hobbies = hobbies;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Hobbies getHobbies() {
        return hobbies;
    }

    public void setHobbies(Hobbies hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", idNumber='" + idNumber + '\'' +
                ", hobbies=" + hobbies +
                '}';
    }
}
