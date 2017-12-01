package com.yx.test.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * User: LiWenC
 * Date: 16-8-31
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        final Count ct = new Count();
        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    ct.get();
                }
            }.start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    ct.put();
                }
            }.start();
        }
    }
}

class Count {
    final ReentrantLock lock = new ReentrantLock();


    public void get() {
        lock.lock(); // 加锁
        System.out.println(Thread.currentThread().getName() + "get begin");
        System.out.println(Thread.currentThread().getName() + "get end");
        lock.unlock(); // 解锁
    }

    public void put() {
        lock.lock(); // 加锁
        System.out.println(Thread.currentThread().getName() + "put begin");
        System.out.println(Thread.currentThread().getName() + "put end");
        lock.unlock(); // 解锁
    }
}