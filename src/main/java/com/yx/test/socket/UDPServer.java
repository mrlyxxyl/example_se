package com.yx.test.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        byte[] buff = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buff, buff.length);
        DatagramSocket ds = new DatagramSocket(8888);
        while (true) {
            ds.receive(dp);
            System.out.println(new String(buff, 0, dp.getLength()));
        }
    }
}
