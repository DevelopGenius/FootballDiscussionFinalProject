package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.models.UserModel;

public class MainViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();

    public void userLoggedInHandler(Listener<Void> onLoggedInCallback, Listener<Void> onLoggedOutCallback){
        userModel.userLoggedInHandler(onLoggedInCallback, onLoggedOutCallback);
    }

}
