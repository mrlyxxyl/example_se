package com.yx.test.bat;

import java.io.IOException;
import java.io.InputStream;

public class InvokeBat {
    public void runBat(String batName) {
        try {
            Process ps = Runtime.getRuntime().exec(batName);
            InputStream in = ps.getInputStream();
            while (in.read() != -1) {
            }
            in.close();
            ps.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        InvokeBat test1 = new InvokeBat();
        String batName = "F:\\build.bat";
        test1.runBat(batName);
    }
}