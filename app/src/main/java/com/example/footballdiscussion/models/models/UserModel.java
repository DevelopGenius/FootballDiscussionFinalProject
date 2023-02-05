package com.example.footballdiscussion.models.models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.footballdiscussion.enums.LoadingState;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.firebase.FirebaseModel;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserModel {
    private static final UserModel _instance = new UserModel();

    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel = new FirebaseModel();
    FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();
    final public MutableLiveData<LoadingState> EventUsersListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    public static UserModel instance(){
        return _instance;
    }

    private UserModel(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }

//    private LiveData<List<User>> usersList;
//    public LiveData<List<User>> getAllUsers() {
//        if(usersList == null){
//            usersList = localDb.userDao().getAll();
//            refreshAllUsers();
//        }
//        return usersList;
//    }

    public void refreshAllUsers(){
        EventUsersListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = User.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllUsersSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(User user:list){
                    // insert new records into ROOM
                    localDb.userDao().insertAll(user);
                    if (time < user.getLastUpdated()){
                        time = user.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                User.setLocalLastUpdate(time);
                EventUsersListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addUser(User user, Listener<Void> listener){
        firebaseModel.addUser(user,(Void)->{
            refreshAllUsers();
            listener.onComplete(null);
        });
    }
}
