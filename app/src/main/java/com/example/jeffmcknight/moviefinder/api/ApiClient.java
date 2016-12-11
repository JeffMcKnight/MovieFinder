package com.example.jeffmcknight.moviefinder.api;

import com.example.jeffmcknight.moviefinder.bean.MovieBean;

import java.io.IOException;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton to make API calls
 * Created by jeffmcknight on 11/17/16.
 */
public class ApiClient {
    private static ApiClient sApiClient;
    private ApiService mApiService;

    public void setApiService(ApiService apiService) {
        mApiService = apiService;
    }

    private ApiClient() {
    }

    /**
     *
     * @return
     */
    public static ApiClient getInstance() {
        if (sApiClient == null) {
            sApiClient = new ApiClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_HTTP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);
            sApiClient.setApiService(apiService);
        }
        return sApiClient;
    }


    /**
     * Asynchronously retrieves the list of popular movies from themoviedb.org
     * @param callback
     */
    public void getPopularMovie(Callback<MovieBean> callback) {
        mApiService.getPopularMovie().enqueue(callback);
    }

    /**
     * Synchronously retrieves the list of popular movies from themoviedb.org
     * @return
     */
    public Response<MovieBean> getPopularMovieSync() {
        try {
            return mApiService.getPopularMovie().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Asynchronously retrieves the list of top rated movies from themoviedb.org
     * @param callback
     */
    public void getTopRatedMovie(Callback<MovieBean> callback){
        mApiService.getTopRatedMovie().enqueue(callback);
    }

}
