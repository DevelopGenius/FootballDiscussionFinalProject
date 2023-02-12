package com.example.footballdiscussion.models.firebase;

import android.util.Log;

import com.example.footballdiscussion.models.common.Listener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class FirebaseAuthentication {
    private FirebaseAuth firebaseAuth;

    public FirebaseAuthentication() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void register(String email, String password, Listener<String> callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Task<AuthResult> task) -> {
            if (task.isSuccessful() && task.getResult().getUser() != null) {
                callback.onComplete(task.getResult().getUser().getUid());
            }
        }).addOnFailureListener((e) -> Log.d("TAG", "register: " + e));
    }


    public String getCurrentLogInUserEmail() {
        return firebaseAuth.getCurrentUser().getEmail();
    }

    public boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public void logout(Listener<Void> callback) {
        firebaseAuth.signOut();
        callback.onComplete(null);
    }

    public void login(String email, String password, Listener<Void> onSuccessCallback, Listener<Void> onFailureCallback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            onSuccessCallback.onComplete(null);
        }).addOnFailureListener(e -> {
            Log.d("TAG", "login: " + e);
            onFailureCallback.onComplete(null);
        });
    }

    public void isEmailExists(String email, Listener<Boolean> callback, Listener<String> failCallback) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnSuccessListener((SignInMethodQueryResult signInMethodQueryResult) -> {
                    boolean isNewUser = signInMethodQueryResult.getSignInMethods().isEmpty();
                    callback.onComplete(isNewUser);

                }).addOnFailureListener(e -> {
                    failCallback.onComplete(e.getMessage());
                });
    }

    public void updateCurrentUserEmail(String email, Listener<Void> callback, Listener<String> failCallback) {
        firebaseAuth.getCurrentUser().updateEmail(email).addOnSuccessListener(unused -> callback.onComplete(null)).addOnFailureListener(e ->
                failCallback.onComplete(e.getMessage()));
    }

}
