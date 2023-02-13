package com.example.footballdiscussion.models.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.footballdiscussion.models.API.GetUpcomingGamesDto;
import com.example.footballdiscussion.models.API.UpcomingGameAdapter;
import com.example.footballdiscussion.models.API.UpcomingGamesApi;
import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDb;
import com.example.footballdiscussion.models.room.FootballDiscussionLocalDbRepository;
import com.example.footballdiscussion.utils.LoadingState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingGameModel {
    final public static UpcomingGameModel _instance = new UpcomingGameModel();
    private final FootballDiscussionLocalDbRepository localDb = FootballDiscussionLocalDb.getAppDb();
    private final Executor executor = Executors.newSingleThreadExecutor();
    public final MutableLiveData<LoadingState> upcomingGamesLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    final String BASE_URL = "https://v3.football.api-sports.io";
    Retrofit retrofit;
    UpcomingGamesApi upcomingGamesApi;
    private LiveData<List<UpcomingGame>> upcomingGamesList;

    private UpcomingGameModel() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("x-rapidapi-key", "9f6bb1ad071d43e9090651f98edb5b3c")
                        .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                        .build();
                return chain.proceed(request);
            }
        });

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UpcomingGame.class, new UpcomingGameAdapter())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        upcomingGamesApi = retrofit.create(UpcomingGamesApi.class);
    }

    public static UpcomingGameModel getInstance() {
        return _instance;
    }

    public LiveData<List<UpcomingGame>> getAllUpcomingGames() {
        if (upcomingGamesList == null) {
            upcomingGamesList = localDb.upcomingGameDao().getAll();
            refreshAllUpcomingGames();
        }
        return upcomingGamesList;
    }

    public void refreshAllUpcomingGames() {
        this.upcomingGamesLoadingState.setValue(LoadingState.LOADING);
        this.upcomingGamesApi.searchUpcomingGamesForLeague().enqueue(new Callback<GetUpcomingGamesDto>() {
            @Override
            public void onResponse(Call<GetUpcomingGamesDto> call, Response<GetUpcomingGamesDto> upcomingGamesResponse) {
                if (upcomingGamesResponse.body().getResponse() != null) {
                    executor.execute(() -> {
                        if (upcomingGamesResponse.isSuccessful()) {
                            Log.d("TAG", "----- Received upcomingGames from API successfully");
                            upcomingGamesLoadingState.postValue(LoadingState.NOT_LOADING);
                            upcomingGamesResponse.body().getResponse().forEach(game -> {
                                localDb.upcomingGameDao().insertAll(game);
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetUpcomingGamesDto> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
