package com.yx.test.thread;

/**Java 信号量 Semaphore 介绍
 * User: LiWenC
 * Date: 16-8-31
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static int count = 1;

    public static void main(String[] args) {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        final Semaphore semaphore = new Semaphore(5);
        // 模拟20个客户端访问
        for (int i = 0; i < 20; i++) {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire(); //获取许可
                        System.out.println(Thread.currentThread().getName() + "-------" + count++);
                        Thread.sleep((long) (Math.random() * 1000));
                        semaphore.release();//访问完后，释放
                        System.out.println("-----------------" + semaphore.availablePermits());//可用信号量
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        exec.shutdown();
    }
}