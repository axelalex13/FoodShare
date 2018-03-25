package com.example.cristinica.foodhelper.apiConnector;

/**
 * Created by alex on 3/25/2018.
 */

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cristi.nica on 3/24/2018.
 */

public class SaveInfoApi {

    public static String apiURL = "http://10.81.130.112/addCompanyInfo.php";

    public static String saveInfo(String email, String newMail, String name, String reprezentant, String phone, String address, int range) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURL);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("email", email);
            postDataParams.put("email_nou", newMail);
            postDataParams.put("nume_companie", name);
            postDataParams.put("telefon", phone);
            postDataParams.put("adresa", address);
            postDataParams.put("nume_reprezentant", reprezentant);
            postDataParams.put("range", range);


            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(postDataParams.toString());
            out.close();
            StringBuilder sb = new StringBuilder();
            sb.append("");
            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            } else {
                System.out.println(connection.getResponseMessage());
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        } finally {
            connection.disconnect();
        }
    }
}