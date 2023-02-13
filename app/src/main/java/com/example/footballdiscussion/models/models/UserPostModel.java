package com.example.footballdiscussion.models.models;

import android.graphics.Bitmap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.footballdiscussion.models.firebase.FirebaseImageStorage;
import com.example.footballdiscussion.models.firebase.UserPostFirebaseModal;
import com.example.footballdiscussion.utils.LoadingState;
import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserPostModel {
    private static final UserPostModel _instance = new UserPostModel();

    private Executor executor = Executors.newSingleThreadExecutor();
    private UserModel userModel = UserModel.instance();
    private UserPostFirebaseModal userPostFirebaseModal = new UserPostFirebaseModal();
    private FirebaseImageStorage firebaseImageStorage = new FirebaseImageStorage();
    FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();

    final public MutableLiveData<LoadingState> eventUserPostsLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    private LiveData<List<UserPost>> userPostList;

    public static UserPostModel instance() {
        return _instance;
    }

    private UserPostModel() {
    }

    public LiveData<UserPost> getUserPostById(String userPostId) {
        return localDb.userPostDao().getUserPostById(userPostId);
    }


    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseImageStorage.uploadImage(name, bitmap, listener);
    }

    public void publishUserPost(UserPost userPost, Listener<Void> callback) {
        userPostFirebaseModal.addUserPost(userPost, (Void) -> {
            refreshAllUserPosts();
            callback.onComplete(null);
        });
    }

    public void publishUserComment(String comment, UserPost userPost, Listener<Void> callback) {
        userPostFirebaseModal.addUserComment(userModel.getCurrentLogInUser(), userPost, comment, (Void) -> {
            callback.onComplete(null);
        });
    }

    public void refreshAllUserPosts() {
        eventUserPostsLoadingState.setValue(LoadingState.LOADING);
        Long localLastUpdate = UserPost.getLocalLastUpdate();
        userPostFirebaseModal.getAllUserPostsSince(localLastUpdate, list -> {
            executor.execute(() -> {
                Long time = localLastUpdate;

                for (UserPost userPost : list) {
                    if (userPost.isDeleted()) {
                        localDb.userPostDao().delete(userPost);
                    } else {
                        localDb.userPostDao().insertAll(userPost);
                    }
                    if (time < userPost.getLastUpdated()) {
                        time = userPost.getLastUpdated();
                    }
                }
                UserPost.setLocalLastUpdate(time);
                eventUserPostsLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public LiveData<List<UserPost>> getAllUserPosts() {
        if (userPostList == null) {
            userPostList = localDb.userPostDao().getAll();
            refreshAllUserPosts();
        }
        return userPostList;
    }

    public MutableLiveData<LoadingState> getEventUserPostsLoadingState() {
        return eventUserPostsLoadingState;
    }

    public void handleUserPostLike(UserPost userPost, String userId) {
        if (userPost.getUserLikes().contains(userId)) {
            userPostFirebaseModal.removeLikeToPost(userPost.getId(), userId, (unused) -> {
                executor.execute(() -> {
                    userPost.getUserLikes().remove(userId);
                    localDb.userPostDao().insertAll(userPost);
                });
            });
        } else {
            userPostFirebaseModal.addLikeToPost(userPost.getId(), userId, (unused) -> {
                executor.execute(() -> {
                    userPost.getUserLikes().add(userId);
                    localDb.userPostDao().insertAll(userPost);
                });
            });
        }
    }

    public void deleteUserPost(UserPost userPost) {
        userPostFirebaseModal.deleteUserPost(userPost.getId(), (unused) -> executor.execute(() -> {
                    localDb.userPostDao().delete(userPost);
                })
        );
    }

    public void updateUserPost(UserPost userPost, Listener<Void> successCallback, Listener<String> failCallback) {
        userPostFirebaseModal.updateUserPost(userPost, (unused) -> {
            refreshAllUserPosts();
            successCallback.onComplete(null);

        }, failCallback);
    }

    public void updateUserPostsUsername(String userId, String username, Listener<Void> successCallback, Listener<String> failCallback) {
        userPostFirebaseModal.updateUserPostsUsername(userId, username, (unused) -> {
            refreshAllUserPosts();
            successCallback.onComplete(null);
        }, failCallback);
    }
}
