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
import com.example.footballdiscussion.fragments.recycler_adapters.UpcomingGamesRecyclerAdapter;
import com.example.footballdiscussion.models.models.UpcomingGameModel;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;
import com.example.footballdiscussion.view_modals.UpcomingGamesViewModel;

public class UpcomingGamesFragment extends Fragment {

    private FragmentUpcomingGamesBinding binding;

    private UpcomingGamesRecyclerAdapter upcomingGamesRecyclerAdapter;
    private UpcomingGamesViewModel viewModel;

    public static UpcomingGamesFragment newInstance() {
        return new UpcomingGamesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUpcomingGamesBinding.inflate(inflater, container, false);

        binding.upcomingGamesRecyclerView.setHasFixedSize(true);
        binding.upcomingGamesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        upcomingGamesRecyclerAdapter = new UpcomingGamesRecyclerAdapter(getLayoutInflater(), this.viewModel.getUpcomingGames());
        binding.upcomingGamesRecyclerView.setAdapter(this.upcomingGamesRecyclerAdapter);

        upcomingGamesRecyclerAdapter.setOnItemClickListener(pos -> {
            Log.d("TAG", "Clicked Row " + pos);
        });

        final FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();
        UpcomingGameModel.getInstance().refreshAllUpcomingGames();
        localDb.upcomingGameDao().getAll().observe(getViewLifecycleOwner(), list -> {
            upcomingGamesRecyclerAdapter.setData(list);
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UpcomingGamesViewModel.class);
    }
}