package com.yx.test.thread;

import java.util.concurrent.ThreadLocalRandom;

/**
 * User: LiWenC
 * Date: 16-8-31
 */
public class TaskLocalRandomTest implements Runnable {
    public TaskLocalRandomTest() {
        ThreadLocalRandom.current();
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "----------" + ThreadLocalRandom.current().nextInt(10));
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new TaskLocalRandomTest()).start();
        }
    }
}
