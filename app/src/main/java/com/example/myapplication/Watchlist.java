package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.myapplication.db.AppDatabase;

@Entity(tableName = AppDatabase.WATCHLIST_TABLE,foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "mUserId",
        childColumns = "mUserId",
        onDelete = ForeignKey.CASCADE))
public class Watchlist {
    @PrimaryKey(autoGenerate = true)
    private int mWatchlistId;

    private int mUserId;
    private int mMovieId;

    public Watchlist(int userId, int movieId) {
        this.mUserId = userId;
        this.mMovieId = movieId;
    }

    public int getWatchlistId() {
        return mWatchlistId;
    }

    public void setWatchlistId(int watchlistId) {
        this.mWatchlistId = watchlistId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        this.mMovieId = movieId;
    }
}


