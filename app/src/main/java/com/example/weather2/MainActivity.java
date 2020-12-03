package com.example.weather2;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    /* Variable init */
    TextView tv_day, tv_temperature, tv_region, tv_time, tv_details, tv_min_max, tv_pressure, tv_humidity, tv_windSpeed;
    TextView tv_sunrise, tv_sunset;

    ImageButton btn_options, btn_back_from_options;


    public void open()
    {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
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
        tv_windSpeed = findViewById(R.id.tv_windSpeed);
        tv_sunrise = findViewById(R.id.tv_sunrise);
        tv_sunset = findViewById(R.id.tv_sunset);


        /* ==========================================================================================================*/
        /* |                                  HANDLE BUTTON CLICKS                                                   */
        /* ==========================================================================================================*/

        /* For button OPTIONS */
        btn_options = findViewById((R.id.btn_options));
        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });



    }




    public void getForecast ()
    {
        //
        String URL = "https://api.openweathermap.org/data/2.5/weather?q=Gliwice,pl&appid=ace729200f31ff6473436ef39ad854ea&units=metric&lang=en";


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Getting JSON objects
                    JSONObject obj_main = response.getJSONObject("main");//for everything
                    JSONObject obj_wind = response.getJSONObject("wind");//for "speed"
                    JSONObject obj_sys = response.getJSONObject("sys");//for "sunrise\sunset"
                    JSONArray array = response.getJSONArray("weather");//for "description"
                    JSONObject obj = array.getJSONObject(0);


                    //Assigning STRING variables (From TOP to BOTTOM)
                    String city = response.getString("name");//From response

                    String temperature = String.valueOf(obj_main.getInt("temp"));
                    String temp_min = String.valueOf(obj_main.getInt("temp_min"));
                    String temp_max = String.valueOf(obj_main.getInt("temp_max"));

                    String feels_like = String.valueOf(obj_main.getInt("feels_like"));
                    String description = obj.getString("description");

                    String pressure = String.valueOf(obj_main.getInt("pressure"));
                    String humidity = String.valueOf(obj_main.getInt("humidity"));
                    String windSpeed = String.valueOf(obj_wind.getInt("speed"));
                    int sunrise = obj_sys.getInt("sunrise");
                    int sunset = obj_sys.getInt("sunset");








                    //Assigning STRING variables to LABELS (From TOP to BOTTOM)
                    //For CALENDAR (CURRENT DATE)
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MM.dd");
                    String formatted_date = dateFormat.format(calendar.getTime());
                    tv_day.setText(formatted_date);
                    tv_region.setText(city);

                    tv_temperature.setText(temperature+"°C");
                    tv_min_max.setText(temp_min+"°... "+temp_max+'°');

                    tv_details.setText("Feels like " + feels_like + ", " +description);

                    tv_pressure.setText("Pressure: " + pressure + " mb");
                    tv_humidity.setText("Humidity: " + humidity + "%");
                    tv_windSpeed.setText("Wind Speed: " + windSpeed + " km/h");

                    //Calendar calendar = Calendar.getInstance();
                    TimeZone tz = TimeZone.getDefault();
                    calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    java.util.Date SRTime=new java.util.Date(sunrise*1000);
                    java.util.Date SSTime=new java.util.Date(sunset*1000);

                    //Toast.makeText(TimeStampChkActivity.this, sdf.format(currenTimeZone), Toast.LENGTH_SHORT).show();
                    tv_sunrise.setText("Sunrise: " + sdf.format(SRTime));
                    tv_sunset.setText("Sunset: " + sdf.format(SSTime));







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