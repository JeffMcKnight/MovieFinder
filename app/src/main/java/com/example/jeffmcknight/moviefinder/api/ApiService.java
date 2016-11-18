package com.example.jeffmcknight.moviefinder.api;

import com.example.jeffmcknight.moviefinder.BuildConfig;
import com.example.jeffmcknight.moviefinder.bean.MovieBean;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jeffmcknight on 11/17/16.
 */

public interface ApiService {
    public static final HttpUrl BASE_HTTP_URL = HttpUrl.parse("http://api.themoviedb.org");
    String API_KEY_QUERY_PARAM = "?api_key=" + BuildConfig.API_KEY_MOVIEDB;
    public static final String PATH_POPULAR_MOVIE = "3/movie/popular" + API_KEY_QUERY_PARAM;

    @GET (PATH_POPULAR_MOVIE)
    Call<MovieBean> getPopularMovie();
}
