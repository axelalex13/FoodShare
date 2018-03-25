package com.example.cristinica.foodhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.cristinica.foodhelper.apiConnector.LoginApi;
import com.example.cristinica.foodhelper.models.LoginModel;
import com.example.cristinica.foodhelper.models.RegisterModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    Button goToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();
        goToRegister = findViewById(R.id.goToRegister);

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);

            }
        });

        final EditText companyMail = findViewById(R.id.email_login);
        final EditText pass = findViewById(R.id.password_login);

        Button register = findViewById(R.id.register2);
        final SharedPreferences sharedPreferences = Login.this.getSharedPreferences("type", Context
                .MODE_PRIVATE);
        //final SharedPreferences.Editor editor = sharedPreferences.edit();
        final int type = sharedPreferences.getInt("type", -1);
        Button login = findViewById(R.id.login2);

        if (type == 0) {
            login.setBackgroundDrawable(getResources().getDrawable(R.drawable.round));
            goToRegister.setBackgroundDrawable(getResources().getDrawable(R.drawable.round));

        } else if (type == 1) {
            login.setBackgroundDrawable(getResources().getDrawable(R.drawable.round2));
            goToRegister.setBackgroundDrawable(getResources().getDrawable(R.drawable.round2));
        } else {
            Log.v("da", "daaaa2");
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    LoginModel loginModel = new LoginModel();
                    String s;
                    @Override
                    protected Void doInBackground(Void... params) {
                        s = LoginApi.login(companyMail.getText().toString(), pass.getText().toString());
                        Log.v("primit", s);
                        Gson g = new Gson();
                        loginModel = g.fromJson(s, LoginModel.class);
                        return null;
                    }

                    @SuppressLint("ShowToast")
                    protected void onPostExecute(Void param) {
                        if (loginModel.status.equals("ok")) {
                            final SharedPreferences sharedPreferences3 = Login.this.getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorUser = sharedPreferences3.edit();
                            editorUser.putString("user", s);
                            editorUser.apply();

                            if (type == 0) {
                                SharedPreferences sharedPreferences2 = Login.this.getSharedPreferences("logged", Context
                                        .MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                editor2.putInt("logged", 1);
                                editor2.apply();

                                Intent intent = new Intent(Login.this, GiverActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            if (type == 1) {
                                SharedPreferences sharedPreferences2 = Login.this.getSharedPreferences("logged", Context
                                        .MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                editor2.putInt("logged", 1);
                                editor2.apply();

                                Intent intent = new Intent(Login.this, ReceiverActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT);
                        }
                    }

                };
                task.execute();
            }
        });

    }


}