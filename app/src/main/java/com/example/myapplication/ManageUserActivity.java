package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.UserDAO;
import com.example.myapplication.db.WatchlistDAO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ManageUserActivity extends AppCompatActivity {
    private UserDAO mUserDAO;
    private WatchlistDAO mWatchlistDAO;

    private AutoCompleteTextView mUserSearchBar;

    private Button mSearchButton;
    private Button mRemoveUserButton;

    private TextView mUserFoundTextview;
    private TextView mUsernameTextview;
    private ImageView mBackButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        getDatabase();
        wireupDisplay();
    }

    private void wireupDisplay(){
        mUserSearchBar = findViewById(R.id.users_search_bar_autotextview);
        mSearchButton = findViewById(R.id.search_users_button);
        mRemoveUserButton = findViewById(R.id.remove_user_button);
        mUserFoundTextview = findViewById(R.id.user_found_textview);
        mUsernameTextview = findViewById(R.id.username_found_textview);
        mBackButton = findViewById(R.id.manageusers_back_arrow_imageview);

        wireupSearchBar();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUserSearchBar.getText().toString();
                User user = mUserDAO.getUserByUsername(username);
                if(user != null){
                    displayUsername(user);
                }
                else {
                    Toast.makeText(ManageUserActivity.this, "User not found. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRemoveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsernameTextview.getText().toString();
                User user = mUserDAO.getUserByUsername(username);
                showRemoveConfirmationDialog(user);

            }
        });


    }

    private void wireupSearchBar(){
        List<User> users = mUserDAO.getAllUsers();
        List<String> usernames = new ArrayList<>();

        for (User user : users){
            usernames.add(user.getUsername());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        mUserSearchBar.setAdapter(adapter);
    }

    private void displayUsername(User user){
        mUsernameTextview.setText(user.getUsername());

        mUsernameTextview.setVisibility(View.VISIBLE);
        mUserFoundTextview.setVisibility(View.VISIBLE);
        mRemoveUserButton.setVisibility(View.VISIBLE);
    }

    private void showRemoveConfirmationDialog(User user) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Permanently delete user?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mUserDAO.delete(user);
                Toast.makeText(ManageUserActivity.this,"User removed",Toast.LENGTH_SHORT).show();
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

    private void getDatabase(){
        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build().getUserDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, ManageUserActivity.class);
        return intent;
    }
}