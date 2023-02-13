package com.example.footballdiscussion.models.firebase;

import androidx.annotation.NonNull;

import com.example.footballdiscussion.models.common.Listener;
import com.example.footballdiscussion.models.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class UserFirebaseModal {
    FirebaseFirestore db;
    static final String USERS_COLLECTION = "users";

    public UserFirebaseModal() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
//        storage = FirebaseStorage.getInstance();
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

}
