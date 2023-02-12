package com.example.footballdiscussion.models.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.footballdiscussion.models.entities.UpcomingGame;
import com.example.footballdiscussion.models.entities.User;
import com.example.footballdiscussion.models.entities.UserPost;
import com.example.footballdiscussion.models.room.daos.UpcomingGameDao;
import com.example.footballdiscussion.models.room.daos.UserDao;
import com.example.footballdiscussion.models.room.daos.UserPostDao;

@Database(entities = {User.class, UpcomingGame.class, UserPost.class}, version = 17)
@TypeConverters({Converters.class})
public abstract class FootballDiscussionLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract UpcomingGameDao upcomingGameDao();
    public abstract UserPostDao userPostDao();
}
