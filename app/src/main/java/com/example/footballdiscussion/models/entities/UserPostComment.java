package com.example.footballdiscussion.models.entities;

import com.example.footballdiscussion.models.models.UserModel;

public class UserPostComment {
    private String comment;
    private String username;

    public UserPostComment(String username, String comment) {
        this.username = username;
        this.comment = comment;
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
}
