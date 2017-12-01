package com.yx.test.http;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: LiWenC
 * Date: 16-9-2
 * jar
 *
<dependency>
    <groupId>commons-net</groupId>
    <artifactId>commons-net</artifactId>
    <version>2.2</version>
</dependency>

<dependency>
    <groupId>org.lucee</groupId>
    <artifactId>commons-httpclient</artifactId>
    <version>3.1</version>
</dependency>

<dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
    <version>1.9</version>
</dependency>
 */
public class HTTPTest {
    @Test
    public void testPostMethod() {
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8080/mvc_maven/person/testParam.do");
        postMethod.setParameter("name", "post");
        try {
            int statusCode = httpclient.executeMethod(postMethod);
            System.out.println(statusCode);
            System.out.println(postMethod.getResponseBodyAsString());
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMethod() {
        HttpClient httpclient = new HttpClient();
        GetMethod getMethod = new GetMethod("http://localhost:8080/mvc_maven/person/testParam.do?name=get");//GEt方法的参数直接跟在地址后面
        try {
            int statusCode = httpclient.executeMethod(getMethod);
            System.out.println(statusCode);
            System.out.println(getMethod.getResponseBodyAsString());
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDownload() {//小米应用  搜索 计时器 测试下载量
        HttpClient httpclient = new HttpClient();
        GetMethod getMethod = new GetMethod("http://app.mi.com/download/13761?ref=search");
        try {
            for (int i = 0; i < 100; i++) {
                httpclient.executeMethod(getMethod);
                InputStream im = getMethod.getResponseBodyAsStream();
                FileOutputStream fos = new FileOutputStream("d:/apk/" + System.currentTimeMillis() + ".apk");
                int len;
                byte[] buff = new byte[1024];
                while ((len = im.read(buff)) > 0) {
                    fos.write(buff, 0, len);
                }
                fos.close();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
