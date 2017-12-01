package com.yx.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 写文件加锁
 */
public class ThreadWriteFile extends Thread {

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        File file = new File("E:/a.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile out = new RandomAccessFile(file, "rw");//对文件进行加锁
            FileChannel outChannel = out.getChannel();
            FileLock flout;
            while (true) {
                try {
                    flout = outChannel.tryLock();
                    break;
                } catch (Exception e) {
                    System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒！");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            for (int i = 1; i < 500; i++) {
                sleep(100);
                StringBuffer sb = new StringBuffer();
                sb.append("line-->" + i + "\r\n");
                out.write(sb.toString().getBytes("utf-8"));
            }
            flout.release();
            outChannel.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("写文件共花了:" + (System.currentTimeMillis() - start) + "ms");
    }
}

