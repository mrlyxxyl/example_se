package com.yx.test.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class MyRtLock extends Thread {
    TestRtLock lock;
    private int id;

    public MyRtLock(int i, TestRtLock test) {
        this.id = i;
        this.lock = test;
    }

    public void run() {
        lock.print(id);
    }

    public static void main(String args[]) {
        ExecutorService service = Executors.newCachedThreadPool();
        TestRtLock lock = new TestRtLock();
        for (int i = 0; i < 10; i++) {
            service.submit(new MyRtLock(i, lock));
        }
        service.shutdown();
    }
}

class TestRtLock {
    private ReentrantLock lock = new ReentrantLock();

    public void print(int str) {
        try {
            lock.lock();
            System.out.println(str + "获得");
            Thread.sleep((int) (Math.random() * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(str + "释放");
            lock.unlock();
        }
    }
}