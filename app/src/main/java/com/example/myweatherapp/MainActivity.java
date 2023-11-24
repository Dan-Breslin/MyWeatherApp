package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String API = "cdcf768c71456152312d5dbeda863008";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txtView_Weather);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=" + API;

        // Basic Location Grab
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        textView.setText("Response is: " + response.substring(0));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didnt work!");
//            }
//        });
        //queue.add(stringRequest);


//        // Edit in class check later
//        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONObject topLevel = response;
//                    JSONObject main = topLevel.getJSONObject("main");
//                    String temp = main.getString("temp");
////                    Toast.makeText(MainActivity.this, main.toString(), Toast.LENGTH_SHORT).show();
//            } catch (JSONException e){
//                    Log.e("Error", e.toString());
////                    throw new RuntimeException(e);
//                }
//        })


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
// Test API Working:   http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=cdcf768c71456152312d5dbeda863008
                    JSONObject topLevel = response;
                    JSONObject main = topLevel.getJSONObject("mai");
                    String temp = main.getString("feels_like");
                    textView.setText("Temp: " + temp + "C");

                    // Basic JSON Pulling
//                    JSONObject main = response.getJSONObject("main");
//                    String temp = String.valueOf(main.getDouble("temp"));
//                    textView.setText("Temp: " + temp + "C");
//                    Toast.makeText(MainActivity.this, main.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("Error", e.toString());
                    //throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
    });
        queue.add(request);
    }
}