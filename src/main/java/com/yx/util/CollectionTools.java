package com.yx.util;

import com.yx.bean.PersonTest;
import org.apache.commons.collections.CollectionUtils;

import java.sql.SQLException;
import java.util.*;

/**
 * User: LiWenC
 * Date: 16-10-10
 */
public class CollectionTools {

    /**
     * 去重
     *
     * @param list
     * @return
     * @throws SQLException
     */
    public static Map<String, List<PersonTest>> duplicateRemoval(List<PersonTest> list) {
        Map<String, List<PersonTest>> map = new HashMap<String, List<PersonTest>>();
        List<PersonTest> validBeans = new ArrayList<PersonTest>();

        List<PersonTest> invalidBeans = new ArrayList<PersonTest>();
        PersonTest o;//迭代变量
        Iterator<PersonTest> iterator;

        for (int i = 0, len = list.size(); i < len; i++) {
            o = list.get(i);
            if (validBeans.contains(o)) {
                invalidBeans.add(o);
            } else {
                validBeans.add(o);
            }
        }

        if (invalidBeans.size() > 0) {
            iterator = validBeans.iterator();
            while (iterator.hasNext()) {
                o = iterator.next();
                if (invalidBeans.contains(o)) {
                    invalidBeans.add(o);
                    iterator.remove();
                }
            }
        }

        map.put("key1", validBeans);
        map.put("key2", invalidBeans);
        return map;
    }

    /**
     * 去重 效率不如上面方法
     *
     * @param list
     * @return
     * @throws SQLException
     */
    public static Map<String, List<PersonTest>> duplicateRemovalTwo(List<PersonTest> list) {
        Map<String, List<PersonTest>> map = new HashMap<String, List<PersonTest>>();
        List<PersonTest> validBeans = new ArrayList<PersonTest>();

        List<PersonTest> invalidBeans = new ArrayList<PersonTest>();
        PersonTest o;//迭代变量

        boolean[] flag = new boolean[list.size()];
        for (int i = 0, len = list.size(); i < len; i++) {
            o = list.get(i);
            if (Collections.frequency(list, o) > 1) {//全部遍历
                flag[i] = true;
            }
        }
        for (int i = 0, len = flag.length; i < len; i++) {
            if (flag[i]) {
                invalidBeans.add(list.get(i));
            } else {
                validBeans.add(list.get(i));
            }
        }
        map.put("key1", validBeans);
        map.put("key2", invalidBeans);
        return map;
    }

    public static void main(String[] args) {
        List<PersonTest> persons = new ArrayList<PersonTest>();
        for (int i = 0; i < 1; i++) {
            persons.add(new PersonTest("a" + i, "a" + i));
        }
        Iterator<PersonTest> iterator = persons.iterator();
        while (iterator.hasNext()){
            PersonTest personTest = iterator.next();
            personTest.setName("fjkdlsjflkds");
        }

        System.out.println(persons.get(0));
        for (PersonTest test : persons){
            test.setName("teswt");
        }

        System.out.println(persons.get(0));
        for (int i=0 ; i<persons.size();i++){
            PersonTest dd = persons.get(i);
            dd.setName("ddddddd");
        }

        System.out.println(persons.get(0));
    }
}
