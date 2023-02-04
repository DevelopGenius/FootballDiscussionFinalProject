package com.example.footballdiscussion.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.databinding.FragmentRegisterPageBinding;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.models.UserModel;

public class RegisterPageFragment extends Fragment {

    private FragmentRegisterPageBinding binding;

    private RegisterPageViewModel mViewModel;

    public static RegisterPageFragment newInstance() {
        return new RegisterPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterPageBinding.inflate(inflater, container, false);

        binding.registerButton.setOnClickListener(view1 -> {
            String username = binding.usernameRegisterEt.getText().toString();
            String password = binding.passwordRegisterEt.getText().toString();
            String phone = binding.phoneRegisterEt.getText().toString();
            String email = binding.emailRegisterEt.getText().toString();
            User user = new User("3", username, password, phone, email, "");
            UserModel.instance().addUser(user, ()-> {
                Navigation.findNavController(view1).popBackStack();
            });

            UserModel.instance().getAllUsers(data -> {
                System.out.println(data);
            });
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mViewModel = new ViewModelProvider(this).get(RegisterPageViewModel.class);
    }
}