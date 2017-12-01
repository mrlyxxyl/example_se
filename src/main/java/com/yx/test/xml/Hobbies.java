package com.yx.test.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Hobby: NMY
 * Date: 16-8-29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement(name = "hobbies")
public class Hobbies {

    @XmlElement(name = "hobby")
    private List<Hobby> hobbies;

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "Hobbies{" +
                "hobbies=" + hobbies +
                '}';
    }
}
