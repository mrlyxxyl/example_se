package com.yx.test.socket;

import java.io.IOException;
import java.net.*;

public class UDPClient {

    public static void main(String[] args) throws IOException {
        byte[] buff = "hello world!".getBytes();
        DatagramPacket dp = new DatagramPacket(buff, buff.length, new InetSocketAddress("localhost", 8888));
        DatagramSocket ds = new DatagramSocket(9999);
        ds.send(dp);
        ds.close();
    }

}
