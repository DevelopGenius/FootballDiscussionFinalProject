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

import com.example.footballdiscussion.databinding.FragmentUpcomingGamesBinding;
import com.example.footballdiscussion.utils.LoadingState;
import com.example.footballdiscussion.fragments.recycler_adapters.UpcomingGamesRecyclerAdapter;
import com.example.footballdiscussion.models.models.UpcomingGameModel;
import com.example.footballdiscussion.view_modals.UpcomingGamesViewModel;

public class UpcomingGamesFragment extends Fragment {

    private FragmentUpcomingGamesBinding binding;

    private UpcomingGamesRecyclerAdapter upcomingGamesRecyclerAdapter;
    private UpcomingGamesViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUpcomingGamesBinding.inflate(inflater, container, false);

        binding.upcomingGamesRecyclerView.setHasFixedSize(true);
        binding.upcomingGamesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        upcomingGamesRecyclerAdapter = new UpcomingGamesRecyclerAdapter(getLayoutInflater(), viewModel.getUpcomingGames().getValue());
        binding.upcomingGamesRecyclerView.setAdapter(upcomingGamesRecyclerAdapter);
        setListeners();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UpcomingGamesViewModel.class);
    }

    public void setListeners(){
        binding.upcomingGamesSwipeRefresh.setOnRefreshListener(() -> {
            viewModel.refreshAllUpcomingGames();
        });

        viewModel.getUpcomingGamesLoadingState().observe(getViewLifecycleOwner(), status -> {
            binding.upcomingGamesSwipeRefresh.setRefreshing(status == LoadingState.LOADING);
        });

        viewModel.getUpcomingGames().observe(getViewLifecycleOwner(), list -> {
            upcomingGamesRecyclerAdapter.setData(list);
        });
    }
}