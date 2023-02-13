package com.example.footballdiscussion.models.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.footballdiscussion.models.API.UpcomingGamesApi;
import com.example.footballdiscussion.models.entities.UpcomingGame;

import java.util.List;

@Dao
public interface UpcomingGameDao {
    @Query("select * from UpcomingGame")
    LiveData<List<UpcomingGame>> getAll();

    @Query("select * from UpcomingGame where id = :id")
    UpcomingGame getUpcomingGameById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UpcomingGame... upcomingGames);

    @Query("DELETE FROM UpcomingGame ")
    void deleteAll();
}
