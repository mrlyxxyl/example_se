package com.yx.test.rsa;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    public static final String ENCRYPTION_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 生成密钥
     */
    public static MyPair<PublicKey, PrivateKey> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return new MyPair(keyPair.getPublic(), keyPair.getPrivate());
    }

    /**
     * 取得公钥
     */
    public static String getPublicKey(MyPair<PublicKey, PrivateKey> pair) throws Exception {
        return Base64Util.encryptBASE64(pair.getL().getEncoded());
    }

    /**
     * 取得私钥
     */
    public static String getPrivateKey(MyPair<PublicKey, PrivateKey> pair) throws Exception {
        return Base64Util.encryptBASE64(pair.getR().getEncoded());
    }

    /**
     * 加密
     */
    public static String encrypt(String data, String keyString, boolean isPublic) throws Exception {
        MyPair<Key, KeyFactory> pair = generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = pair.getR();
        Key key = pair.getL();
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64Util.encryptBASE64(cipher.doFinal(data.getBytes()));
    }

    /**
     * 解密
     */
    public static String decrypt(String data, String keyString, boolean isPublic) throws Exception {

        MyPair<Key, KeyFactory> pair = generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = pair.getR();
        Key key = pair.getL();
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64Util.decryptBASE64(data)));
    }

    /**
     * 生成钥匙
     */
    public static MyPair<Key, KeyFactory> generateKeyAndFactory(String keyString, boolean isPublic) throws Exception {
        byte[] keyBytes = Base64Util.decryptBASE64(keyString);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        Key key;
        if (isPublic) {
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(keyBytes);
            key = keyFactory.generatePublic(encodedKeySpec);
        } else {
            PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            key = keyFactory.generatePrivate(encodedKeySpec);
        }
        return new MyPair(key, keyFactory);
    }

    /**
     * 对信息生成数字签名（用私钥）
     */
    public static String sign(byte[] data, String keyString) throws Exception {
        MyPair<Key, KeyFactory> pair = generateKeyAndFactory(keyString, false);
        Key key = pair.getL();
        PrivateKey privateKey = (PrivateKey) key;
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return Base64Util.encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名（用公钥）
     */
    public static boolean verify(byte[] data, String keyString, String sign) throws Exception {
        MyPair<Key, KeyFactory> pair = generateKeyAndFactory(keyString, true);
        Key key = pair.getL();
        PublicKey publicKey = (PublicKey) key;
        // 取公钥匙对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(Base64Util.decryptBASE64(sign));
    }
}