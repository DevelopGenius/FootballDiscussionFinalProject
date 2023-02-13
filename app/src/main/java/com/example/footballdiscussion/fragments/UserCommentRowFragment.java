package com.example.footballdiscussion.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.footballdiscussion.view_modals.UserCommentRowViewModel;
import com.example.footballdiscussion.view_modals.UserPostRowViewModel;

public class UserCommentRowFragment extends Fragment {
    private UserCommentRowViewModel viewModel;
    private com.example.footballdiscussion.databinding.FragmentCommentRowBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = com.example.footballdiscussion.databinding.FragmentCommentRowBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserCommentRowViewModel.class);
    }
}
