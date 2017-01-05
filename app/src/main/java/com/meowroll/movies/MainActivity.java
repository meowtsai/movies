package com.meowroll.movies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.meowroll.movies.utilities.MovieDBJsonUtils;
import com.meowroll.movies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements movieDBAdapter.movieDBAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private movieDBAdapter mMovieDBAdapterAdapter;
    private ProgressBar mLoadingIndicator;
    private SpinAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context mContext = this;
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //final String[] strRankingBy = { "popular","top_rated"};

        SortCriteria[] sortCriterias = new SortCriteria[2];

        sortCriterias[0] = new SortCriteria();
        sortCriterias[0].set_value("popular");
        sortCriterias[0].set_displayText("Most Popular");

        sortCriterias[1] = new SortCriteria();
        sortCriterias[1].set_value("top_rated");
        sortCriterias[1].set_displayText("Highest Rated");

         adapter = new SpinAdapter(this,
                android.R.layout.simple_spinner_item,
                sortCriterias);

        //ArrayAdapter<String> rankbyList = new ArrayAdapter<>(MainActivity.this,                android.R.layout.simple_spinner_dropdown_item ,                strRankingBy);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "你選的是" + strRankingBy[position], Toast.LENGTH_SHORT).show();

                mMovieDBAdapterAdapter.setmMovieData(null);

        /* Once all of our views are setup, we can load the weather data. */

                loadMovieData(adapter.getItem(position).get_value());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_moviegrid );
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);


        GridLayoutManager layoutManager = new GridLayoutManager(this,3 );

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieDBAdapterAdapter = new movieDBAdapter(this);
        mRecyclerView.setAdapter(mMovieDBAdapterAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the weather data.*/
        loadMovieData("popular");

    }

    private void loadMovieData(String sRankBy) {
        //String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(sRankBy);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {


            if (params.length == 0) {
                return null;
            }

            String sType = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sType);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                //Log.v(TAG, "jsonMovieResponse= " + jsonMovieResponse);

                String[] simpleJsonMovieData = MovieDBJsonUtils
                        .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                mMovieDBAdapterAdapter.setmMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }
    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String thatMovie) {
        Intent intent= new Intent(this,DetailActivity.class);
        intent.putExtra("thatMovie", thatMovie);
        startActivity(intent);
    }
}
