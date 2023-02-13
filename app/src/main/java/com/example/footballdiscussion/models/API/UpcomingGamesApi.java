package com.example.footballdiscussion.models.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UpcomingGamesApi {
    @GET("/fixtures?status=NS&last=20")
    Call<GetUpcomingGamesDto> searchUpcomingGamesForLeague();
}
