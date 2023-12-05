package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

//import android.app.Activity;
//import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.db.UserDAO;
import android.util.Log;

import com.example.myapplication.db.AppDatabase;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.myapplication.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.myapplication.PREFERENCES_KEY";

    private int mUserId = -1;
    private UserDAO mUserDAO;
    private User mUser;
    private SharedPreferences mPreferences = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();

        checkForUser();

        loginUser(mUserId);

    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        //do we have a user in the preferences?
        if (mUserId != -1) {
            return;
        }

        if (mPreferences == null) {
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if (mUserId != -1) {
            return;
        }

        //do we have any users at all?
        List<User> users = mUserDAO.getAllUsers();
        if (users.size() <= 0) {
            User defaultUser = new User("testuser1", "testuser1",false);
            User adminUser = new User("admin2", "admin2", true);
            mUserDAO.insert(defaultUser);
            mUserDAO.insert(adminUser);
        }


        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);

    }

//    private void logoutUser(){
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//
//        alertBuilder.setMessage(R.string.logout);
//    }


    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
    private void getDatabase() {
        try {
            Log.d("MainActivity3", "Initializing database");
            mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                    .allowMainThreadQueries().build().getUserDAO();
        } catch (Exception e) {
            Log.e("MainActivity2", "Error initializing database", e);
        }
    }

    private void loginUser(int userId) {
        //check if userID is valid
        mUser = mUserDAO.getUserById(userId);
        //check if mUser is not null
        addUserToPreference(userId);
        invalidateOptionsMenu();
    }
    private void addUserToPreference(int userId) {
        if (mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

}