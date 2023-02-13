package com.example.footballdiscussion.models.entities;

public class UserPostComment {
    private String comment;
    private String username;
    private String id;

    public UserPostComment(String username, String comment, String id) {
        this.username = username;
        this.comment = comment;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
