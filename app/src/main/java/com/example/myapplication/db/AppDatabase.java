package com.example.myapplication.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public static final String DB_NAME = "com.example.myapplication.MOVIE_DATABASE";
    public static final String USER_TABLE = "users";

    public abstract UserDAO getUserDAO();
}
