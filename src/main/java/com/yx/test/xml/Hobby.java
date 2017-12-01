package com.yx.test.xml;

import javax.xml.bind.annotation.*;

/**
 * Hobby: NMY
 * Date: 16-8-29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement(name = "hobby")
public class Hobby {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "name")
    private String name;

    public Hobby() {
    }

    public Hobby(int id, String name) {
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

    @Override
    public String toString() {
        return "Hobby{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
