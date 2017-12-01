package com.yx.test.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: LiWenC
 * Date: 16-9-12
 */
public class TestDownload {
    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        HttpClient httpclient = new HttpClient();
                        PostMethod postMethod = new PostMethod("http://app.mi.com/download/13761?ref=search");
                        httpclient.executeMethod(postMethod);
                        InputStream inputStream = postMethod.getResponseBodyAsStream();
                        FileOutputStream fos = new FileOutputStream("/root/apk/" + System.currentTimeMillis() + ".apk");
                        int len;
                        byte[] buff = new byte[1024];
                        while ((len = inputStream.read(buff)) > 0) {
                            fos.write(buff, 0, len);
                        }
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                File file = new File("/root/apk");
                for (File tmp : file.listFiles()) {
                    tmp.delete();
                }
            }
        }, 1000, 1000 * 5);
    }
}