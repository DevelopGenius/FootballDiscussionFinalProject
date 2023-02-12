package com.example.footballdiscussion.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballdiscussion.databinding.FragmentUserPostsBinding;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.utils.LoadingState;
import com.example.footballdiscussion.fragments.recycler_adapters.UserPostsRecyclerAdapter;
import com.example.footballdiscussion.view_modals.UserPostsViewModel;

import java.util.List;

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
        List<UserPost> allUsersPosts = viewModel.getAllUserPosts().getValue();
        userPostsRecyclerAdapter = new UserPostsRecyclerAdapter(getLayoutInflater(), allUsersPosts, this.viewModel.getCurrentUser().getId());
        binding.userPostsRecyclerView.setAdapter(this.userPostsRecyclerAdapter);

        userPostsRecyclerAdapter.setOnItemClickListener(pos -> {
            UserPostsFragmentDirections.ActionUserPostsFragmentToUserPostDetailsFragment action =
                    UserPostsFragmentDirections.actionUserPostsFragmentToUserPostDetailsFragment(allUsersPosts.get(pos).getUserId());
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });

        userPostsRecyclerAdapter.setOnIconClickListener(userPost -> {
            if(!viewModel.isOwnPost(userPost)){
                viewModel.handleUserPostLike(userPost);
            }
        });

        viewModel.getAllUserPosts().observe(getViewLifecycleOwner(),list->{
            userPostsRecyclerAdapter.setData(list);
        });

       viewModel.getEventUserPostsLoadingState().observe(getViewLifecycleOwner(),status->{
            binding.userPostsSwipeRefresh.setRefreshing(status == LoadingState.LOADING);
        });

        binding.userPostsSwipeRefresh.setOnRefreshListener(()->{
            viewModel.refreshAllUserPosts();
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostsViewModel.class);

    }
}