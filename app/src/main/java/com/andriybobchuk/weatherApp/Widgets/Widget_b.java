package com.andriybobchuk.weatherApp.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import com.andriybobchuk.weatherApp.Activities.MainActivity;
import com.andriybobchuk.weatherApp.R;
import com.andriybobchuk.weatherApp.Services.ForecastService;
import com.andriybobchuk.weatherApp.Services.SimpleForecastService;
import com.andriybobchuk.weatherApp.Structures.TimeAndDate;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

import static com.andriybobchuk.weatherApp.Services.UserPreferencesService.PREF_FILE;


/**
 * Implementation of App Widget functionality.
 */
public class Widget_b extends AppWidgetProvider implements SimpleForecastService.apiCallback{

    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews("com.example.weather2", R.layout.widget_b);
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
        SimpleForecastService.setCallback(Widget_b.this);



        // Setup update button to send an update request as a pending intent.
        Intent intentUpdate = new Intent(context, Widget_b.class);

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

        views.setTextViewText(R.id.appwidget_text_temp, "13°");
        views.setTextViewText(R.id.appwidget_text_description, "Gliwice\nUpdated 17:41");
        views.setImageViewResource(R.id.iv_ico, R.drawable.b_sun);

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
                              String wind,
                              String main) {


        views.setTextViewText(R.id.appwidget_text_temp, temp);
        views.setTextViewText(R.id.appwidget_text_description, StringUtils.capitalize(city) + "\nUpdated " + new TimeAndDate().getTimeFormat().format(Calendar.getInstance().getTime()));

        switch(main)
        {
            case "Clouds":
                views.setImageViewResource(R.id.iv_ico, R.drawable.b_clouds);
                break;
            case "Clear":
                views.setImageViewResource(R.id.iv_ico, R.drawable.b_sun);
                break;
            case "Rain":
                views.setImageViewResource(R.id.iv_ico, R.drawable.b_rain);
                break;
            case "Snow":
                views.setImageViewResource(R.id.iv_ico, R.drawable.b_snow);
                break;
            default:
                views.setImageViewResource(R.id.iv_ico, R.drawable.b_clouds);
        }

        Toast.makeText(context, "Widget b updated " + awid, Toast.LENGTH_SHORT).show();



        awm.updateAppWidget(awid, views);

    }
}

