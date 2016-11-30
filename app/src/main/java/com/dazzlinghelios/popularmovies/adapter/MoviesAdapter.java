package com.dazzlinghelios.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dazzlinghelios.popularmovies.R;
import com.dazzlinghelios.popularmovies.activity.DetailActivity;
import com.dazzlinghelios.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Merlin on 11/6/16.
 *
 * Binds from movies data to views that are displayed within a recyclerview
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private static final String MOVIE_THUMBNAIL_URL = "http://image.tmdb.org/t/p/w342/";
    private static final String TAG = MoviesAdapter.class.getSimpleName();

    private List<Movie> movies;
    private int rowLayout;
    private Context context;

    class MovieViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        LinearLayout moviesLayout;
//        TextView movieTitle;
//        TextView movieDescription;
//        TextView movieRating;
        ImageView thumbnail;

        MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
//            movieTitle = (TextView) v.findViewById(R.id.title);
//            movieDescription = (TextView) v.findViewById(R.id.description);
//            movieRating = (TextView) v.findViewById(R.id.rating);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            int position;

            position = this.getLayoutPosition();
            Intent i = newIntent(context,
                                position,
                                movies.get(position).getTitle(),
                                movies.get(position).getOverview(),
                                movies.get(position).getVoteAverage(),
                                movies.get(position).getReleaseDate(),
                                MOVIE_THUMBNAIL_URL + movies.get(position).getPosterPath());

            context.startActivity(i);
        }
    }

    // create new Intent which will be sent to receiver activity
    private static Intent newIntent(Context context, int imgPosition, String title,
                                    String plot, double rate, String releaseDate,
                                    String posterPath) {
        Intent i = new Intent(context, DetailActivity.class);

        // put extra data that will be displayed in receiver activity
        i.putExtra(DetailActivity.EXTRA_MOVIE_ID, imgPosition);
        i.putExtra(DetailActivity.EXTRA_MOVIE_TITLE, title);
        i.putExtra(DetailActivity.EXTRA_MOVIE_PLOT, plot);
        i.putExtra(DetailActivity.EXTRA_MOVIE_RATE, rate);
        i.putExtra(DetailActivity.EXTRA_MOVIE_RELEASE_DATE, releaseDate);
        i.putExtra(DetailActivity.EXTRA_MOVIE_POSTER_PATH, posterPath);

        return i;
    }

    // Constructor
    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
//        holder.movieTitle.setText(movies.get(position).getTitle());
//        holder.movieRating.setText(movies.get(position).getVoteAverage().toString());
//        holder.movieDescription.setText(movies.get(position).getOverview());
//        holder.data.setText(movies.get(position).getReleaseDate());
        String posterPath = MOVIE_THUMBNAIL_URL + movies.get(position).getPosterPath();

        // Load thumbnail using Picasso
        Picasso
            .with(context)
            .load(posterPath)
//            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .fit()
//            .centerCrop()
            .into(holder.thumbnail);

        Log.d(TAG, posterPath);
    }

    @Override
    public int getItemCount() {
        return (movies == null) ? 0 : movies.size();
    }


}
