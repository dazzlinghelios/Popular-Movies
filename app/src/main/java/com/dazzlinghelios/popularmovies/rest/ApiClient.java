package com.dazzlinghelios.popularmovies.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Merlin on 11/6/16.
 *
 * A Retrofit API client
 *
 */
public class ApiClient {
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    /**
     *
     * Returns a Retrofit instance according to the base url and converter
     * factory that user provided.
     *
     * @return Retrofit instance
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
