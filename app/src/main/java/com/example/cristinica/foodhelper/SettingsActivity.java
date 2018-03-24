package com.example.cristinica.foodhelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
            Button logout = findViewById(R.id.logout);
            SharedPreferences sharedPreferences2 = SettingsActivity.this.getSharedPreferences("type", Context
                    .MODE_PRIVATE);
            int type = sharedPreferences2.getInt("type", 1);
            if (type == 0){
                logout.setBackgroundDrawable(getResources().getDrawable(R.drawable.round));
            } else if(type == 1) {
                logout.setBackgroundDrawable(getResources().getDrawable(R.drawable.round2));
            }


            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences2 = SettingsActivity.this.getSharedPreferences("logged", Context
                            .MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    editor.putInt("logged", 0);
                    editor.apply();
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
    }
