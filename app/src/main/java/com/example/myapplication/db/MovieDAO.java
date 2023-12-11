package com.example.myapplication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Movie;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void insert(Movie... movie);
    @Insert
    void insertAll(List<Movie> movies);

    @Update
    void update(Movie... movies);

    @Delete
    void delete(Movie... movie);

    @Query("SELECT * FROM " + AppDatabase.MOVIE_TABLE + " WHERE mMovieTitle = :title LIMIT 1")
    Movie getMovieByTitle(String title);

    @Query("SELECT * FROM " + AppDatabase.MOVIE_TABLE)
    List<Movie> getAllMovies();

    @Query("SELECT * FROM " + AppDatabase.MOVIE_TABLE + " WHERE mMovieId = :movieId")
    Movie getMovieById(int movieId);

}
