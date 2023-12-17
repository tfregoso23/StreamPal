package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.WatchlistDAO;

import java.util.List;

public class WatchlistActivity extends AppCompatActivity {
    /**
     * This activity handles the users watchlist. Each movie that a user adds
     * appears here. It has a recycler view that
     * generates a card for each movie.(Details in WatchlistAdapter)
     */

    private RecyclerView recyclerView;
    private WatchlistAdapter adapter;

    private int userId;

    private WatchlistDAO mWatchlistDAO;

    private ImageView mBackArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        //Gets userId
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.PREFERENCES_KEY", MODE_PRIVATE);
        userId = sharedPreferences.getInt("com.example.myapplication.userIdKey", -1);

        getDatabase();
        wireupDisplay();

    }

    private void wireupDisplay() {
        //Back arrow to leave activity
        mBackArrow = findViewById(R.id.watchlist_back_arrow_imageview);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Sets up recycler view
        recyclerView = findViewById(R.id.recyclerview_watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchMovies(userId);
    }


    /**
     * Gets the list of movies associated with each user
     * Sets uo the recycler view using custom adapter
     * If you hit remove it calls confirmation dialouge
     * @param userId
     */
    private void fetchMovies(int userId) {
        List<Movie> watchlistMovies = mWatchlistDAO.getMoviesForUser(userId);
        adapter = new WatchlistAdapter(watchlistMovies);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new WatchlistAdapter.OnItemClickListener() {
            @Override
            public void onRemoveClick(int position) {
                showRemoveConfirmationDialog(position);
            }
        });
    }


    /**
     * Removes movie from watchlist and removes card from position
     * @param position
     */
    private void removeMovieFromWatchlist(int position) {
        Movie movieToRemove = adapter.getMovies().get(position);

        mWatchlistDAO.deleteMovieFromWatchlist(movieToRemove.getMovieId());

        adapter.getMovies().remove(position);
        adapter.notifyItemRemoved(position);
    }


    /**
     * Shows confirmation dialouge to remove movie and calls removeFromWatchlist
     * @param position
     */
    private void showRemoveConfirmationDialog(final int position) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Remove movie?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeMovieFromWatchlist(position);
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }


    private void getDatabase() {
        mWatchlistDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build().getWatchlistDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, WatchlistActivity.class);
        return intent;
    }

}