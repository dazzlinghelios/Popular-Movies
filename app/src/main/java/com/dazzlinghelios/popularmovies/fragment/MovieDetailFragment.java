package com.dazzlinghelios.popularmovies.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazzlinghelios.popularmovies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Merlin on 11/13/16.
 *
 */
public class MovieDetailFragment extends Fragment {
    private int movieId;
    private String movieTitle;
    private String moviePlot;
    private double movieRate;
    private String movieReleaseDate;
    private String moviePosterPath;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Movie Details");  // set actionbar title

        if (savedInstanceState != null)
            movieId = savedInstanceState.getInt("movieId");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView plot = (TextView) view.findViewById(R.id.plot);
            TextView rate = (TextView) view.findViewById(R.id.rate);
            TextView releaseDate = (TextView) view.findViewById(R.id.release_date);
            ImageView poster = (ImageView) view.findViewById(R.id.poster);

            title.setText(movieTitle);
            plot.setText(moviePlot);
//            rate.setText(String.valueOf(movieRate) + "/10");
            rate.setText(this.getString(R.string.rate, movieRate, 10));
            releaseDate.setText(movieReleaseDate);
            Picasso.with(view.getContext())
                   .load(moviePosterPath)
                   .fit()
                   .into(poster);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("movieId", movieId);
    }

    public void setMovieId(int id) {
        this.movieId = id;
    }

    public void setMovieTitle(String title) {
        this.movieTitle = title;
    }

    public void setMoviePlot(String plot) {
        this.moviePlot = plot;
    }

    public void setMovieRate(double rate) {
        this.movieRate = rate;
    }

    public void setMovieReleaseDate(String date) {
        this.movieReleaseDate = date;
    }

    public void setMoviePosterPath(String path) {
        this.moviePosterPath = path;
    }

}