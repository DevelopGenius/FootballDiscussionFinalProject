package com.example.footballdiscussion.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentCreatePostBinding;
import com.example.footballdiscussion.databinding.FragmentUserPostsBinding;
import com.example.footballdiscussion.view_modals.UserPostsViewModel;


public class CreatePostFragment extends Fragment {
    private UserPostsViewModel viewModel;

    private FragmentCreatePostBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostsViewModel.class);

    }
}