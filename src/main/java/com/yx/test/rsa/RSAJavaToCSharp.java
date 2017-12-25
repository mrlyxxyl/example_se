package com.yx.test.rsa;

import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * java秘钥转换成C#
 */
public class RSAJavaToCSharp {

    /**
     * 私钥转换成C#格式
     *
     * @param privateKey
     * @return
     */
    private static String getRSAPrivateKeyAsNetFormat(String privateKey) {
        try {
            StringBuffer buff = new StringBuffer(1024);
            byte[] encodedPrivateKey = Base64Util.decryptBASE64(privateKey);
            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(pvkKeySpec);
            buff.append("<RSAKeyValue>");
            buff.append("<Modulus>" + encodeBase64(removeMSZero(pvkKey.getModulus().toByteArray())) + "</Modulus>");
            buff.append("<Exponent>" + encodeBase64(removeMSZero(pvkKey.getPublicExponent().toByteArray())) + "</Exponent>");
            buff.append("<P>" + encodeBase64(removeMSZero(pvkKey.getPrimeP().toByteArray())) + "</P>");
            buff.append("<Q>" + encodeBase64(removeMSZero(pvkKey.getPrimeQ().toByteArray())) + "</Q>");
            buff.append("<DP>" + encodeBase64(removeMSZero(pvkKey.getPrimeExponentP().toByteArray())) + "</DP>");
            buff.append("<DQ>" + encodeBase64(removeMSZero(pvkKey.getPrimeExponentQ().toByteArray())) + "</DQ>");
            buff.append("<InverseQ>" + encodeBase64(removeMSZero(pvkKey.getCrtCoefficient().toByteArray())) + "</InverseQ>");
            buff.append("<D>" + encodeBase64(removeMSZero(pvkKey.getPrivateExponent().toByteArray())) + "</D>");
            buff.append("</RSAKeyValue>");
            return buff.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥转成C#格式
     *
     * @param publicKey
     * @return
     */
    private static String getRSAPublicKeyAsNetFormat(String publicKey) {
        try {
            StringBuffer buff = new StringBuffer(1024);
            byte[] encodedPublicKey = Base64Util.decryptBASE64(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey pukKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(encodedPublicKey));
            buff.append("<RSAKeyValue>");
            buff.append("<Modulus>" + encodeBase64(removeMSZero(pukKey.getModulus().toByteArray())) + "</Modulus>");
            buff.append("<Exponent>" + encodeBase64(removeMSZero(pukKey.getPublicExponent().toByteArray())) + "</Exponent>");
            buff.append("</RSAKeyValue>");
            return buff.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data
     * @return
     */
    private static byte[] removeMSZero(byte[] data) {
        byte[] data1;
        int len = data.length;
        if (data[0] == 0) {
            data1 = new byte[data.length - 1];
            System.arraycopy(data, 1, data1, 0, len - 1);
        } else
            data1 = data;

        return data1;
    }

    /**
     * base64编码
     *
     * @param input
     * @return
     * @throws Exception
     */
    public static String encodeBase64(byte[] input) throws Exception {
        Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod = clazz.getMethod("encode", byte[].class);
        mainMethod.setAccessible(true);
        Object retObj = mainMethod.invoke(null, new Object[]{input});
        return (String) retObj;
    }

    public static void main(String[] args) throws Exception {

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi/7xYbqYcgK3REOscrj1pXbWEv4VnkbuIGzfu\n" +
                "TzxQq2v3uiTIQDM6Bo9h2Iv0c2MVzN4P7YMgyn1oaxKEU8/jH+a5bXtuYeMbwO0h0/uxfPtyw6sV\n" +
                "hwGep2QOw9FER1xDF4utS7xaie8qnWBVAcigIUOOGXbQCYVzPHaIjXHYIwIDAQAB\n";

        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKL/vFhuphyArdEQ6xyuPWldtYS/\n" +
                "hWeRu4gbN+5PPFCra/e6JMhAMzoGj2HYi/RzYxXM3g/tgyDKfWhrEoRTz+Mf5rlte25h4xvA7SHT\n" +
                "+7F8+3LDqxWHAZ6nZA7D0URHXEMXi61LvFqJ7yqdYFUByKAhQ44ZdtAJhXM8doiNcdgjAgMBAAEC\n" +
                "gYEAiom99ZOmD9e25Fjy3B+kBqglHb0Cj/KnZ5lqwlwKMa5hR96oYKNgZtMojstUuOJRtze3Deab\n" +
                "awVv7Y4D4QCzUUCLTmKoBl65eJvlABnYESTntglmd8H3mr/bJ+BaFEcqq1R0QEjwZ2KvKzaIfSOF\n" +
                "JlTpHXmljqjcBzVCxW/M7ZECQQD2AiUnSeITAVhz51tJ53AMvK62VBN5cNQIBseNn6DGU2rZUzH9\n" +
                "SFeNTyOaU3lxBpdbQnSUz2EZXr/zSjxaEXYlAkEAqZ6BjPyW7i0ORZjvkUs8esjB7tIlFLEwbtwd\n" +
                "3DTodtPL9SqW6b62MZqazsmFMLruAbnMHrZqHqPWN8OQPEzOpwJBALUh8zUwYzLXJMeCK2cI7ymR\n" +
                "q5o34XbjKlsWksumIvyj4uJNLVtubnhvMqmomJwt3DengKso3jeN2gtKN5GhlbkCQDCRsKr8UQOW\n" +
                "ZzXKmx7RmlnWTqHiDrgkHKhB41MA22fbMdXcAiagc280xAtebQMNI8Xeu/I3bZ+kbly9ZPm/TJUC\n" +
                "QEx3x5Cxjh1HJKnCtz35Xt//+7DafjHY2GvPhtjcQR/CkVwW5bBHcsE8YH3aQef3GDdZndA/LPln\n" +
                "R8v+46kdxvM=\n";

        String publicKeyString = getRSAPublicKeyAsNetFormat(publicKey);
        String privateKeyString = getRSAPrivateKeyAsNetFormat(privateKey);
        System.out.println(publicKeyString);
        System.out.println(privateKeyString);
    }
}