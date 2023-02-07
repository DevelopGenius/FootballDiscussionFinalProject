package com.example.footballdiscussion.models.models;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.firebase.FirebaseModel;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserPostModel {
    private static final UserPostModel _instance = new UserPostModel();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();

    public static UserPostModel instance(){
        return _instance;
    }
    private UserPostModel(){
    }


    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }

    public void publishUserPost(UserPost userPost, Listener<Void> callback){
        firebaseModel.addUserPost(userPost,(Void)->{

            //TODO: ADD refresh posts after insert
           //refreshAllPosts();
            callback.onComplete(null);
        });
    }
}
