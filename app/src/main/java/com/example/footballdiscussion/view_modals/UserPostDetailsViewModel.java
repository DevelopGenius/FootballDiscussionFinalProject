package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;

public class UserPostDetailsViewModel extends ViewModel {
    private UserPostModel userPostModel = UserPostModel.instance();
    private UserModel userModel = UserModel.instance();

    public LiveData<UserPost> getUserPostById(String userPostId) {
        return userPostModel.getUserPostById(userPostId);
    }

    public User getCurrentUser() {
        return userModel.getCurrentLogInUser();
    }

    public void publishUserComment(String comment, UserPost userPost, Listener<Void> callback) {
        userPostModel.publishUserComment(comment, userPost, callback);
    }

}
