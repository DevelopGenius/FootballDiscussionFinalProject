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
    public List<UpcomingGame> getUpcomingGames() {
        List<UpcomingGame> list = new ArrayList<>();
        list.add(new UpcomingGame("1","premier league", "27.7.2022", "Arsenal", "Manchester United"));
        list.add(new UpcomingGame("1","premier league", "28.7.2022", "Arsenal", "Manchester United"));
        list.add(new UpcomingGame("1","premier league", "29.7.2022", "Arsenal", "Manchester United"));
        list.add(new UpcomingGame("1","premier league", "30.7.2022", "Arsenal", "Manchester United"));
        return list;
    }
}