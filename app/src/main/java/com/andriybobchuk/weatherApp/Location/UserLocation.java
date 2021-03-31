package com.andriybobchuk.weatherApp.Location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.widget.TextView;
import com.andriybobchuk.weatherApp.Activities.MainActivity;
import com.andriybobchuk.weatherApp.Features.UserPreferences;
import com.andriybobchuk.weatherApp.R;
import com.andriybobchuk.weatherApp.Structures.TimeAndDate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserLocation {

    // User's location:
    public static Double lat;
    public static Double lon;

    static String result = "not_null";


    /**
     * The following function is getting User's location. (lon, lat)
     *
     * Algorithm:
     *      - If we have permission -> get location
     *      - If we do not have permission -> ask for it -> get location
     *
     * @param mainActivity
     */
    public static void getUserLocation(final MainActivity mainActivity)
    {
        //final TextView latlon = mainActivity.findViewById(R.id.latlon);
        FusedLocationProviderClient fusedLocationProviderClient;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mainActivity);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            /** If we have permission -> get location */
            if(mainActivity.getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {

                // get the location
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if(location != null)
                                {
                                    lat = location.getLatitude();
                                    lon = location.getLongitude();


                                    // Decoding lat\lon coordinates to a city name
                                    Geocoder gcd = new Geocoder(mainActivity, Locale.getDefault());
                                    try {
                                        List<Address> addresses = gcd.getFromLocation(lat, lon, 1);

                                        result = addresses.get(0).getLocality();
                                        TextView tv_region = mainActivity.findViewById(R.id.tv_region);
                                        tv_region.setText(result + " at " + String.valueOf(new TimeAndDate().getTimeFormat().format(new TimeAndDate().getCurrentDateAndTime())));


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }



                                }

                            }
                        });
            }
//            else /** If we do not have permission -> ask for it -> get location */
//            {
//                // request the permission
//                mainActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//                // get the location
//                fusedLocationProviderClient.getLastLocation()
//                        .addOnSuccessListener(new OnSuccessListener<Location>() {
//                            @Override
//                            public void onSuccess(Location location) {
//
//                                if(location != null)
//                                {
//                                    lat = location.getLatitude();
//                                    lon = location.getLongitude();
//
//                                    //latlon.setText(lat + "  -   " + lon );
//                                }
//
//                            }
//                        });
//            }
        }
    }

//    public static void renderCity (MainActivity mainActivity)
//    {
//        getUserLocation(mainActivity);
//
//        TextView tv_region = mainActivity.findViewById(R.id.tv_region);
//        tv_region.setText(lat + " at " + String.valueOf(new TimeAndDate().getTimeFormat().format(new TimeAndDate().getCurrentDateAndTime())));
//    }
}
