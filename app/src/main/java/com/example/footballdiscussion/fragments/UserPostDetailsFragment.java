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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.footballdiscussion.R;
import com.example.footballdiscussion.databinding.FragmentPostDetailsBinding;
import com.example.footballdiscussion.fragments.recycler_adapters.UserCommentsRecyclerAdapter;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.entities.UserPostComment;
import com.example.footballdiscussion.utils.ImageUtils;
import com.example.footballdiscussion.view_modals.UserPostDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserPostDetailsFragment extends Fragment {
    private UserPostDetailsViewModel viewModel;
    private String userPostId;
    private FragmentPostDetailsBinding binding;
    private UserCommentsRecyclerAdapter userCommentsRecyclerAdapter;

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

        binding.userCommentsRecyclerView.setHasFixedSize(true);
        binding.userCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<UserPostComment> newList = new ArrayList<>();
        newList.add(new UserPostComment("aaa","Cool game 1"));
        newList.add(new UserPostComment("bbb","Cool game 2"));
        newList.add(new UserPostComment("ccc","Cool game 3"));
        newList.add(new UserPostComment("ddd","Cool game 4"));
        newList.add(new UserPostComment("eee","Cool game 5"));
        newList.add(new UserPostComment("fff","Cool game 6"));
        newList.add(new UserPostComment("fff","Cool game 6"));
        newList.add(new UserPostComment("fff","Cool game 6"));
        newList.add(new UserPostComment("fff","Cool game 6"));
        newList.add(new UserPostComment("fff","Cool game 6"));
        userCommentsRecyclerAdapter = new UserCommentsRecyclerAdapter(getLayoutInflater(), newList, this.viewModel.getCurrentUser().getId());
        binding.userCommentsRecyclerView.setAdapter(this.userCommentsRecyclerAdapter);
        userCommentsRecyclerAdapter.setData(newList);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserPostDetailsViewModel.class);
    }

    private void setUserPostData(UserPost userPost){
        if(userPost != null){
            binding.postDetailsRowTitle.setText(userPost.getPostTitle());
            binding.postDetailsRowUsername.setText(userPost.getUsername());
            ImageUtils.loadImage(userPost.getImageUrl(), binding.postDetailsRowImage, R.drawable.football_stadium);
        }
    }
}
