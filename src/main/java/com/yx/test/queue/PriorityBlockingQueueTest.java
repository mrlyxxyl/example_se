package com.yx.test.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * User: LiWenC
 * Date: 16-8-31
 */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<User> queue = new PriorityBlockingQueue<User>();//有序
        queue.put(new User(1,"a",33));
        queue.put(new User(5,"c",53));
        queue.put(new User(3,"d",23));
        queue.put(new User(4,"e",24));

        while (true){
            System.out.println(queue.take());
        }
    }
}
