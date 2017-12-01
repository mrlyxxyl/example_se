package com.yx.test.thread.ThreadLocal;

import java.util.*;
import java.util.concurrent.*;

/**
 * User: LiWenC
 * Date: 17-3-23
 */
public class FTest {

    ExecutorService executorService = Executors.newFixedThreadPool(10);//线程池 初始化十个线程 和JDBC连接池是一个意思 实现重用
    static List<FutureTask<Integer>> futureTasks = new ArrayList<FutureTask<Integer>>();//进行异步任务列表
    static Map<Integer, FutureTask<Integer>> map = new HashMap<Integer, FutureTask<Integer>>();

    public static void main(String[] args) {
        FTest test = new FTest();
        for (int i = 0; i < 1000; i++) {
            try {
                test.test(i);
            } catch (ExecutionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
//            System.out.println(i);
        }


        while (!map.containsKey(5000)) {
            System.out.println("解密中！");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        try {
            System.out.println("-------------------------------------------" + map.remove(500).get());
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Set<Integer> keys = map.keySet();

        for (Integer key : keys) {
//            System.out.println(key);
        }
//
//        System.out.println("------------------------");
//        for (FutureTask<Integer> futureTask : futureTasks) {
////            System.out.println(futureTask.get());//得到我们想要的结果
//            //该方法有一个重载get(long timeout, TimeUnit unit) 第一个参数为最大等待时间，第二个为时间的单位
//        }
    }

    public void test(final int i) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = new Callable<Integer>() {//类似与run方法的实现 Callable是一个接口，在call中手写逻辑代码
            @Override
            public Integer call() throws Exception {
                int time = (int) (Math.random() * 100);
                Thread.sleep(time);
                if (i == 50) {
                    throw new RuntimeException("shibai");
                }
                System.out.println(i + "-------" + Thread.currentThread().getName());
                return time;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
//        futureTasks.add(futureTask);
        map.put(i, futureTask);
        executorService.submit(futureTask);
    }
}
