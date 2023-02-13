package com.example.footballdiscussion.models.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UpcomingGamesApi {
    @GET("/fixtures?league=39&season=2022&last=20")
    Call<GetUpcomingGamesDto> searchUpcomingGamesForLeague();
}
