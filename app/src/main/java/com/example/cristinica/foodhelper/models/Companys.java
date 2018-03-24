package com.example.cristinica.foodhelper.models;

/**
 * Created by alex on 3/24/2018.
 */

import java.util.ArrayList;

/**
 * Created by cristi.nica on 3/24/2018.
 */

public class Companys {
    public int id;
    public String nume;
    public String email;
    public String parola;
    public String nume_reprezentant;
    public String telefon;
    public String adresa;
    public String status;
    public ArrayList<Food> foods = new ArrayList<>();
}