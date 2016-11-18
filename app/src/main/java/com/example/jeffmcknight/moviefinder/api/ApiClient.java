package com.example.jeffmcknight.moviefinder.api;

import com.example.jeffmcknight.moviefinder.bean.MovieBean;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton to make API calls
 * Created by jeffmcknight on 11/17/16.
 */
public class ApiClient {
    private static ApiClient sApiClient;
    private ApiService mApiService;

    public static ApiClient create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_HTTP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        return getInstance(apiService);
    }

    public void setApiService(ApiService apiService) {
        mApiService = apiService;
    }

    private ApiClient() {
    }

    /**
     *
     * @return
     * @param apiService
     */
    public static ApiClient getInstance(ApiService apiService) {
        if (sApiClient == null) {
            sApiClient = new ApiClient();
            sApiClient.setApiService(apiService);
        }
        return sApiClient;
    }


    public void getPopularMovie(Callback<MovieBean> callback) {
        mApiService.getPopularMovie().enqueue(callback);
    }

    public void getTopRatedMovie(Callback<MovieBean> callback){
        mApiService.getTopRatedMovie().enqueue(callback);
    }
}
