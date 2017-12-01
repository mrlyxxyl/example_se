package com.yx.test.common;

import com.yx.bean.PersonTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: LiWenC
 * Date: 16-8-29
 */
public class TestSet {
    public static void main(String[] args) {
        List<PersonTest> persons = new ArrayList<PersonTest>();
        persons.add(new PersonTest("a", "a"));
        persons.add(new PersonTest("b", "b"));
        persons.add(new PersonTest("c", "c"));
        persons.add(new PersonTest("d", "c"));
        persons.add(new PersonTest("e", "c"));
        System.out.println(Collections.frequency(persons, new PersonTest("dd", "c")));

    }
}
