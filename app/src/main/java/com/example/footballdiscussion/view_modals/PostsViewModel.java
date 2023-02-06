package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.models.UserModel;

public class PostsViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();

    public void logout(Listener<Void> listener) {
        userModel.logout(listener);
    }

}
