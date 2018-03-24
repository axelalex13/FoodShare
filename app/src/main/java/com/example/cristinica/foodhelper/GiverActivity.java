package com.example.cristinica.foodhelper;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GiverActivity extends AppCompatActivity  implements FoodFragment.OnFragmentInteractionListener,AddFood.OnFragmentInteractionListener{
    ViewPager view_pager;
    ViewPagerAdapter adapter;
    public static SlidingTabLayout tabs;
    CharSequence Titles[] = {"Evenimente","Adauga"};
    int Numboftabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giver);

        /// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        view_pager = (ViewPager) findViewById(R.id.view_pagerHome);
        view_pager.setAdapter(adapter);
        //seteaza cate pagini vor fi retinute! daca vrem sa avem refresh de fiecare data trebuie setata la 0
        view_pager.setOffscreenPageLimit(2);


        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabsHome);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabView(R.layout.custom_tab, 0);
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(view_pager);


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
