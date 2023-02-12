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

import com.example.footballdiscussion.databinding.FragmentUserCommentsBinding;
import com.example.footballdiscussion.fragments.recycler_adapters.UserCommentsRecyclerAdapter;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPostComment;
import com.example.footballdiscussion.view_modals.UserCommentsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserCommentsFragment extends Fragment {

    private UserCommentsViewModel viewModel;
    private UserCommentsRecyclerAdapter userCommentsRecyclerAdapter;
    private FragmentUserCommentsBinding binding;
    public static UserCommentsFragment newInstance() {
        return new UserCommentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserCommentsBinding.inflate(inflater, container, false);
        binding.userCommentsRecyclerView.setHasFixedSize(true);
        binding.userCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<UserPostComment> newList = new ArrayList<>();
        newList.add(new UserPostComment("aaa","Cool game 1"));
        newList.add(new UserPostComment("bbb","Cool game 2"));
        newList.add(new UserPostComment("ccc","Cool game 3"));
        newList.add(new UserPostComment("ddd","Cool game 4"));
        userCommentsRecyclerAdapter = new UserCommentsRecyclerAdapter(getLayoutInflater(), newList, this.viewModel.getCurrentUser().getId());
        binding.userCommentsRecyclerView.setAdapter(this.userCommentsRecyclerAdapter);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserCommentsViewModel.class);
    }
}
