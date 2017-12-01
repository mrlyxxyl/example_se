package com.yx.service;

import com.yx.plugin.AutoTest;

/**
 * User: LiWenC
 * Date: 16-10-12
 */
@AutoTest
public class TestService {
    public void m1() {
        System.out.println("m1");
    }

    public void m2(String str) {
        System.out.println("m2    " + str);
    }

    public String m3(String str) {
        return "m3    " + str;
    }

    public void m4(int num) {
        System.out.println(num);
    }
}
