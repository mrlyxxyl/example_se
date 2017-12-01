package com.yx.event;

import java.util.Enumeration;
import java.util.Vector;

/**
 * User: LiWenC
 * Date: 17-12-1
 */
public class BeanListener {

    private Vector<StoryListener> repository = new Vector<StoryListener>();
    private StoryListener listener;

    public void addStoryListener(StoryListener listener) {
        repository.add(listener);
    }

    public void removeStoryListener(StoryListener listener) {
        repository.remove(listener);
    }

    protected void notifyStoryEvent(BeanEvent event) {
        Story story = (Story) event.getSource();
        Enumeration<StoryListener> enumeration = repository.elements();
        while (enumeration.hasMoreElements()) {
            listener = enumeration.nextElement();
            listener.deleteStory(story.getId());
        }
    }


}
