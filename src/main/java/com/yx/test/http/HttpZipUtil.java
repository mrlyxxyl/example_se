package com.yx.test.http;

import javax.swing.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * 读取压缩网页
 */
public class HttpZipUtil {
    private final static String ENCODING = "UTF-8";
    private final static String ZIP_CODING = "gzip";

    public static String getHtmlInfo(String link) throws Exception {
        String result;
        HttpURLConnection conn = null;
        URL url = new URL(link);
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0");
        conn.connect();
        String encoding = conn.getContentEncoding();
        result = readStream(conn.getInputStream(), encoding);
        conn.disconnect();
        return result;
    }

    private static String readStream(InputStream inputStream, String encoding) throws Exception {
        StringBuffer buffer = new StringBuffer();
        ProgressMonitorInputStream monitorInputStream = null;
        InputStreamReader inputStreamReader;
        GZIPInputStream gZIPInputStream = null;
        if (ZIP_CODING.equals(encoding)) {
            gZIPInputStream = new GZIPInputStream(inputStream);
            inputStreamReader = new InputStreamReader(gZIPInputStream, ENCODING);
        } else {
            inputStreamReader = new InputStreamReader(inputStream, ENCODING);
        }
        char[] c = new char[1024];
        int len;
        while ((len = inputStreamReader.read(c)) != -1) {
            buffer.append(new String(c, 0, len));
        }
        if (inputStream != null) {
            inputStream.close();
        }
        if (gZIPInputStream != null) {
            gZIPInputStream.close();
        }
        if (monitorInputStream != null) {
            gZIPInputStream.close();
        }
        return buffer.toString();
    }
}