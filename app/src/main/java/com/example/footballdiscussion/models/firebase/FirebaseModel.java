package com.example.footballdiscussion.models.firebase;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;
    static final String USERS_COLLECTION = "users";
    static final String USER_POSTS_COLLECTION = "user_posts";

    public FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
    }

    public void getUserByEmail(String email, Listener<User> callback) {
        db.collection(USERS_COLLECTION).whereEqualTo("email", email)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonList = task.getResult();
                        User user = null;
                        for (DocumentSnapshot json : jsonList) {
                            user = User.fromJson(json.getData());
                        }

                        callback.onComplete(user);
                    }
                });
    }

    public void isUsernameExists(String username, Listener<Boolean> callback) {
        db.collection(USERS_COLLECTION).whereEqualTo("name", username)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonList = task.getResult();
                        callback.onComplete(!jsonList.isEmpty());
                    }
                });
    }

    public void getAllUsersSince(Long since, Listener<List<User>> callback) {
        db.collection(USERS_COLLECTION)
                .whereGreaterThanOrEqualTo(User.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<User> list = new LinkedList<>();
                        if (task.isSuccessful()) {
                            QuerySnapshot jsonList = task.getResult();
                            for (DocumentSnapshot json : jsonList) {
                                User user = User.fromJson(json.getData());
                                list.add(user);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    public void updateUser(User user, Listener<Void> callback) {
        db.collection(USERS_COLLECTION).whereEqualTo("id", user.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot QuerySnapshots = task.getResult();
                    QuerySnapshots.forEach(queryDocumentSnapshot -> {
                        queryDocumentSnapshot.getReference().update("name", user.getUsername(),
                                "email", user.getEmail(),
                                "phone", user.getPhone(),
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

    public void addUser(User user, Listener<User> listener) {
        db.collection(USERS_COLLECTION).add(user.toJson())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentReference.get().addOnSuccessListener(documentSnapshot ->
                                listener.onComplete(User.fromJson(documentSnapshot.getData())));

                    }
                });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
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

    public void getUserPostById(String userPostId, Listener<UserPost> callback) {
        db.collection(USER_POSTS_COLLECTION).whereEqualTo("id", userPostId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonList = task.getResult();
                        UserPost userPost = null;
                        for (DocumentSnapshot json : jsonList) {
                            userPost = UserPost.fromJson(json.getData());
                        }
                        callback.onComplete(userPost);
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
}

