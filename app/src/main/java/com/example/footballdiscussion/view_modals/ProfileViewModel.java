package com.example.footballdiscussion.view_modals;

import android.graphics.Bitmap;
import androidx.lifecycle.ViewModel;
import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.models.UserModel;

public class ProfileViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();

    public void updateUserProfile(String username, String email, String phone,
                                  Bitmap bitmap,
                                  Listener<Void> successCallback,
                                  Listener<String> failCallback) {
        userModel.updateUserProfile(username, email, phone, bitmap, successCallback, failCallback);
    }

    public User getCurrentUser(){
        return userModel.getCurrentLogInUser();
    }
}