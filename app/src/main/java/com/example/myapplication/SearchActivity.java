package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.MovieDAO;
import com.example.myapplication.db.WatchlistDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchActivity extends AppCompatActivity {

    /**
     * This activity handles the search for movies function
     * It has an autocomplete textview that shows the list of movies as the user types.
     * Once a movie is found the details are displayed and the user has the option to add the movie
     * to their watchlist. If the movie is already in the users watchlist it alerts them.
     */


    private MovieDAO mMovieDAO;
    private WatchlistDAO mWatchlistDAO;

    private AutoCompleteTextView mSearchBar;

    private Button mSearchForMovieButton;
    private Button mAddMovieButton;

    private ImageView mBackArrow;

    private TextView mTitleTextView;
    private TextView mYearTextView;
    private TextView mGenretextView;
    private TextView mPlatformTextView;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Gets user ID to access their watchlist
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.PREFERENCES_KEY", MODE_PRIVATE);
        userId = sharedPreferences.getInt("com.example.myapplication.userIdKey", -1); // -1 as default value if not found

        getDatabase();
        checkForMovies();
        wireupDisplay();

    }

    private void wireupDisplay() {
        mSearchBar = findViewById(R.id.search_bar_autotextview);
        mTitleTextView = findViewById(R.id.title_textview);
        mYearTextView = findViewById(R.id.year_textview);
        mGenretextView = findViewById(R.id.genre_textview);
        mPlatformTextView = findViewById(R.id.platform_textview);
        mAddMovieButton = findViewById(R.id.add_movie_button);
        mSearchForMovieButton = findViewById(R.id.search_this_movie_button);
        mAddMovieButton = findViewById(R.id.add_movie_button);
        mBackArrow = findViewById(R.id.search_back_arrow_imageview);

        /**
         * This wires up the search bar by getting all the movies and setting the movie titles
         * in the adapter
         */
        List<Movie> movies = mMovieDAO.getAllMovies();
        List<String> movieTitles = new ArrayList<>();

        for (Movie movie : movies) {
            movieTitles.add(movie.getMovieTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieTitles);
        mSearchBar.setAdapter(adapter);


        //Displays the movie info once the search button is hit
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

        //Adds movie to user watchlist
        mAddMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovieToWatchlist();
            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getDatabase() {
        mMovieDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build().getMovieDAO();
        mWatchlistDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build().getWatchlistDAO();
    }

    /**
     * This Initializes the list of movies for the search bar (if they aren't already there)
     * Calls function from class MovieDataInit which has all the titles that
     * will be available on app.
     * If the movies
     */
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

    /**
     * Gets movie info from the search bar and gets the movie id from the title
     * Inserts the movie into the watchlist using the movie id and userId(from prefreneces)
     */
    private void addMovieToWatchlist(){
        Movie movie = mMovieDAO.getMovieByTitle(mSearchBar.getText().toString());
        int movieId = movie.getMovieId();

        Watchlist existingEntry = mWatchlistDAO.getWatchlistMovie(userId, movieId);
        if (existingEntry == null) {
            // Movie is not in the watchlist, add it
            Watchlist newEntry = new Watchlist(userId, movieId);
            mWatchlistDAO.insert(newEntry);
            Toast.makeText(this, "Movie added to watchlist", Toast.LENGTH_SHORT).show();
        } else {
            // Movie is already in the watchlist
            Toast.makeText(this, "Movie is already in your watchlist", Toast.LENGTH_SHORT).show();
        }


    }

    private void displayMovieInfo(Movie movie){
        StreamingPlatform platform = movie.getPlatform();
        String platformString = platform.getDisplayName();


        //This makes the Title: etc parts bold
        SpannableString titleSpannable = new SpannableString("Title: " + movie.getMovieTitle());
        titleSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString yearSpannable = new SpannableString("Year: " + movie.getMovieYear());
        yearSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString genreSpannable = new SpannableString("Genre: " + movie.getMovieGenre());
        genreSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString platformSpannable = new SpannableString("Platform: " + platformString);
        platformSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        //Setting textview to the above defined strings
        mTitleTextView.setText(titleSpannable);
        mYearTextView.setText(yearSpannable);
        mGenretextView.setText(genreSpannable);
        mPlatformTextView.setText(platformSpannable);


        //sets visibility to visible
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