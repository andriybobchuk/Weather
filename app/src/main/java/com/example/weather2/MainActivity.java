package com.example.weather2;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView tv_day, tv_temperature, tv_region, tv_time, tv_details, tv_min_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_day = findViewById(R.id.tv_day);
        tv_temperature = findViewById(R.id.tv_temperature);
        tv_min_max = findViewById(R.id.tv_min_max);
        tv_region = findViewById(R.id.tv_region);
        //tv_time = findViewById(R.id.tv_time);
        tv_details = findViewById(R.id.tv_details);

        getForecast();

    }


    public void getForecast ()
    {
        String URL = "https://api.openweathermap.org/data/2.5/weather?q=Gliwice,pl&appid=ace729200f31ff6473436ef39ad854ea&units=metric&lang=ru";


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject obj_main = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject obj = array.getJSONObject(0);

                    String temperature = String.valueOf(obj_main.getInt("temp"));
                    //
                    String pressure = String.valueOf(obj_main.getInt("pressure"));
                    String humidity = String.valueOf(obj_main.getInt("humidity"));
                    String temp_min = String.valueOf(obj_main.getInt("temp_min"));
                    String temp_max = String.valueOf(obj_main.getInt("temp_max"));


                    //
                    String description = obj.getString("description");
                    String city = response.getString("name");

                    tv_temperature.setText(temperature+"°C");
                    tv_min_max.setText(temp_min+"°... "+temp_max+'°');

                    tv_region.setText(city);
                    tv_details.setText(description);

                    //
                    //tv_time.setText(pressure);
                    //

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MM.dd");
                    String formatted_date = dateFormat.format(calendar.getTime());
                    tv_day.setText(formatted_date);




                } catch (JSONException e) {
                    e.printStackTrace();
                    tv_region.setText("COCU catch");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                tv_region.setText("COCU");
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

    }






}