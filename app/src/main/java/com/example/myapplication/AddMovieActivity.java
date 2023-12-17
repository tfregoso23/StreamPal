package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.MovieDAO;

public class AddMovieActivity extends AppCompatActivity {

    /**
     * This activity allows admins to enter movie info and add
     * the movie to the overall list of movies.
     * Verifies to make sure movie already isn't in database
     */

    private Spinner mPlatfromSpinner;

    private MovieDAO mMovieDAO;

    private String mMovieTitle;
    private Integer mMovieYear;
    private String mMovieGenre;
    private StreamingPlatform mMoviePlatform;

    private EditText mEnteredTitle;
    private EditText mEnteredYear;
    private EditText mEnteredGenre;
    private Button mAddMovieButton;

    private ImageView mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        getDatabase();
        wireupDisplay();
    }

    private void wireupDisplay(){
        mEnteredTitle = findViewById(R.id.title_edittext);
        mEnteredYear = findViewById(R.id.year_edittext);
        mEnteredGenre = findViewById(R.id.genre_edittext);
        mAddMovieButton = findViewById(R.id.addtitle_addmovie_button);
        mBackButton = findViewById(R.id.addtitle_back_arrow_imageview);
        mPlatfromSpinner = findViewById(R.id.select_platform_spinner);

        setUpSpinner();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAddMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if (confirmMovie(mMovieTitle)){
                    addMovieToDatabase();
                }else {
                    return;
                }
            }
        });

    }

    /**
     * Sets up spinner to display lists of Platform(enums) to choose from when adding movie
     */
    private void setUpSpinner(){
        StreamingPlatform[] platforms = StreamingPlatform.values();
        ArrayAdapter<StreamingPlatform> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, platforms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlatfromSpinner.setAdapter(adapter);

        mPlatfromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StreamingPlatform selectedPlatformName = (StreamingPlatform) parent.getItemAtPosition(position);
                mMoviePlatform = selectedPlatformName;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddMovieActivity.this, "Must select a platform", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Gets all values from display so it can be entered as new movie
     */

    private void getValuesFromDisplay(){
        mMovieTitle = mEnteredTitle.getText().toString();
        mMovieYear = Integer.parseInt(mEnteredYear.getText().toString());
        mMovieGenre = mEnteredGenre.getText().toString();
    }

    /**
     * Verifies movie doesnt exist in database by referencing usernames
     * @param movieTitle
     * @return
     */
    private boolean confirmMovie(String movieTitle){
        Movie registeredMovie = mMovieDAO.getMovieByTitle(movieTitle);
        if(registeredMovie != null){
            Toast.makeText(this, "Movie already exists..", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Adds new movie into database
     */
    private void addMovieToDatabase(){
        Movie movie = new Movie(mMovieTitle,mMovieYear,mMovieGenre,mMoviePlatform);
        mMovieDAO.insert(movie);
        Toast.makeText(this, "Movie added!", Toast.LENGTH_SHORT).show();
    }

    private void getDatabase(){
        mMovieDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build().getMovieDAO();
    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AddMovieActivity.class);
        return intent;
    }

}