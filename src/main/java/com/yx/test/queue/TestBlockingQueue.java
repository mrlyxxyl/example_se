package com.yx.test.queue;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * User: NMY
 * Date: 16-8-28
 */
public class TestBlockingQueue {
    static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

    public static void main(String[] args) throws InterruptedException, IOException {
//        new Thread(new Add()).start();
//        new Thread(new Del()).start();
//        new Thread(new Reduce()).start();

       /* final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println();
                    queue.put("aaaaaaaaaaa");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        System.out.println(queue.take());*/

        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

        System.out.println(queue.remove());//从头移除非阻塞
        System.out.println(queue.take());//从头移除阻塞
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.element());
    }

    static class Add implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String str = UUID.randomUUID().toString();
                    queue.put(str);//会阻塞
                    System.out.println("---------put--------" + str);
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Reduce implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("----------remove---------" + queue.take());//会阻塞
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Del implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Random random = new Random();
                    int d = random.nextInt(30000);
                    queue.remove("v" + d);
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
