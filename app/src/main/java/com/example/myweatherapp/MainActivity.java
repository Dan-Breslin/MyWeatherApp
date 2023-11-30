package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String API = "cdcf768c71456152312d5dbeda863008";
    private static final String API2 = "fJp0xyULbALUkMqxMrvs8VKk3UjeVtJlh8kc4r7Y";

    TextView tVTemp, tVHum, tVWind, tVWeather, tVInDepth, tVTitle;
    Button ent_button;
    EditText ent_text;
    String newLocation = "Derry";

    WebView webView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tVTemp = (TextView) findViewById(R.id.tv_Temp);
        tVHum = (TextView) findViewById(R.id.tv_Hum);
        tVWind = (TextView) findViewById(R.id.tv_Wind);
        tVWeather = (TextView) findViewById(R.id.tv_Cond);
        tVInDepth = (TextView) findViewById(R.id.tv_Depth);
        ent_button = (Button) findViewById(R.id.btn_Enter);
        ent_text = (EditText) findViewById(R.id.edittxt_Location);
        webView = (WebView) findViewById(R.id.webNasa);
        tVTitle = (TextView) findViewById(R.id.tv_Title);

        getJson();

        ent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLocation = ent_text.getText().toString();
                getJson();
            }
        });
}

    public void getJson(){
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://api.openweathermap.org/data/2.5/weather?q="+ newLocation + ",world&APPID=" + API;
            String urlPic = "https://api.nasa.gov/planetary/apod?api_key=" + API2;

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
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {
                    try {
// Test API Working:   http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=cdcf768c71456152312d5dbeda863008
                        // Basic JSON Pull
//                    JSONObject topLevel = response;
//                    JSONObject main = topLevel.getJSONObject("main");
//                    String temp = main.getString("feels_like");
//                    textView.setText("Temp: " + temp + "C");

                        // JSON Array Pull from Weather
                        String condition = "",description = "";
                        JSONObject topLevel = response;
                        JSONArray jsonarray = new JSONArray(topLevel.getString("weather"));

                        for(int i=0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String id       = jsonobject.getString("id");
                            condition    = jsonobject.getString("main");
                            description  = jsonobject.getString("description");
                            String icon = jsonobject.getString("icon");
                        }

                        // JSON Pulling
                        JSONObject main = response.getJSONObject("main");
                        String temp = String.valueOf(main.getDouble("temp"));
                        String hum = String.valueOf(main.getDouble("humidity"));
                        JSONObject wind = response.getJSONObject("wind");
                        String speed = String.valueOf(wind.getDouble("speed"));

                        tVTemp.setText(temp + "°C"); // ALT+0176
                        tVHum.setText(hum + "%");
                        tVWind.setText(speed + "mph");
                        tVWeather.setText(condition);
                        tVInDepth.setText(description);

                    } catch (JSONException e) {
                        Log.e("Error", e.toString());
                        //throw new RuntimeException(e);
                    }
                }
            }, error -> Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show());
            queue.add(request);

            JsonObjectRequest requestPic = new JsonObjectRequest(Request.Method.GET, urlPic, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response2) {
                    try {
                        JSONObject topLevel2 = response2;
                        String pic = topLevel2.getString("url");
                        String title = topLevel2.getString("title");
                        webView.loadUrl(pic);
                        webView.getSettings().setLoadWithOverviewMode(true);
                        webView.getSettings().setUseWideViewPort(true);
                        tVTitle.setText(title);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, error -> Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show());
            queue.add(requestPic);
        }
}