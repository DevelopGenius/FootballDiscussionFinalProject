package com.example.footballdiscussion.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.footballdiscussion.databinding.FragmentOwnUserPostsBinding;
import com.example.footballdiscussion.fragments.recycler_adapters.UserPostsRecyclerAdapter;
import com.example.footballdiscussion.utils.LoadingState;
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
        ownUserPostsRecyclerAdapter = new UserPostsRecyclerAdapter(getLayoutInflater(), viewModel.getOwnUserPosts().getValue(), this.viewModel.getCurrentUser().getId());
        binding.ownUserPostsRecyclerView.setAdapter(this.ownUserPostsRecyclerAdapter);
        setListeners(view);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostsViewModel.class);
    }

    private void setListeners(View view) {
        viewModel.getOwnUserPosts().observe(getViewLifecycleOwner(), list -> {
            ownUserPostsRecyclerAdapter.setData(list);
        });

        viewModel.getEventUserPostsLoadingState().observe(getViewLifecycleOwner(), status -> {
            binding.ownUserPostsSwipeRefresh.setRefreshing(status == LoadingState.LOADING);
        });

        binding.ownUserPostsSwipeRefresh.setOnRefreshListener(() -> {
            viewModel.refreshOwnUserPosts();
        });
        ownUserPostsRecyclerAdapter.setOnDeleteClickListener((pos) -> {
            viewModel.deleteUserPost(viewModel.getOwnUserPosts().getValue().get(pos));
        });

        ownUserPostsRecyclerAdapter.setOnIconClickListener(pos -> {
            Navigation.findNavController(view).navigate(OwnUserPostsFragmentDirections
                    .actionOwnUserPostsFragmentToEditOwnUserPostFragment(viewModel.getOwnUserPosts().getValue().get(pos).getId()));
        });

        ownUserPostsRecyclerAdapter.setOnItemClickListener(pos -> {
            OwnUserPostsFragmentDirections.ActionOwnUserPostsFragmentToUserPostDetailsFragment action =
                    OwnUserPostsFragmentDirections
                            .actionOwnUserPostsFragmentToUserPostDetailsFragment
                                    (viewModel.getOwnUserPosts().getValue().get(pos).getId());
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });

    }
}