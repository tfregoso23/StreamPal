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
import android.widget.TextView;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.WatchlistDAO;

import java.util.List;

public class WatchlistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WatchlistAdapter adapter;

    private int userId;

    private WatchlistDAO mWatchlistDAO;

    private ImageView mBackArrow;

    private TextView mRemoveClickableTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        getDatabase();

        mBackArrow = findViewById(R.id.watchlist_back_arrow_imageview);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        recyclerView = findViewById(R.id.recyclerview_watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myapplication.PREFERENCES_KEY", MODE_PRIVATE);
        userId = sharedPreferences.getInt("com.example.myapplication.userIdKey", -1);

        fetchMovies(userId);




    }

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

    private void getDatabase() {
        mWatchlistDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build().getWatchlistDAO();
    }

    private void removeMovieFromWatchlist(int position) {
        Movie movieToRemove = adapter.getMovies().get(position);

        mWatchlistDAO.deleteMovieFromWatchlist(movieToRemove.getMovieId());

        adapter.getMovies().remove(position);
        adapter.notifyItemRemoved(position);
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, WatchlistActivity.class);
        return intent;
    }


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

}