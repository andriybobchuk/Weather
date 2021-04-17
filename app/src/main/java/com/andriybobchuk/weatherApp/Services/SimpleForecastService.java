package com.andriybobchuk.weatherApp.Services;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleForecastService {


    public static apiCallback callback;
    public static String temp, desc;

    public static void setCallback(apiCallback newCallback) {
        callback = newCallback;
    }

    public static void simpleGetForecaast(Context context) {

        String URL = "https://api.openweathermap.org/data/2.5/onecall?lat=50.29761&lon=18.67658&exclude=minutely,hourly,daily,alerts&appid=ace729200f31ff6473436ef39ad854ea&units=metric&lang=en";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    temp = String.valueOf(response.getJSONObject("current").getInt("temp") + "°");
                    desc = String.valueOf(response.getJSONObject("current").getInt("temp") + "r");

                    callback.displayResult(temp, desc);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jor);
    }

    public interface apiCallback {
        void displayResult(String temp, String desc);
    }
}
