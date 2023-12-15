package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminPageActivity extends AppCompatActivity {

    private ImageView mBackArrow;

    private Button mAddTitleButton;
    private Button mUpdateTitleButton;
    private Button mManageUsersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        wireupDisplay();



    }

    private void wireupDisplay(){
        mBackArrow = findViewById(R.id.admin_backarrow_imageview);
        mAddTitleButton = findViewById(R.id.add_title_button);
        mUpdateTitleButton = findViewById(R.id.update_title_button);
        mManageUsersButton = findViewById(R.id.manage_user_button);

        mAddTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddMovieActivity.intentFactory(getApplicationContext());
                startActivity(intent);

            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mUpdateTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = UpdateTitleActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        mManageUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ManageUserActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AdminPageActivity.class);
        return intent;
    }
}