package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;

import java.util.List;

public class UserCommentsViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();
    private UserPostModel userPostModel = UserPostModel.instance();

    public User getCurrentUser() {
        return userModel.getCurrentLogInUser();
    }
}
