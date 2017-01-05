package com.meowroll.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    //<!--title, release date, movie poster, vote average, and plot synopsis.-->
    private TextView tvTitle ;
    private TextView tvReleaseDate ;
    private TextView tvVoteAverate ;
    private TextView tvPlot ;
    private ImageView ivPoster;
    private String mMovie;
    private TextView tv_original_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv_original_title= (TextView) findViewById(R.id.tv_original_title);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvVoteAverate = (TextView) findViewById(R.id.tv_vote_average );
        tvPlot = (TextView) findViewById(R.id.tv_plot_synopsis);
        ivPoster =(ImageView)findViewById(R.id.iv_poster);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("thatMovie")) {
                //parsedMovieData[i] = title + " - " + original_title + " - " + poster_path + " - " + vote_average + " - " + release_date+ " - " + overview;
                mMovie = intentThatStartedThisActivity.getStringExtra("thatMovie");


                String[] tmpArray = mMovie.split("#");
                tvTitle.setText(tmpArray[0]);
                tv_original_title.setText(tmpArray[1]);
                tvReleaseDate.setText("Release Date: " + tmpArray[4]);
                tvVoteAverate.setText("Vote Average: " + tmpArray[3]);
                tvPlot.setText( tmpArray[5]);
                String filePath = "https://image.tmdb.org/t/p/w500"+tmpArray[2].trim();

                Picasso.with(this).load(filePath).into(ivPoster);
                //mWeatherDisplay.setText(mForecast);
            }
        }
    }
}
