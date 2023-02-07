package com.example.footballdiscussion.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.databinding.FragmentUserPostsBinding;
import com.example.footballdiscussion.fragments.recycler_adapters.UserPostsRecyclerAdapter;
import com.example.footballdiscussion.view_modals.UserPostsViewModel;

public class UserPostsFragment extends Fragment {

    private UserPostsViewModel viewModel;
    private UserPostsRecyclerAdapter userPostsRecyclerAdapter;
    private FragmentUserPostsBinding binding;
    public static UserPostsFragment newInstance() {
        return new UserPostsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserPostsBinding.inflate(inflater, container, false);
        binding.userPostsRecyclerView.setHasFixedSize(true);
        binding.userPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userPostsRecyclerAdapter = new UserPostsRecyclerAdapter(getLayoutInflater(), this.viewModel.getUserPosts(), this.viewModel.getCurrentUser().getId());
        binding.userPostsRecyclerView.setAdapter(this.userPostsRecyclerAdapter);

        userPostsRecyclerAdapter.setOnItemClickListener(pos -> {
            Log.d("TAG", "Clicked Row " + pos);
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostsViewModel.class);

    }
}