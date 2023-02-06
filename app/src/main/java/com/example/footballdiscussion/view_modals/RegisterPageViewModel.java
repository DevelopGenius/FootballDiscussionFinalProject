package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.enums.LoadingState;
import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.models.UserModel;

public class RegisterPageViewModel extends ViewModel {
    private UserModel userModel = UserModel.instance();

    public MutableLiveData<LoadingState> getEventUserLoadingState() {
        return userModel.getEventUserLoadingState();
    }

    public void addUser(User user, String password, Listener<User> listener) {
        userModel.addUser(user,password, listener);
    }
}