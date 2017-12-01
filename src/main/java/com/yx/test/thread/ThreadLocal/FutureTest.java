package com.yx.test.thread.ThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * User: LiWenC
 * Date: 17-3-23
 */
public class FutureTest {
    static ExecutorService executorService = Executors.newFixedThreadPool(3);//线程池 初始化十个线程 和JDBC连接池是一个意思 实现重用
    static List<FutureTask<Integer>> futureTasks = new ArrayList<FutureTask<Integer>>();//进行异步任务列表

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable<Integer> callable = new Callable<Integer>() {//类似与run方法的实现 Callable是一个接口，在call中手写逻辑代码
            @Override
            public Integer call() throws Exception {
                Integer res = new Random().nextInt(100);
                Thread.sleep((long) (Math.random() * 1000));
//                System.out.println("任务执行:获取到结果 :" + res + "--------------" + Thread.currentThread().getName());
                return res;
            }
        };

        for (int i = 0; i < 100; i++) {
            FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
            futureTasks.add(futureTask);
            executorService.submit(futureTask);

        }


       for (FutureTask<Integer> futureTask : futureTasks) {
            System.out.println(futureTask.get());//得到我们想要的结果
            //该方法有一个重载get(long timeout, TimeUnit unit) 第一个参数为最大等待时间，第二个为时间的单位
        }
    }

    public void test(final int i) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = new Callable<Integer>() {//类似与run方法的实现 Callable是一个接口，在call中手写逻辑代码
            @Override
            public Integer call() throws Exception {
                Integer res = new Random().nextInt(100);
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("任务执行:获取到结果 :" + res + "------------" + i + "--------------------" + Thread.currentThread().getName());
                return res;
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        executorService.submit(futureTask);
        System.out.println(futureTask.get());

    }
}
