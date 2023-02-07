package com.example.footballdiscussion.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPost {
    @PrimaryKey
    @NonNull
    private String id;
    private String userId;
    private String username;
    private String postTitle;
    private String imageUrl;

    private List<String> userLikes;
    private boolean isDeleted;

    private Long lastUpdated;

    public UserPost(@NonNull String id, String userId, String username, String postTitle, String imageUrl,List<String> userLikes, boolean isDeleted) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
        this.userLikes = userLikes;
        this.isDeleted = isDeleted;
    }

    @Ignore
    public UserPost(String id, String userId, String username, String postTitle, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
        this.isDeleted = false;
        this.userLikes = new ArrayList<>();
    }


    static final String ID = "id";
    static final String USER_ID = "userId";
    static final String USERNAME = "username";
    static final String POST_TITLE = "postTitle";
    static final String IMAGE_URL = "imageUrl";
    static final String IS_DELETED = "isDeleted";
    static final String USERS_LIKE= "usersLike";
    public static final String LAST_UPDATED = "lastUpdated";

    public static UserPost fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String userId = (String)json.get(USER_ID);
        String username = (String)json.get(USERNAME);
        String postTitle = (String)json.get(POST_TITLE);
        String imageUrl = (String)json.get(IMAGE_URL);
        Boolean isDeleted = (Boolean)json.get(IS_DELETED);
        List<String> userLikes =  ((List<String>) json.get(USERS_LIKE));

        UserPost userPost = new UserPost(id,userId,username, postTitle,imageUrl,userLikes, isDeleted);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            userPost.setLastUpdated(time.getSeconds());
        }catch(Exception e){
            throw e;
        }

        return userPost;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(USER_ID, getUserId());
        json.put(USERNAME, getUsername());
        json.put(POST_TITLE, getPostTitle());
        json.put(IMAGE_URL, getImageUrl());
        json.put(IS_DELETED, isDeleted());
        json.put(USERS_LIKE, getUserLikes());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<String> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<String> userLikes) {
        this.userLikes = userLikes;
    }
}
