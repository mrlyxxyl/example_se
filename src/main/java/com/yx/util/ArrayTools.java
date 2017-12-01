package com.yx.util;

import java.util.ArrayList;
import java.util.List;

/**
 * User: LiWenC
 * Date: 16-9-13
 */
public class ArrayTools {

    /**
     * 根据指定字符串将字符数组拼装字符串
     *
     * @param strings
     * @param delimiter
     * @return
     */
    public static String join(String[] strings, String delimiter) {
        if (strings == null || strings.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int i;
        for (i = 0; i < strings.length - 1; i++) {
            sb.append(strings[i]).append(delimiter);
        }
        return sb.append(strings[i]).toString();
    }

    /**
     * 根据指定字符串将字符数组拼装字符串
     *
     * @param list
     * @param delimiter
     * @return
     */
    public static String join(List<String> list, String delimiter) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int i;
        for (i = 0; i < list.size() - 1; i++) {
            sb.append(list.get(i)).append(delimiter);
        }
        return sb.append(list.get(i)).toString();
    }

    public static void main(String[] args) {
        String[] strings = {"aaa", "bbb", "ccc"};
        System.out.println(join(strings, "_"));
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        System.out.println(join(list, ","));
    }
}
