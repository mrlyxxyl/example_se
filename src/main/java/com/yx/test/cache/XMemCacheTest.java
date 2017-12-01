package com.yx.test.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import java.io.IOException;

public class XMemCacheTest {
    private static MemcachedClient client;

    static {
        try {
            MemcachedClientBuilder builder = new XMemcachedClientBuilder("localhost:11211");
            client = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            client.set("name", 0, "hello");
            client.set("age", 0, "1");
            client.set("sex", 0, "man");
            client.set("address", 0, "china");
            System.out.println(client.get("name"));
            System.out.println(client.get("age"));
            System.out.println(client.get("sex"));
            System.out.println(client.get("address"));
            System.out.println(client.get("new"));
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
