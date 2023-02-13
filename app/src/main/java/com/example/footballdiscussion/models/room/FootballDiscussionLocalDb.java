package com.example.footballdiscussion.models.room;

import androidx.room.Room;

import com.example.footballdiscussion.ApplicationContext;

public class FootballDiscussionLocalDb {
    static public FootballDiscussionLocalDbRepository getAppDb() {
        return Room.databaseBuilder(ApplicationContext.getContext(),
                        FootballDiscussionLocalDbRepository.class,
                        "footballDiscussionDb.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private FootballDiscussionLocalDb() {
    }
}
