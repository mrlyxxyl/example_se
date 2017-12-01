package com.yx.test.listener;

import java.util.EventListener;

/**
 * User: LiWenC
 * Date: 16-8-31
 */
public interface StoryListener extends EventListener {
    void deleteStory(String id);
}
