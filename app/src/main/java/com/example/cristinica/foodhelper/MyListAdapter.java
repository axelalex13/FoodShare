package com.example.cristinica.foodhelper;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cristinica.foodhelper.models.Companys;
import com.example.cristinica.foodhelper.models.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 3/24/2018.
 */

public class MyListAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Food> orderName;


    public MyListAdapter(Context context, ArrayList<Food> orderName){
        this.context = context;
        this.orderName = orderName;

    }

    @Override
    public int getCount() {
        return orderName.size();
    }

    @Override
    public Object getItem(int position) {
        return orderName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View listView;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = inflater.inflate(R.layout.list_layout, null);

        TextView name = (TextView) listView.findViewById(R.id.nameFood);
        TextView data = (TextView) listView.findViewById(R.id.dateFood);
        TextView cantitate = (TextView) listView.findViewById(R.id.cantitateFood);
        TextView um = (TextView) listView.findViewById(R.id.umFood);

        name.setText(orderName.get(position).nume);
        data.setText(orderName.get(position).data_expirare);
        cantitate.setText(String.valueOf(orderName.get(position).cantitate));
        um.setText(orderName.get(position).um);

        return listView;
    }
}