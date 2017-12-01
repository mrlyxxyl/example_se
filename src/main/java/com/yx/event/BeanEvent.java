package com.yx.event;

import java.util.EventObject;

/**
 * User: LiWenC
 * Date: 17-12-1
 */
public class BeanEvent extends EventObject {

    private Object object;

    public BeanEvent(Object source) {
        super(source);
        this.object = source;
    }

    @Override
    public Object getSource() {
        return object;
    }
}