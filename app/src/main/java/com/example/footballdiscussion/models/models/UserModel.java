package com.example.footballdiscussion.models.models;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.footballdiscussion.utils.LoadingState;
import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.firebase.FirebaseAuthentication;
import com.example.footballdiscussion.models.firebase.FirebaseModel;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserModel {
    private static final UserModel _instance = new UserModel();
    private FirebaseAuthentication firebaseAuthentication;
    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel = new FirebaseModel();
    FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();
    final public MutableLiveData<LoadingState> EventUsersListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    final public MutableLiveData<LoadingState> eventLoggedInUserLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    private final MutableLiveData<User> currentLoggedInUser = new MutableLiveData<>(null);

    public static UserModel instance() {
        return _instance;
    }

    private UserModel() {
        firebaseAuthentication = new FirebaseAuthentication();
    }


//    private LiveData<List<User>> usersList;
//    public LiveData<List<User>> getAllUsers() {
//        if(usersList == null){
//            usersList = localDb.userDao().getAll();
//            refreshAllUsers();
//        }
//        return usersList;
//    }

    public void refreshAllUsers() {
        EventUsersListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = User.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllUsersSince(localLastUpdate, list -> {
            executor.execute(() -> {
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for (User user : list) {
                    // insert new records into ROOM
                    localDb.userDao().insertAll(user);
                    if (time < user.getLastUpdated()) {
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

    public void addUser(User user, String password, Listener<Void> listener) {
        firebaseAuthentication.register(user.getEmail(), password, (userId) -> {
            user.setId(userId);
            firebaseModel.addUser(user, (newUser) -> {
                addLoggedInUserToCache(newUser, listener);
            });
        });
    }

    private void addLoggedInUserToCache(User user, Listener<Void> listener) {
        executor.execute(() -> {
            localDb.userDao().insertAll(user);
            currentLoggedInUser.postValue(user);
            listener.onComplete(null);
        });
    }

    public User getCurrentLogInUser() {
        return currentLoggedInUser.getValue();
    }

    public void logout(Listener<Void> listener) {
        firebaseAuthentication.logout((unused) -> {
            currentLoggedInUser.postValue(null);
            listener.onComplete(null);
        });
    }

    public void login(String email, String password, Listener<Void> onSuccessCallback, Listener<Void> onFailureCallback) {
        firebaseAuthentication.login(email, password, (unused) -> successfulLogin(email, onSuccessCallback), onFailureCallback);
    }

    public void successfulLogin(String email, Listener<Void> onSuccessCallback) {
        firebaseModel.getUserByEmail(email, (user) -> addLoggedInUserToCache(user, onSuccessCallback));
    }


    public void getUserByEmail(String email, Listener<Void> callback) {
        eventLoggedInUserLoadingState.setValue(LoadingState.LOADING);
        firebaseModel.getUserByEmail(email, (user) -> {
            addLoggedInUserToCache(user, (unused) -> {
                eventLoggedInUserLoadingState.postValue(LoadingState.NOT_LOADING);
                callback.onComplete(null);
            });
        });
    }

    public void userLoggedInHandler(Listener<Void> onLoggedInCallback, Listener<Void> onLoggedOutCallback) {
        if (firebaseAuthentication.isUserLoggedIn()) {
            String loggedInEmail = firebaseAuthentication.getCurrentLogInUserEmail();

            getUserByEmail(loggedInEmail, onLoggedInCallback);
        } else {
            onLoggedOutCallback.onComplete(null);
        }
    }

    public void updateUserProfile(String username, String email, String phone,
                                  Bitmap bitmap,
                                  Listener<Void> successCallback,
                                  Listener<String> failCallback) {
        User oldUser = currentLoggedInUser.getValue();
        validateNewEmail(oldUser, email, (unused) -> {
            validateNewUsername(oldUser, username, (unused1) -> {
                oldUser.setEmail(email);
                oldUser.setPhone(phone);
                oldUser.setUsername(username);
                if (bitmap != null) {
                    firebaseModel.uploadImage(username, bitmap, (url) -> {
                        oldUser.setImageUrl(url);
                        saveUpdatedUser(oldUser, successCallback, failCallback);
                    });
                } else {
                    saveUpdatedUser(oldUser, successCallback, failCallback);
                }
            }, failCallback);
        }, failCallback);
    }

    public void saveUpdatedUser(User user, Listener<Void> successCallback,
                                Listener<String> failCallback) {
        firebaseAuthentication.updateCurrentUserEmail(user.getEmail(), (unused) ->{
            firebaseModel.updateUser(user, (unused1 -> {
                addLoggedInUserToCache(user, successCallback);
            }));
        }, failCallback);
    }

    private void validateNewEmail(User oldUser, String email,
                                  Listener<Void> successCallback,
                                  Listener<String> failCallback) {
        if (oldUser.getEmail().equals(email)) {
            successCallback.onComplete(null);
        } else {
            validateEmail(email, successCallback, failCallback);
        }
    }

    private void validateEmail(String email, Listener<Void> successCallback, Listener<String> failCallback) {
        firebaseAuthentication.isEmailExists(email, (isExist) -> {
            if (isExist) {
                failCallback.onComplete("This email already exists");
            } else {
                successCallback.onComplete(null);
            }
        }, failCallback);
    }

    private void validateNewUsername(User oldUser, String username, Listener<Void> successCallback, Listener<String> failCallback) {
        if (oldUser.getUsername().equals(username)) {
            successCallback.onComplete(null);
        } else {
            firebaseModel.isUsernameExists(username, (isExist) -> {
                if (isExist) {
                    failCallback.onComplete("This username already exists");
                } else {
                    successCallback.onComplete(null);
                }
            });
        }
    }
}
