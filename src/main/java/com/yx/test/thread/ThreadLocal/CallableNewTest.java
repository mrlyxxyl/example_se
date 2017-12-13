package com.yx.test.thread.ThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class CallableNewTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new CallableNewTest().exec();
    }

    void exec() throws InterruptedException, ExecutionException {

        List<FutureTask<Integer>> futureTasks = new ArrayList<FutureTask<Integer>>();//进行异步任务列表
        ExecutorService executorService = Executors.newFixedThreadPool(3);//线程池 初始化十个线程 和JDBC连接池是一个意思 实现重用

        Callable<Integer> callable = new Callable<Integer>() {//类似与run方法的实现 Callable是一个接口，在call中手写逻辑代码
            @Override
            public Integer call() throws Exception {
                Integer res = new Random().nextInt(100);
                Thread.sleep(1000);
                System.out.println("任务执行:获取到结果 :" + res + "------------" + Thread.currentThread().getName());
                return res;
            }
        };

        for (int i = 0; i < 10; i++) {
            FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);//创建一个异步任务
            futureTasks.add(futureTask);
            executorService.submit(futureTask);
        }

        int count = 0;
        for (FutureTask<Integer> futureTask : futureTasks) {
            count += futureTask.get();
        }

        System.out.println("线程池的任务全部完成:结果为:" + count + "，main线程关闭，进行线程的清理");
        executorService.shutdown();//清理线程池
    }
}