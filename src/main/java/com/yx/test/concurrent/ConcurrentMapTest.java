package com.yx.test.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * User: LiWenC
 * Date: 16-9-8
 */
public class ConcurrentMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        String temp = map.putIfAbsent("a", "hello");//如果存在a 则temp为a的值，否则为null即返回的是map中的值
        System.out.println(map.get("a"));
        System.out.println(temp);
    }
}
