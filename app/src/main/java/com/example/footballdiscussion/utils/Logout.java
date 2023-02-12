package com.example.footballdiscussion.utils;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.models.UserModel;

public class Logout {
    private static UserModel userModel = UserModel.instance();

    public static void logout(Listener<Void> listener) {
        userModel.logout(listener);
    }
}
