package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.UserDAO;

public class SignupActivity extends AppCompatActivity {

    /**
     * This activity handles new users signing up for the app
     * It verifies to make sure passwords match and checks and adds user to database
     */

    EditText mNewUsernameField;
    EditText mNewPasswordField;
    EditText mConfirmPasswordField;

    TextView mSignInClickText;

    Button mSignUpButton;

    String mNewUsername;
    String mNewPassword;
    String mConfirmPassword;

    private UserDAO mUserDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        wireupDisplay();
        getDatabase();
    }

    private void wireupDisplay() {
        mNewUsernameField = findViewById(R.id.enter_username_edittext);
        mNewPasswordField = findViewById(R.id.enter_password_edittext);
        mConfirmPasswordField = findViewById(R.id.confirm_password_edittext);
        mSignUpButton = findViewById(R.id.signup_button);
        mSignInClickText = findViewById(R.id.signin_clickable_text);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if(confirmUsername(mNewUsername)){
                    if (validatePasswords(mNewPassword,mConfirmPassword)){
                        registerUser();
                    }
                }
                else {
                    return;
                };
            }
        });

        mSignInClickText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private void registerUser() {
        User newUser = new User(mNewUsername,mNewPassword,false);

        //Use long to make sure you get the userID(this fixed my bug from going from signup to main)
        long userId = mUserDAO.insert(newUser);

        //Use long here and declare userID as an int to meet params
        Intent intent = MainActivity.intentFactory(getApplicationContext(),(int) userId);
        startActivity(intent);
    }

    private boolean validatePasswords(String password1, String password2) {
        if(!password1.equals(password2)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Checks to make sure the username is unique and doesnt exist
    private boolean confirmUsername(String username) {
        User registeredUser = mUserDAO.getUserByUsername(username);
        if(registeredUser != null){
            Toast.makeText(this, "Username already taken...please enter a new username", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getValuesFromDisplay() {
        mNewUsername = mNewUsernameField.getText().toString();
        mNewPassword = mNewPasswordField.getText().toString();
        mConfirmPassword = mConfirmPasswordField.getText().toString();
    }
    private void getDatabase() {
            mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                    .allowMainThreadQueries().build().getUserDAO();
    }
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, SignupActivity.class);
        return intent;
    }
}