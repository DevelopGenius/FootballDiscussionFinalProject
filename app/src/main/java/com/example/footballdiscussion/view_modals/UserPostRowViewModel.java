package com.example.footballdiscussion.view_modals;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.enums.LoadingState;
import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;

import java.util.List;
import java.util.stream.Collectors;

public class UserPostRowViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();
    private UserPostModel userPostModel = UserPostModel.instance();

    public User getCurrentUser() {
        return userModel.getCurrentLogInUser();
    }

    public void publishUserComment(String comment, Listener<Void> callback) {
        userPostModel.publishUserComment(comment, callback);
    }
}
