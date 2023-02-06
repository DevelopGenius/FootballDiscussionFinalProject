package com.example.footballdiscussion.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
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

import com.example.footballdiscussion.activities.PostsActivity;
import com.example.footballdiscussion.databinding.FragmentLoginPageBinding;
import com.example.footballdiscussion.view_modals.LoginPageViewModel;

public class LoginPageFragment extends Fragment {

    private LoginPageViewModel viewModel;
    private FragmentLoginPageBinding binding;

    public static LoginPageFragment newInstance() {
        return new LoginPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginPageBinding.inflate(inflater, container, false);


        binding.registerLink.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(LoginPageFragmentDirections.actionLoginPageFragmentToRegisterPageFragment());
        });

        binding.loginButton.setOnClickListener(view -> {
            binding.loginProgressIndicator.show();
            String email = binding.emailEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();
            viewModel.login(email,password, (unused) -> openPostsActivity(), (unused) -> {
                binding.loginProgressIndicator.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Unable to login")
                        .setMessage("Your email or password are incorrect")
                        .setPositiveButton("OK", null)
                        .show();
            });
        });
        return binding.getRoot();

    }

    private void openPostsActivity() {
        Intent postsActivityIntent = new Intent(getActivity(), PostsActivity.class);
        startActivity(postsActivityIntent);
        getActivity().finish();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(LoginPageViewModel.class);

    }
}