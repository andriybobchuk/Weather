package com.andriybobchuk.weatherApp.Activities;

import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.andriybobchuk.weatherApp.Features.UserPreferences;
import com.andriybobchuk.weatherApp.Location.UserLocation;
import com.andriybobchuk.weatherApp.Network.GetForecast;
import com.andriybobchuk.weatherApp.R;

import com.andriybobchuk.weatherApp.Structures.TimeAndDate;
import com.andriybobchuk.weatherApp.Structures.WeatherData;


/** This class updates User interface
 *
 * NOTE:
 * This class ONLY updates User interface **/

public class MainActivity extends AppCompatActivity {


    /* Bottom M T W T F S S panel
    Used in OnCreate() and updateButtonPanel(), thus declared global */
    Button btn_day0,btn_day1,btn_day2,btn_day3,btn_day4,btn_day5,btn_day6;




    /** Opens Options Activity on button click **/
    public void openOptions() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    //private ResultProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ResultProfileBinding.inflate(getLayoutInflater());
        //View view = binding.getRoot();
        setContentView(R.layout.activity_main);

        /** =============== ↓ IMPORTANT ZONE ↓ =======================================================
         * So, the plan is the following:
         *      - Get User's Location - We will get LON & LAT Doubles.
         *      - [PROBABLY: call converter from LON\LAT to CITY]
         *      - [PROBABLY: send this CITY to UserPrefs to set default]
         *
         *      - Get User's Prefs for the city - we will get either ANY city or DEFAULT city name
         *      - We take this city name and convert to LON\LAT
         *      - Call GetForecast with those values
         * */





//        // Passes variable "currentCity" to next class - UserPreferences
//        UserLocation.getUserLocation(this);
//        // ↓
//
//        // Uses "currentCity" as a default preference; returns lon\lat of either default or chosen city
//        UserPreferences.getUserPrefs(this);
//        // ↓


        // Gets the forecast for the given lon\lat
        GetForecast.getForecast(this);


        /** =============== ↑ IMPORTANT ZONE ↑ =======================================================*/












        /* For button OPTIONS */
        ImageButton btn_options = findViewById((R.id.btn_options));
        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptions();
            }
        });


        final RadioGroup group= (RadioGroup) findViewById(R.id.rg_dayOfTheWeek);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                final Vibrator vibrator = (Vibrator)getSystemService(MainActivity.VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE));

                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rb_Mon:
                        loadUI(0);
                        break;
                    case R.id.rb_Tue:
                        loadUI(1);
                        break;
                    case R.id.rb_Wed:
                        loadUI(2);
                        break;
                    case R.id.rb_Thr:
                        loadUI(3);
                        break;
                    case R.id.rb_Fri:
                        loadUI(4);
                        break;
                    case R.id.rb_Sat:
                        loadUI(5);
                        break;
                    case R.id.rb_Sun:
                        loadUI(6);
                        break;
                }
            }
        });

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                /** 2nd GETFORECAST() call out of 2 **/
                GetForecast.getForecast(MainActivity.this);
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

        RadioButton rb_day0 = findViewById(R.id.rb_Mon);
        RadioButton rb_day1 = findViewById(R.id.rb_Tue);
        RadioButton rb_day2 = findViewById(R.id.rb_Wed);
        RadioButton rb_day3 = findViewById(R.id.rb_Thr);
        RadioButton rb_day4 = findViewById(R.id.rb_Fri);
        RadioButton rb_day5 = findViewById(R.id.rb_Sat);
        RadioButton rb_day6 = findViewById(R.id.rb_Sun);
        RadioButton daysRadioButtons[] = { rb_day0, rb_day1, rb_day2, rb_day3, rb_day4, rb_day5, rb_day6 };


        for(int i=0; i<=6; i++)
        {
            daysRadioButtons[i].setText(String.valueOf(new WeatherData().arr_date[i].charAt(0)));
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
                iv_theme.setImageResource(R.drawable.d);
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
        //UserLocation.getUserLocation(this);
        //UserPreferences.getPrefCity(this);

//        tv_region.setText(UserLocation.getUserLocation(this) + " at " + String.valueOf(new TimeAndDate().getTimeFormat().format(new TimeAndDate().getCurrentDateAndTime())));
        UserLocation.getUserLocation(this);


        //TODO the is a problem with finding user location. It appears from the second attemp ↑

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


        // Feels like -5°, scattered clouds
        TextView tv_details = findViewById(R.id.tv_details);
        tv_details.setText(new WeatherData().arr_details[dayIndex]);


        //POP
        TextView tv_pop = findViewById(R.id.tv_pop);
        tv_pop.setText(new WeatherData().arr_pop[dayIndex]);

        //Wind
        TextView tv_wind = findViewById(R.id.tv_wind);
        tv_wind.setText(new WeatherData().arr_wind[dayIndex]);

        //Humidity
        TextView tv_humidity = findViewById(R.id.tv_humidity);
        tv_humidity.setText(new WeatherData().arr_humidity[dayIndex]);

        //UV
        TextView tv_uvi = findViewById(R.id.tv_uvi);
        tv_uvi.setText(new WeatherData().arr_uvi[dayIndex]);

        //Pressure
        TextView tv_pressure = findViewById(R.id.tv_pressure);
        tv_pressure.setText(new WeatherData().arr_pressure[dayIndex]);

        //Clouds
        TextView tv_clouds = findViewById(R.id.tv_clouds);
        tv_clouds.setText(new WeatherData().arr_clouds[dayIndex]);


        //Sunrise
        TextView tv_sunrise = findViewById(R.id.tv_sunrise);
        tv_sunrise.setText(new WeatherData().arr_sunrise[dayIndex]);

        //Sunset
        TextView tv_sunset = findViewById(R.id.tv_sunset);
        tv_sunset.setText(new WeatherData().arr_sunset[dayIndex]);


        /**  =================================================================================================== **/
        /**                INSIDE THE DAY PANELS                                                                 **/
        /**  =================================================================================================== **/

        /** 1 - BIG inside the day panel (for current day) **/

        /* Those four are layouts on which four data columns are situated */
        LinearLayout ll_time = findViewById(R.id.ll_time);
        LinearLayout ll_temp = findViewById(R.id.ll_temp);
        LinearLayout ll_icos = findViewById(R.id.ll_icos);
        LinearLayout ll_pres = findViewById(R.id.ll_pres);

        /* Those are four 12-valued columns for big panel */
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
        TextView timeColVals[] = { tv_time0, tv_time1, tv_time2, tv_time3, tv_time4, tv_time5, tv_time6, tv_time7,
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
        TextView temperatureColVals[] = { tv_temp0, tv_temp1, tv_temp2, tv_temp3, tv_temp4, tv_temp5, tv_temp6, tv_temp7,
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
        TextView pressureColVals[] = { tv_pressure0, tv_pressure1, tv_pressure2, tv_pressure3, tv_pressure4,
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
        ImageView icosColVals[] = { iv_ico0, iv_ico1, iv_ico2, iv_ico3, iv_ico4, iv_ico5, iv_ico6, iv_ico7,
                iv_ico8,iv_ico9,iv_ico10,iv_ico11 };


        /** 2 - SMALL INSIDE THE DAY PANEL (ALL FOLLOWING DAYS) **/

        TextView tv_temp_morning = findViewById(R.id.tv_temp_morning);
        TextView tv_temp_evening = findViewById(R.id.tv_temp_evening);
        TextView tv_temp_afternoon = findViewById(R.id.tv_temp_afternoon);
        TextView tv_temp_night = findViewById(R.id.tv_temp_night);

        LinearLayout ll_InsideTheDayShortened = findViewById(R.id.ll_InsideTheDayShortened);

        //--- END OF DECLARATION ----------------------------------------------------------------------------

        /**
         * Now it gets serious.
         * if the day is TODAY (index 0), we have to:
         *      - use variable "today_temperature" as it's more precise.
         *      - set BIG "Inside the day panel" as it's more precise.
         *
         * if the day is any other (indices 1-6):
         *      - use "arr_temperature" as we have accessto it.
         *      - use SMALL "inside the day panel" as we have access only to it.
         */

        if (dayIndex == 0)
        {
            tv_temperature.setText(new WeatherData().today_temperature + "°C");

            // Activate all 4 columns of big panel
            ll_time.setVisibility(View.VISIBLE);
            ll_temp.setVisibility(View.VISIBLE);
            ll_icos.setVisibility(View.VISIBLE);
            ll_pres.setVisibility(View.VISIBLE);

            // Deactivate small panel
            ll_InsideTheDayShortened.setVisibility(View.GONE);


            /**
             *  Attention!
             *  here we assign each 12 values for each of 4 data columns:
             *          - Time column vals with time array;
             *          - Temperature column vals with temp array;
             *          - pressure - with pressure;
             *          BUT!
             *          - Column "icos" we assign depending on arr_descript, thus SWITCH.
             *          **/
            for(int i=0; i<=11; i++) {
                timeColVals[i].setText(new WeatherData().arr_time[i]);
                temperatureColVals[i].setText(new WeatherData().arr_temp[i]);
                pressureColVals[i].setText(new WeatherData().arr_pres[i]);

                switch (new WeatherData().arr_descript[i]) {
                    case "Clouds":
                        icosColVals[i].setImageResource(R.drawable.clouds_icon);
                        break;
                    case "Clear":
                        icosColVals[i].setImageResource(R.drawable.sun);
                        break;
                    case "Rain":
                        icosColVals[i].setImageResource(R.drawable.rain_icon);
                        break;
                    case "Snow":
                        icosColVals[i].setImageResource(R.drawable.snow_icon);
                        break;
                    default:
                        icosColVals[i].setImageResource(R.drawable.clouds_icon);
                }
            }
        }
        else //  IF any other day (indexes (indices) 0-6):
        {
            // Temperature we get from array
            tv_temperature.setText(new WeatherData().arr_temperature[dayIndex] + "°C");

            // Deactivate all 4 cols of big panel
            ll_time.setVisibility(View.GONE);
            ll_temp.setVisibility(View.GONE);
            ll_icos.setVisibility(View.GONE);
            ll_pres.setVisibility(View.GONE);

            // Activate small panel
            ll_InsideTheDayShortened.setVisibility(View.VISIBLE);

            // Assign values to SMALL panel:
            tv_temp_morning.setText(new WeatherData().arr_morning[dayIndex]);
            tv_temp_afternoon.setText(new WeatherData().arr_afternoon[dayIndex]);
            tv_temp_evening.setText(new WeatherData().arr_eve[dayIndex]);
            tv_temp_night.setText(new WeatherData().arr_night[dayIndex]);

        }
        /**  =================================================================================================== **/
        /**               END OF INSIDE THE DAY PANELS                                                           **/
        /**  =================================================================================================== **/

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


}