package com.example.footballdiscussion.view_modals;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserPostsViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();
    private UserPostModel userPostModel = UserPostModel.instance();

    public List<UserPost> getUserPosts() {
        List<String> likes = new ArrayList<>();
        likes.add("3");
        List<UserPost> list = new ArrayList<>();
        list.add(new UserPost("1","1","John", "What will happen in the GAME?", "URL", likes, false));
        list.add(new UserPost("2","2","Adam", "NEW postttttttttttt?", "URL", likes, false));
        list.add(new UserPost("3","1","John", "What will happen in the GAME?", "URL", likes, false));
        list.add(new UserPost("4","1","John", "What will happen in the GAME?", "URL", likes, false));

        return list;
    }

    public List<UserPost> getOwnUserPosts() {
        List<String> likes = new ArrayList<>();
        List<UserPost> list = new ArrayList<>();
        list.add(new UserPost("2","2","Adam", "NEW postttttttttttt?", "URL", likes, false));

        return list;
    }
    public User getCurrentUser(){
        return userModel.getCurrentLogInUser();
    }

    public void uploadImage(String imageId, Bitmap bitmap, Listener<String> listener){
        userPostModel.uploadImage(imageId,bitmap,listener);
    }
    public void publishUserPost(UserPost userPost, Listener<Void> callback){
        userPostModel.publishUserPost(userPost, callback);
    }


}