package com.yx.test.queue;

/**
 * 考生
 * User: LiWenC
 * Date: 16-8-31
 */
public class User implements Comparable {
    private int id;
    private String name;
    private int score;

    public User(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        return user.score > this.score ? -1 : user.score == this.score ? 0 : 1;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }


}
