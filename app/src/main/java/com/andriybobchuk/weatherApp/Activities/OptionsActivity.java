package com.andriybobchuk.weatherApp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.andriybobchuk.weatherApp.R;

public class OptionsActivity extends AppCompatActivity {

    ImageButton btn_back_from_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        load_UI();

        /* BackToMain */
        btn_back_from_options = findViewById((R.id.btn_back_from_options));
        btn_back_from_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUserPrefs();
                openMain();
            }
        });


    }



    /**
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
     */

    public void load_UI()
    {
        /**
         * Loads the UI with all user's preferences which he has set
         * before. (or with default values if he hasn't).
         * **/

        // Define "preferences" as our file with user settings
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

        // Declare EditText for user's city (location)
        EditText et_location = findViewById(R.id.et_location);

        // Initialise
        et_location.setText(sharedPreferences.getString("CITY", "Moscow"));

    }

    public void setUserPrefs()
    {
        /**
         * This function works on btn BackToMAin click.
         *      It saves all the changes in settings the user has done so far
         *      to a SharedPreferences file.
         *      After it makes a Toast.**/

        // Define "preferences" as our file with user settings
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

        // Initialize EditText for user's city (location)
        EditText et_location = findViewById(R.id.et_location);

        // Transfer user's city from EditText to a String variable
        String location = et_location.getText().toString();

        // Set the editor mechanism for modifying the preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Fill the field "CITY" in user's preferences file with location variable
        editor.putString("CITY", location);

        // Save it
        editor.apply();

        // Notify user his settings were saved
        Toast.makeText(OptionsActivity.this, "Preferences saved!", Toast.LENGTH_SHORT).show();

    }

    public void openMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}