package com.example.footballdiscussion.models.firebase;
import androidx.annotation.NonNull;
import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.entities.UserPostComment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import java.util.ArrayList;
import java.util.List;

public class UserPostFirebaseModal {
    FirebaseFirestore db;
    static final String USER_POSTS_COLLECTION = "user_posts";

    public UserPostFirebaseModal() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }


    public void addUserComment(User user, UserPost userPost, String comment, Listener<Void> callback) {
        db.collection(USER_POSTS_COLLECTION).whereEqualTo("id", userPost.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot QuerySnapshots = task.getResult();
                    QuerySnapshots.forEach(queryDocumentSnapshot -> {
                        UserPostComment userPostComment = new UserPostComment(user.getUsername(), comment);
                        queryDocumentSnapshot.getReference().update("userComments", FieldValue.arrayUnion(userPostComment),
                                        "lastUpdated", FieldValue.serverTimestamp())
                                .addOnCompleteListener(command -> {
                                    if (command.isSuccessful()) {
                                        callback.onComplete(null);
                                    }
                                });
                    });
                }
            }
        });
    }

    public void addUserPost(UserPost userPost, Listener<Void> callback) {
        db.collection(USER_POSTS_COLLECTION).document().set(userPost.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.onComplete(null);
            }
        });
    }

    //.whereGreaterThanOrEqualTo(UserPost.LAST_UPDATED, new Timestamp(since, 0))
    public void getAllUserPostsSince(Long since, Listener<List<UserPost>> callback) {
        db.collection(USER_POSTS_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<UserPost> list = new ArrayList<>();
                        if (task.isSuccessful()) {
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json : jsonsList) {
                                UserPost userPost = UserPost.fromJson(json.getData());
                                list.add(userPost);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    public void removeLikeToPost(String userPostId, String userId, Listener<Void> callback) {
        db.collection(USER_POSTS_COLLECTION).whereEqualTo("id", userPostId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot QuerySnapshots = task.getResult();
                    QuerySnapshots.forEach(queryDocumentSnapshot -> {
                        queryDocumentSnapshot.getReference().update("usersLike", FieldValue.arrayRemove(userId),
                                "lastUpdated", FieldValue.serverTimestamp()).addOnCompleteListener(command -> {
                            if (command.isSuccessful()) {
                                callback.onComplete(null);
                            }
                        });
                    });
                }
            }
        });
    }

    public void addLikeToPost(String userPostId, String userId, Listener<Void> callback) {
        db.collection(USER_POSTS_COLLECTION).whereEqualTo("id", userPostId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot QuerySnapshots = task.getResult();
                    QuerySnapshots.forEach(queryDocumentSnapshot -> {
                        queryDocumentSnapshot.getReference().update("usersLike", FieldValue.arrayUnion(userId),
                                "lastUpdated", FieldValue.serverTimestamp()).addOnCompleteListener(command -> {
                            if (command.isSuccessful()) {
                                callback.onComplete(null);
                            }
                        });
                    });

                }
            }
        });
    }

    public void deleteUserPost(String userPostId, Listener<Void> callback) {
        db.collection(USER_POSTS_COLLECTION).whereEqualTo("id", userPostId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot QuerySnapshots = task.getResult();
                    QuerySnapshots.forEach(queryDocumentSnapshot -> {
                        queryDocumentSnapshot.getReference().update("isDeleted", true,
                                "lastUpdated", FieldValue.serverTimestamp()).addOnCompleteListener(command -> {
                            if (command.isSuccessful()) {
                                callback.onComplete(null);
                            }
                        });
                    });

                }
            }
        });
    }

    public void updateUserPost(UserPost userPost, Listener<Void> successCallback, Listener<String> failCallback) {
        db.collection(USER_POSTS_COLLECTION).whereEqualTo("id", userPost.getId()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            queryDocumentSnapshots.forEach(queryDocumentSnapshot -> {
                queryDocumentSnapshot.getReference().update("postTitle", userPost.getPostTitle(),
                        "imageUrl", userPost.getImageUrl(),
                        "lastUpdated", FieldValue.serverTimestamp()).addOnSuccessListener(unused -> {
                    successCallback.onComplete(null);
                }).addOnFailureListener(e -> {
                    failCallback.onComplete(e.getMessage());
                });
            });
        }).addOnFailureListener(e -> {
            failCallback.onComplete(e.getMessage());
        });
    }

    public void updateUserPostsUsername(String userId, String username, Listener<Void> successCallback, Listener<String> failCallback) {
        db.collection(USER_POSTS_COLLECTION).whereEqualTo("userId", userId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            WriteBatch writeBatch = db.batch();
            queryDocumentSnapshots.forEach(queryDocumentSnapshot -> {
                writeBatch.update(queryDocumentSnapshot.getReference(), "username", username,
                        "lastUpdated", FieldValue.serverTimestamp());
            });
            writeBatch.commit().addOnSuccessListener(unused -> {
                successCallback.onComplete(null);
            }).addOnFailureListener(e -> {
                failCallback.onComplete(e.getMessage());
            });
        }).addOnFailureListener(e -> {
            failCallback.onComplete(e.getMessage());
        });
    }
}
