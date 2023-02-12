package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;

public class UserCommentRowViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();
    private UserPostModel userPostModel = UserPostModel.instance();

    public void publishUserComment(String comment, Listener<Void> callback) {
        userPostModel.publishUserComment(comment, callback);
    }
}
