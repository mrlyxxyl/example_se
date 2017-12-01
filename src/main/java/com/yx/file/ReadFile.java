package com.yx.file;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 不加锁读文件，若当前文件被加锁，则等待
 * Date: 17-11-30
 */
public class ReadFile extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("e:/a.txt"));
                String str;
                while ((str = reader.readLine()) != null) {
                    System.out.println(Thread.currentThread().getName() + "-->" + str);
                    Thread.sleep(100);
                }
                reader.close();
                break;
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + "--->当前文件正在使用,当前线程休眠1000毫秒！");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                }
            }
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new ReadFile().start();
        }
    }
}
