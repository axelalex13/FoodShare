package com.example.cristinica.foodhelper;

/**
 * Created by alex on 3/24/2018.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cristinica.foodhelper.apiConnector.AddFoodApi;
import com.example.cristinica.foodhelper.apiConnector.SearchApi;
import com.example.cristinica.foodhelper.models.Companys;
import com.example.cristinica.foodhelper.models.LoginModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by cristi.nica on 3/24/2018.
 */

public class AskForFood extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    RadioGroup radioGroup;
    String s;
    Button search;
    ListView listViewAsk;
    MyListAdapterAsk adapter;
    static public ArrayList<Companys> companys = new ArrayList<Companys>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AskForFood() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_for_food, container, false);
        final TextView textViewFood = view.findViewById(R.id.textView6);
        final EditText cantitate =  view.findViewById(R.id.cantitate);
        listViewAsk = view.findViewById(R.id.listAsk);
        final ArrayList<ItemData> list=new ArrayList<>();
        search = view.findViewById(R.id.search);
        list.add(new ItemData("Main Course",R.drawable.maincourse));
        list.add(new ItemData("Soup",R.drawable.soup));
        list.add(new ItemData("Sandwich",R.drawable.sandwich));
        list.add(new ItemData("Desert",R.drawable.cupcake));
        list.add(new ItemData("Juice",R.drawable.juice));
        final Spinner sp=(Spinner) view.findViewById(R.id.spinner_ask_food);
        final SpinnerAdapter adapter2=new SpinnerAdapter(getActivity(), R.layout.spinner_layout,R.id.txt,list);
        sp.setAdapter(adapter2);

        final  ArrayList<ItemData> list3=new ArrayList<>();


        list3.add(new ItemData("Oil",R.drawable.oil));
        list3.add(new ItemData("Sugar",R.drawable.sugar));
        list3.add(new ItemData("Flour",R.drawable.flour));
        list3.add(new ItemData("Rice",R.drawable.rice));
        final SpinnerAdapter adapter4=new SpinnerAdapter(getActivity(), R.layout.spinner_layout,R.id.txt,list3);


        radioGroup=(RadioGroup)view.findViewById(R.id.radioGroup);
        final RadioButton rb1 = (RadioButton)  view.findViewById(R.id.radio_food);
        final RadioButton rb2 = (RadioButton)  view.findViewById(R.id.radio_ingredients);
        radioGroup.check(rb1.getId());
        View.OnClickListener button1Listener = new View.OnClickListener() {
            public void onClick(View v) {
                sp.setAdapter(adapter2);
                textViewFood.setText("Food type");


            }
        };

        View.OnClickListener button2Listener = new View.OnClickListener() {
            public void onClick(View v) {
                sp.setAdapter(adapter4);
                textViewFood.setText("Select ingredient");
            }
        };
        rb1.setOnClickListener(button1Listener);
        rb2.setOnClickListener(button2Listener);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String cantitateS = cantitate.getText().toString();

                final String numeS=list.get(sp.getSelectedItemPosition()).getText();
                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


                    @Override
                    protected Void doInBackground(Void... params) {
                        s = SearchApi.search(numeS,cantitateS);
                        Log.v("am primit la search", s);
                        Gson g = new Gson();
                        companys = g.fromJson(s,  new TypeToken<ArrayList<Companys>>(){}.getType());
                        return null;

                    }
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        adapter = new MyListAdapterAsk(getContext(), companys);
                        listViewAsk.setAdapter(adapter);
                        if (s.equals("null\n")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setTitle("0 results");

                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();


                        }
                    }
                };

                task.execute();
            }
        });









        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
