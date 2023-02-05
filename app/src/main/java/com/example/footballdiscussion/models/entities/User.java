package com.example.footballdiscussion.models.entities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.footballdiscussion.ApplicationContext;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

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
    public Long lastUpdated;

    @Ignore
    public User(String username, String password, String phone, String email, String imageUrl){
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.imageUrl = imageUrl;
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
    public static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "users_local_last_update";

    public static User fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String username = (String)json.get(USERNAME);
        String password = (String)json.get(PASSWORD);
        String phone = (String)json.get(PHONE);
        String email = (String)json.get(EMAIL);
        String imageUrl = (String)json.get(IMAGE_URL);
        User user = new User(id, username, password, phone, email, imageUrl);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            user.setLastUpdated(time.getSeconds());
        }catch(Exception e){
            throw e;
        }

        return user;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = ApplicationContext.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = ApplicationContext.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(USERNAME, getUsername());
        json.put(PASSWORD, getPassword());
        json.put(PHONE, getPhone());
        json.put(EMAIL, getEmail());
        json.put(IMAGE_URL, getImageUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
