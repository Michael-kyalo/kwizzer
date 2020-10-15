package com.oyeafrica.kwizzer.Models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Kwiz implements Serializable {
    @DocumentId
    String id;
    String title,description,category,image,difficulty,visibility;
    @ServerTimestamp
    Date date;
    long length;

    public Kwiz() {
    }

    public Kwiz(String id,String title, String description, String category, String image, String difficulty, String visibility, Date date, long length) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.difficulty = difficulty;
        this.visibility = visibility;
        this.date = date;
        this.length = length;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
