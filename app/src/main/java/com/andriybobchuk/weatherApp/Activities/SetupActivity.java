package com.andriybobchuk.weatherApp.Activities;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.andriybobchuk.weatherApp.R;
import com.andriybobchuk.weatherApp.Services.UserPreferencesService;
import com.andriybobchuk.weatherApp.databinding.ActivitySetupBinding;

public class SetupActivity extends AppCompatActivity {

    ActivitySetupBinding binding;
    public static final String PREF_FILE = "new9SHARED_PREF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        binding.clPage1.setVisibility(View.VISIBLE);
        binding.clPage1Bottom.setVisibility(View.VISIBLE);


        binding.btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do GPS stuff
                // +

                binding.clPage1.setVisibility(View.GONE);
                binding.clPage1Bottom.setVisibility(View.GONE);
                binding.clPage2.setVisibility(View.VISIBLE);
                binding.clPage2Bottom.setVisibility(View.VISIBLE);

                animateI();
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the text to prefs
                String city = String.valueOf(binding.etCity.getText()); // TODO: FIX NULL SAFETY
                getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString("CITY", city).apply();

                // +
                binding.clPage1.setVisibility(View.GONE);
                binding.clPage1Bottom.setVisibility(View.GONE);
                binding.clPage2.setVisibility(View.VISIBLE);
                binding.clPage2Bottom.setVisibility(View.VISIBLE);

                animateI();
            }
        });

        binding.btnMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do Preferences stuff
                getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString("UNITS", "metric").apply();

                // +
                binding.clPage2.setVisibility(View.GONE);
                binding.clPage2Bottom.setVisibility(View.GONE);
                binding.clPage3.setVisibility(View.VISIBLE);
                binding.page3.setVisibility(View.VISIBLE);

                animateII();
            }
        });

        binding.btnImperial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do Preferences stuff
                getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString("UNITS", "imperial").apply();

                // +
                binding.clPage2.setVisibility(View.GONE);
                binding.clPage2Bottom.setVisibility(View.GONE);
                binding.clPage3.setVisibility(View.VISIBLE);
                binding.page3.setVisibility(View.VISIBLE);

                animateII();

            }
        });

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMain();
                finish();
            }
        });


    }

    public void openMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void animateI() {
        Animation rightAnimation = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        binding.clPage2.startAnimation(rightAnimation);

        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        binding.clPage2Bottom.startAnimation(bottomAnimation);
    }
    public void animateII() {
        Animation leftAnimation = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        binding.page3.startAnimation(leftAnimation);

        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        binding.clPage3.startAnimation(topAnimation);
    }
}