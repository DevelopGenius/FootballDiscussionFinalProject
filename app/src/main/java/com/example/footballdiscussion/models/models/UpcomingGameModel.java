package com.example.footballdiscussion.models.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.footballdiscussion.enums.LoadingState;
import com.example.footballdiscussion.models.API.UpcomingGameAdapter;
import com.example.footballdiscussion.models.API.UpcomingGamesApi;
import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingGameModel {
    final public static UpcomingGameModel _instance = new UpcomingGameModel();
    private final FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<LoadingState> upcomingGamesLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    final String BASE_URL = "https://v3.football.api-sports.io";
    Retrofit retrofit;
    UpcomingGamesApi upcomingGamesApi;

    private UpcomingGameModel() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UpcomingGame.class, new UpcomingGameAdapter())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        upcomingGamesApi = retrofit.create(UpcomingGamesApi.class);
    }

    public void refreshAllUpcomingGames() {
        this.upcomingGamesLoadingState.setValue(LoadingState.LOADING);
        this.upcomingGamesApi.searchUpcomingGamesForLeague().enqueue(new Callback<List<UpcomingGame>>() {
            @Override
            public void onResponse(Call<List<UpcomingGame>> call, Response<List<UpcomingGame>> upcomingGamesResponse) {
                if (upcomingGamesResponse.isSuccessful()) {
                    executor.execute(() -> {
                        if (upcomingGamesResponse.isSuccessful()) {
                            System.out.println(upcomingGamesResponse.body());
                            upcomingGamesLoadingState.postValue(LoadingState.NOT_LOADING);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<UpcomingGame>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<Gson> getStatus() {
        MutableLiveData<Gson> gsonData = new MutableLiveData<>();
        Call<Gson> call = upcomingGamesApi.getStatus();
        call.enqueue(new Callback<Gson>() {
            @Override
            public void onResponse(Call<Gson> call, Response<Gson> response) {
                if(response.isSuccessful()) {
                    Log.d("TAG","------ getStatus response successfully arrived");
                    System.out.println(response.body().toString());
                    Gson res = response.body();
                    gsonData.setValue(res);
                } else {
                    Log.d("TAG","------ getStatus response error");
                }
            }

            @Override
            public void onFailure(Call<Gson> call, Throwable t) {
                Log.d("TAG","------ searchUpcomingGames fetch failed");
                Log.d("TAG", "The error - " + t.toString());
            }
        });

        return gsonData;
    }
}
