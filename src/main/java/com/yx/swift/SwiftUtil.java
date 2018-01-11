package com.yx.swift;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: LiWenC
 * Date: 18-1-11
 */
public class SwiftUtil {

    public static void main(String[] args) {
        Map<String, Header> map = genUrlAndToken("http://192.168.1.181:8080/auth/v1.0", "test:tester", "testing");
        Header storageUrl = map.get("storageUrl");
        Header authToken = map.get("authToken");
        System.out.println(storageUrl.getValue());
        System.out.println(authToken.getValue());

        createContainer(storageUrl, authToken, "live");

        uploadFile(storageUrl, authToken, "live", "e:", "ccc.png");

        downloadFile(storageUrl, authToken, "live", "e:", "ccc.png");
    }

    /**
     * 测试账号，并且获得url和Token信息
     */
    public static Map<String, Header> genUrlAndToken(String url, String user, String password) {
        Map<String, Header> map = new HashMap<String, Header>();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet req = new HttpGet(url);
            req.addHeader("X-Storage-User", user);
            req.addHeader("X-Storage-Pass", password);
            HttpResponse rsp = httpClient.execute(req);
            Header storageUrl = rsp.getFirstHeader("X-Storage-Url");
            Header authToken = rsp.getFirstHeader("X-Auth-Token");
            map.put("storageUrl", storageUrl);
            map.put("authToken", authToken);

            //以下为测试信息
            HttpEntity entity = rsp.getEntity();
            Header[] headers = rsp.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }

            HttpHead hph = new HttpHead(storageUrl.getValue());
            hph.addHeader(authToken);
            rsp = httpClient.execute(hph);
            headers = rsp.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 创建Container
     *
     * @param storageUrl
     * @param authToken
     * @param containerName
     */
    public static void createContainer(Header storageUrl, Header authToken, String containerName) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPut hpp = new HttpPut(storageUrl.getValue() + "/" + containerName);
            hpp.addHeader(authToken);
            CloseableHttpResponse rsp = httpClient.execute(hpp);
            Header[] headers = rsp.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @param storageUrl
     * @param authToken
     * @param containerName
     * @param filePath
     * @param fileName
     */
    public static void uploadFile(Header storageUrl, Header authToken, String containerName, String filePath, String fileName) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            File file = new File(filePath + "/" + fileName);
            FileEntity fileEntity = new FileEntity(file);
            fileEntity.setContentType("text/plain; charset=\"UTF-8\"");
            HttpPut httpPost = new HttpPut(storageUrl.getValue() + "/" + containerName + "/" + fileName);
            httpPost.setHeader(authToken);
            httpPost.setHeader("Content-Type", "image/jpeg");
            httpPost.setHeader("X-Object-Meta-flower", "一张美图");
            httpPost.setEntity(fileEntity);
            CloseableHttpResponse rsp = httpClient.execute(httpPost);
            Header[] headers = rsp.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param storageUrl
     * @param authToken
     * @param containerName
     * @param storePath
     * @param fileName
     */
    public static void downloadFile(Header storageUrl, Header authToken, String containerName, String storePath, String fileName) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(storageUrl.getValue() + "/" + containerName + "/" + fileName);
            httpget.addHeader(authToken);
            HttpResponse response = httpClient.execute(httpget);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println(entity.getContentType());
                    System.out.println(entity.isStreaming());
                    File storeFile = new File(storePath + "/" + System.currentTimeMillis() + ".png");
                    FileOutputStream output = new FileOutputStream(storeFile);
                    InputStream input = entity.getContent();
                    byte b[] = new byte[1024];
                    int j;
                    while ((j = input.read(b)) != -1) {
                        output.write(b, 0, j);
                    }
                    output.flush();
                    output.close();
                }
                if (entity != null) {
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
