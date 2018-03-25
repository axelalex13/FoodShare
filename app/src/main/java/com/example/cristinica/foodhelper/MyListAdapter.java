package com.example.cristinica.foodhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristinica.foodhelper.apiConnector.ApiDelete;
import com.example.cristinica.foodhelper.apiConnector.LoginApi;
import com.example.cristinica.foodhelper.models.Companys;
import com.example.cristinica.foodhelper.models.Food;
import com.example.cristinica.foodhelper.models.LoginModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alex on 3/24/2018.
 */

public class MyListAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Food> orderName;


    public MyListAdapter(Context context, ArrayList<Food> orderName) {
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

        listView.findViewById(R.id.item_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
                alertDialog.setTitle("Are you sure?");
                alertDialog.setContentText("Do you want to delete this offer?");
                alertDialog.setConfirmText("Yes");
                alertDialog.setCancelText("No");
                alertDialog.show();
                alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                            LoginModel loginModel = new LoginModel();
                            String s;

                            @Override
                            protected Void doInBackground(Void... params) {
                                s = ApiDelete.delete(orderName.get(position).id);
                                Log.v("primit", s);
                                Gson g = new Gson();
                                //loginModel = g.fromJson(s, LoginModel.class);
                                return null;
                            }

                            @SuppressLint("ShowToast")
                            protected void onPostExecute(Void param) {
                                orderName.remove(position);
                                notifyDataSetChanged();
                            }

                        };
                        task.execute();
                        alertDialog.dismiss();
                    }
                });


            }
        });

        return listView;
    }
}
