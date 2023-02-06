package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.models.UserModel;

public class RegisterPageViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();


    public void addUser(User user, String password, Listener<Void> listener) {
        userModel.addUser(user,password, listener);
    }
}