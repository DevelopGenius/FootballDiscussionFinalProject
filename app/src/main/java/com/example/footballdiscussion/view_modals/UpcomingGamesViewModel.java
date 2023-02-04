package com.example.footballdiscussion.view_modals;

import androidx.lifecycle.ViewModel;

import com.example.footballdiscussion.models.entities.UpcomingGame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpcomingGamesViewModel extends ViewModel {
    public List<UpcomingGame> getUpcomingGames() {
        List<UpcomingGame> list = new ArrayList<>();
        list.add(new UpcomingGame("1","premier league", new Date(), "Arsenal", "Manchester United"));
        list.add(new UpcomingGame("1","premier league", new Date(), "Arsenal", "Manchester United"));
        list.add(new UpcomingGame("1","premier league", new Date(), "Arsenal", "Manchester United"));
        list.add(new UpcomingGame("1","premier league", new Date(), "Arsenal", "Manchester United"));

        return list;
    }
}