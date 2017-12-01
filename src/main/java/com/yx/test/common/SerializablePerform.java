package com.yx.test.common;

import com.yx.bean.UserInfo;
import org.msgpack.MessagePack;

/**
 * User: NMY
 * Date: 16-8-29
 */
public class SerializablePerform {

    public static void main(String[] args) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(250).buildUserName("hello");

        MessagePack messagePack = new MessagePack();

        byte[] bs = messagePack.write(userInfo);//序列化
        System.out.println("byte array's length is : " + bs.length);

        UserInfo serializableUserInfo = messagePack.read(bs, UserInfo.class);//反序列化
        System.out.println(serializableUserInfo);
    }
}