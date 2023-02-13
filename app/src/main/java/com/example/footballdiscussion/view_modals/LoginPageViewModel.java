package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.models.UserModel;

public class LoginPageViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();

    public void login(String email, String password, Listener<Void> onSuccessCallback, Listener<Void> onFailureCallback) {
        userModel.login(email, password, onSuccessCallback, onFailureCallback);
    }
}