package com.meowroll.movies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Meow on 2017/1/3.
 */

public final class MovieDBJsonUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param movieJsonStr JSON response from server
     *
     * @return Array of Strings describing weather data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getSimpleMovieStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        //Log.v(TAG, "getSimpleMovieStringsFromJson " + movieJsonStr);
        /* root of the item list */
        final String j_result = "results";

        /* All temperatures are children of the "temp" object */
        final String OWM_TEMPERATURE = "temp";

        /* Max temperature for the day */
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";


        /* String array to hold each day's weather String */
        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        /* Is there an error? */



        JSONArray movieArray = movieJson.getJSONArray(j_result);




        parsedMovieData = new String[movieArray.length()];

        //long localDate = System.currentTimeMillis();
        //long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
        //long startDay = SunshineDateUtils.normalizeDate(utcDate);

        for (int i = 0; i < movieArray.length(); i++) {
            //Log.v(TAG, "movieArray.length()= " + movieArray.length());
            /* These are the values that will be collected */
            String poster_path;
            String overview;
            String release_date;
            String id;
            String original_title;
            String original_language;
            String title;
            String backdrop_path;
            double vote_average;




            /* Get the JSON object representing each movie */
            JSONObject eachMovie = movieArray.getJSONObject(i);



            poster_path = eachMovie.getString("poster_path");
            release_date = eachMovie.getString("release_date");
            overview = eachMovie.getString("overview");
            id = eachMovie.getString("id");
            original_title = eachMovie.getString("original_title");
            original_language = eachMovie.getString("original_language");
            backdrop_path = eachMovie.getString("backdrop_path");
            title = eachMovie.getString("title");
            //Log.v(TAG, "title= " + title);
            vote_average = eachMovie.getDouble("vote_average");


            /*
             * Temperatures are sent by Open Weather Map in a child object called "temp".
             *
             * Editor's Note: Try not to name variables "temp" when working with temperature.
             * It confuses everybody. Temp could easily mean any number of things, including
             * temperature, temporary and is just a bad variable name.
             */

            parsedMovieData[i] = title + "#" + original_title + "#" + poster_path + "#" + vote_average + "#" + release_date+ "#" + overview;
        }

        return parsedMovieData;
    }


}