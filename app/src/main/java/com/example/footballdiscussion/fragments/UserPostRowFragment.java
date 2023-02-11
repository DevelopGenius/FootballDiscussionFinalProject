package com.example.footballdiscussion.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.footballdiscussion.databinding.FragmentUserPostsBinding;
import com.example.footballdiscussion.enums.LoadingState;
import com.example.footballdiscussion.fragments.recycler_adapters.UserPostsRecyclerAdapter;
import com.example.footballdiscussion.view_modals.UserPostRowViewModel;
import com.example.footballdiscussion.view_modals.UserPostsViewModel;

public class UserPostRowFragment extends Fragment {
    private UserPostRowViewModel viewModel;
    private FragmentUserPostsBinding binding;
    public static UserPostRowFragment newInstance() {
        return new UserPostRowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserPostsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostRowViewModel.class);
    }
}
