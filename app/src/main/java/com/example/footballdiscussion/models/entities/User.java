package com.example.footballdiscussion.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id="";
    public String username="";
    public String password="";
    public String phone="";
    public String email="";
    public String imageUrl="";

    public User(){
    }
    public User( String id,String username, String password, String phone, String email, String imageUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    static final String ID = "id";
    static final String USERNAME = "name";
    static final String PASSWORD = "password";
    static final String PHONE = "phone";
    static final String EMAIL = "email";
    static final String IMAGE_URL = "imageUrl";

    public static User fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String username = (String)json.get(USERNAME);
        String password = (String)json.get(PASSWORD);
        String phone = (String)json.get(PHONE);
        String email = (String)json.get(EMAIL);
        String imageUrl = (String)json.get(IMAGE_URL);

        User user = new User(id, username, password, phone, email, imageUrl);
        return user;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(USERNAME, getUsername());
        json.put(PASSWORD, getPassword());
        json.put(PHONE, getPhone());
        json.put(EMAIL, getEmail());
        json.put(IMAGE_URL, getImageUrl());
        return json;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
