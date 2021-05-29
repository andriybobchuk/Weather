package com.andriybobchuk.weatherApp.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.andriybobchuk.weatherApp.Activities.MainActivity;
import com.andriybobchuk.weatherApp.R;
import org.apache.commons.lang3.StringUtils;
import java.lang.Math;
import java.net.URLDecoder;

import static com.andriybobchuk.weatherApp.Services.UserPreferencesService.PREF_FILE;

/**
 * This class listens to the timer set from MainActivity, runs getSimpleForecast and makes the notification
 */

public class ReminderBroadcast extends BroadcastReceiver implements SimpleForecastService.apiCallback {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

        SimpleForecastService.getSimpleForecaast(context, sharedPreferences.getString("CITY", "London"), sharedPreferences.getString("UNITS", "METRIC"));
        SimpleForecastService.setCallback(this);
    }

    @Override
    public void displayResult(String temp,
                              String desc,
                              Context context,
                              String city,
                              String min_max,
                              String theme_tomorrow,
                              String temp_tomorrow,
                              String temp_today,
                              String wind, String main) {

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        if(sharedPreferences.getString("DAILY", "false").equals("true"))
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alaska")
                    .setSmallIcon(R.drawable.clouds_icon)
                    .setContentTitle(StringUtils.capitalize(desc) + " in " + StringUtils.capitalize(city) )
                    .setContentText(min_max + "  •  See the full forecast")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setSound(null);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify(0000, builder.build());
        }
        if(sharedPreferences.getString("RAIN", "false").equals("true"))
        {
            if(theme_tomorrow.equalsIgnoreCase("Rain"))
            {
                Spanned water_emoji;
                water_emoji = Html.fromHtml(URLDecoder.decode("&#128166"));

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alaska")
                        .setSmallIcon(R.drawable.umbrella_icon)
                        .setContentTitle("Expect rain tomorrow  " + water_emoji)
                        .setContentText("In " + StringUtils.capitalize(city))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                notificationManager.notify(0001, builder.build());
            }

        }
        if(sharedPreferences.getString("TEMP", "false").equals("true"))
        {
            int difference = Math.abs(Integer.valueOf(temp_today) - Integer.valueOf(temp_tomorrow));
            if(difference >= 10)
            {
                Spanned pokerface_emoji;
                pokerface_emoji = Html.fromHtml(URLDecoder.decode("&#x1F5FF"));

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alaska")
                        .setSmallIcon(R.drawable.temp_icon)
                        .setContentTitle("It's " + String.valueOf(difference) + "° different tomorrow!  " + pokerface_emoji)
                        .setContentText(temp_tomorrow + "° on average")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                notificationManager.notify(0010, builder.build());
            }

        }
        if(sharedPreferences.getString("WIND", "false").equals("true"))
        {
//            if(sharedPreferences.getString("UNITS", "metric").equals("metric"))
//            {
//                if(Integer.valueOf(wind) >= 20)
//                {
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alaska")
//                            .setSmallIcon(R.drawable.wind_icon)
//                            .setContentTitle("It's windy" + " in " + StringUtils.capitalize(city))
//                            .setContentText(temp_today + "° on average  •  See the full forecast")
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                            .setContentIntent(pendingIntent);
//
//                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//                    notificationManager.notify(0011, builder.build());
//                }
//            } else {
//                if(Integer.valueOf(wind) >= 13)
//                {
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alaska")
//                            .setSmallIcon(R.drawable.clouds_icon)
//                            .setContentTitle("It's windy" + " in " + StringUtils.capitalize(city))
//                            .setContentText(temp_today + "° on average  •  See the full forecast")
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                            .setContentIntent(pendingIntent);
//
//                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//                    notificationManager.notify(0100, builder.build());
//                }
//            }





        }


    }
}
