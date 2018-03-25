package com.example.cristinica.foodhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.cristinica.foodhelper.apiConnector.SaveInfoApi;
import com.example.cristinica.foodhelper.models.LoginModel;
import com.example.cristinica.foodhelper.models.RegisterModel;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingsActivity extends AppCompatActivity {
    public static int range = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button logout = findViewById(R.id.logout);

        final SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);

        final TextView name = findViewById(R.id.editText2);
        final TextView email = findViewById(R.id.editText3);
        final TextView address = findViewById(R.id.editText5);
        final TextView phone = findViewById(R.id.editText4);
        final TextView reprezentant = findViewById(R.id.editText6);
        final LoginModel loginModel;

        Gson g = new Gson();
        loginModel = g.fromJson(sharedPreferences.getString("user", ""), LoginModel.class);
        if (range == -1)
            range = loginModel.range;
        if (loginModel.nume != null)
            name.setText(loginModel.nume);
        if (loginModel.email != null)
            email.setText(loginModel.email);
        if (loginModel.adresa != null)
            address.setText(loginModel.adresa);
        if (loginModel.telefon != null)
            phone.setText(loginModel.telefon);
        if (loginModel.nume_reprezentant != null)
            reprezentant.setText(loginModel.nume_reprezentant);


        SharedPreferences sharedPreferences2 = SettingsActivity.this.getSharedPreferences("type", Context
                .MODE_PRIVATE);
        int type = sharedPreferences2.getInt("type", 1);
        if (type == 0) {
            logout.setBackgroundDrawable(getResources().getDrawable(R.drawable.round));
        } else if (type == 1) {
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


        Button save = findViewById(R.id.save);
        if (type == 0) {
            save.setBackgroundDrawable(getResources().getDrawable(R.drawable.round));
            findViewById(R.id.seekBar).setAlpha(0);
            findViewById(R.id.textView13).setAlpha(0);
            findViewById(R.id.imageView9).setAlpha(0);
        } else if (type == 1) {
            save.setBackgroundDrawable(getResources().getDrawable(R.drawable.round2));
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    RegisterModel registerModel = new RegisterModel();

                    @Override
                    protected Void doInBackground(Void... params) {
                        String s = SaveInfoApi.saveInfo(loginModel.email, email.getText().toString(), name.getText().toString(),
                                reprezentant.getText().toString(), phone.getText().toString(), address.getText().toString(), range);
                        Log.v("am primit", s);
                        Gson g = new Gson();
                        System.out.println(s);
                        //registerModel = g.fromJson(s, RegisterModel.class);
                        return null;
                    }

                    protected void onPostExecute(Void param) {
                        final SweetAlertDialog alertDialog = new SweetAlertDialog(SettingsActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        alertDialog.setTitle("Your data has been changed!");

                        alertDialog.setConfirmText("Ok");
                        alertDialog.show();
                        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                alertDialog.dismiss();
                                finish();
                            }
                        });
                    }

                };
                task.execute();

            }
        });

        if (type == 1) {
            SeekBar seekBar = findViewById(R.id.seekBar);
            seekBar.setProgress(loginModel.range);
            TextView textView = findViewById(R.id.textView13);
            textView.setText(String.valueOf(range));
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    range = i;
                    TextView textView = findViewById(R.id.textView13);
                    textView.setText(String.valueOf(i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }


    }
}