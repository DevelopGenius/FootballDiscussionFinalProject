package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.models.UserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserPostsViewModel extends ViewModel {
    public List<UserPost> getUserPosts() {
        List<UserPost> list = new ArrayList<>();
        list.add(new UserPost("1","1","John", "What will happen in the GAME?", "URL", false, false));
        list.add(new UserPost("2","2","Adam", "NEW postttttttttttt?", "URL", true, false));
        list.add(new UserPost("3","1","John", "What will happen in the GAME?", "URL", false, true));
        list.add(new UserPost("4","1","John", "What will happen in the GAME?", "URL", false, false));

        return list;
    }

    public List<UserPost> getOwnUserPosts() {
        List<UserPost> list = new ArrayList<>();
        list.add(new UserPost("2","2","Adam", "NEW postttttttttttt?", "URL", true, false));

        return list;
    }
}