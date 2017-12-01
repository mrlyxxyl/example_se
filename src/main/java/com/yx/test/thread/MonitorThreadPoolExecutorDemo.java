package com.yx.test.thread;

import java.util.concurrent.*;

/**
 * 自定义线程池
 * User: LiWenC
 * Date: 16-8-31
 */
public class MonitorThreadPoolExecutorDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = new MonitorThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 100; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread.sleep((long) (Math.random() * 100));
            executor.execute(runnable);
        }
        executor.shutdown();
    }
}

class MonitorThreadPoolExecutor extends ThreadPoolExecutor {

    public MonitorThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected void beforeExecute(Thread paramThread, Runnable paramRunnable) {
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        showInfo();
    }

    protected void terminated() {
        System.out.println("ThreadPoolExecutor terminated:");
    }

    private void showInfo() {
        System.out.println("getCorePoolSize:" + this.getCorePoolSize() + "；getPoolSize:" + this.getPoolSize() + "；getTaskCount:" + this.getTaskCount() + "；getCompletedTaskCount:"
                + this.getCompletedTaskCount() + "；getLargestPoolSize:" + this.getLargestPoolSize() + "；getActiveCount:" + this.getActiveCount());
    }
}