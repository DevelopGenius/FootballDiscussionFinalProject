package com.example.footballdiscussion.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.footballdiscussion.databinding.FragmentPostDetailsBinding;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.view_modals.UserPostDetailsViewModel;

public class UserPostDetailsFragment extends Fragment {
    private UserPostDetailsViewModel viewModel;
    private String userPostId;
    private FragmentPostDetailsBinding binding;
    public static UserPostRowFragment newInstance(String userPostId) {
        return new UserPostRowFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false);
        this.userPostId = UserPostDetailsFragmentArgs.fromBundle(getArguments()).getUserPostId();

        viewModel.getUserPostById(userPostId).observe(getViewLifecycleOwner(), userPost -> {
            setUserPostData(userPost);
        });


        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostDetailsViewModel.class);
    }

    private void setUserPostData(UserPost userPost){
        if(userPost != null){
            //TODO: set data
        }
    }
}
