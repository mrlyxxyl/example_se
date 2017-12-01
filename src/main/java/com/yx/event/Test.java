package com.yx.event;

/**
 * User: LiWenC
 * Date: 17-12-1
 */
public class Test {
    public static void main(String[] args) {
        Story story = new Story("1");

        StoryListener listenerOne = new StoryListener() {
            @Override
            public void deleteStory(String id) {
                System.out.println("first delete story id is " + id);
            }
        };

        StoryListener listenerTwo = new StoryListener() {
            @Override
            public void deleteStory(String id) {
                System.out.println("second delete story id is " + id);
            }
        };

        story.addStoryListener(listenerOne);
        story.addStoryListener(listenerTwo);

        story.deleteStory();

        story.removeStoryListener(listenerOne);
        story.removeStoryListener(listenerTwo);

        story.deleteStory();
    }
}
