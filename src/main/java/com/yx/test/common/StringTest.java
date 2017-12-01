package com.yx.test.common;

/**
 * 强大的正则替换,多个值时的快捷替换
 * User: LiWenC
 * Date: 17-4-6
 */
public class StringTest {
    public static void main(String[] args) {
        String string = "丽江市2016年上半年公共资源交易平台运行情况";
        string = string.replaceAll("丽江市|上半年", "<font color='#f00'>$0</font>");
        System.out.println(string);
        //output: <font color='#f00'>丽江市</font>2016年<font color='#f00'>上半年</font>公共资源交易平台运行情况
    }
}
