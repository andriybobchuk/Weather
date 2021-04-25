package com.andriybobchuk.weatherApp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.andriybobchuk.weatherApp.R;
import com.andriybobchuk.weatherApp.Services.UserLocationService;
import com.andriybobchuk.weatherApp.Services.UserPreferencesService;
import com.andriybobchuk.weatherApp.databinding.ActivitySetupBinding;
import static com.andriybobchuk.weatherApp.Services.UserPreferencesService.PREF_FILE;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.*;

public class SetupActivity extends AppCompatActivity implements UserLocationService.UserLocationApiCallback {

    ActivitySetupBinding binding;
   // LocationManager locationManager;
    //String latitude, longitude;
    //private static final int REQUEST_LOCATION = 1;

    public void requestLocation(Context context)
    {
        //GPS Stuff
        UserLocationService.getUserLocation(context, this);
        UserLocationService.setCallback(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


//        ActivityCompat.requestPermissions( this,
//                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        binding.clPageB.setVisibility(View.VISIBLE);
        binding.clPageB.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
        binding.clPageC.setVisibility(View.GONE);
        binding.clPageD.setVisibility(View.GONE);
        binding.clPageA.setVisibility(View.GONE);

//        binding.btnA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.clPageA.setVisibility(View.GONE);
//                binding.clPageB.setVisibility(View.VISIBLE);
//                //animateI();
//            }
//        });

        binding.btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do GPS stuff
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(GPS_PROVIDER)) {
                    requestLocation(getApplicationContext());
                } else {
                    Toast.makeText(SetupActivity.this, "No GPS or network signal please fill the location manually!", Toast.LENGTH_SHORT).show();
                    //OnGPS();
                }

            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = String.valueOf(binding.etCity.getText()); // TODO: FIX NULL SAFETY
                getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString("CITY", city).apply();
                binding.clPageB.setVisibility(View.GONE);
                binding.clPageC.setVisibility(View.VISIBLE);
               // animateI();
                binding.clPageC.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right));

            }
        });

        binding.btnMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString("UNITS", "metric").apply();
                binding.clPageC.setVisibility(View.GONE);
                binding.clPageD.setVisibility(View.VISIBLE);
                //animateII();
                binding.clPageD.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right));

            }
        });

        binding.btnImperial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString("UNITS", "imperial").apply();
                binding.clPageC.setVisibility(View.GONE);
                binding.clPageD.setVisibility(View.VISIBLE);
                //animateII();
                binding.clPageD.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right));

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


    @Override
    public void displayLocation(String cityName) {
        getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit().putString("CITY", cityName).apply();
        if(cityName.equals(""))
        {
            Toast.makeText(this, "NaH", Toast.LENGTH_SHORT).show();

        }
        Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
        binding.clPageB.setVisibility(View.GONE);
        binding.clPageC.setVisibility(View.VISIBLE);
        //animateI();
        binding.clPageC.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right));
    }




    /////////
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
//            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (locationGPS != null) {
//                double lat = locationGPS.getLatitude();
//                double longi = locationGPS.getLongitude();
//                latitude = String.valueOf(lat);
//                longitude = String.valueOf(longi);
//
//                // Decoding lat\lon coordinates to a city name
//                Geocoder gcd = new Geocoder(this, Locale.getDefault());
//                try {
//                    List<Address> addresses = gcd.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
//                    String cityName = addresses.get(0).getLocality();
//                    //callback.displayLocation(cityName);
//                    Toast.makeText(this, "Ur loc is " + cityName, Toast.LENGTH_SHORT).show();
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}