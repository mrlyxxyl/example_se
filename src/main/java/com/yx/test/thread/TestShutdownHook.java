package com.yx.test.thread;

public class TestShutdownHook {

    public static void main(String[] args) {
        Thread shutdownThread = new Thread() {
            public void run() {
                System.out.println("jvm关闭时执行............");
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownThread);

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread:" + Thread.currentThread().getName());
                }
            }).start();
        }
    }
}