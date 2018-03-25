package com.example.cristinica.foodhelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cristinica.foodhelper.models.Companys;
import com.example.cristinica.foodhelper.models.Food;

import java.util.ArrayList;

public class FoodDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        getSupportActionBar().setTitle("Details");

        TextView name = findViewById(R.id.textView2);
        TextView locatie = findViewById(R.id.textView8);
        TextView telefon = findViewById(R.id.textView9);
        TextView persoana = findViewById(R.id.textView10);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        final Companys companys = SearchFood.arr.get(position);
        name.setText(companys.nume);
        locatie.setText(companys.adresa);
        telefon.setText(companys.telefon);
        persoana.setText(companys.nume_reprezentant);
        findViewById(R.id.call).setBackgroundDrawable(getResources().getDrawable(R.drawable.round2));
        findViewById(R.id.go_to).setBackgroundDrawable(getResources().getDrawable(R.drawable.round2));

        ArrayList<Food> foods;
        ListAdapter adapter;
        ListView listView;
        listView = findViewById(R.id.list);
        adapter = new SecondListAdapter(FoodDetails.this, companys.foods);
        listView.setAdapter(adapter);

        Button call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = companys.telefon;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        Button goTo = findViewById(R.id.go_to);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Uri gmmIntentUri = Uri.parse("geo:0,0?q=1600 Amphitheatre Parkway, Mountain+View, California");
                String[] arr = companys.adresa.split(" ");
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + companys.adresa);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }
}

