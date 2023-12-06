package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.UserDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;
    private TextView mSignUpClickText;

    private Button mLoginButton;



    private UserDAO mUserDAO;

    private String mUsername;
    private String mPassword;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        wireupDisplay();
        getDatabase();

    }

    private void wireupDisplay(){
        mUsernameField = findViewById(R.id.login_username_edittext);
        mPasswordField = findViewById(R.id.login_password_edittext);
        mLoginButton = findViewById(R.id.login_button);
        mSignUpClickText = findViewById(R.id.signup_clickable_text);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if (checkForUserInDatabase()){
                    if (!validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();

                    }else {
                        Intent intent = MainActivity.intentFactory(getApplicationContext(),mUser.getUserId());
                        startActivity(intent);
                    }
                };
            }
        });

        mSignUpClickText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignupActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private boolean validatePassword() {
        return mUser.getPassword().equals(mPassword);
    }

    private void getValuesFromDisplay() {
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();

    }

    private boolean checkForUserInDatabase(){
        mUser = mUserDAO.getUserByUsername(mUsername);
        if(mUser == null){
            Toast.makeText(this, "No user " + mUsername + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getDatabase(){
        try {
            Log.d("MainActivity", "Initializing database");
            mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                    .allowMainThreadQueries().build().getUserDAO();
        } catch (Exception e) {
            Log.e("MainActivity", "Error initializing database", e);
        }
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginActivity.class);

        return intent;
    }
}