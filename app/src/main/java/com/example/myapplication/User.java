package com.example.myapplication;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.db.AppDatabase;

/**
 * Defines user class for user datatable
 */

@Entity(tableName = AppDatabase.USER_TABLE)

public class User {
    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUsername;
    private String mPassword;
    private boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.mUsername = username;
        this.mPassword = password;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
