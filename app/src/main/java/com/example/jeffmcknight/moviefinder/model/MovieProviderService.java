package com.example.jeffmcknight.moviefinder.model;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

import com.example.jeffmcknight.moviefinder.api.ApiClient;
import com.example.jeffmcknight.moviefinder.bean.MovieBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jeffmcknight on 12/9/16.
 */

public class MovieProviderService extends IntentService {

    private static final String TAG = MovieProviderService.class.getSimpleName();
    private static final String EXTRA_BEAN = MovieProviderService.class.getCanonicalName() + ".extra_bean";

    public MovieProviderService() {
        super(TAG);
    }

    /**
     * @param uri
     * @param context
     * @param bean
     */
    public static void launch(Uri uri, Context context, Parcelable bean){
        Intent intent = new Intent(context, MovieProviderService.class);
        intent.setData(uri);
        intent.putExtra(EXTRA_BEAN, bean);
        context.startService(intent);
    }

    /**
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (DbContract.Movie.buildBulkInsertUri().equals(intent.getData())){
            updateModel((MovieBean) intent.getParcelableExtra(EXTRA_BEAN));
        } else {
            Log.w(TAG, "onHandleIntent *** NO MATCH FOR intent.getData(): " + intent.getData());
        }
    }

    /**
     * Updates the "movie" database table.
     *
     * TODO: make a synchronous version of {@link ApiClient#getPopularMovie(Callback)} and call that
     * from {@link #onHandleIntent(Intent)} instead of doing all this unnecessary parcellizing.
     *
     * @param context
     */
    public static void getPopularMovies(final Context context){
        Log.d(TAG, "getPopularMovies -- context: "+ context);
        Callback<MovieBean> callback = new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                Log.i(TAG, "onResponse()"
                        +"\n -- Thread.currentThread().getName(): " + Thread.currentThread().getName()
                        +"\n -- response.body(): " + response.body()
                );
                launch(DbContract.Movie.buildBulkInsertUri(), context, response.body());
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                Log.w(TAG, "onFailure: ", t);
            }
        };
        ApiClient.getInstance().getPopularMovie(callback);

    }

    /**
     *
     * @param movieBean
     */
    private void updateModel(MovieBean movieBean) {
        Log.d(TAG, "updateModel -- movieBean: "+ movieBean);
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
