package com.oyeafrica.kwizzer.Models;

import java.io.Serializable;

public class User implements Serializable {
    String uid;
    String image;
    String username;
    int score;

    public User() {
    }

    public User(String uid, String image, String username, int score) {
        this.uid = uid;
        this.image = image;
        this.username = username;
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
