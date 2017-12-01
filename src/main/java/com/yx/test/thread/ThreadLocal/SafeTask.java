package com.yx.test.thread.ThreadLocal;

import java.util.Date;

/**利用线程本地变量ThreadLocal实现安全访问
 * User: LiWenC
 * Date: 16-8-31
 */
public class SafeTask implements Runnable {

    private ThreadLocal<Date> startDate = new ThreadLocal<Date>() {
        @Override
        protected Date initialValue() {
            return new Date();
        }
    };

    @Override
    public void run() {
        System.out.printf("Starting ------------- Thread: %s : %s\n", Thread.currentThread().getName(), startDate.get());
        try {
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Finished =============== Thread: %s : %s\n", Thread.currentThread().getName(), startDate.get());
    }
}

class SafeTaskTest {
    public static void main(String[] args) throws InterruptedException {
        SafeTask task = new SafeTask();
        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
            Thread.sleep(1000);
        }
    }
}
