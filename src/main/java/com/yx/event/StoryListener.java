package com.yx.event;

import java.util.EventListener;

/**
 * User: LiWenC
 * Date: 17-12-1
 */
public interface StoryListener extends EventListener {
    void deleteStory(String id);
}