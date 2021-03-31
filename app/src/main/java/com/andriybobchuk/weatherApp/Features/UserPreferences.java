package com.andriybobchuk.weatherApp.Features;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andriybobchuk.weatherApp.Activities.MainActivity;
import com.andriybobchuk.weatherApp.Activities.OptionsActivity;
import com.andriybobchuk.weatherApp.R;





/**
 * IMPORTANT!
 *
 * Working with User preferences in this project is separated into 3 functions:
 *
 * 1 - getUserPrefs() - On MainActivity start it retrieves the preferences from the file.
 *
 * 2 - load_UI() - On OptionsActivity start it retrieves the preferences from the file
 *                 to load the UI elements with this data (So the user could see what's happenin').
 *
 * 3 - setUserPrefs() - In OptionsActivity, when user wants to go back to MainActivity after some
 *                      changes it saves his new preferences into the file.
 *
 *
 */
public class UserPreferences {


    public static String currentCity = null; // We determine it by user's current location.
    public static String prefCity = null; // getUserPrefs() determines it by pref file.


    /**
     * This function reads user preferences from the file for the later use.
     *
     * @param mainActivity*/
   public static String getPrefCity(MainActivity mainActivity)
   {
       SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("haahSHARED_PREF", Context.MODE_PRIVATE);

       return sharedPreferences.getString("CITY", currentCity);
   }


    /**
     * Loads the UI with all user's preferences which he has set
     * before. (or with default values if he hasn't).
     * @param optionsActivity
     * **/
    public static void load_UI(OptionsActivity optionsActivity)
    {

        // Define "preferences" as our file with user settings
        SharedPreferences sharedPreferences = optionsActivity.getSharedPreferences("haahSHARED_PREF", Context.MODE_PRIVATE);

        // Declare EditText for user's city (location)
        EditText et_location = optionsActivity.findViewById(R.id.et_location);

        // Initialise
        et_location.setText(sharedPreferences.getString("CITY", currentCity));

    }


    public static void setUserPrefs(OptionsActivity optionsActivity)
    {
        /**
         * This function works on btn BackToMAin click.
         *      It saves all the changes in settings the user has done so far
         *      to a SharedPreferences file.
         *      After it makes a Toast.**/

        // Define "preferences" as our file with user settings
        SharedPreferences sharedPreferences = optionsActivity.getSharedPreferences("haahSHARED_PREF", Context.MODE_PRIVATE);

        // Initialize EditText for user's city (location)
        EditText et_location = optionsActivity.findViewById(R.id.et_location);

        // Transfer user's city from EditText to a String variable
        String location = et_location.getText().toString();

        // Set the editor mechanism for modifying the preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Fill the field "CITY" in user's preferences file with location variable
        editor.putString("CITY", location);

        // Save it
        editor.apply();

        // Notify user his settings were saved
        Toast.makeText(optionsActivity, "Preferences saved!", Toast.LENGTH_SHORT).show();

    }
}
