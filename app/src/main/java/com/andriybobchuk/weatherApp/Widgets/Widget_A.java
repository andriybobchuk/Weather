package com.andriybobchuk.weatherApp.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import com.andriybobchuk.weatherApp.Activities.MainActivity;
import com.andriybobchuk.weatherApp.R;
import com.andriybobchuk.weatherApp.Services.SimpleForecastService;

import static com.andriybobchuk.weatherApp.Services.UserPreferencesService.PREF_FILE;


/**
 * Implementation of App Widget functionality.
 */
public class Widget_A extends AppWidgetProvider implements SimpleForecastService.apiCallback{

    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews("com.example.weather2", R.layout.widget__a);
    Context c;
    AppWidgetManager awm;
    int awid;


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        c = context;
        awm = appWidgetManager;
        awid = appWidgetId;

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SimpleForecastService.getSimpleForecaast(context, sharedPreferences.getString("CITY", "London"), sharedPreferences.getString("UNITS", "METRIC"));
        SimpleForecastService.setCallback(Widget_A.this);



        // Setup update button to send an update request as a pending intent.
        Intent intentUpdate = new Intent(context, Widget_A.class);

        // The intent action must be an app widget update.
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        // Include the widget ID to be updated as an intent extra.
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        // Wrap it all in a pending intent to send a broadcast.
        // Use the app widget ID as the request code (third argument) so that
        // each intent is unique.
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context,
                appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

        // Assign the pending intent to the button onClick handler
        views.setOnClickPendingIntent(R.id.appwidget_text_temp, pendingUpdate);
        views.setOnClickPendingIntent(R.id.appwidget_text_description, pendingUpdate);


        // Construct the RemoteViews object
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget__a);

        views.setTextViewText(R.id.appwidget_text_temp, "JFGI");
        views.setTextViewText(R.id.appwidget_text_description, "Loading");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
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
                              String wind) {


        views.setTextViewText(R.id.appwidget_text_temp, temp);
        views.setTextViewText(R.id.appwidget_text_description, "#" + desc);

        Toast.makeText(context, "Widget updated " + awid, Toast.LENGTH_SHORT).show();

        awm.updateAppWidget(awid, views);

    }
}

