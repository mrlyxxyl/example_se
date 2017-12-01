package com.yx.test.http;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;

public class FtpServer extends FTPClient {
    private String userName;
    private String password;
    private String url;
    private int port;

    public FtpServer() {
        url = "192.168.202.27";
        port = 21;
        userName = "admin";
        password = "admin";
    }

    /**
     * 获取ftp连接句柄
     *
     * @return
     * @throws java.io.IOException
     */
    public FTPClient connect() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(url, port);
        ftpClient.setControlEncoding("UTF-8");
        FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_NT);
        ftpConfig.setServerLanguageCode("zh");
        ftpClient.login(userName, password);
        return ftpClient;

    }

    /**
     * @param ftpClient :退出FTP登录
     * @return boolean :是否已经关闭连接
     * @throws java.io.IOException
     */
    public static boolean closeConnections(FTPClient ftpClient) throws IOException {
        return false;
    }

    /**
     * 方法用于上传文件到FTP服务器的指定文件夹中
     *
     * @param fileName  :上传文件的名称
     * @param input     :上传文件的输入流对象
     * @param toFtpPath :上传到FTP的目的路径
     * @return boolean:表示上传是否成功
     */
    public boolean uploadFileToFtp(String fileName, InputStream input, String toFtpPath) {
        try {
            // 转到上传文件的FTP目录中
            FTPClient ftpClient = connect();
            ftpClient.changeWorkingDirectory(toFtpPath);// 设置处理文件的类型为字节流的形式
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 如果缺省该句 传输txt正常
            ftpClient.storeFile(fileName, input);
            input.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 方法用于从FTP服务器中下载文件
     *
     * @param ftpUrl   :下载文件所处FTP中路径
     * @param fileName :下载的文件名称
     * @param os       :下载文件的输出流对象
     * @return boolean :表示是否上传成功
     */
    public boolean downloadFileFromFtp(String ftpUrl, String fileName, OutputStream os) {
        try {
            FTPClient ftpClient = connect();
            ftpClient.changeWorkingDirectory(ftpUrl);
            ftpClient.retrieveFile(fileName, os);
            os.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 方法用户删T除FP上的指定的文件
     *
     * @param fileUrl  :文件在FTP中的路径
     * @param fileName :文件的名称
     * @return boolean:删除是否成功
     */
    public boolean deleteFileOnFtp(String fileName, String fileUrl) {
        try {
            FTPClient ftpClient = connect();
            ftpClient.changeWorkingDirectory(fileUrl);
            ftpClient.deleteFile(fileName);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 判断指定文件中是否存在相同名称的文件
     *
     * @param remotePath    :FTP上的远程目录
     * @param fileName:文件名称
     * @return boolean :判断是否存在相同名称
     */
    public boolean isSameName(String remotePath, String fileName) {
        try {
            FTPClient ftpClient = connect();
            FTPFile[] ftpFiles = ftpClient.listFiles();
            ftpClient.changeWorkingDirectory(remotePath);
            for (int i = 0; i < ftpFiles.length; i++) {
                if (fileName.equals(ftpFiles[i].getName())) {
                    System.out.println("存在和指定文件相同名称的文件");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更改文件名称
     */
    public String changeName(String remotePath, String fileName, String newName) {
        if (isSameName(remotePath, fileName)) {
            fileName = fileName + "." + newName;
        }
        return fileName;
    }

    public void newFileOnFTP(String pathName) {
        try {
            FTPClient ftpClient = connect();
            ftpClient.mkd(pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 上传整个目录到FTP的指定目录中
    public void uploadDirFiles(String dirPath, String toRemotePath) throws IOException {
        if (dirPath != null && !dirPath.equals("")) {
            FTPClient ftpClient = connect();
            // 建立上传目录的File对象
            File dirFile = new File(dirPath);
            // 判断File对象是否为目录类型
            if (dirFile.isDirectory()) {
                // 如果是目录类型。
                // 在FTP上创建一个和File对象文件相同名称的文件夹

                ftpClient.makeDirectory(toRemotePath + dirFile.getName());
                // 获得File对象中包含的子目录数组
                File[] subFiles = dirFile.listFiles();
                // 路径
                String path = toRemotePath + dirFile.getName();
                // 判断数组是否为空
                if (subFiles != null && subFiles.length > 0) {
                    // 遍历整个File数组
                    for (int i = 0; i < subFiles.length; i++) {
                        // 判断是否为目录类型
                        if (subFiles[i].isDirectory()) {
                            // 如果为目录类型
                            // 跳转到FTP的根目录层级
                            ftpClient.changeWorkingDirectory("//");
                            // 在FTP上建立相同的目录名称
                            ftpClient.makeDirectory(path + "//" + subFiles[i].getName());
                            // 递归调用自身方法，进行到下一层级的目录循环
                            uploadDirFiles(subFiles[i].getAbsolutePath(), path);
                        } else {
                            // 如果为文件类型
                            // 建立一个文件输出流对象
                            FileInputStream input = new FileInputStream(subFiles[i]);
                            // 调用文件上传方法，将文件上传到FTP上
                            uploadFileToFtp(subFiles[i].getName(), input, path + "//");
                            // 关闭文件输入流
                            input.close();
                        }
                    }
                }
            } else {
                // 如果为文件类型
                // 建立一个文件输出流对象
                FileInputStream input = new FileInputStream(dirFile);
                // 调用文件上传方法，将文件上传到FTP上
                uploadFileToFtp(dirFile.getName(), input, toRemotePath);
                // 关闭文件输入流
                input.close();
            }
        }
    }

    // 本方法用于下载FTP上的目录结构到本地中
    public void downloadDirFiles(String remotePath, String localPath, String fileName) throws IOException {
        if (remotePath != null && !remotePath.equals("")) {
            FTPClient ftpClient = connect();
            // 在本地建立一个相同的文件目录
            File localFile = new File(localPath + "\\" + fileName);
            localFile.mkdirs();
            // 获得目录在本地的绝对路径
            localPath = localFile.getAbsolutePath();
            // 获得FTPFile对象数组
            FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
            if (ftpFiles != null && ftpFiles.length > 0) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile subFile = ftpFiles[i];
                    // 判断是否为目录结构
                    if (subFile.isDirectory()) {
                        // 如果为目录结构
                        // 调用自身方法，进行下一层级目录循环
                        downloadDirFiles(remotePath + "//" + subFile.getName(), localPath, subFile.getName());
                    } else {
                        // 如果不为目录结构,为文件类型
                        FileOutputStream os = new FileOutputStream(new File(localPath + "\\" + subFile.getName()));
                        // 调用下载方法对文件进行下载
                        downloadFileFromFtp(remotePath, subFile.getName(), os);
                        // 关闭文件输出流
                        os.close();
                    }
                }
            }
        }
    }
}
