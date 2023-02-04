package com.example.footballdiscussion.models.models;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.firebase.FirebaseModel;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserModel {
    private static final UserModel _instance = new UserModel();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();

    public static UserModel instance(){
        return _instance;
    }
    private UserModel(){
    }

    FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();
    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }

    public void getAllUsers(GetAllUsersListener callback){
        firebaseModel.getAllUsers(callback);
//        executor.execute(()->{
//            List<User> data = localDb.userDao().getAll();
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mainHandler.post(()->{
//                callback.onComplete(data);
//            });
//        });
    }

    public interface AddUserListener{
        void onComplete();
    }

    public void addUser(User user, AddUserListener userListener){
        firebaseModel.addUser(user, userListener);
//        executor.execute(()->{
//            localDb.userDao().insertAll(user);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("HEGIA");
//            mainHandler.post(()->{
//                userListener.onComplete();
//            });
//        });
    }
}
