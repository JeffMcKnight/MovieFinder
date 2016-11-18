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
    static final int API_VERSION = 3;
    static final String API_KEY_QUERY_PARAM = "?api_key=" + BuildConfig.API_KEY_MOVIEDB;
    public static final String PATH_POPULAR_MOVIE = API_VERSION + "/movie/popular" + API_KEY_QUERY_PARAM;
    public static final String PATH_MOVIE_TOP_RATED = API_VERSION + "/movie/top_rated" + API_KEY_QUERY_PARAM;

    @GET (PATH_POPULAR_MOVIE)
    Call<MovieBean> getPopularMovie();

    @GET(PATH_MOVIE_TOP_RATED)
    Call<MovieBean> getTopRatedMovie();
}
