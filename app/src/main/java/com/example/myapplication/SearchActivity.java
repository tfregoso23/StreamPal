package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.MovieDAO;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView mSearchBar;
    private MovieDAO mMovieDAO;
    private Button mSearchForMovieButton;
    private Button mAddMovieButton;

    private TextView mTitleTextView;
    private TextView mYearTextView;
    private TextView mGenretextView;
    private TextView mPlatformTextView;

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

        mTitleTextView = findViewById(R.id.title_textview);
        mYearTextView = findViewById(R.id.year_textview);
        mGenretextView = findViewById(R.id.genre_textview);
        mPlatformTextView = findViewById(R.id.platform_textview);
        mAddMovieButton = findViewById(R.id.add_movie_button);

        mSearchForMovieButton = findViewById(R.id.search_this_movie_button);
        mSearchForMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieTitle = mSearchBar.getText().toString();
                Movie movie = mMovieDAO.getMovieByTitle(movieTitle);
                if(movie != null){
                    displayMovieInfo(movie);
                }
                else {
                    Toast.makeText(SearchActivity.this, "Title not found. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    private void displayMovieInfo(Movie movie){
        StreamingPlatform platform = movie.getPlatform();
        String platformString = platform.getDisplayName();
        mTitleTextView.setText("Title: " + movie.getMovieTitle());
        mYearTextView.setText("Year: " + movie.getMovieYear());
        mGenretextView.setText("Genre: " + movie.getMovieGenre());
        mPlatformTextView.setText("Platform: " + platformString);
        mTitleTextView.setVisibility(View.VISIBLE);
        mYearTextView.setVisibility(View.VISIBLE);
        mGenretextView.setVisibility(View.VISIBLE);
        mPlatformTextView.setVisibility(View.VISIBLE);
        mAddMovieButton.setVisibility(View.VISIBLE);

    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

}