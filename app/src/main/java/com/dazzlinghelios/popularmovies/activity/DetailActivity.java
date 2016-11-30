package com.dazzlinghelios.popularmovies.activity;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dazzlinghelios.popularmovies.R;
import com.dazzlinghelios.popularmovies.fragment.MovieDetailFragment;

/**
 * Created by Merlin on 11/13/16.
 *
 */
public class DetailActivity extends AppCompatActivity{
    public static final String EXTRA_MOVIE_ID = "id";
    public static final String EXTRA_MOVIE_TITLE = "title";
    public static final String EXTRA_MOVIE_POSTER_PATH = "poster";
    public static final String EXTRA_MOVIE_PLOT = "plot";
    public static final String EXTRA_MOVIE_RATE = "rate";
    public static final String EXTRA_MOVIE_RELEASE_DATE = "release_date";
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_detail);

        // enable up button
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

//        MovieDetailFragment movieDetailFragment = (MovieDetailFragment)
//                getFragmentManager().findFragmentById(R.id.detail_fragment);
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.detail_fragment, movieDetailFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.addToBackStack(null);
        ft.commit();

        // receive the data that sent in Intent
        int movieId = (int) getIntent().getExtras().get(EXTRA_MOVIE_ID);
        String movieTitle = (String) getIntent().getExtras().get(EXTRA_MOVIE_TITLE);
        String moviePlot= (String) getIntent().getExtras().get(EXTRA_MOVIE_PLOT);
        double movieRate = (double) getIntent().getExtras().get(EXTRA_MOVIE_RATE);
        String movieReleaseDate = (String) getIntent().getExtras().get(EXTRA_MOVIE_RELEASE_DATE);
        String moviePosterPath = (String) getIntent().getExtras().get(EXTRA_MOVIE_POSTER_PATH);

        // attach the data to fragment
        movieDetailFragment.setMovieId(movieId);
        movieDetailFragment.setMovieTitle(movieTitle);
        movieDetailFragment.setMoviePlot(moviePlot);
        movieDetailFragment.setMovieRate(movieRate);
        movieDetailFragment.setMovieReleaseDate(movieReleaseDate);
        movieDetailFragment.setMoviePosterPath(moviePosterPath);
    }


}
