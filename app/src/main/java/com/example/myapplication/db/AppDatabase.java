package com.example.myapplication.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.Movie;
import com.example.myapplication.MovieDataInit;
import com.example.myapplication.StreamingPlatform;
import com.example.myapplication.User;
import com.example.myapplication.Watchlist;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {User.class, Movie.class, Watchlist.class},version = 3)
public abstract class AppDatabase extends RoomDatabase{
    public static final String DB_NAME = "com.example.myapplication.MOVIE_DATABASE";
    public static final String USER_TABLE = "users";
    public static final String MOVIE_TABLE = "movies";
    public static final String WATCHLIST_TABLE = "watchlist";

    public abstract UserDAO getUserDAO();
    public abstract MovieDAO getMovieDAO();
    public abstract WatchlistDAO getWatchlistDAO();


}
