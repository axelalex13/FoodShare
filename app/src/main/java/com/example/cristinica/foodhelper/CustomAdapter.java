package com.example.cristinica.foodhelper;

/**
 * Created by alex on 3/24/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cristinica.foodhelper.models.Companys;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Companys> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewDescriere;
        TextView textViewDates;
        TextView textViewLocatie;
        ImageView coperta;
        public Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.titlu);
            this.textViewDescriere = (TextView) itemView.findViewById(R.id.descriere);
            this.textViewDates = (TextView) itemView.findViewById(R.id.dates);
            this.textViewLocatie = (TextView) itemView.findViewById(R.id.locatie);

            this.coperta = (ImageView) itemView.findViewById(R.id.coperta);

        }
    }

    public CustomAdapter(ArrayList<Companys> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewDescriere = holder.textViewDescriere;
        TextView textViewDates = holder.textViewDates;
        TextView textViewLocatie = holder.textViewLocatie;
        ImageView coperta = holder.coperta;

        textViewName.setText(dataSet.get(listPosition).adresa);
        textViewDescriere.setText(dataSet.get(listPosition).nume_reprezentant);
        textViewDates.setText(dataSet.get(listPosition).nume);
        textViewLocatie.setText(dataSet.get(listPosition).telefon);
        coperta.setImageResource(R.drawable.background);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(), FoodDetails.class);
                intent.putExtra("position",listPosition);
                v.getContext().startActivity(intent);

            }
        } );


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
 
