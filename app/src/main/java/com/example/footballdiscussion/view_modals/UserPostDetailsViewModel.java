package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.models.UserPostModel;

public class UserPostDetailsViewModel extends ViewModel {
    private UserPostModel userPostModel = UserPostModel.instance();

    public LiveData<UserPost> getUserPostById(String userPostId) {
        return userPostModel.getUserPostById(userPostId);
    }

}
