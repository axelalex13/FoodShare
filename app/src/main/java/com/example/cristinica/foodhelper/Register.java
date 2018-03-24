package com.example.cristinica.foodhelper;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cristinica.foodhelper.apiConnector.RegisterApi;
import com.example.cristinica.foodhelper.models.LoginModel;

import com.example.cristinica.foodhelper.models.RegisterModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText companyName = findViewById(R.id.firstName);
        final EditText companyMail = findViewById(R.id.email);
        final EditText pass = findViewById(R.id.password);

        Button register = findViewById(R.id.register2);
        SharedPreferences sharedPreferences = Register.this.getSharedPreferences("type", Context
                .MODE_PRIVATE);
        //final SharedPreferences.Editor editor = sharedPreferences.edit();
        final int type = sharedPreferences.getInt("type", -1);

        if (type == 0) {
            register.setBackgroundDrawable(getResources().getDrawable(R.drawable.round));
        } else if (type == 1) {
            register.setBackgroundDrawable(getResources().getDrawable(R.drawable.round2));
        } else {
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    RegisterModel registerModel = new RegisterModel();

                    @Override
                    protected Void doInBackground(Void... params) {
                        String s = RegisterApi.register(companyMail.getText().toString(), companyName.getText().toString(), pass.getText().toString(), 1);
                        Log.v("am primit", s);
                        Gson g = new Gson();
                        registerModel = g.fromJson(s, RegisterModel.class);
                        return null;
                    }

                    protected void onPostExecute(Void param) {
                        if (registerModel.status.equals("ok")) {
                            if (type == 0) {
                                Intent intent = new Intent(Register.this, GiverActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else if (type == 1) {
                                Intent intent = new Intent(Register.this, ReceiverActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        } else {

                        }
                    }

                };
                task.execute();
            }
        });
    }
}