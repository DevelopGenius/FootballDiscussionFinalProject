package com.example.footballdiscussion.models.API;

import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface UpcomingGamesApi {
    @Headers({"x-apisports-key: 9f6bb1ad071d43e9090651f98edb5b3c"})
    @GET("/leagues?id=39&season=2022")
    Call<List<UpcomingGame>> searchUpcomingGamesForLeague();

    @Headers({"x-apisports-key: 9f6bb1ad071d43e9090651f98edb5b3c"})
    @GET("/status")
    Call<Gson> getStatus();
}
