package com.example.myapplication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.myapplication.Watchlist;


@Dao
public interface WatchlistDAO {
    @Insert
    void insert(Watchlist... watchlist);

    @Insert
    void addMovieToWatchlist(Watchlist watchlist);
    @Delete
    void removeMovieFromWatchlist(Watchlist watchlist);

    @Update
    void update(Watchlist... watchlists);

    @Query("SELECT * FROM " + AppDatabase.WATCHLIST_TABLE + " WHERE mUserId = :userId")
    Watchlist getWatchlistForUser(int userId);

    @Query("SELECT * FROM " + AppDatabase.WATCHLIST_TABLE + " WHERE mWatchlistId = :watchlistId")
    Watchlist getWatchlistById(int watchlistId);
    @Query("SELECT * FROM watchlist WHERE mUserId = :userId AND mMovieId = :movieId LIMIT 1")
    Watchlist getWatchlistMovie(int userId, int movieId);
}

