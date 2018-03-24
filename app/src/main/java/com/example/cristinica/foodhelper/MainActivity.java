package com.example.cristinica.foodhelper;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout help = findViewById(R.id.share_food);
        RelativeLayout need = findViewById(R.id.need_food);
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("type", Context
                .MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPreferences sharedPreferences2 = MainActivity.this.getSharedPreferences("logged", Context
                .MODE_PRIVATE);
        int logged = sharedPreferences2.getInt("logged", -1);
        int type = sharedPreferences.getInt("type", -1);
        if (logged == 1 && type == 0) {
            Intent intent = new Intent(MainActivity.this, GiverActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (logged == 1 && type == 1) {
            Intent intent = new Intent(MainActivity.this, ReceiverActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        //companie
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("type", 0);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
        //azil
        need.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("type", 1);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }
}