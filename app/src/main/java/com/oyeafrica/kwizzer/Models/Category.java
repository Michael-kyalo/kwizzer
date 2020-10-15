package com.oyeafrica.kwizzer.Models;

import com.google.firebase.firestore.DocumentId;

public class Category {
    private String name;
    private String image;
    @DocumentId
    private String id;
    private String description;
    private String visibility;



    public Category(String name, String image, String id, String description, String visibility) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.description = description;
        this.visibility = visibility;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Category() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
