package com.example.cristinica.foodhelper.apiConnector;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alex on 3/24/2018.
 */

public class LoginApi {

    public static String apiURL = "http://10.81.130.112/login.php";

    public static String login(String email, String password) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURL);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("email", email);
            postDataParams.put("parola", password);
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
            System.out.println("aici: " + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            System.out.println("eroare");
            return new String("Exception: " + e.getMessage());
        } finally {
            connection.disconnect();
        }
    }
}
