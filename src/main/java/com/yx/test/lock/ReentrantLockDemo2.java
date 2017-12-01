package com.yx.test.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * User: LiWenC
 * Date: 16-8-31
 */
public class ReentrantLockDemo2 {

    public static void main(String[] args) {
        final Person person = new Person();
        for (int i = 0; i < 20; i++) {
            new Thread() {
                @Override
                public void run() {
                    person.updateAge();
                }
            }.start();
        }
    }
}


class Person {
    private int age;
    ReentrantLock lock = new ReentrantLock();

    public void updateAge() {
        try {
            lock.lock();
            age++;
            System.out.println("set" + age);
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("get" + age);
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}