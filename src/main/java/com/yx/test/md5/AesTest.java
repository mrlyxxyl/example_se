package com.yx.test.md5;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 使用AES算法完成对数据的加密和解密
 */
public class AesTest {

    private static final String IV_STRING = "5858588ys8585855";     //偏移量
    private static final String KEY = "8585855ys5858588";           //秘钥

    /**
     * 加密
     *
     * @param content
     * @return
     */
    public static String encryptAES(String content) {
        try {
            byte[] byteContent = content.getBytes("UTF-8");
            byte[] enCodeFormat = KEY.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(byteContent);
            return new BASE64Encoder().encode(encryptedBytes);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 解密
     *
     * @param content
     * @return
     */
    public static String decryptAES(String content) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] encryptedBytes = decoder.decodeBuffer(content);
            byte[] enCodeFormat = KEY.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] result = cipher.doFinal(encryptedBytes);
            return new String(result, "UTF-8");
        } catch (Exception e) {
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        String str = encryptAES("hello");
        System.out.println(str);
        System.out.println(decryptAES("Shme8cW4jN4iKluWs3LHCA=="));
    }
}