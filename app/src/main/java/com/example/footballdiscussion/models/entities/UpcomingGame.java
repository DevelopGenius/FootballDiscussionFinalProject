package com.example.footballdiscussion.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class UpcomingGame {
    @PrimaryKey
    @NonNull
    private String id;

    private String leagueName;

    private Date gameDate;

    private String firstTeamName;

    private String secondTeamName;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM");


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
}
