package com.yx.event;

/**
 * User: LiWenC
 * Date: 17-12-1
 */
public class Story extends BeanListener {

    private String id;

    public Story(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void deleteStory() {
        System.out.println("exec delete story");  //执行本来方法
        notifyStoryEvent(new BeanEvent(this));     //触发删除事件
    }
}