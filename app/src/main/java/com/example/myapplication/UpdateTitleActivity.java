package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.MovieDAO;

import java.util.ArrayList;
import java.util.List;

public class UpdateTitleActivity extends AppCompatActivity {
    /**
     * This activity allows admins to search for a movie
     * in the database and update the streaming platform
     * the movie is available on
     */

    private MovieDAO mMovieDAO;

    private AutoCompleteTextView mSearchBar;
    private StreamingPlatform mMoviePlatform;

    private Button mSearchButton;
    private Button mUpdateButton;

    private TextView mTitleTextview;
    private TextView mCurrentPlatformTextview;
    private TextView mUpdateTextview;

    private Spinner mPlatformSpinner;

    private ImageView mBackArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_title);

        getDatabase();
        wireupDisplay();


        //This sets up the search bar
        List<Movie> movies = mMovieDAO.getAllMovies();
        List<String> movieTitles = new ArrayList<>();

        for (Movie movie : movies) {
            movieTitles.add(movie.getMovieTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieTitles);
        mSearchBar.setAdapter(adapter);
    }

    private void wireupDisplay() {
        mSearchBar = findViewById(R.id.update_search_bar_autotextview);
        mSearchButton = findViewById(R.id.search_this_update_movie_button);
        mUpdateButton = findViewById(R.id.update_platform_button);
        mTitleTextview = findViewById(R.id.update_title_textview);
        mCurrentPlatformTextview = findViewById(R.id.current_platform_textview);
        mUpdateTextview = findViewById(R.id.update_invisible_textview);
        mPlatformSpinner = findViewById(R.id.select_platform_update_spinner);
        mBackArrow = findViewById(R.id.updatetitle_back_arrow_imageview);

        checkForMovies();
        setUpSpinner();

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieTitle = mSearchBar.getText().toString();
                Movie movie = mMovieDAO.getMovieByTitle(movieTitle);
                if(movie != null){
                    displayMovieInfo(movie);
                }
                else {
                    Toast.makeText(UpdateTitleActivity.this, "Title not found. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMovieDAO.updateMoviePlatform(mTitleTextview.getText().toString(),mMoviePlatform);
                Toast.makeText(UpdateTitleActivity.this, "Title updated!", Toast.LENGTH_SHORT).show();
                mCurrentPlatformTextview.setText("Platform: " + mMoviePlatform.getDisplayName());
            }
        });



    }


    //since movies get initialized on search, call this in case this is the first scren user goes to
    //Movies wont be initialized if this is the first place users go
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

        mTitleTextview.setText(movie.getMovieTitle());
        mCurrentPlatformTextview.setText("Platform: " + platformString);

        mTitleTextview.setVisibility(View.VISIBLE);
        mCurrentPlatformTextview.setVisibility(View.VISIBLE);
        mUpdateTextview.setVisibility(View.VISIBLE);
        mUpdateButton.setVisibility(View.VISIBLE);
        mPlatformSpinner.setVisibility(View.VISIBLE);
    }

    //Sets up platform enum spinner
    private void setUpSpinner(){
        StreamingPlatform[] platforms = StreamingPlatform.values();
        ArrayAdapter<StreamingPlatform> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, platforms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlatformSpinner.setAdapter(adapter);

        mPlatformSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StreamingPlatform selectedPlatformName = (StreamingPlatform) parent.getItemAtPosition(position);
                mMoviePlatform = selectedPlatformName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(UpdateTitleActivity.this, "Must select a platform", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getDatabase(){
        mMovieDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build().getMovieDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, UpdateTitleActivity.class);
        return intent;
    }
}