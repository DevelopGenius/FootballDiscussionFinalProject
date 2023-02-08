package com.example.footballdiscussion.models.API;

import com.example.footballdiscussion.models.entities.UpcomingGame;

import java.util.List;

public class GetUpcomingGamesDto {
    private List<UpcomingGame> response;

    public List<UpcomingGame> getResponse() {
        return this.response;
    }

    public void setResults(List<UpcomingGame> results) {
        this.response = results;
    }
}
