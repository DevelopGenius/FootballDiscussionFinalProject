package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.models.models.UpcomingGameModel;
import com.example.footballdiscussion.utils.LoadingState;


import java.util.List;

public class UpcomingGamesViewModel extends ViewModel {

    UpcomingGameModel upcomingGameModel = UpcomingGameModel.getInstance();
    public LiveData<List<UpcomingGame>> getUpcomingGames() {
        return upcomingGameModel.getAllUpcomingGames();
    }

    public void refreshAllUpcomingGames(){
        upcomingGameModel.refreshAllUpcomingGames();;
    }

    public MutableLiveData<LoadingState> getUpcomingGamesLoadingState(){
        return upcomingGameModel.upcomingGamesLoadingState;
    }
}