package com.yx.file;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 读文件加锁
 */
public class ThreadReadFile extends Thread {

    @Override
    public void run() {

        long start = System.currentTimeMillis();
        try {
            sleep(5000); //同时读写
            File file = new File("E:/a.txt");
            RandomAccessFile fis = new RandomAccessFile(file, "rw");//给该文件加锁
            FileChannel fileChannel = fis.getChannel();
            FileLock flin;
            while (true) {
                try {
                    flin = fileChannel.tryLock();
                    break;
                } catch (Exception e) {
                    System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒");
                    sleep(1000);
                }
            }
            byte[] buf = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((fis.read(buf)) > 0) {
                bos.write(buf);
            }
            System.out.println(new String(bos.toByteArray()));
            flin.release();
            fileChannel.close();
            fis.close();
            System.out.println("读文件共花了:" + (System.currentTimeMillis() - start) + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

