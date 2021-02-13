package com.andriybobchuk.weatherApp.Structures;

public class WeatherData {

    /**
     * IMPORTANT NOTE:
     * In this file I declare all variables - String arrays for weather data for 7 days.
     * I put them in a SEPARATE (this) file to be able to use it from any other file.
     * All of them MUST be PUBLIC variables so I could access them from another files.
     * All of them MUST be STATIC so I can use variables even without having access to an object of the specified class.
     *
     * Now it should be clear.
     *
     * AND ONE MORE THING:
     * I know it's bad to do like this, so I'll improve this code later with getters and setters so my
     * variables can be private
     */

    /** ARRAYS WITH WEATHER DATA STRINGS **/
    public static String [ ] arr_date = new String [7];
    public static String [ ] arr_min_max = new String [7];
    public static String [ ] arr_details = new String [7];
    public static String [ ] arr_humidity = new String [7];
    public static String [ ] arr_clouds = new String [7];
    public static String [ ] arr_uvi = new String [7];
    public static String [ ] arr_wind = new String [7];
    public static String [ ] arr_temperature = new String [7];
    public static String [ ] arr_pressure = new String [7];
    public static String [ ] arr_theme = new String [7];
    public static String [ ] arr_sunrise = new String [7];
    public static String [ ] arr_sunset = new String [7];
    public static String [ ] arr_pop = new String [7];
    ///
    public static String[] arr_time = new String [12];
    public static String[] arr_temp = new String [12];
    public static String[] arr_descript = new String [12];
    public static String[] arr_pres = new String [12];
    ///
    public static String [ ] arr_morning = new String [7];
    public static String [ ] arr_afternoon = new String [7];
    public static String [ ] arr_eve = new String [7];
    public static String [ ] arr_night = new String [7];

    /// For widgets
    public static String today_temperature;


}
