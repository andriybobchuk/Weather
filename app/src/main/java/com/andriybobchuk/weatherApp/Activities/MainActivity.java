package com.andriybobchuk.weatherApp.Activities;

import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.andriybobchuk.weatherApp.R;

import com.andriybobchuk.weatherApp.Structures.TimeAndDate;
import com.andriybobchuk.weatherApp.Structures.WeatherData;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;

import java.util.Date;

/** This class updates User interface
 *
 * NOTE:
 * This class ONLY updates User interface **/

public class MainActivity extends AppCompatActivity {


    /* Bottom M T W T F S S panel
    Used in OnCreate() and updateButtonPanel(), thus global */
    Button btn_day0,btn_day1,btn_day2,btn_day3,btn_day4,btn_day5,btn_day6;



    /** Opens Options Activity on button click **/
    public void openOptions() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /** 1st GETFORECAST() call out of 2 **/
        getForecast();


        /* For button OPTIONS */
        ImageButton btn_options = findViewById((R.id.btn_options));
        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptions();
            }
        });


        final Vibrator vibrator = (Vibrator)getSystemService(MainActivity.VIBRATOR_SERVICE);

        btn_day0 = findViewById((R.id.btn_day0));
        btn_day0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUI(0);


                btn_day0.setSelected(true);btn_day1.setSelected(false);btn_day2.setSelected(false);btn_day3.setSelected(false);btn_day4.setSelected(false);btn_day5.setSelected(false);btn_day6.setSelected(false);

                vibrator.vibrate(VibrationEffect.createOneShot(42, VibrationEffect.DEFAULT_AMPLITUDE));



            }
        });


        btn_day1 = findViewById((R.id.btn_day1));
        btn_day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUI(1);


                vibrator.vibrate(VibrationEffect.createOneShot(42, VibrationEffect.DEFAULT_AMPLITUDE));
                btn_day1.setSelected(true);

                btn_day0.setSelected(false);
                btn_day2.setSelected(false);
                btn_day3.setSelected(false);
                btn_day4.setSelected(false);
                btn_day5.setSelected(false);
                btn_day6.setSelected(false);

            }
        });

        btn_day2 = findViewById((R.id.btn_day2));
        btn_day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUI(2);


                vibrator.vibrate(VibrationEffect.createOneShot(42, VibrationEffect.DEFAULT_AMPLITUDE));
                btn_day2.setSelected(true);

                btn_day1.setSelected(false);
                btn_day0.setSelected(false);
                btn_day3.setSelected(false);
                btn_day4.setSelected(false);
                btn_day5.setSelected(false);
                btn_day6.setSelected(false);
            }
        });
        btn_day3 = findViewById((R.id.btn_day3));
        btn_day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUI(3);


                vibrator.vibrate(VibrationEffect.createOneShot(42, VibrationEffect.DEFAULT_AMPLITUDE));
                btn_day3.setSelected(true);

                btn_day1.setSelected(false);
                btn_day2.setSelected(false);
                btn_day0.setSelected(false);
                btn_day4.setSelected(false);
                btn_day5.setSelected(false);
                btn_day6.setSelected(false);
            }
        });
        btn_day4 = findViewById((R.id.btn_day4));
        btn_day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUI(4);

                vibrator.vibrate(VibrationEffect.createOneShot(42, VibrationEffect.DEFAULT_AMPLITUDE));
                btn_day4.setSelected(true);

                btn_day1.setSelected(false);
                btn_day2.setSelected(false);
                btn_day3.setSelected(false);
                btn_day0.setSelected(false);
                btn_day5.setSelected(false);
                btn_day6.setSelected(false);
            }
        });
        btn_day5 = findViewById((R.id.btn_day5));
        btn_day5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUI(5);

                vibrator.vibrate(VibrationEffect.createOneShot(42, VibrationEffect.DEFAULT_AMPLITUDE));
                btn_day5.setSelected(true);

                btn_day1.setSelected(false);
                btn_day2.setSelected(false);
                btn_day3.setSelected(false);
                btn_day4.setSelected(false);
                btn_day0.setSelected(false);
                btn_day6.setSelected(false);
            }
        });
        btn_day6 = findViewById((R.id.btn_day6));
        btn_day6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUI(6);
                vibrator.vibrate(VibrationEffect.createOneShot(42, VibrationEffect.DEFAULT_AMPLITUDE));
                btn_day6.setSelected(true);

                btn_day1.setSelected(false);
                btn_day2.setSelected(false);
                btn_day3.setSelected(false);
                btn_day4.setSelected(false);
                btn_day5.setSelected(false);
                btn_day0.setSelected(false);

            }
        });

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                /** 2nd GETFORECAST() call out of 2 **/
                getForecast();
                refreshLayout.setRefreshing(false);
            }
        });


    }


    /** Sets M T W T F S S button sequence **/
    public void updateButtonPanel() {

        /**
         * 1 - Function for filling the bottom 7-days button panel with the first letters of week days.
         * 2 - It also highlights the 0-th day button and unhighlights the rest
         *
         * NOTE:
         * Should be executed only once on app start or restart but not on button clicks ↓
         * → That is why I call it only in RE-loadUI(), not in normal loadUI(dayIndex)
         */

        btn_day0 = findViewById(R.id.btn_day0);
        btn_day1 = findViewById(R.id.btn_day1);
        btn_day2 = findViewById(R.id.btn_day2);
        btn_day3 = findViewById(R.id.btn_day3);
        btn_day4 = findViewById(R.id.btn_day4);
        btn_day5 = findViewById(R.id.btn_day5);
        btn_day6 = findViewById(R.id.btn_day6);
        Button daysButtons[] = { btn_day0, btn_day1, btn_day2, btn_day3, btn_day4, btn_day5, btn_day6 };

        // sets buttons highlighting
        btn_day0 = findViewById(R.id.btn_day0);
        btn_day0.setSelected(true);
        btn_day1.setSelected(false);
        btn_day2.setSelected(false);
        btn_day3.setSelected(false);
        btn_day4.setSelected(false);
        btn_day5.setSelected(false);
        btn_day6.setSelected(false);

        for(int i=0; i<=6; i++)
        {
            daysButtons[i].setText(String.valueOf(new WeatherData().arr_date[i].charAt(0)));
        }
    }


    /** Sets background Zing image and description label */
    public  void updateBackground(int dayIndex)
    {
        TextView tv_myWeatherDescription = findViewById(R.id.tv_hint);
        ImageView iv_theme = findViewById(R.id.iv_theme);
        switch(new WeatherData().arr_theme[dayIndex])
        {
            case "Clouds":
                tv_myWeatherDescription.setText("Not the nicest weather");
                iv_theme.setImageResource(R.drawable.clouds);
                break;
            case "Clear":
                tv_myWeatherDescription.setText("The weather's just perfect!");
                iv_theme.setImageResource(R.drawable.theme_sunny);
                break;
            case "Rain":
                tv_myWeatherDescription.setText("Take ur umbrella with you!");
                iv_theme.setImageResource(R.drawable.theme_rain);
                break;
            case "Snow":
                tv_myWeatherDescription.setText("All the weather outside is frightful..");
                iv_theme.setImageResource(R.drawable.theme_snow);
                break;
            default:
                tv_myWeatherDescription.setText("It's strange outside");
                iv_theme.setImageResource(R.drawable.clouds);
        }
    }


    /** Fills all labels using all arrays */
    public void updateData(int dayIndex)
    {
        /**
         * 1 - This function takes day index from pressed button (0-6) or from loadApp()(0)
         * 2 - DECLARE and INITIALIZE LOCAL text labels with data from WeatherData
         *
         * And that's all. So simple
         *
         * NOTE:
         * In order to avoid confusion, text labels will be initialized as they appear
         * on the app screen (one after another).
         */

        // Tuesday, Feb 13
        TextView tv_day = findViewById(R.id.tv_day);
        tv_day.setText(new WeatherData().arr_date[dayIndex]);


        // Gliwice at 13:45
        TextView tv_region = findViewById(R.id.tv_region);
        tv_region.setText("Gliwice at " + String.valueOf(new TimeAndDate().getTimeFormat().format(new TimeAndDate().getCurrentDateAndTime())));


        //  -9°C
        TextView tv_temperature = findViewById(R.id.tv_temperature);
        if (dayIndex == 0) {
            tv_temperature.setText(new WeatherData().today_temperature + "°C");
        } else {
            tv_temperature.setText(new WeatherData().arr_temperature[dayIndex] + "°C");
        }


        //-3°...0°
        TextView tv_min_max = findViewById(R.id.tv_min_max);
        tv_min_max.setText(new WeatherData().arr_min_max[dayIndex]);


//TODO:------------------------------------------------------------------------------------------
//        /* Horizontal ScrollView */
//        TextView tv_pop, tv_pressure, tv_humidity, tv_wind, tv_uvi, tv_clouds;
//
//                tv_region, tv_temperature, tv_min_max, tv_details
//
//
//
//        tv_details.setText(new WeatherData().arr_details[dayIndex]);
//        tv_humidity.setText(new WeatherData().arr_humidity[dayIndex]);
//        tv_clouds.setText(new WeatherData().arr_clouds[dayIndex]);
//        tv_uvi.setText(new WeatherData().arr_uvi[dayIndex]);
//        tv_wind.setText(new WeatherData().arr_wind[dayIndex]);
//        tv_pressure.setText(new WeatherData().arr_pressure[dayIndex]);
//        tv_sunrise.setText(new WeatherData().arr_sunrise[dayIndex]);
//        tv_sunset.setText(new WeatherData().arr_sunset[dayIndex]);
//        tv_pop.setText(new WeatherData().arr_pop[dayIndex]);
//
//
//
//        /* Bar under the horizontal ScrollView */
//        TextView tv_sunrise, tv_sunset;
//
//



        //TODO:------------------------------------------------------------------------------------------


//        tv_temperature = findViewById(R.id.tv_temperature);
//        tv_min_max = findViewById(R.id.tv_min_max);
//
//        tv_details = findViewById(R.id.tv_details);
//        tv_pressure = findViewById(R.id.tv_pressure);
//        tv_humidity = findViewById(R.id.tv_humidity);
//        tv_wind = findViewById(R.id.tv_wind);
//        tv_uvi = findViewById(R.id.tv_uvi);
//        tv_clouds = findViewById(R.id.tv_clouds);
//        tv_pop = findViewById(R.id.tv_pop);
//
//        tv_sunrise = findViewById(R.id.tv_sunrise);
//        tv_sunset = findViewById(R.id.tv_sunset);

        //tv_region = findViewById(R.id.tv_region);
        //

        /* Hourly forecast panel */
//        LinearLayout ll_time = findViewById(R.id.ll_time);
//        LinearLayout ll_temp = findViewById(R.id.ll_temp);
//        LinearLayout ll_icos = findViewById(R.id.ll_icos);
//        LinearLayout ll_pres = findViewById(R.id.ll_pres);

//        TextView tv_time0 = findViewById(R.id.tv_time0);
//        TextView tv_time1 = findViewById(R.id.tv_time1);
//        TextView tv_time2 = findViewById(R.id.tv_time2);
//        TextView tv_time3 = findViewById(R.id.tv_time3);
//        TextView tv_time4 = findViewById(R.id.tv_time4);
//        TextView tv_time5 = findViewById(R.id.tv_time5);
//        TextView tv_time6 = findViewById(R.id.tv_time6);
//        TextView tv_time7 = findViewById(R.id.tv_time7);
//        TextView tv_time8 = findViewById(R.id.tv_time8);
//        TextView tv_time9 = findViewById(R.id.tv_time9);
//        TextView tv_time10 = findViewById(R.id.tv_time10);
//        TextView tv_time11 = findViewById(R.id.tv_time11);
//        TextView _12_timeLabels[] = { tv_time0, tv_time1, tv_time2, tv_time3, tv_time4, tv_time5, tv_time6, tv_time7,
//                tv_time8,tv_time9,tv_time10,tv_time11 };

//        TextView tv_temp0 = findViewById(R.id.tv_temp0);
//        TextView tv_temp1 = findViewById(R.id.tv_temp1);
//        TextView tv_temp2 = findViewById(R.id.tv_temp2);
//        TextView tv_temp3 = findViewById(R.id.tv_temp3);
//        TextView tv_temp4 = findViewById(R.id.tv_temp4);
//        TextView tv_temp5 = findViewById(R.id.tv_temp5);
//        TextView tv_temp6 = findViewById(R.id.tv_temp6);
//        TextView tv_temp7 = findViewById(R.id.tv_temp7);
//        TextView tv_temp8 = findViewById(R.id.tv_temp8);
//        TextView tv_temp9 = findViewById(R.id.tv_temp9);
//        TextView tv_temp10 = findViewById(R.id.tv_temp10);
//        TextView tv_temp11 = findViewById(R.id.tv_temp11);
//        TextView _12_tempLabels[] = { tv_temp0, tv_temp1, tv_temp2, tv_temp3, tv_temp4, tv_temp5, tv_temp6, tv_temp7,
//                tv_temp8,tv_temp9,tv_temp10,tv_temp11 };

//        TextView tv_pressure0 = findViewById(R.id.tv_pressure0);
//        TextView tv_pressure1 = findViewById(R.id.tv_pressure1);
//        TextView tv_pressure2 = findViewById(R.id.tv_pressure2);
//        TextView tv_pressure3 = findViewById(R.id.tv_pressure3);
//        TextView tv_pressure4 = findViewById(R.id.tv_pressure4);
//        TextView tv_pressure5 = findViewById(R.id.tv_pressure5);
//        TextView tv_pressure6 = findViewById(R.id.tv_pressure6);
//        TextView tv_pressure7 = findViewById(R.id.tv_pressure7);
//        TextView tv_pressure8 = findViewById(R.id.tv_pressure8);
//        TextView tv_pressure9 = findViewById(R.id.tv_pressure9);
//        TextView tv_pressure10 = findViewById(R.id.tv_pressure10);
//        TextView tv_pressure11 = findViewById(R.id.tv_pressure11);
//        TextView _12_pressureLabels[] = { tv_pressure0, tv_pressure1, tv_pressure2, tv_pressure3, tv_pressure4,
//                tv_pressure5, tv_pressure6, tv_pressure7, tv_pressure8,tv_pressure9,tv_pressure10,tv_pressure11 };

//        ImageView iv_ico0 = findViewById(R.id.iv_ico0);
//        ImageView iv_ico1 = findViewById(R.id.iv_ico1);
//        ImageView iv_ico2 = findViewById(R.id.iv_ico2);
//        ImageView iv_ico3 = findViewById(R.id.iv_ico3);
//        ImageView iv_ico4 = findViewById(R.id.iv_ico4);
//        ImageView iv_ico5 = findViewById(R.id.iv_ico5);
//        ImageView iv_ico6 = findViewById(R.id.iv_ico6);
//        ImageView iv_ico7 = findViewById(R.id.iv_ico7);
//        ImageView iv_ico8 = findViewById(R.id.iv_ico8);
//        ImageView iv_ico9 = findViewById(R.id.iv_ico9);
//        ImageView iv_ico10 = findViewById(R.id.iv_ico10);
//        ImageView iv_ico11 = findViewById(R.id.iv_ico11);
//        ImageView _12_icosLabels[] = { iv_ico0, iv_ico1, iv_ico2, iv_ico3, iv_ico4, iv_ico5, iv_ico6, iv_ico7,
//                iv_ico8,iv_ico9,iv_ico10,iv_ico11 };


//        TextView tv_temp_morning = findViewById(R.id.tv_temp_morning);
//        TextView tv_temp_evening = findViewById(R.id.tv_temp_evening);
//        TextView tv_temp_afternoon = findViewById(R.id.tv_temp_afternoon);
//        TextView tv_temp_night = findViewById(R.id.tv_temp_night);
        //LinearLayout ll_InsideTheDayShortened = findViewById(R.id.ll_InsideTheDayShortened);


//        if (dayIndex == 0)
//       // {
//            tv_temperature.setText(new WeatherData().today_temperature + "°C");

//            ll_time.setVisibility(View.VISIBLE);
//            ll_temp.setVisibility(View.VISIBLE);
//            ll_icos.setVisibility(View.VISIBLE);
//            ll_pres.setVisibility(View.VISIBLE);
//            ll_InsideTheDayShortened.setVisibility(View.GONE);


//            for(int i=0; i<=11; i++) {
//                _12_timeLabels[i].setText(new WeatherData().arr_time[i]);
//                _12_tempLabels[i].setText(new WeatherData().arr_temp[i]);
//                _12_pressureLabels[i].setText(new WeatherData().arr_pres[i]);
//
//                switch (new WeatherData().arr_descript[i]) {
//                    case "Clouds":
//                        _12_icosLabels[i].setImageResource(R.drawable.clouds_icon);
//                        break;
//                    case "Clear":
//                        _12_icosLabels[i].setImageResource(R.drawable.sun);
//                        break;
//                    case "Rain":
//                        _12_icosLabels[i].setImageResource(R.drawable.rain_icon);
//                        break;
//                    case "Snow":
//                        _12_icosLabels[i].setImageResource(R.drawable.snow_icon);
//                        break;
//                    default:
//                        iv_theme.setImageResource(R.drawable.clouds_icon);
//                }
//            }
//        }
//        else
//        {
//            ll_time.setVisibility(View.GONE);
//            ll_temp.setVisibility(View.GONE);
//            ll_icos.setVisibility(View.GONE);
//            ll_pres.setVisibility(View.GONE);
//            ll_InsideTheDayShortened.setVisibility(View.VISIBLE);
//
//
//            tv_temperature.setText(new WeatherData().arr_temperature[dayIndex] + "°C");
//        }









//        tv_temp_morning.setText(new WeatherData().arr_morning[dayIndex]);
//        tv_temp_afternoon.setText(new WeatherData().arr_afternoon[dayIndex]);
//        tv_temp_evening.setText(new WeatherData().arr_eve[dayIndex]);
//        tv_temp_night.setText(new WeatherData().arr_night[dayIndex]);


        //setTheme(dayIndex, new WeatherData().arr_theme);

    }


    /** Updates UI on RESTART **/
    public void reloadUI() {

        /**
         * Runs ONLY on app START or RESTART. (getForecast() calls it)
         *
         * 1 - updates all text labels with data for the CURRENT DAY.
         * 2 - updates background image of Zing with weather condition for the CURRENT DAY.
         * 3 - updates bottom button panel setting the CURRENT day letter first in a row. (Also highlights it)
         */

        updateData(0);

        updateBackground(0);

        updateButtonPanel();
    }


    /**  Updates UI ANYTIME **/
    public void loadUI(int dayIndex) {

        /**
         * Runs EVERY TIME you change the day. (called after button clicks)
         *
         * 1 - updates all text labels with data for the CHOSEN DAY.
         * 2 - updates background image of Zing with weather condition for the CHOSEN DAY.
         */

        updateData(dayIndex);

        updateBackground(dayIndex);

    }





    public void getForecast ()
    {
        /**
         * IMPORTANT NOTE:
         * This function ONLY fills the WeatherData arrays.
         * And that's all.
         * Nothing else.
         * ...
         * And reloads User Interface;)
         * like it should do.
         */


        //TODO Find user's location: lat, lon

        //API Call which is an important part of this block!!!
        String URL = "https://api.openweathermap.org/data/2.5/onecall?lat=50.29761&lon=18.67658&exclude=minutely&appid=ace729200f31ff6473436ef39ad854ea&units=metric&lang=en";


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    new TimeAndDate().setTimezone_offset(response.getInt("timezone_offset")/3600); // timezone offset in hours
                    new TimeAndDate().setCurrentDateAndTime(response.getJSONObject("current").getLong("dt")*1000); // time of last API call


                    for(int dayIndex = 0; dayIndex <= 6; dayIndex++)
                    {

                        Date date = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("dt"))*1000);
                        new WeatherData().arr_date[dayIndex] = String.valueOf(new TimeAndDate().getDateFormat().format(date));


                        Date sunriseTime = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("sunrise"))*1000);
                        new WeatherData().arr_sunrise[dayIndex] = String.valueOf(new TimeAndDate().getTimeFormat().format(sunriseTime));


                        Date sunsetTime = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("sunset"))*1000);
                        new WeatherData().arr_sunset[dayIndex] = String.valueOf(new TimeAndDate().getTimeFormat().format(sunsetTime));


                        new WeatherData().arr_pop[dayIndex]= String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("pop") * 100 +"%");
                        new WeatherData().arr_temperature[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("day"));
                        new WeatherData().arr_min_max[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("min")+"°... "+response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("max")+"°");
                        new WeatherData().arr_details[dayIndex] = String.valueOf("Feels like " + String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("feels_like").getInt("day")) + "°, " + response.getJSONArray("daily").getJSONObject(dayIndex).getJSONArray("weather").getJSONObject(0).getString("description"));
                        new WeatherData().arr_humidity[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("humidity")+"%");
                        new WeatherData().arr_clouds[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("clouds")+"%");
                        new WeatherData().arr_uvi[dayIndex]= String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("uvi")+"%");
                        new WeatherData().arr_wind[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("wind_speed")+" kmh");
                        new WeatherData().arr_pressure[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("pressure")+" mb");
                        new WeatherData().arr_theme[dayIndex] = response.getJSONArray("daily").getJSONObject(dayIndex).getJSONArray("weather").getJSONObject(0).getString("main");

                        new WeatherData().arr_morning[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("morn") + "°");
                        new WeatherData().arr_afternoon[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("day") + "°");
                        new WeatherData().arr_eve[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("eve") + "°");
                        new WeatherData().arr_night[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("night") + "°");

                    }
                    new WeatherData().today_temperature = String.valueOf(response.getJSONObject("current").getInt("temp"));


                    /* INSIDE THE DAY (long) */
                    for(int time = 0; time <= 11; time++)
                    {
                        new WeatherData().arr_time[time] = String.valueOf(new TimeAndDate().getTimeFormat().format((response.getJSONArray("hourly").getJSONObject(time).getLong("dt"))*1000));
                        new WeatherData().arr_temp[time] = response.getJSONArray("hourly").getJSONObject(time).getInt("temp") +"°C";
                        new WeatherData().arr_descript[time] = response.getJSONArray("hourly").getJSONObject(time).getJSONArray("weather").getJSONObject(0).getString("main");
                        new WeatherData().arr_pres[time] = response.getJSONArray("hourly").getJSONObject(time).getInt("pressure") +" mb";
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                reloadUI();

                // Remove internet failure msg box
                ConstraintLayout cl_internetError = findViewById(R.id.cl_internetError);
                cl_internetError.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Internet failure msg box
                ConstraintLayout cl_internetError = findViewById(R.id.cl_internetError);
                cl_internetError.setVisibility(View.VISIBLE);
            }
        });




        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);


    }



}