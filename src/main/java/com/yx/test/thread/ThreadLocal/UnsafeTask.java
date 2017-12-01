package com.yx.test.thread.ThreadLocal;

import java.util.Date;

/**不安全的
 * User: LiWenC
 * Date: 16-8-31
 */
public class UnsafeTask implements Runnable {
    private Date startDate  = new Date();;

    @Override
    public void run() {
        startDate = new Date();
        System.out.printf("Starting ------------- Thread: %s : %s\n", Thread.currentThread().getName(), startDate);
        try {
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Finished =============== Thread: %s : %s\n", Thread.currentThread().getName(), startDate);
    }
}

class UnsafeTaskTest {
    public static void main(String[] args) throws InterruptedException {
        UnsafeTask task = new UnsafeTask();
        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
            Thread.sleep(1000);
        }
    }
}
