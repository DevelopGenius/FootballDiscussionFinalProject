package com.example.footballdiscussion.models.entities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.footballdiscussion.ApplicationContext;

@Entity
public class UpcomingGame {
    private static final String LOCAL_LAST_UPDATE_TIME = "upcomingGameLocalLastUpdateTime";

    @PrimaryKey
    @NonNull
    private String id;
    private String leagueName;
    private String gameDate;
    private String firstTeamName;
    private String secondTeamName;
    private String imageUrl;

    @Ignore
    public UpcomingGame() {

    }

    public UpcomingGame(@NonNull String id, String leagueName, String gameDate, String firstTeamName, String secondTeamName, String imageUrl) {
        this.id = id;
        this.leagueName = leagueName;
        this.gameDate = gameDate;
        this.firstTeamName = firstTeamName;
        this.secondTeamName = secondTeamName;
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getFirstTeamName() {
        return firstTeamName;
    }

    public void setFirstTeamName(String firstTeamName) {
        this.firstTeamName = firstTeamName;
    }

    public String getSecondTeamName() {
        return secondTeamName;
    }

    public void setSecondTeamName(String secondTeamName) {
        this.secondTeamName = secondTeamName;
    }

    public String getGameTitle(){
        return leagueName + " " + gameDate;
    }

    public String getGameDescription(){
        return firstTeamName + " vs "+ secondTeamName;
    }

    public static Long getLocalLastUpdateTime() {
        return ApplicationContext.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(UpcomingGame.LOCAL_LAST_UPDATE_TIME, 0);
    }

    public static void setLocalLastUpdateTime(Long localLastUpdateTime) {
        ApplicationContext.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .edit()
                .putLong(UpcomingGame.LOCAL_LAST_UPDATE_TIME, localLastUpdateTime)
                .apply();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
