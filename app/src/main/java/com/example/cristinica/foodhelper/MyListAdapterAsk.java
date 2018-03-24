package com.example.cristinica.foodhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cristinica.foodhelper.R;
import com.example.cristinica.foodhelper.models.Companys;

import java.util.ArrayList;

/**
 * Created by alex on 3/25/2018.
 */

public class MyListAdapterAsk extends BaseAdapter {

    private Context context;

    private ArrayList<Companys> orderName;


    public MyListAdapterAsk(Context context, ArrayList<Companys> orderName){
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
        listView = inflater.inflate(R.layout.list_layout_ask, null);

        TextView name = (TextView) listView.findViewById(R.id.companyName);


        name.setText(orderName.get(position).nume);


        return listView;
    }
}
