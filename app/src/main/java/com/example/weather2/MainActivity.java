package com.example.weather2;

import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    /* Main part (top) */
    SwipeRefreshLayout refreshLayout;
    ImageButton btn_options; // round circle in the top right corner
    ImageView iv_theme; // background image with wizard
    TextView tv_day, tv_region, tv_temperature, tv_min_max, tv_details, tv_myWeatherDescription;

    /* Horizontal ScrollView */
    TextView tv_pop, tv_pressure, tv_humidity, tv_wind, tv_uvi, tv_clouds;

    /* Bar under the horizontal ScrollView */
    TextView tv_sunrise, tv_sunset;




    /* Bottom navigation panel (Day of the week) */
    Button btn_day0,btn_day1,btn_day2,btn_day3,btn_day4,btn_day5,btn_day6;



    /**
     * Function for filling the bottom navigation panel with the first letters of week days
     * @param response
     * @throws JSONException
     */
    public void daysOfWeekPanel(JSONObject response) throws JSONException {

        btn_day0 = findViewById(R.id.btn_day0);
        btn_day1 = findViewById(R.id.btn_day1);
        btn_day2 = findViewById(R.id.btn_day2);
        btn_day3 = findViewById(R.id.btn_day3);
        btn_day4 = findViewById(R.id.btn_day4);
        btn_day5 = findViewById(R.id.btn_day5);
        btn_day6 = findViewById(R.id.btn_day6);
        Button daysButtons[] = { btn_day0, btn_day1, btn_day2, btn_day3, btn_day4, btn_day5, btn_day6 };

        SimpleDateFormat dateFormat = new SimpleDateFormat("E");

        for(int i=0; i<=6; i++)
        {
            Date date = new Date((response.getJSONArray("daily").getJSONObject(i).getLong("dt"))*1000);
            String currentDayName = String.valueOf(dateFormat.format(date));
            daysButtons[i].setText(String.valueOf(currentDayName.charAt(0)));
        }
    }

    public void openOptions() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getForecast();

        /* For button OPTIONS */
        btn_options = findViewById((R.id.btn_options));
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
                setForecastData(0);
                btn_day0.setSelected(true);btn_day1.setSelected(false);btn_day2.setSelected(false);btn_day3.setSelected(false);btn_day4.setSelected(false);btn_day5.setSelected(false);btn_day6.setSelected(false);

                vibrator.vibrate(VibrationEffect.createOneShot(42, VibrationEffect.DEFAULT_AMPLITUDE));



            }
        });


        btn_day1 = findViewById((R.id.btn_day1));
        btn_day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setForecastData(1);
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
                setForecastData(2);
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
                setForecastData(3);
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
                setForecastData(4);
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
                setForecastData(5);
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
                setForecastData(6);
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

        refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getForecast();
                refreshLayout.setRefreshing(false);
            }
        });

    }



    /** Function sets the background with the wizard and my description of the weather depending on theme */
    public  void setTheme(int dayIndex, String[] arr_theme)
    {
        tv_myWeatherDescription = findViewById(R.id.tv_hint);
        iv_theme = findViewById(R.id.iv_theme);
        switch(arr_theme[dayIndex])
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
    public void setForecastData(int dayIndex)
    {

        /* initialization of labels */
        tv_day = findViewById(R.id.tv_day);
        tv_temperature = findViewById(R.id.tv_temperature);
        tv_min_max = findViewById(R.id.tv_min_max);

        tv_details = findViewById(R.id.tv_details);
        tv_pressure = findViewById(R.id.tv_pressure);
        tv_humidity = findViewById(R.id.tv_humidity);
        tv_wind = findViewById(R.id.tv_wind);
        tv_uvi = findViewById(R.id.tv_uvi);
        tv_clouds = findViewById(R.id.tv_clouds);
        tv_pop = findViewById(R.id.tv_pop);

        tv_sunrise = findViewById(R.id.tv_sunrise);
        tv_sunset = findViewById(R.id.tv_sunset);

        /* Hourly forecast panel */
        LinearLayout ll_time = findViewById(R.id.ll_time);
        LinearLayout ll_temp = findViewById(R.id.ll_temp);
        LinearLayout ll_icos = findViewById(R.id.ll_icos);
        LinearLayout ll_pres = findViewById(R.id.ll_pres);

        TextView tv_time0 = findViewById(R.id.tv_time0);
        TextView tv_time1 = findViewById(R.id.tv_time1);
        TextView tv_time2 = findViewById(R.id.tv_time2);
        TextView tv_time3 = findViewById(R.id.tv_time3);
        TextView tv_time4 = findViewById(R.id.tv_time4);
        TextView tv_time5 = findViewById(R.id.tv_time5);
        TextView tv_time6 = findViewById(R.id.tv_time6);
        TextView tv_time7 = findViewById(R.id.tv_time7);
        TextView tv_time8 = findViewById(R.id.tv_time8);
        TextView tv_time9 = findViewById(R.id.tv_time9);
        TextView tv_time10 = findViewById(R.id.tv_time10);
        TextView tv_time11 = findViewById(R.id.tv_time11);
        TextView _12_timeLabels[] = { tv_time0, tv_time1, tv_time2, tv_time3, tv_time4, tv_time5, tv_time6, tv_time7,
                tv_time8,tv_time9,tv_time10,tv_time11 };

        TextView tv_temp0 = findViewById(R.id.tv_temp0);
        TextView tv_temp1 = findViewById(R.id.tv_temp1);
        TextView tv_temp2 = findViewById(R.id.tv_temp2);
        TextView tv_temp3 = findViewById(R.id.tv_temp3);
        TextView tv_temp4 = findViewById(R.id.tv_temp4);
        TextView tv_temp5 = findViewById(R.id.tv_temp5);
        TextView tv_temp6 = findViewById(R.id.tv_temp6);
        TextView tv_temp7 = findViewById(R.id.tv_temp7);
        TextView tv_temp8 = findViewById(R.id.tv_temp8);
        TextView tv_temp9 = findViewById(R.id.tv_temp9);
        TextView tv_temp10 = findViewById(R.id.tv_temp10);
        TextView tv_temp11 = findViewById(R.id.tv_temp11);
        TextView _12_tempLabels[] = { tv_temp0, tv_temp1, tv_temp2, tv_temp3, tv_temp4, tv_temp5, tv_temp6, tv_temp7,
                tv_temp8,tv_temp9,tv_temp10,tv_temp11 };

        TextView tv_pressure0 = findViewById(R.id.tv_pressure0);
        TextView tv_pressure1 = findViewById(R.id.tv_pressure1);
        TextView tv_pressure2 = findViewById(R.id.tv_pressure2);
        TextView tv_pressure3 = findViewById(R.id.tv_pressure3);
        TextView tv_pressure4 = findViewById(R.id.tv_pressure4);
        TextView tv_pressure5 = findViewById(R.id.tv_pressure5);
        TextView tv_pressure6 = findViewById(R.id.tv_pressure6);
        TextView tv_pressure7 = findViewById(R.id.tv_pressure7);
        TextView tv_pressure8 = findViewById(R.id.tv_pressure8);
        TextView tv_pressure9 = findViewById(R.id.tv_pressure9);
        TextView tv_pressure10 = findViewById(R.id.tv_pressure10);
        TextView tv_pressure11 = findViewById(R.id.tv_pressure11);
        TextView _12_pressureLabels[] = { tv_pressure0, tv_pressure1, tv_pressure2, tv_pressure3, tv_pressure4,
                tv_pressure5, tv_pressure6, tv_pressure7, tv_pressure8,tv_pressure9,tv_pressure10,tv_pressure11 };

        ImageView iv_ico0 = findViewById(R.id.iv_ico0);
        ImageView iv_ico1 = findViewById(R.id.iv_ico1);
        ImageView iv_ico2 = findViewById(R.id.iv_ico2);
        ImageView iv_ico3 = findViewById(R.id.iv_ico3);
        ImageView iv_ico4 = findViewById(R.id.iv_ico4);
        ImageView iv_ico5 = findViewById(R.id.iv_ico5);
        ImageView iv_ico6 = findViewById(R.id.iv_ico6);
        ImageView iv_ico7 = findViewById(R.id.iv_ico7);
        ImageView iv_ico8 = findViewById(R.id.iv_ico8);
        ImageView iv_ico9 = findViewById(R.id.iv_ico9);
        ImageView iv_ico10 = findViewById(R.id.iv_ico10);
        ImageView iv_ico11 = findViewById(R.id.iv_ico11);
        ImageView _12_icosLabels[] = { iv_ico0, iv_ico1, iv_ico2, iv_ico3, iv_ico4, iv_ico5, iv_ico6, iv_ico7,
                iv_ico8,iv_ico9,iv_ico10,iv_ico11 };


        TextView tv_temp_morning = findViewById(R.id.tv_temp_morning);
        TextView tv_temp_evening = findViewById(R.id.tv_temp_evening);
        TextView tv_temp_afternoon = findViewById(R.id.tv_temp_afternoon);
        TextView tv_temp_night = findViewById(R.id.tv_temp_night);
        LinearLayout ll_InsideTheDayShortened = findViewById(R.id.ll_InsideTheDayShortened);


        if (dayIndex == 0)
        {
            tv_temperature.setText(today_temperature + "°C");

            ll_time.setVisibility(View.VISIBLE);
            ll_temp.setVisibility(View.VISIBLE);
            ll_icos.setVisibility(View.VISIBLE);
            ll_pres.setVisibility(View.VISIBLE);
            ll_InsideTheDayShortened.setVisibility(View.GONE);


            for(int i=0; i<=11; i++) {
                _12_timeLabels[i].setText(arr_time[i]);
                _12_tempLabels[i].setText(arr_temp[i]);
                _12_pressureLabels[i].setText(arr_pres[i]);

                switch (arr_descript[i]) {
                    case "Clouds":
                        _12_icosLabels[i].setImageResource(R.drawable.clouds_icon);
                        break;
                    case "Clear":
                        _12_icosLabels[i].setImageResource(R.drawable.sun);
                        break;
                    case "Rain":
                        _12_icosLabels[i].setImageResource(R.drawable.rain_icon);
                        break;
                    case "Snow":
                        _12_icosLabels[i].setImageResource(R.drawable.snow_icon);
                        break;
                    default:
                        iv_theme.setImageResource(R.drawable.clouds_icon);
                }
            }
        }
        else
        {
            ll_time.setVisibility(View.GONE);
            ll_temp.setVisibility(View.GONE);
            ll_icos.setVisibility(View.GONE);
            ll_pres.setVisibility(View.GONE);
            ll_InsideTheDayShortened.setVisibility(View.VISIBLE);


            tv_temperature.setText(arr_temperature[dayIndex] + "°C");
        }
        tv_day.setText(arr_day[dayIndex]);
        tv_min_max.setText(arr_min_max[dayIndex]);
        tv_details.setText(arr_details[dayIndex]);
        tv_humidity.setText(arr_humidity[dayIndex]);
        tv_clouds.setText(arr_clouds[dayIndex]);
        tv_uvi.setText(arr_uvi[dayIndex]);
        tv_wind.setText(arr_wind[dayIndex]);
        tv_pressure.setText(arr_pressure[dayIndex]);
        tv_sunrise.setText(arr_sunrise[dayIndex]);
        tv_sunset.setText(arr_sunset[dayIndex]);
        tv_pop.setText(arr_pop[dayIndex]);

        tv_temp_morning.setText(arr_morning[dayIndex]);
        tv_temp_afternoon.setText(arr_afternoon[dayIndex]);
        tv_temp_evening.setText(arr_eve[dayIndex]);
        tv_temp_night.setText(arr_night[dayIndex]);


        setTheme(dayIndex, arr_theme);

    }


    /** ARRAYS WITH WEATHER DATA STRINGS **/
    String [ ] arr_day = new String [1000];
    String [ ] arr_min_max = new String [7];
    String [ ] arr_details = new String [7];
    String [ ] arr_humidity = new String [7];
    String [ ] arr_clouds = new String [7];
    String [ ] arr_uvi = new String [7];
    String [ ] arr_wind = new String [7];
    String [ ] arr_temperature = new String [7];
    String today_temperature;
    String timeOfRefresh;
    String [ ] arr_pressure = new String [7];
    String [ ] arr_theme = new String [7];
    String [ ] arr_sunrise = new String [7];
    String [ ] arr_sunset = new String [7];
    String [ ] arr_pop = new String [7];
    ///
    String[] arr_time = new String [12];
    String[] arr_temp = new String [12];
    String[] arr_descript = new String [12];
    String[] arr_pres = new String [12];
    ///
    String [ ] arr_morning = new String [7];
    String [ ] arr_afternoon = new String [7];
    String [ ] arr_eve = new String [7];
    String [ ] arr_night = new String [7];



    /** 2D array for everything in the "Inside the day" block for current day */
    String[][] hourlyData = new String[3][12]; //3 for 3 fields to update; 12 for 12 hours

    public void getForecast ()
    {

        //API Call
        String URL = "https://api.openweathermap.org/data/2.5/onecall?lat=50.29761&lon=18.67658&exclude=minutely&appid=ace729200f31ff6473436ef39ad854ea&units=metric&lang=en";


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    daysOfWeekPanel(response);


                    /** === Here save ALL THE DATA to 7-days arrays of data strings for filling labels === **/

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d");
                    dateFormat.setTimeZone(TimeZone.getTimeZone(String.valueOf("GMT+" + response.getInt("timezone_offset")/3600)));

                    SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");//One time format for all app
                    timeFormat.setTimeZone(TimeZone.getTimeZone(String.valueOf("GMT+" + response.getInt("timezone_offset")/3600))); //One time zone for all app
                    Date timeOfRefresh = new Date((response.getJSONObject("current").getLong("dt"))*1000);

                    tv_region = findViewById(R.id.tv_region);
                    tv_region.setText("[region] at " + String.valueOf(timeFormat.format(timeOfRefresh)));

                    for(int dayIndex = 0; dayIndex <= 6; dayIndex++)
                    {
                        Date date = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("dt"))*1000);
                        arr_day[dayIndex] = String.valueOf(dateFormat.format(date));
                        Date sunriseTime = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("sunrise"))*1000);
                        arr_sunrise[dayIndex] = String.valueOf(timeFormat.format(sunriseTime));
                        Date sunsetTime = new Date((response.getJSONArray("daily").getJSONObject(dayIndex).getLong("sunset"))*1000);
                        arr_sunset[dayIndex] = String.valueOf(timeFormat.format(sunsetTime));

                        arr_pop[dayIndex]= String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getInt("pop") * 100 +"%");
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


                    for(int time = 0; time <= 11; time++)
                    {
                        arr_time[time] = String.valueOf(timeFormat.format((response.getJSONArray("hourly").getJSONObject(time).getLong("dt"))*1000));
                        arr_temp[time] = response.getJSONArray("hourly").getJSONObject(time).getInt("temp") +"°C";
                        arr_descript[time] = response.getJSONArray("hourly").getJSONObject(time).getJSONArray("weather").getJSONObject(0).getString("main");
                        arr_pres[time] = response.getJSONArray("hourly").getJSONObject(time).getInt("pressure") +" mb";
                    }

                    //Ми можемо винести цей блок в OnCreate щоб усунути баг з викидом на нульовий день
                    setForecastData(0);
                    btn_day0.setSelected(true);
                    btn_day1.setSelected(false);
                    btn_day2.setSelected(false);
                    btn_day3.setSelected(false);
                    btn_day4.setSelected(false);
                    btn_day5.setSelected(false);
                    btn_day6.setSelected(false);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

    }






}