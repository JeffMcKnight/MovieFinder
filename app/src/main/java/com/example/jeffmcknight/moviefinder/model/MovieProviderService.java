package com.example.jeffmcknight.moviefinder.model;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.jeffmcknight.moviefinder.api.ApiClient;
import com.example.jeffmcknight.moviefinder.bean.MovieBean;

import retrofit2.Response;

/**
 * Created by jeffmcknight on 12/9/16.
 */

public class MovieProviderService extends IntentService {

    private static final String TAG = MovieProviderService.class.getSimpleName();

    public MovieProviderService() {
        super(TAG);
    }

    /**
     * @param uri
     * @param context
     */
    public static void launch(Uri uri, Context context){
        Intent intent = new Intent(context, MovieProviderService.class);
        intent.setData(uri);
        context.startService(intent);
    }

    /**
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (DbContract.Movie.buildBulkInsertUri().equals(intent.getData())){
            getPopularMovies();
        } else {
            Log.w(TAG, "onHandleIntent *** NO MATCH FOR intent.getData(): " + intent.getData());
        }
    }

    /**
     * Call MovieDb API to get list of popular movies and update database table "movie".
     * <p>
     *     <b>This method should only be called from {@link #onHandleIntent(Intent)} because it
     *     performs synchronous network call.</b>
     * <p>
     *     We use the synchronous version because the asynchronous version returns its callback on
     *     the main thread, but we want to use them in a background thread to insert the result into
     *     the database.
     */
    private void getPopularMovies(){
        // Make synchronous API call
        Response<MovieBean> popularMovies = ApiClient.getInstance().getPopularMovieSync();
        if (popularMovies == null){
            onFailure(popularMovies);
        } else {
            MovieBean movieBean = popularMovies.body();
            Log.d(TAG, "getPopularMovies() -- movieBean: "+ movieBean);
            ContentValues[] contentValues = new ContentValues[movieBean.results.length];
            for (int i=0; i<contentValues.length; i++){
                contentValues[i] = new ContentValues(5);
                contentValues[i].put(DbContract.Movie.COLUMN_OVERVIEW, movieBean.results[i].overview);
                contentValues[i].put(DbContract.Movie.COLUMN_POSTER_PATH, movieBean.results[i].poster_path);
                contentValues[i].put(DbContract.Movie.COLUMN_RELEASE_DATE, movieBean.results[i].release_date);
                contentValues[i].put(DbContract.Movie.COLUMN_TITLE, movieBean.results[i].original_title);
                contentValues[i].put(DbContract.Movie.COLUMN_VOTE_AVERAGE, movieBean.results[i].vote_average);
            }
            getContentResolver().bulkInsert(DbContract.Movie.buildBulkInsertUri(), contentValues);
        }
    }

    /**
     * TODO: Implement some user messaging and retry logic here. Do not do an UI work here because
     * this method is called from the {@link MovieProviderService} background thread
     * @param response
     */
    private void onFailure(Response response) {
        Log.d(TAG, "onFailure() -- Thread.currentThread().getName(): "+ Thread.currentThread().getName());
        Log.w(TAG, "onFailure() *** response: " + response);
    }

}
