package com.andriybobchuk.weatherApp.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.andriybobchuk.weatherApp.Activities.MainActivity;
import com.andriybobchuk.weatherApp.R;
import org.apache.commons.lang3.StringUtils;

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
    public void displayResult(String temp, String desc, Context context, String city, String min_max) {

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alaska")
                .setSmallIcon(R.drawable.clouds_icon)
                .setContentTitle(StringUtils.capitalize(desc) + " in " + StringUtils.capitalize(city))
                .setContentText(min_max + "  •  See full forecast")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(0000, builder.build());

    }
}
