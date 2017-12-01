package com.yx.test.listener;

/**
 * User: LiWenC
 * Date: 16-8-31
 */
public class StoryController {
    public static void main(String[] args) {
        Story story = new Story("1");

        StoryListener listener = new StoryListener() {
            @Override
            public void deleteStory(String id) {
                System.out.println("delete story id is " + id);
            }
        };
        story.addStoryListener(listener);

        story.deleteStory();

        story.removeStoryListener(listener);

        story.deleteStory();
    }
}
