package com.andriybobchuk.weatherApp.Activities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.andriybobchuk.weatherApp.Features.UserPreferences;
import com.andriybobchuk.weatherApp.R;

public class OptionsActivity extends AppCompatActivity {

    ImageButton btn_back_from_options;

    /**
     * So here on OptionsActivity start we update UI using userPrefs file;
     *
     * On BackToMain click we save NEW userPrefs to a file and open MAinActivity.
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        UserPreferences.load_UI(this);

        /* BackToMain */
        btn_back_from_options = findViewById((R.id.btn_back_from_options));
        btn_back_from_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserPreferences.setUserPrefs(OptionsActivity.this);
                openMain();
            }
        });


    }


    public void openMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}