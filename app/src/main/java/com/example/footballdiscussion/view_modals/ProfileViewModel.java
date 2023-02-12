package com.example.footballdiscussion.view_modals;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.models.UserModel;
import com.example.footballdiscussion.models.models.UserPostModel;

public class ProfileViewModel extends ViewModel {
    private final UserModel userModel = UserModel.instance();
    private final UserPostModel userPostModel = UserPostModel.instance();

    public void updateUserProfile(String username, String email, String phone,
                                  Bitmap bitmap,
                                  Listener<Void> successCallback,
                                  Listener<String> failCallback) {
        boolean isUsernameChanged = !getCurrentUser().getUsername().equals(username);
        userModel.updateUserProfile(username, email, phone, bitmap, (unused) -> {
            if (isUsernameChanged) {
                userPostModel.updateUserPostsUsername(getCurrentUser().getId(), username,
                        successCallback, failCallback);
            } else {
                successCallback.onComplete(null);
            }
        }, failCallback);
    }

    public User getCurrentUser() {
        return userModel.getCurrentLogInUser();
    }
}