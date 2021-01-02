package com.example.weather2;

import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

    TextView tv_day, tv_temperature, tv_region, tv_details, tv_min_max, tv_hint, tv_sunrise, tv_sunset,
            tv_pressure, tv_humidity, tv_wind, tv_uvi, tv_clouds, tv_pop, tv_morning, tv_afternoon,
            tv_eve, tv_night;
    ImageButton btn_options;
    Button btn_day0,btn_day1,btn_day2,btn_day3,btn_day4,btn_day5,btn_day6;
    ImageView iv_theme;
    SwipeRefreshLayout swiperefresh;

    boolean firstCall = true; // to avoid problem with refreshing


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




    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "ChannelA")
                .setSmallIcon(R.drawable.clouds_icon)
                .setContentTitle("Title")
                .setContentText("textttttjkhbh.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

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
                notificationManager.notify(100, builder.build());
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

        swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //tv_day.setText("TU COCHYB");
                getForecast();
                swiperefresh.setRefreshing(false);
            }
        });

    }


//TODO: make a loading of UI like in spotify - at first the weather data loads, then UI. Thus on start we will see nothing
    public  void setTheme(int dayIndex, String[] arr_theme)
    {
        tv_hint = findViewById(R.id.tv_hint);
        iv_theme = findViewById(R.id.iv_theme);
        switch(arr_theme[dayIndex])
        {
            case "Clouds":
                tv_hint.setText("Not the nicest weather");
                iv_theme.setImageResource(R.drawable.clouds);
                break;
            case "Clear":
                tv_hint.setText("The weather's just perfect!");
                iv_theme.setImageResource(R.drawable.theme_sunny);
                break;
            case "Rain":
                tv_hint.setText("Take ur umbrella with you!");
                iv_theme.setImageResource(R.drawable.theme_rain);
                break;
            case "Snow":
                tv_hint.setText("All the weather outside is frightful..");
                iv_theme.setImageResource(R.drawable.theme_snow);
                break;
            default:
                tv_hint.setText("It's strange outside");
                iv_theme.setImageResource(R.drawable.clouds);
        }
    }


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

        tv_morning = findViewById(R.id.tv_morning);
        tv_afternoon = findViewById(R.id.tv_afternoon);
        tv_eve = findViewById(R.id.tv_eve);
        tv_night = findViewById(R.id.tv_night);

        if (dayIndex == 0)
        {
            tv_temperature.setText(today_temperature + "°C");
        }
        else
        {
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

        tv_morning.setText(arr_morning[dayIndex]);
        tv_afternoon.setText(arr_afternoon[dayIndex]);
        tv_eve.setText(arr_eve[dayIndex]);
        tv_night.setText(arr_night[dayIndex]);

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

    String [ ] arr_morning = new String [7];
    String [ ] arr_afternoon = new String [7];
    String [ ] arr_eve = new String [7];
    String [ ] arr_night = new String [7];

    public void getForecast ()
    {

        //API Call
        String URL = "https://api.openweathermap.org/data/2.5/onecall?lat=50.29761&lon=18.67658&exclude=minutely,hourly&appid=ace729200f31ff6473436ef39ad854ea&units=metric&lang=en";


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    daysOfWeekPanel(response);


                    /** === Here save ALL THE DATA to 7-days arrays of data strings for filling labels === **/

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MM.dd");
                    dateFormat.setTimeZone(TimeZone.getTimeZone(String.valueOf("GMT+" + response.getInt("timezone_offset")/3600)));

                    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa");
                    timeFormat.setTimeZone(TimeZone.getTimeZone(String.valueOf("GMT+" + response.getInt("timezone_offset")/3600)));
                    Date timeOfRefresh = new Date((response.getJSONObject("current").getLong("dt"))*1000);
                    tv_region = findViewById(R.id.tv_region);
                    tv_region.setText("[region], UPD  " + String.valueOf(timeFormat.format(timeOfRefresh)));

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

                        arr_morning[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("morn") + "°C in the morning");
                        arr_afternoon[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("day") + "°C in the afternoon");
                        arr_eve[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("eve") + "°C in the evening");
                        arr_night[dayIndex] = String.valueOf(response.getJSONArray("daily").getJSONObject(dayIndex).getJSONObject("temp").getInt("night") + "°C at night");
                    }
                    today_temperature = String.valueOf(response.getJSONObject("current").getInt("temp"));


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