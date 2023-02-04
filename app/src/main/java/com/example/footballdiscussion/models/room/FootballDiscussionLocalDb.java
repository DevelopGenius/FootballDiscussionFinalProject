package com.example.footballdiscussion.models.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.footballdiscussion.ApplicationContext;
import com.example.footballdiscussion.models.room.daos.UserDao;
import com.example.footballdiscussion.models.entities.User;

@Database(entities = {User.class}, version = 55)
abstract class FootballDiscussionDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
}

public class FootballDiscussionLocalDb {
    static public FootballDiscussionDbRepository getAppDb() {
        return Room.databaseBuilder(ApplicationContext.getContext(),
                        FootballDiscussionDbRepository.class,
                        "footballDiscussionDb.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private FootballDiscussionLocalDb(){}
}
