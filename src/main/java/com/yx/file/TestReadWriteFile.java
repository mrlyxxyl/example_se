package com.yx.file;

public class TestReadWriteFile {

    public static void main(String[] args) {
        ThreadWriteFile wr = new ThreadWriteFile();
        ThreadReadFile rf = new ThreadReadFile();
        wr.start();
        rf.start();
    }
}