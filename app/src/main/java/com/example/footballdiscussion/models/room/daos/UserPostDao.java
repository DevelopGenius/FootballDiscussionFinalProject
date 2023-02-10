package com.example.footballdiscussion.models.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.footballdiscussion.models.entities.UserPost;

import java.util.List;

@Dao
public interface UserPostDao {
    @Query("select * from UserPost")
    LiveData<List<UserPost>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserPost... userPosts);

    @Delete
    void delete(UserPost userPost);
}
