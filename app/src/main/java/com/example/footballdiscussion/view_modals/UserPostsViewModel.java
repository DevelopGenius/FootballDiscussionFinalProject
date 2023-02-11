package com.example.footballdiscussion.view_modals;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.enums.LoadingState;
import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserPostsViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();
    private UserPostModel userPostModel = UserPostModel.instance();

    public MutableLiveData<LoadingState> getEventUserPostsLoadingState() {
        return userPostModel.getEventUserPostsLoadingState();
    }

    public LiveData<List<UserPost>> getAllUserPosts() {
        return userPostModel.getAllUserPosts();
    }

    public void refreshAllUserPosts() {
        userPostModel.refreshAllUserPosts();
    }

    public List<UserPost> getOwnUserPosts() {
        User currentUser = getCurrentUser();

        return userPostModel.getAllUserPosts().getValue().stream()
                .filter(userPost -> userPost.getUserId().equals(currentUser.id))
                .collect(Collectors.toList());
    }

    public User getCurrentUser() {
        return userModel.getCurrentLogInUser();
    }

    public void uploadImage(String imageId, Bitmap bitmap, Listener<String> listener) {
        userPostModel.uploadImage(imageId, bitmap, listener);
    }

    public void publishUserPost(UserPost userPost, Listener<Void> callback) {
        userPostModel.publishUserPost(userPost, callback);
    }

    public boolean isOwnPost(UserPost userPost){
        return userPost.getUserId().equals(getCurrentUser().getId());
    }
    public void handleUserPostLike(UserPost userPost){
        userPostModel.handleUserPostLike(userPost, getCurrentUser().getId());
    }
}