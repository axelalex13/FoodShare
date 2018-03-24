package com.example.cristinica.foodhelper.apiConnector;

/**
 * Created by alex on 3/24/2018.
 */

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cristi.nica on 3/24/2018.
 */

public class GetFoodApi {

    public static String apiURL = "http://10.81.130.112/getProducts.php";
    public static String getAllFood() throws IOException, JSONException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        try {

            URL url = new URL(apiURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return result.toString();
    }
}
