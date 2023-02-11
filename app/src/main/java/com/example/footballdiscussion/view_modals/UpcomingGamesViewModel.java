package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.models.models.UpcomingGameModel;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpcomingGamesViewModel extends ViewModel {
    public final FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();
    public void getUpcomingGames() {
        UpcomingGameModel.getInstance().refreshAllUpcomingGames();
    }
}