package com.yx.test.md5;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TestZIPFile {
    public static void main(String[] args) {
        compressFile("D:/festival", "D:/festival.zip");
        ShowFiles();
    }

    /**
     * 查看zip内部文件
     */
    private static void ShowFiles() {
        try {
            ZipFile zipFile = new ZipFile("D:/festival.zip");
            Enumeration enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) enumeration.nextElement();
                System.out.println(entry.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件/文件夹
     *
     * @param srcPathName 需要被压缩的文件/文件夹
     * @param endPathName 生成文件全路径
     */
    public static void compressFile(String srcPathName, String endPathName) {
        File srcDir = new File(srcPathName);
        if (!srcDir.exists()) {
            throw new RuntimeException(srcPathName + "不存在！");
        }

        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(new File(endPathName));
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(srcDir);
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
        //fileSet.setExcludes(...); //排除哪些文件或文件夹
        zip.addFileset(fileSet);
        zip.execute();
    }
}
