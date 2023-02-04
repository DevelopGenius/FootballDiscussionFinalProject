package com.example.footballdiscussion.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class UserPost {
    @PrimaryKey
    @NonNull
    private String id;
    private String userId;
    private String username;
    private String postTitle;
    private String imageUrl;
    private boolean isOwnPost;
    private boolean isLiked;

    public UserPost(@NonNull String id, String userId, String username, String postTitle, String imageUrl, boolean isOwnPost, boolean isLiked) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
        this.isOwnPost = isOwnPost;
        this.isLiked = isLiked;
    }

    @Ignore
    public UserPost(String userId, String username, String postTitle, String imageUrl) {
        this.userId = userId;
        this.username = username;
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isOwnPost() {
        return isOwnPost;
    }

    public void setOwnPost(boolean ownPost) {
        isOwnPost = ownPost;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
