package com.yx.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

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
        long fileName = System.currentTimeMillis();
        upload(fileName);
        downloadFile("100", fileName + ".pdf", "e:/");
        disConnect();
    }

    /**
     * 上传文件
     */
    public static void upload(long fileName) {

        try {
            InputStream is;
            String path = "100";
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

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String remotePath, String fileName, String localPath) {
        try {
            int reply = ftp.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
                FTPFile[] fs = ftp.listFiles();
                for (FTPFile ff : fs) {
                    if (ff.getName().equals(fileName)) {
                        File localFile = new File(localPath + "/" + ff.getName());
                        OutputStream os = new FileOutputStream(localFile);
                        ftp.retrieveFile(ff.getName(), os);
                        os.close();
                    }
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
