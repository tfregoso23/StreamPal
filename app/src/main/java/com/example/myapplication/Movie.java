package com.example.myapplication;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.db.AppDatabase;

@Entity(tableName = AppDatabase.MOVIE_TABLE)

public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int mMovieId;

    private String mMovieTitle;
    private int mMovieYear;
    private String mMovieGenre;
    private StreamingPlatform mPlatform;

    public Movie(String mMovieTitle, int mMovieYear, String mMovieGenre, StreamingPlatform mPlatform){
        this.mMovieTitle = mMovieTitle;
        this.mMovieYear = mMovieYear;
        this.mMovieGenre = mMovieGenre;
        this.mPlatform = mPlatform;
    }

    public int getMovieId(){return mMovieId;}

    public void setMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.mMovieTitle = movieTitle;
    }

    public int getMovieYear() {
        return mMovieYear;
    }

    public void setMovieYear(int movieYear) {
        this.mMovieYear = movieYear;
    }

    public String getMovieGenre() {
        return mMovieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.mMovieGenre = movieGenre;
    }

    public StreamingPlatform getPlatform() {
        return mPlatform;
    }

    public void setPlatform(StreamingPlatform platform) {
        this.mPlatform = platform;
    }
}
