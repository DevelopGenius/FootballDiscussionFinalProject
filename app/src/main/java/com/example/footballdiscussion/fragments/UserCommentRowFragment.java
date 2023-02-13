package com.example.footballdiscussion.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class UserCommentRowFragment extends Fragment {
    private com.example.footballdiscussion.databinding.FragmentCommentRowBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = com.example.footballdiscussion.databinding.FragmentCommentRowBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
