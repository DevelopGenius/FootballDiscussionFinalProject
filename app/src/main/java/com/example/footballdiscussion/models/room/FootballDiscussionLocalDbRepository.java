package com.example.footballdiscussion.models.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.room.daos.UserDao;

@Database(entities = {User.class}, version = 16)
public abstract class FootballDiscussionLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
}
