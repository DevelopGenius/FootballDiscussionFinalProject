package com.example.footballdiscussion.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.databinding.FragmentOwnUserPostsBinding;
import com.example.footballdiscussion.utils.LoadingState;
import com.example.footballdiscussion.fragments.recycler_adapters.UserPostsRecyclerAdapter;
import com.example.footballdiscussion.view_modals.UserPostsViewModel;

public class OwnUserPostsFragment extends Fragment {

    private UserPostsViewModel viewModel;
    private UserPostsRecyclerAdapter ownUserPostsRecyclerAdapter;
    private FragmentOwnUserPostsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOwnUserPostsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.ownUserPostsRecyclerView.setHasFixedSize(true);
        binding.ownUserPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ownUserPostsRecyclerAdapter = new UserPostsRecyclerAdapter(getLayoutInflater(), this.viewModel.getOwnUserPosts(), this.viewModel.getCurrentUser().getId());
        binding.ownUserPostsRecyclerView.setAdapter(this.ownUserPostsRecyclerAdapter);

        viewModel.getAllUserPosts().observe(getViewLifecycleOwner(), list -> {
            ownUserPostsRecyclerAdapter.setData(this.viewModel.getOwnUserPosts());
        });

        viewModel.getEventUserPostsLoadingState().observe(getViewLifecycleOwner(), status -> {
            binding.ownUserPostsSwipeRefresh.setRefreshing(status == LoadingState.LOADING);
        });

        binding.ownUserPostsSwipeRefresh.setOnRefreshListener(() -> {
            viewModel.refreshAllUserPosts();
        });
        ownUserPostsRecyclerAdapter.setOnDeleteClickListener((userPost) -> {
            viewModel.deleteUserPost(userPost);
        });

        ownUserPostsRecyclerAdapter.setOnIconClickListener(userPost -> {
            Navigation.findNavController(view).navigate(OwnUserPostsFragmentDirections
                    .actionOwnUserPostsFragmentToEditOwnUserPostFragment(userPost.getId()));
        });

        ownUserPostsRecyclerAdapter.setOnItemClickListener(pos -> {
            OwnUserPostsFragmentDirections.ActionOwnUserPostsFragmentToUserPostDetailsFragment action =
                    OwnUserPostsFragmentDirections
                            .actionOwnUserPostsFragmentToUserPostDetailsFragment
                                    (viewModel.getOwnUserPosts().get(pos).getId());
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostsViewModel.class);

    }
}