package com.yx.test.md5;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.security.spec.KeySpec;

/**
 * 使用AES算法完成对数据的加密和解密
 */
public class PBESecretKeyTest {
    public static void main(String[] args) throws Exception {
        //定义需要进行加密的数据  
        String data = "hello world";
        System.out.println("加密前的数据为：" + data);
        //对数据进行加密  
        secretEncrypt(data);
        //对数据进行解密  
        secretDecrypt("D:/PBencrypted.data", "D:/PBsecret.key");
    }

    public static void secretEncrypt(String data) throws Exception {
        //使用PBEWithMD5AndDES算法获取Cipher实例  
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        //初始化密钥  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        KeySpec keySpec = new PBEKeySpec("password".toCharArray());
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        //初始化Cipher为加密器  
        PBEParameterSpec parameterSpec = new PBEParameterSpec(new byte[]{1, 2, 3, 4, 5, 6, 7, 8}, 1000);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        //对数据进行加密  
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        //输出加密后的内容  
        System.out.println("加密后的数据为：" + new String(encryptedData));

        //将密钥进行持久化和序列化  
        FileOutputStream keyFos = new FileOutputStream("D:/PBsecret.key");
        ObjectOutputStream keyOos = new ObjectOutputStream(keyFos);
        keyOos.writeObject(secretKey);
        keyOos.close();
        keyFos.close();

        //将加密后的数据进行持久化
        FileOutputStream encryptedDataFos = new FileOutputStream("D:/PBencrypted.data");
        encryptedDataFos.write(encryptedData);
        encryptedDataFos.close();
    }

    public static void secretDecrypt(String dataPath, String keyPath) throws Exception {

        //通过IO流获得密钥和加密数据  
        FileInputStream keyFis = new FileInputStream(keyPath);
        ObjectInputStream keyOis = new ObjectInputStream(keyFis);

        //使用PBEWithMD5AndDES算法获取Cipher实例  
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        //初始化密钥  
        SecretKey secretKey = (SecretKey) keyOis.readObject();
        keyOis.close();

        //初始化Cipher为解密器  
        PBEParameterSpec parameterSpec = new PBEParameterSpec(new byte[]{1, 2, 3, 4, 5, 6, 7, 8}, 1000);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        //获得加密后的数据  
        FileInputStream encryptedDataFis = new FileInputStream(dataPath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        copyStream(encryptedDataFis, bos);

        //获得解密后的数据  
        byte[] decryptedData = cipher.doFinal(bos.toByteArray());

        bos.close();
        encryptedDataFis.close();

        System.out.println("解密后的数据为：" + new String(decryptedData));

    }

    public static void copyStream(FileInputStream fis, ByteArrayOutputStream bos) throws Exception {
        byte[] buf = new byte[1024];
        int len;
        while ((len = fis.read(buf)) != -1) {
            bos.write(buf, 0, len);
        }
    }
}