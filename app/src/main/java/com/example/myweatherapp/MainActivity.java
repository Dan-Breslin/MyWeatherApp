package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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




                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        textView.setText("Response is: " + response.substring(0));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didnt work!");
            }
        });


        queue.add(stringRequest);

    }
}