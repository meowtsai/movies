package com.meowroll.movies.utilities;

/**
 * Created by Meow on 2017/1/3.
 */

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the weather servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String apiKey = "cb6f6a2667af1c852790468594b68e7e";

    private static final String THEMOVIEDB_API_URL =
            "https://api.themoviedb.org/3/movie/";




    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param rankingType The rankingType that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String rankingType) {
        Uri builtUri = Uri.parse(THEMOVIEDB_API_URL + rankingType).buildUpon()
                .appendQueryParameter("api_key", apiKey)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }



    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                Log.v(TAG, "return " );
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}