package com.yx.bean;

import org.msgpack.annotation.Message;
import org.msgpack.annotation.Optional;

/**
 * User: NMY
 * Date: 16-8-29
 */
@Message
public class UserInfo {

    @Optional
    private String userName;

    @Optional
    private int userID;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public UserInfo buildUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserID(int userID) {
        this.userID = userID;
        return this;
    }

    @Override
    public String toString() {
        return "UserInfo [userName=" + userName + ", userID=" + userID + "]";
    }
}
