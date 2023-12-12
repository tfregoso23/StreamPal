package com.example.myapplication.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.Movie;
import com.example.myapplication.MovieDataInit;
import com.example.myapplication.StreamingPlatform;
import com.example.myapplication.User;
import com.example.myapplication.Watchlist;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {User.class, Movie.class, Watchlist.class},version = 3)
public abstract class AppDatabase extends RoomDatabase{
    public static final String DB_NAME = "com.example.myapplication.MOVIE_DATABASE";
    public static final String USER_TABLE = "users";
    public static final String MOVIE_TABLE = "movies";
    public static final String WATCHLIST_TABLE = "watchlist";

    public abstract UserDAO getUserDAO();
    public abstract MovieDAO getMovieDAO();
    public abstract WatchlistDAO getWatchlistDAO();

//    private static volatile AppDatabase INSTANCE;
//
//    public static AppDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                                    AppDatabase.class, "movie_database")
//                            .addCallback(sRoomDatabaseCallback)
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            // Create a new thread to perform database operations
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // Get the DAO
//                    MovieDAO dao = INSTANCE.getMovieDAO();
//
//                    // Insert the hardcoded list of movies
//                    List<Movie> movies = new ArrayList<>();
//                    movies.add(new Movie("Movie 1", 2022, "Genre 1", StreamingPlatform.APPLE_TV));
//                    movies.add(new Movie("Movie 2", 2022, "Genre 2", StreamingPlatform.DISNEY_PLUS));
//                    // Add more movies as needed
//
//                    dao.insertAll(movies);
//                }
//            }).start();
//        }
//};
}
