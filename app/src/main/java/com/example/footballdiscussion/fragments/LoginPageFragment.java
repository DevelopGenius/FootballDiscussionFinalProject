package com.example.footballdiscussion.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.footballdiscussion.databinding.FragmentLoginPageBinding;
import com.example.footballdiscussion.view_modals.LoginPageViewModel;

public class LoginPageFragment extends Fragment {

    private LoginPageViewModel mViewModel;
    private FragmentLoginPageBinding binding;

    public static LoginPageFragment newInstance() {
        return new LoginPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginPageBinding.inflate(inflater, container, false);

//        firebaseAuth = FirebaseAuth.getInstance();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();

//                firebaseAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Login successful, navigate to main activity
//                                } else {
//                                    // Login failed, display error message
//                                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
            }
        });
        binding.registerLink.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(LoginPageFragmentDirections.actionLoginPageFragmentToRegisterPageFragment());
        });

        binding.loginButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(LoginPageFragmentDirections.actionLoginPageFragmentToUpcomingGamesFragment());
        });
        return binding.getRoot();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = new ViewModelProvider(this).get(LoginPageViewModel.class);

    }
}