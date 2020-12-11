package com.example.weather2;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    /* Variable init */
    TextView tv_day, tv_temperature, tv_region, tv_details, tv_min_max;
    TextView tv_sunrise, tv_sunset, tv_pressure, tv_humidity, tv_wind, tv_uvi, tv_clouds;;

    ImageButton btn_options;

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
    public void onClick(View v)
    {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getForecast();

        /* initialization of labels */
        tv_day = findViewById(R.id.tv_day);
        tv_temperature = findViewById(R.id.tv_temperature);
        tv_min_max = findViewById(R.id.tv_min_max);
        tv_region = findViewById(R.id.tv_region);
        tv_details = findViewById(R.id.tv_details);
        tv_pressure = findViewById(R.id.tv_pressure);
        tv_humidity = findViewById(R.id.tv_humidity);
        tv_wind = findViewById(R.id.tv_wind);
        tv_uvi = findViewById(R.id.tv_uvi);
        tv_clouds = findViewById(R.id.tv_clouds);
//        tv_sunrise = findViewById(R.id.tv_sunrise);
//        tv_sunset = findViewById(R.id.tv_sunset);



        /* For button OPTIONS */
        btn_options = findViewById((R.id.btn_options));
        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptions();
            }
        });





//        btn_day0 = findViewById((R.id.btn_day0));
//        btn_day0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // The following construction is for selecting & deselecting with click, but...
//                // We need smth else
////                if (btn_day0.isSelected())
////                {
////                    btn_day0.setSelected(false);
////                } else
////                {
////                    btn_day0.setSelected(true);
////                }
//
//                //Smth like that
//                btn_day0.setSelected(true);
//
//                btn_day1.setSelected(false);
//                btn_day2.setSelected(false);
//                btn_day3.setSelected(false);
//                btn_day4.setSelected(false);
//                btn_day5.setSelected(false);
//                btn_day6.setSelected(false);
//
//                getForecast ();
//
//            }
//        });
//        btn_day1 = findViewById((R.id.btn_day1));
//        btn_day1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_day1.setSelected(true);
//
//                btn_day0.setSelected(false);
//                btn_day2.setSelected(false);
//                btn_day3.setSelected(false);
//                btn_day4.setSelected(false);
//                btn_day5.setSelected(false);
//                btn_day6.setSelected(false);
//            }
//        });
//        btn_day2 = findViewById((R.id.btn_day2));
//        btn_day2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_day2.setSelected(true);
//
//                btn_day1.setSelected(false);
//                btn_day0.setSelected(false);
//                btn_day3.setSelected(false);
//                btn_day4.setSelected(false);
//                btn_day5.setSelected(false);
//                btn_day6.setSelected(false);
//            }
//        });
//        btn_day3 = findViewById((R.id.btn_day3));
//        btn_day3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_day3.setSelected(true);
//
//                btn_day1.setSelected(false);
//                btn_day2.setSelected(false);
//                btn_day0.setSelected(false);
//                btn_day4.setSelected(false);
//                btn_day5.setSelected(false);
//                btn_day6.setSelected(false);
//            }
//        });
//        btn_day4 = findViewById((R.id.btn_day4));
//        btn_day4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_day4.setSelected(true);
//
//                btn_day1.setSelected(false);
//                btn_day2.setSelected(false);
//                btn_day3.setSelected(false);
//                btn_day0.setSelected(false);
//                btn_day5.setSelected(false);
//                btn_day6.setSelected(false);
//            }
//        });
//        btn_day5 = findViewById((R.id.btn_day5));
//        btn_day5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_day5.setSelected(true);
//
//                btn_day1.setSelected(false);
//                btn_day2.setSelected(false);
//                btn_day3.setSelected(false);
//                btn_day4.setSelected(false);
//                btn_day0.setSelected(false);
//                btn_day6.setSelected(false);
//            }
//        });
//        btn_day6 = findViewById((R.id.btn_day6));
//        btn_day6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_day6.setSelected(true);
//
//                btn_day1.setSelected(false);
//                btn_day2.setSelected(false);
//                btn_day3.setSelected(false);
//                btn_day4.setSelected(false);
//                btn_day5.setSelected(false);
//                btn_day0.setSelected(false);
//            }
//        });
//

    }









    public void getForecast ()
    {

        //API Call
        String URL = "https://api.openweathermap.org/data/2.5/onecall?lat=50.29761&lon=18.67658&exclude=minutely,hourly&appid=ace729200f31ff6473436ef39ad854ea&units=metric&lang=en";


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    daysOfWeekPanel(response);

                    //
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MM.dd, HH");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
                    //
                    JSONObject current = response.getJSONObject("current");
                    //JSONArray dailyArray = response.getJSONArray("daily");
                    Date date = new Date(current.getLong("dt")*1000);
                    tv_day.setText(dateFormat.format(date));

                    tv_temperature.setText(current.getInt("temp")+"°C");
                    tv_min_max.setText(String.valueOf(response.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").getInt("min"))+"°/"+String.valueOf(response.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").getInt("max"))+"°");

                    tv_details.setText("Feels like " + String.valueOf(current.get("feels_like"))+"°, " + String.valueOf(current.getJSONArray("weather").getJSONObject(0).getInt("description")));




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