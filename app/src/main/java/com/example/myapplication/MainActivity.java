package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

//import android.app.Activity;
//import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.db.UserDAO;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.db.AppDatabase;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.myapplication.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.myapplication.PREFERENCES_KEY";

    private int mUserId = -1;
    private UserDAO mUserDAO;
    private User mUser;
    private SharedPreferences mPreferences = null;

    private Button mLogoutButton;
    private Button mAdminButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireUpDisplay();

        getDatabase();

        checkForUser();

        loginUser(mUserId);
        mAdminButton = findViewById(R.id.admin_button);

        if(mUser.isAdmin()){
            mAdminButton.setVisibility(View.VISIBLE);
        }
        else
            mAdminButton.setVisibility(View.GONE);

        String username = mUser.getUsername().toString(); // Replace this with the actual username variable

        // Set the text of the TextView to the username
        TextView usernameTextView = findViewById(R.id.username_display_textview);
        usernameTextView.setText(username);
    }


    private void wireUpDisplay() {
        mLogoutButton = findViewById(R.id.logout_button);

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("loggingOut","loggedOut");
                logoutUser();
            }
        });
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

    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId = -1;
                        checkForUser();
                    }
                });
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBuilder.create().show();
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

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY,-1);
    }

    private void clearUserFromPref(){
        addUserToPreference(-1);
    }

}