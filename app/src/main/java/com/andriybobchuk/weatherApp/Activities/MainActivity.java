package com.andriybobchuk.weatherApp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.andriybobchuk.weatherApp.Services.UserLocationService;
import com.andriybobchuk.weatherApp.Services.ForecastService;
import com.andriybobchuk.weatherApp.R;

import com.andriybobchuk.weatherApp.Services.UserPreferencesService;
import com.andriybobchuk.weatherApp.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


/* This class updates User interface
 *
 * NOTE:
 * This class ONLY updates User interface */

public class MainActivity extends AppCompatActivity implements UserLocationService.UserLocationCallback {


    /** Opens Options Activity on button click **/
    public void openOptions() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    //For enabling binding: Declare binding object from binding class
    ActivityMainBinding binding;


    @Override
    public void displayLat(Double lat) { binding.latlon.setText(String.valueOf(lat)); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //2 more lines for enabling binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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






        if (UserPreferencesService.getPrefCity(this) == "DEFAULT")
        {
            // Get user location
            // call forecast


            UserLocationService.setCallback(this);
           // getUserLocation(this);
            //binding.latlon.setText(String.valueOf(lat));
            //TextView ll = findViewById(R.id.latlon);
            //ll.setText(String.valueOf(UserLocationService.lat));


        } else {
            binding.latlon.setText("ELSE");
            // convert to lat lon
            // call forecast
        }


        /** =============== ↑ IMPORTANT ZONE ↑ =======================================================*/

//        // Passes variable "currentCity" to next class - UserPreferences
//        UserLocation.getUserLocation(this);
//        // ↓
//
//        // Uses "currentCity" as a default preference; returns lon\lat of either default or chosen city
//        UserPreferences.getUserPrefs(this);
//        // ↓


        // Gets the forecast for the given lon\lat
        ForecastService.getForecast(this);


        /** =============== ↑ IMPORTANT ZONE ↑ =======================================================*/








        /* For button OPTIONS */
        ImageButton btn_options = findViewById((R.id.btn_options));
        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptions();
            }
        });


        final RadioGroup group = (RadioGroup) findViewById(R.id.rg_dayOfTheWeek);
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
                ForecastService.getForecast(MainActivity.this);
                refreshLayout.setRefreshing(false);
            }
        });


    }


    /** Assigns right M T W T F S S sequence to RB-group + checks the 0th day **/
    public void updateButtonPanel() {

        RadioButton daysRadioButtons[] = {
                binding.rbMon,
                binding.rbTue,
                binding.rbWed,
                binding.rbThr,
                binding.rbFri,
                binding.rbSat,
                binding.rbSun
        };

        binding.rbMon.setChecked(true);

        for(int i=0; i<=6; i++)
        {
            daysRadioButtons[i].setText(String.valueOf(new ForecastService().arr_date[i].charAt(0)));
        }
    }


    /** Sets background Zing image and description label */
    public  void updateBackground(int dayIndex)
    {
        TextView tv_myWeatherDescription = findViewById(R.id.tv_hint);
        ImageView iv_theme = findViewById(R.id.iv_theme);
        switch(new ForecastService().arr_theme[dayIndex])
        {
            case "Clouds":
                tv_myWeatherDescription.setText("Just a bit of clouds");
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
                tv_myWeatherDescription.setText("It's kinda strange outside");
                iv_theme.setImageResource(R.drawable.clouds);
        }
    }


    /**
     * Fills all labels using all the data arrays
     * 1 - This function takes day index from pressed button (0-6) or from loadApp()(0)
     * 2 - DECLARE and INITIALIZE LOCAL text labels with data from WeatherData
     *
     * NOTE:
     * In order to avoid confusion, text labels will be initialized as they appear
     * on the app screen (one after another).
     */
    public void updateData(int dayIndex)
    {

        // Tuesday, Feb 13
        binding.tvDay.setText(new ForecastService().arr_date[dayIndex]);


        //TODO - deal with tv_region
        // Gliwice at 13:45
        // TextView tv_region = findViewById(R.id.tv_region);
        // UserLocation.getUserLocation(this);
        // UserPreferences.getPrefCity(this);

//        tv_region.setText(UserLocation.getUserLocation(this) + " at " + String.valueOf(new TimeAndDate().getTimeFormat().format(new TimeAndDate().getCurrentDateAndTime())));
        UserLocationService.getUserLocation(this);


        //TODO there is a problem with finding user location. It appears from the second attemp ↑

        //  -9°C
        if (dayIndex == 0) {
            binding.tvTemperature.setText(new ForecastService().today_temperature + "°C");
        } else {
            binding.tvTemperature.setText(new ForecastService().arr_temperature[dayIndex] + "°C");
        }

        //TODO: I create a separate object WeatherData for every property. it
        // will be better to instantiate one obj and use its properties
        //-3°...0°
        binding.tvMinMax.setText(new ForecastService().arr_min_max[dayIndex]);

        // Feels like -5°, scattered clouds
        binding.tvDetails.setText(new ForecastService().arr_details[dayIndex]);

        // POP
        binding.tvPop.setText(new ForecastService().arr_pop[dayIndex]);

        // Wind
        binding.tvWind.setText(new ForecastService().arr_wind[dayIndex]);

        // Humidity
        binding.tvHumidity.setText(new ForecastService().arr_humidity[dayIndex]);

        // UV
        binding.tvUvi.setText(new ForecastService().arr_uvi[dayIndex]);

        // Pressure
        binding.tvPressure.setText(new ForecastService().arr_pressure[dayIndex]);

        // Clouds
        binding.tvClouds.setText(new ForecastService().arr_clouds[dayIndex]);

        // Sunrise
        binding.tvSunrise.setText(new ForecastService().arr_sunrise[dayIndex]);

        // Sunset
        binding.tvSunset.setText(new ForecastService().arr_sunset[dayIndex]);


        /**  =================================================================================================== **/
        /**                INSIDE THE DAY PANELS                                                                 **/
        /**  =================================================================================================== **/

        TextView timeColVals[] = {
                binding.tvTime0, binding.tvTime1, binding.tvTime2, binding.tvTime3, binding.tvTime4, binding.tvTime5,
                binding.tvTime6, binding.tvTime7, binding.tvTime8, binding.tvTime9, binding.tvTime10, binding.tvTime11
        };

        TextView temperatureColVals[] = {
                binding.tvTemp0, binding.tvTemp1, binding.tvTemp2, binding.tvTemp3, binding.tvTemp4, binding.tvTemp5,
                binding.tvTemp6, binding.tvTemp7, binding.tvTemp8, binding.tvTemp9, binding.tvTemp10, binding.tvTemp11
        };

        TextView pressureColVals[] = {
                binding.tvPressure0, binding.tvPressure1, binding.tvPressure2, binding.tvPressure3, binding.tvPressure4,
                binding.tvPressure5, binding.tvPressure6, binding.tvPressure7, binding.tvPressure8, binding.tvPressure9,
                binding.tvPressure10, binding.tvPressure11
        };

        ImageView icosColVals[] = {
                binding.ivIco0, binding.ivIco1, binding.ivIco2, binding.ivIco3, binding.ivIco4, binding.ivIco5,
                binding.ivIco6, binding.ivIco7, binding.ivIco8, binding.ivIco9, binding.ivIco10, binding.ivIco11
        };

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
            binding.tvTemperature.setText(new ForecastService().today_temperature + "°C");

            // Activate all 4 columns of big panel
            binding.llTime.setVisibility(View.VISIBLE);
            binding.llTemp.setVisibility(View.VISIBLE);
            binding.llIcos.setVisibility(View.VISIBLE);
            binding.llPres.setVisibility(View.VISIBLE);

            // Deactivate small panel
            binding.llInsideTheDayShortened.setVisibility(View.GONE);

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
                timeColVals[i].setText(new ForecastService().arr_time[i]);
                temperatureColVals[i].setText(new ForecastService().arr_temp[i]);
                pressureColVals[i].setText(new ForecastService().arr_pres[i]);

                switch (new ForecastService().arr_descript[i]) {
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
            binding.tvTemperature.setText(new ForecastService().arr_temperature[dayIndex] + "°C");

            // Deactivate all 4 cols of big panel
            binding.llTime.setVisibility(View.GONE);
            binding.llTemp.setVisibility(View.GONE);
            binding.llIcos.setVisibility(View.GONE);
            binding.llPres.setVisibility(View.GONE);

            // Activate small panel
            binding.llInsideTheDayShortened.setVisibility(View.VISIBLE);

            // Assign values to SMALL panel:
            binding.tvTempMorning.setText(new ForecastService().arr_morning[dayIndex]);
            binding.tvTempAfternoon.setText(new ForecastService().arr_afternoon[dayIndex]);
            binding.tvTempEvening.setText(new ForecastService().arr_eve[dayIndex]);
            binding.tvTempNight.setText(new ForecastService().arr_night[dayIndex]);

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