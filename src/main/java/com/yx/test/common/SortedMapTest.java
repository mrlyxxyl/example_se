package com.yx.test.common;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * User: NMY
 * Date: 16-8-29
 */
public class SortedMapTest {
    public static void main(String[] args) {
        SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();
        sortedMap.put(1, "v1");
        sortedMap.put(2, "v2");
        sortedMap.put(4, "v4");
        sortedMap.put(3, "v3");
        System.out.println(sortedMap.firstKey());
        System.out.println(sortedMap.lastKey());
    }
}
