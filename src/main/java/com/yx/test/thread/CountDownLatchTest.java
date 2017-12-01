package com.yx.test.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);//两个工人的协作
        Worker worker1 = new Worker(latch);
        Worker worker2 = new Worker(latch);
        worker1.start();
        worker2.start();
        latch.await();//等待所有工人完成工作
        System.out.println("all work done");
    }


    static class Worker extends Thread {
        CountDownLatch latch;

        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run() {
            doWork();//工作了
            latch.countDown();//工人完成工作，计数器减一
        }

        private void doWork() {
            try {
                Thread.sleep(new Random().nextInt(1000));
                System.out.println("work over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}