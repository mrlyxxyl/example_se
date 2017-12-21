package com.yx.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: LiWenC
 * Date: 17-12-21
 */
public class FTPUtil {
    static FTPClient ftp;

    public static void connect() {
        try {
            ftp = new FTPClient();
            ftp.connect("192.168.1.124", 21);
            ftp.login("admin", "admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void disConnect() {
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        connect();
        for (int i = 0; i < 10; i++) {
            upload(System.currentTimeMillis());
        }

        disConnect();
    }

    /**
     * 上传文件
     */
    public static void upload(long fileName) {

        try {
            InputStream is;
            String path = "/100";
            boolean flag = ftp.changeWorkingDirectory(path);
            if (!flag) {
                ftp.makeDirectory(path);
            }
            ftp.changeWorkingDirectory(path);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            File file = new File("e:/test.pdf");
            is = new FileInputStream(file);
            ftp.storeFile(fileName + ".pdf", is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
