package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.MovieDAO;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView mSearchBar;
    private MovieDAO mMovieDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getDatabase();
        checkForMovies();

        mSearchBar = findViewById(R.id.search_bar_autotextview);


        List<Movie> movies = mMovieDAO.getAllMovies();
        List<String> movieTitles = new ArrayList<>();

        for (Movie movie : movies) {
            movieTitles.add(movie.getMovieTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieTitles);
        mSearchBar.setAdapter(adapter);
    }

    private void getDatabase() {
        mMovieDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build().getMovieDAO();
    }

    private void checkForMovies(){
        List<Movie> movies = mMovieDAO.getAllMovies();
        if (movies.size() <= 0){
            List<Movie> initialMovies = MovieDataInit.getInitMovies();
            for (Movie movie : initialMovies) {
                // Check if the movie already exists in the database
                Movie existingMovie = mMovieDAO.getMovieByTitle(movie.getMovieTitle());
                if (existingMovie == null) {
                    // If the movie doesn't exist, insert it
                    mMovieDAO.insert(movie);
                }
            }
        }
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

}