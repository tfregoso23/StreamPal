package com.example.myapplication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.myapplication.Movie;
import com.example.myapplication.Watchlist;

import java.util.List;


@Dao
public interface WatchlistDAO {
    @Insert
    void insert(Watchlist... watchlist);

    @Insert
    void addMovieToWatchlist(Watchlist watchlist);
    @Delete
    void removeMovieFromWatchlist(Watchlist watchlist);

    @Query("DELETE FROM watchlist WHERE mMovieId = :movieId")
    void deleteMovieFromWatchlist(int movieId);

    @Update
    void update(Watchlist... watchlists);

    @Query("SELECT * FROM " + AppDatabase.WATCHLIST_TABLE + " WHERE mUserId = :userId")
    Watchlist getWatchlistForUser(int userId);

    @Query("SELECT * FROM " + AppDatabase.WATCHLIST_TABLE + " WHERE mWatchlistId = :watchlistId")
    Watchlist getWatchlistById(int watchlistId);
    @Query("SELECT * FROM watchlist WHERE mUserId = :userId AND mMovieId = :movieId LIMIT 1")
    Watchlist getWatchlistMovie(int userId, int movieId);

    @Query("SELECT movies.* FROM " + AppDatabase.MOVIE_TABLE +
            " INNER JOIN " + AppDatabase.WATCHLIST_TABLE +
            " ON movies.mMovieId = Watchlist.mMovieId " +
            "WHERE Watchlist.mUserId = :userId")
    List<Movie> getMoviesForUser(int userId);

}

