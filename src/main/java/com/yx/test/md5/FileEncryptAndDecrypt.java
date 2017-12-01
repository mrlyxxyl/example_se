package com.yx.test.md5;

import java.io.*;

public class FileEncryptAndDecrypt {
    public static void main(String[] args) throws Exception {
        String flag = readFileLastByte("d:\\index.jpg", 6);
        if (flag.indexOf("hello") > 0) {
        } else {
            new FileEncryptAndDecrypt().encrypt("d:/index.jpg", "hello");
        }
        String tmpUrl = new FileEncryptAndDecrypt().decrypt("d:/index.jpg", "d:/index_tmp.jpg", 6);
        System.out.println(tmpUrl);
    }

    /**
     * 文件file进行加密
     *
     * @param fileUrl 文件路径
     * @param key     秘钥
     * @throws Exception
     */
    public static boolean encrypt(String fileUrl, String key) throws Exception {
        try {
            File file = new File(fileUrl);
            String path = file.getPath();
            if (!file.exists()) {
                return false;
            }
            int index = path.lastIndexOf("\\");
            String dFile = path.substring(0, index) + "\\" + System.currentTimeMillis() + ".jpg";
            File targetFile = new File(dFile);
            InputStream in = new FileInputStream(fileUrl);
            OutputStream out = new FileOutputStream(dFile);
            byte[] buffer = new byte[1024];
            int r;
            byte[] buffer2 = new byte[1024];
            while ((r = in.read(buffer)) > 0) {
                for (int i = 0; i < r; i++) {
                    byte b = buffer[i];
                    buffer2[i] = b == 255 ? 0 : ++b;
                }
                out.write(buffer2, 0, r);
                out.flush();
            }
            in.close();
            out.close();
            file.delete();
            targetFile.renameTo(new File(fileUrl));
            appendMethodA(fileUrl, key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param fileName
     * @param content  密钥
     */
    public static void appendMethodA(String fileName, String content) {
        try {
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密
     *
     * @param fileUrl   源文件
     * @param tempUrl   临时文件
     * @param keyLength 密码长度
     * @return
     * @throws Exception
     */
    public static String decrypt(String fileUrl, String tempUrl, int keyLength) throws Exception {
        File file = new File(fileUrl);
        if (!file.exists()) {
            return null;
        }
        File targetFile = new File(tempUrl);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }

        InputStream is = new FileInputStream(fileUrl);
        OutputStream out = new FileOutputStream(tempUrl);

        byte[] buffer = new byte[1024];
        byte[] buffer2 = new byte[1024];
        byte bMax = (byte) 255;
        long size = file.length() - keyLength;
        int mod = (int) (size % 1024);
        int div = (int) (size >> 10);
        int count = mod == 0 ? div : (div + 1);
        int k = 1, r;
        while ((k <= count && (r = is.read(buffer)) > 0)) {
            if (mod != 0 && k == count) {
                r = mod;
            }
            for (int i = 0; i < r; i++) {
                byte b = buffer[i];
                buffer2[i] = b == 0 ? bMax : --b;
            }
            out.write(buffer2, 0, r);
            k++;
        }
        out.close();
        is.close();
        return tempUrl;
    }

    /**
     * 判断文件是否加密
     *
     * @param fileName
     * @return
     */
    public static String readFileLastByte(String fileName, int keyLength) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        try {
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "r");
            long fileLength = randomFile.length();
            for (int i = keyLength; i >= 1; i--) {
                randomFile.seek(fileLength - i);
                str.append((char) randomFile.read());
            }
            randomFile.close();
            return str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}