package com.andriybobchuk.weatherApp.Services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.andriybobchuk.weatherApp.Activities.MainActivity;
import com.andriybobchuk.weatherApp.R;
import com.andriybobchuk.weatherApp.Structures.TimeAndDate;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import static java.lang.StrictMath.round;


/*
    ForecastService is an object template that has its properties - fields with weather data and things it can do -
    get the weather forecast
 */
public class ForecastService {


    /** ARRAYS WITH WEATHER 7-day DATA STRINGS **/
    public static String [ ] arr_date = new String [7];
    public static String [ ] arr_min_max = new String [7];
    public static String [ ] arr_details = new String [7];
    public static String [ ] arr_humidity = new String [7];
    public static String [ ] arr_clouds = new String [7];
    public static String [ ] arr_uvi = new String [7];
    public static String [ ] arr_wind = new String [7];
    public static String [ ] arr_temperature = new String [7];
    public static String [ ] arr_pressure = new String [7];
    public static String [ ] arr_theme = new String [7];
    public static String [ ] arr_sunrise = new String [7];
    public static String [ ] arr_sunset = new String [7];
    public static String [ ] arr_pop = new String [7];

    /** ARRAYS WITH 12-HOUR WEATHER DATA FOR "INSIDE THE DAY LONG PANEL" **/
    public static String[] arr_time = new String [12];
    public static String[] arr_temp = new String [12];
    public static String[] arr_descript = new String [12];
    public static String[] arr_pres = new String [12];

    /** ARRAYS WITH 7-day WEATHER DATA FOR "INSIDE THE DAY" SHORT PANEL **/
    public static String [ ] arr_morning = new String [7];
    public static String [ ] arr_afternoon = new String [7];
    public static String [ ] arr_eve = new String [7];
    public static String [ ] arr_night = new String [7];

    public static String today_temperature;


    public static void getForecast(final MainActivity mainActivity, String city, String units)
    {
        String lat = null, lon = null;

        Geocoder coder = new Geocoder(mainActivity);
        List<Address> address;

        try {
            address = coder.getFromLocationName(city, 5);
            if (address == null) {

            }
            Address location = address.get(0);
            lat = String.valueOf(location.getLatitude());
            lon = String.valueOf(location.getLongitude());


        } catch (Exception ex) {

            Toast.makeText(mainActivity, "Converting lat\\lon, wait..", Toast.LENGTH_SHORT);
            ex.printStackTrace();
        }


        //String URL = "https://api.openweathermap.org/data/2.5/onecall?lat=50.29761&lon=18.67658&exclude=minutely&appid=ace729200f31ff6473436ef39ad854ea&units=metric&lang=en";
        String URLbase = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=minutely&appid=ace729200f31ff6473436ef39ad854ea&units=" + units + "&lang=en";


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URLbase, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                ShimmerFrameLayout shimmer = mainActivity.findViewById(R.id.shimmer);
                shimmer.stopShimmer();
                shimmer.setVisibility(View.GONE);

                HorizontalScrollView horizontalScrollView = mainActivity.findViewById(R.id.horizontalScrollView);
                horizontalScrollView.setVisibility(View.VISIBLE);

                LinearLayout ll_sunrise_sunset = mainActivity.findViewById(R.id.ll_sunrise_sunset);
                ll_sunrise_sunset.setVisibility(View.VISIBLE);

                TextView tv_insidetheday = mainActivity.findViewById(R.id.tv_insidetheday);
                tv_insidetheday.setVisibility(View.VISIBLE);

                ConstraintLayout cl_insideTheDaySimplified = mainActivity.findViewById(R.id.cl_insideTheDaySimplified);
                cl_insideTheDaySimplified.setVisibility(View.VISIBLE);

                try {

                    new TimeAndDate().setTimezone_offset(response.getInt("timezone_offset")/3600); // timezone offset in hours
                    new TimeAndDate().setCurrentDateAndTime(response.getJSONObject("current").getLong("dt")*1000); // time of last API call


                    for(int dayIndex = 0; dayIndex <= 6; dayIndex++)
                    {

                        Date date = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("dt"))*1000);
                        arr_date[dayIndex] = String.valueOf(new TimeAndDate().getDateFormat().format(date));

                        //new TimeAndDate().getTimeFormat().format(new TimeAndDate().getCurrentDateAndTime())));
                        Date sunriseTime = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("sunrise"))*1000);
                        arr_sunrise[dayIndex] = String.valueOf(new TimeAndDate().getTimeFormat().format(sunriseTime));

                        Date sunsetTime = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("sunset"))*1000);
                        arr_sunset[dayIndex] = String.valueOf(new TimeAndDate().getTimeFormat().format(sunsetTime));

                        arr_pop[dayIndex]= String.valueOf(String.format("%.0f", response.getJSONArray("daily").getJSONObject(dayIndex).getDouble("pop") * 100) +"%");
                        arr_temperature[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("day"));
                        arr_min_max[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("min")+"°... "+response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("max")+"°");
                        arr_details[dayIndex] = String.valueOf("Feels like " + String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("feels_like").getInt("day")) + "°, " + response.getJSONArray("daily").getJSONObject(dayIndex).getJSONArray("weather").getJSONObject(0).getString("description"));
                        arr_humidity[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("humidity")+"%");
                        arr_clouds[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("clouds")+"%");
                        arr_uvi[dayIndex]= String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("uvi")+"%");
                        arr_wind[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("wind_speed")+" kmh");
                        arr_pressure[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("pressure")+" mb");
                        arr_theme[dayIndex] = response.getJSONArray("daily").getJSONObject(dayIndex).getJSONArray("weather").getJSONObject(0).getString("main");

                        arr_morning[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("morn") + "°");
                        arr_afternoon[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("day") + "°");
                        arr_eve[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("eve") + "°");
                        arr_night[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("night") + "°");
                    }
                    today_temperature = String.valueOf(response.getJSONObject("current").getInt("temp"));


                    /* INSIDE THE DAY (long) */
                    for(int time = 0; time <= 11; time++)
                    {
                        arr_time[time] = String.valueOf(new TimeAndDate().getTimeFormat().format((response.getJSONArray("hourly").getJSONObject(time).getLong("dt"))*1000));
                        arr_temp[time] = response.getJSONArray("hourly").getJSONObject(time).getInt("temp") +"°";
                        arr_descript[time] = response.getJSONArray("hourly").getJSONObject(time).getJSONArray("weather").getJSONObject(0).getString("main");
                        arr_pres[time] = response.getJSONArray("hourly").getJSONObject(time).getInt("pressure") +" mb";
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mainActivity, "Converting lat\\lon, wait..", 3000);

                }

                mainActivity.reloadUI();

                // Remove internet failure msg box
                ConstraintLayout cl_internetError = mainActivity.findViewById(R.id.cl_internetError);
                cl_internetError.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView tv_error = mainActivity.findViewById(R.id.tv_error);
                tv_error.startAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.blinking));

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    tv_error.setText("No internet connection");

                } else if (error instanceof ClientError) {
                    tv_error.setText(String.valueOf("Client error"));


                } else if (error instanceof ServerError) {
                    tv_error.setText("Server's dead, try later");

                } else if (error instanceof NetworkError) {
                } else if (error instanceof ParseError) {
                }

                // Internet failure msg box
                ConstraintLayout cl_internetError = mainActivity.findViewById(R.id.cl_internetError);
                cl_internetError.setVisibility(View.VISIBLE);
            }
        });


        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        queue.add(jor);
    }





}
