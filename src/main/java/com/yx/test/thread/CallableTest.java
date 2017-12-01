package com.yx.test.thread;

import java.util.concurrent.*;

/**
 * Callable测试
 * User: LiWenC
 * Date: 16-8-31
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        int number = 6;
        FactorialCalculator calculator = new FactorialCalculator(number);
        Future<Integer> result = executor.submit(calculator);
        Integer num = result.get();//阻塞的，直到任务完成返回结果
        executor.shutdown();
        System.out.println(number + "------------" + num);
    }
}

class FactorialCalculator implements Callable<Integer> {
    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        int result = 1;
        if ((number == 0) || (number == 1)) {
            result = 1;
        } else {
            for (int i = 2; i <= number; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }
        Thread.sleep((long) (Math.random() * 1000));
        return result;
    }
}
