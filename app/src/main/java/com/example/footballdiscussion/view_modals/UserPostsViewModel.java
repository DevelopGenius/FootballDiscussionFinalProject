package com.example.footballdiscussion.view_modals;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;
import com.example.footballdiscussion.utils.LoadingState;

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
        List<UserPost> userPosts = userPostModel.getAllUserPosts().getValue();
        return userPosts == null ? null : filterOwnPosts(userPosts);
    }

    public List<UserPost> filterOwnPosts(List<UserPost> userPosts){
        return userPosts.stream().filter(this::isOwnPost).collect(Collectors.toList());
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

    public boolean isOwnPost(UserPost userPost) {
        return userPost.getUserId().equals(getCurrentUser().getId());
    }

    public void handleUserPostLike(UserPost userPost) {
        userPostModel.handleUserPostLike(userPost, getCurrentUser().getId());
    }

    public void deleteUserPost(UserPost userPost) {
        userPostModel.deleteUserPost(userPost);
    }

    public LiveData<UserPost> getUserPostById(String userPostId){
        return userPostModel.getUserPostById(userPostId);
    }

    public void updateUserPost(UserPost userPost, Listener<Void> successCallback, Listener<String> failCallback) {
        userPostModel.updateUserPost(userPost,successCallback, failCallback);
    }
}