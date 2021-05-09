package com.andriybobchuk.weatherApp.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;

import com.andriybobchuk.weatherApp.Activities.MainActivity;
import com.andriybobchuk.weatherApp.Activities.OptionsActivity;
import com.andriybobchuk.weatherApp.R;


public class UserPreferencesService {


    public static final String PREF_FILE = "new24SHARED_PREF";

    public static String getPrefCity(MainActivity mainActivity)
    {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

        return sharedPreferences.getString("CITY", "DEFAULT");
    }

    public static String getPrefUnits(MainActivity mainActivity)
    {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

        return sharedPreferences.getString("UNITS", "DEFAULT");
    }

    public static void setPrefCity(MainActivity mainActivity, String city)
    {
        mainActivity.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString("CITY", city).apply();
    }

    public static void setPrefUnits(MainActivity mainActivity, String units)
    {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UNITS", units);
        editor.apply();
    }







   //TODO: you can remove this function as getPrefCity works the same way
    /**
     * Loads the UI with all user's preferences which he has set
     * before. (or with default values if he hasn't).
     * @param optionsActivity
     * **/
    public static void load_UI(OptionsActivity optionsActivity)
    {
        // Define "preferences" as our file with user settings
        SharedPreferences sharedPreferences = optionsActivity.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

        // Declare EditText for user's city (location)
        EditText et_location = optionsActivity.findViewById(R.id.et_location);

        // Initialise
        et_location.setText(sharedPreferences.getString("CITY", "DEFAULT"));


        CheckBox cb_daily = optionsActivity.findViewById(R.id.cb_daily);
        cb_daily.setChecked(Boolean.valueOf(sharedPreferences.getString("DAILY", "false")));

        CheckBox cb_rain = optionsActivity.findViewById(R.id.cb_rain);
        cb_rain.setChecked(Boolean.valueOf(sharedPreferences.getString("RAIN", "false")));

        CheckBox cb_temp = optionsActivity.findViewById(R.id.cb_temp);
        cb_temp.setChecked(Boolean.valueOf(sharedPreferences.getString("TEMP", "false")));

        CheckBox cb_wind = optionsActivity.findViewById(R.id.cb_real_wind);
        cb_wind.setChecked(Boolean.valueOf(sharedPreferences.getString("WIND", "false")));
    }


    public static void setUserPrefs(OptionsActivity optionsActivity)
    {
        /**
         * This function works on btn BackToMAin click.
         *      It saves all the changes in settings the user has done so far
         *      to a SharedPreferences file.
         *      After it makes a Toast.**/


//    // Define "preferences" as our file with user settings
//    SharedPreferences sharedPreferences = optionsActivity.getSharedPreferences("haahccSHARED_PREF", Context.MODE_PRIVATE);
//
//    // Initialize EditText for user's city (location)
//    EditText et_location = optionsActivity.findViewById(R.id.et_location);
//
//    // Transfer user's city from EditText to a String variable
//    String location = et_location.getText().toString();
//
//    // Set the editor mechanism for modifying the preferences
//    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//    // Fill the field "CITY" in user's preferences file with location variable
//        editor.putString("CITY", location);
//        // Save it
//        editor.apply();
//
//        // Notify user his settings were saved
//        Toast.makeText(optionsActivity, "Preferences saved!", Toast.LENGTH_SHORT).show();
    }
}
