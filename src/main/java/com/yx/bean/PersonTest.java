package com.yx.bean;

import java.io.Serializable;

/**
 * User: LiWenC
 * Date: 16-8-29
 */
public class PersonTest implements Serializable {

    private String name;
    private String openId;

    public PersonTest(String name, String openId) {
        this.name = name;
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonTest)) return false;
        PersonTest that = (PersonTest) o;
        return !((name != null ? !name.equals(that.name) : that.name != null) && (openId != null ? !openId.equals(that.openId) : that.openId != null));
    }

    @Override
    public String toString() {
        return "PersonTest{" +
                "name='" + name + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
