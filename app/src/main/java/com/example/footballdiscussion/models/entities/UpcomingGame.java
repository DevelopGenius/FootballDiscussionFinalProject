package com.example.footballdiscussion.models.entities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.footballdiscussion.ApplicationContext;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class UpcomingGame {
    private static final String LOCAL_LAST_UPDATE_TIME = "upcomingGameLocalLastUpdateTime";

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String leagueName;

    @SerializedName("date")
    private Date gameDate;

    @SerializedName("name")
    private String firstTeamName;

    @SerializedName("name")
    private String secondTeamName;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM");

    @Ignore
    public UpcomingGame() {

    }

    public UpcomingGame(@NonNull String id, String leagueName, Date gameDate, String firstTeamName, String secondTeamName) {
        this.id = id;
        this.leagueName = leagueName;
        this.gameDate = gameDate;
        this.firstTeamName = firstTeamName;
        this.secondTeamName = secondTeamName;
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

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
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
        return leagueName + " " + simpleDateFormat.format(gameDate);
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
}