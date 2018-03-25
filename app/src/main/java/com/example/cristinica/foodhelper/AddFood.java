package com.example.cristinica.foodhelper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cristinica.foodhelper.apiConnector.AddFoodApi;
import com.example.cristinica.foodhelper.models.LoginModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFood.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFood#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFood extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    String s;
    RadioGroup radioGroup;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddFood() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFood.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFood newInstance(String param1, String param2) {
        AddFood fragment = new AddFood();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_food, container, false);
        final TextView expirationDate = (TextView) view.findViewById(R.id.expirationDate);
        final Spinner dropdown2 = (Spinner) view.findViewById(R.id.spinner2);

        final Button add =  view.findViewById(R.id.addFood);
        final TextView textViewFood = view.findViewById(R.id.textView6);
        final EditText cantitate =  view.findViewById(R.id.cantitate);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private void updateLabel() {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                expirationDate.setText(sdf.format(myCalendar.getTime()));

            }

        };

        expirationDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        String[] unit = {"Kg","Liters","Porsions"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, unit);
        dropdown2.setAdapter(adapter);

        final  ArrayList<ItemData> list=new ArrayList<>();

        list.add(new ItemData("Main Course",R.drawable.maincourse));
        list.add(new ItemData("Soup",R.drawable.soup));
        list.add(new ItemData("Sandwich",R.drawable.sandwich));
        list.add(new ItemData("Desert",R.drawable.cupcake));
        list.add(new ItemData("Juice",R.drawable.juice));
        final Spinner sp=(Spinner) view.findViewById(R.id.spinner);
        final SpinnerAdapter adapter2=new SpinnerAdapter(getActivity(), R.layout.spinner_layout,R.id.txt,list);
        sp.setAdapter(adapter2);

        final ArrayList<ItemData> list2=new ArrayList<>();

        list2.add(new ItemData("",R.drawable.kg));
        list2.add(new ItemData("",R.drawable.plate));
        list2.add(new ItemData("",R.drawable.liter));

        SpinnerAdapter adapter3=new SpinnerAdapter(getActivity(), R.layout.spinner_layout,R.id.txt,list2);
        dropdown2.setAdapter(adapter3);
        @SuppressLint("UseSparseArrays") final HashMap<Integer,String> ids = new HashMap<>();
        ids.put(list2.get(0).getImageId(),"kg");
        ids.put(list2.get(1).getImageId(),"liters");
        ids.put(list2.get(2).getImageId(),"plates");



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



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                final LoginModel loginModel;
                Gson g = new Gson();
                loginModel = g.fromJson(sharedPreferences.getString("user", ""), LoginModel.class);

                final String cantitateS = cantitate.getText().toString();
                final  int id = list2.get(dropdown2.getSelectedItemPosition()).getImageId();
                final String umS = ids.get(id);
                final String expirationDateS = expirationDate.getText().toString();
                final String numeS=list.get(sp.getSelectedItemPosition()).getText();
                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


                    @Override
                    protected Void doInBackground(Void... params) {
                        s = AddFoodApi.add(loginModel.email,numeS,cantitateS,umS,expirationDateS);
                        Log.v("am primit la add", s);


                        return null;

                    }
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if (s.equals("ok\n")) {
                            final SweetAlertDialog alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                            alertDialog.setTitle("Congratulations!");
                            alertDialog.setContentText("Food was added!");
                            alertDialog.setConfirmText("Ok");
                            alertDialog.show();
                            alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    alertDialog.dismiss();
                                    cantitate.setText("");
                                    expirationDate.setText("");
                                }
                            });



                        } else {

                            final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                            alertDialog.setTitle("Error!");
                            alertDialog.setContentText("Something went wrong :( ");
                            alertDialog.setConfirmText("Ok");
                            alertDialog.show();
                            alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    }
                };

                task.execute();
            }
        });







        // Inflate the layout for this fragment
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
