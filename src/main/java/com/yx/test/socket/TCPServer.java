package com.yx.test.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8888);
            while (true) {
                Socket s = ss.accept();
                System.out.println(s.getInetAddress() + " " + s.getPort());
                DataInputStream dis = new DataInputStream(s.getInputStream());
                System.out.println(dis.readUTF());
                dis.close();
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
