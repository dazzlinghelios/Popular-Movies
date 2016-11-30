package com.dazzlinghelios.popularmovies.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dazzlinghelios.popularmovies.R;
import com.dazzlinghelios.popularmovies.adapter.GridAutofitLayoutManager;
import com.dazzlinghelios.popularmovies.adapter.ItemOffsetDecoration;
import com.dazzlinghelios.popularmovies.adapter.MoviesAdapter;
import com.dazzlinghelios.popularmovies.model.Movie;
import com.dazzlinghelios.popularmovies.model.MovieResponse;
import com.dazzlinghelios.popularmovies.rest.ApiClient;
import com.dazzlinghelios.popularmovies.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Merlin on 11/6/16.
 *
 * Main activity of Popular Movie app
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "b504267ead72b42ada5d01974823ca78";

    private int optionId = R.id.action_sort_by_popular;
    private boolean needFresh;
    private boolean networkError;
    private SwipeRefreshLayout swipeContainer;

    List<Movie> movies;
    GridAutofitLayoutManager layoutManager;
//    Parcelable recyclerViewState;
    ApiInterface apiService;
    Call<MovieResponse> call;
    RecyclerView recyclerView;
    TextView errorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            needFresh = savedInstanceState.getBoolean("needFresh");
            networkError = savedInstanceState.getBoolean("networkError");
//            recyclerViewState = savedInstanceState.getParcelable("recyclerStateKey");

//            Log.d(TAG, "need fresh: " + needFresh);
//            Log.d(TAG, "network: " + networkError);

        } else {
            needFresh = true;
            networkError = false;
        }

        // If API key is empty, toast a message
        if (API_KEY.isEmpty()) {
            Toast.makeText(this, "Please insert your API key", Toast.LENGTH_LONG).show();
            return;
        }

        // Get recyclerview by its id
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        if (recyclerView != null) {
            // use customized gridview layout manager
            layoutManager = new GridAutofitLayoutManager(this, 540);
            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setLayoutManager(layoutManager);
            // add padding, 0dp for this project
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
            recyclerView.addItemDecoration(itemDecoration);

        }

        // Create an API interface for Retrofit
        apiService = ApiClient.getRetrofit().create(ApiInterface.class);

        // swipe refresh layout
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sortByOptions(optionId);
                swipeContainer.setRefreshing(false);

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // default to display movies by popularity
        if (needFresh)
            sortByOptions(optionId);


//        if (networkError) {

//        }
    }

    // Call different API based on which item is selected
    private void sortByOptions(int id) {
        if (id == R.id.action_sort_by_popular)
            call = apiService.getPopularMovies(API_KEY);
        else
            call = apiService.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                Toast.makeText(getApplicationContext(), "Connecting", Toast.LENGTH_SHORT)
                        .show();

                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie,
                        MainActivity.this));
//                needFresh = false;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT)
                        .show();

                needFresh = true;
                networkError = true;
                // if network has some problem, then show error message
                errorTextView = (TextView) findViewById(R.id.network_error);
                errorTextView.setText(MainActivity.this.getString(R.string.net_error));

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        optionId = item.getItemId();
        switch (optionId) {
            case R.id.action_sort_by_popular:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                sortByOptions(R.id.action_sort_by_popular);
                return true;
            case R.id.action_sort_by_rate:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                sortByOptions(R.id.action_sort_by_rate);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Add options menu (sort movies by popular and rate) to Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // set default sorting by popularity
        menu.findItem(R.id.action_sort_by_popular).setChecked(true);
//        MenuItem menuItem = menu.findItem(R.id.action_sort_spinner);
//        Spinner sortSpinner = (Spinner) MenuItemCompat.getActionView(menuItem);
//
//        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(this,
//                R.array.sort_option, android.R.layout.simple_spinner_item);
//        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        sortSpinner.setAdapter(itemAdapter);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("needFresh", needFresh);
        savedInstanceState.putBoolean("networkError", networkError);

        // save recyclerview state
//        recyclerViewState = layoutManager.onSaveInstanceState();
//        savedInstanceState.putParcelable("recyclerStateKey", recyclerViewState);
        super.onSaveInstanceState(savedInstanceState);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();

        // restore recyclerview state
//        if (recyclerViewState != null)
//            layoutManager.onRestoreInstanceState(recyclerViewState);

//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(networkError)
//            needFresh = true;
//    }

}
