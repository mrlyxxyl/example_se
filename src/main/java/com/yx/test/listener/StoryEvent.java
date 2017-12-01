package com.yx.test.listener;

import java.util.EventObject;

/**
 * User: LiWenC
 * Date: 16-8-31
 */
public class StoryEvent extends EventObject {

    private Object object;

    public StoryEvent(Object source) {
        super(source);
        this.object = source;
    }

    public Object getSource() {
        return object;
    }
}
