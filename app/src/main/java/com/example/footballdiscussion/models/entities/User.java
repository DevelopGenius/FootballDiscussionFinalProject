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
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;

    @Ignore
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(@NonNull String id, String firstName, String lastName, String email, String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static User create(Map<String, Object> user, String id) {
        String imageUrl = user.get("imageUrl") != null ? user.get("imageUrl").toString() : null;

        return new User(
                id,
                user.get("firstName").toString(),
                user.get("lastName").toString(),
                user.get("email").toString(),
                imageUrl
        );
    }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("firstName", firstName);
            put("lastName", lastName);
            put("email", email);
            put("imageUrl", imageUrl);
        }};
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
