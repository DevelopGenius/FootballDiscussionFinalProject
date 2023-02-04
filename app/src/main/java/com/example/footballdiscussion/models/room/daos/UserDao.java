package com.example.footballdiscussion.models.room.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.footballdiscussion.models.entities.User;

@Dao
public interface UserDao {

    @Query("select * from User where id = :id")
    User getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);
}
