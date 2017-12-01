package com.yx.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yx.common.BooleanSerializer;


/**
 * User: NMY
 * Date: 16-8-29
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Animal {

    private String name;

    @JsonSerialize(using = BooleanSerializer.class)
    private Boolean sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}