package com.example.footballdiscussion.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.footballdiscussion.databinding.FragmentUserPostRowBinding;

public class UserPostRowFragment extends Fragment {
    private FragmentUserPostRowBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserPostRowBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
