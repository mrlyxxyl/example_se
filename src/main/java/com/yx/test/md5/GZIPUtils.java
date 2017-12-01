package com.yx.test.md5;

import org.apache.commons.codec.binary.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * ZIP解压缩
 */
public class GZIPUtils {

    public static final String ENCODE_UTF_8 = "UTF-8";


    /**
     * 压缩
     *
     * @param str
     * @return
     */
    public static byte[] compress(String str) {
        return compress(str, ENCODE_UTF_8);
    }

    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream;
        try {
            gzipOutputStream = new GZIPOutputStream(out);
            gzipOutputStream.write(str.getBytes(encoding));
            gzipOutputStream.close();
        } catch (IOException e) {
        }
        return out.toByteArray();
    }

    /**
     * 解压
     *
     * @param bytes
     * @return
     */
    public static byte[] unCompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gzipInputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
        }

        return out.toByteArray();
    }

    public static String unCompressToString(byte[] bytes) {
        return unCompressToString(bytes, ENCODE_UTF_8);
    }

    public static String unCompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gzipInputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "111111111111111111111111111111111111111111111111111";
        System.out.println("原长度：" + str.length());
        System.out.println("压缩后字符串：" + GZIPUtils.compress(str).toString());
        System.out.println("解压缩后字符串：" + StringUtils.newStringUtf8(GZIPUtils.unCompress(GZIPUtils.compress(str))));
        System.out.println("解压缩后字符串：" + GZIPUtils.unCompressToString(GZIPUtils.compress(str)));
    }
}  