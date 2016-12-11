package com.example.jeffmcknight.moviefinder;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jeffmcknight.moviefinder.api.ApiClient;
import com.example.jeffmcknight.moviefinder.bean.MovieBean;
import com.example.jeffmcknight.moviefinder.bean.ResultsItem;
import com.example.jeffmcknight.moviefinder.model.DbContract;
import com.example.jeffmcknight.moviefinder.model.MovieProviderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoverFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = DiscoverFragment.class.getSimpleName();
    public static final int SPAN_COUNT = 2;
    public static final int VIEW_TYPE = 0;
    private static final int LOADER_ID = DiscoverFragment.class.hashCode();
    private RecyclerView mRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MovieProviderService.launch(DbContract.Movie.buildBulkInsertUri(), context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_discover, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        mRecyclerView.setAdapter(new ViewHolderAdapter(this));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated()"
                + "\t -- LOADER_ID: " + LOADER_ID
        );
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
//        getPopularMovies();
    }

    /**
     * TODO: move this to {@link com.example.jeffmcknight.moviefinder.model.MovieProviderService}
     */
    void getPopularMovies(){
        Callback<MovieBean> callback = new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                Log.i(TAG, "onResponse()"
                        +"\n -- response.body(): " + response.body()
                );
                updateView(response);
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                Log.w(TAG, "onFailure: ", t);
            }
        };
        ApiClient.getInstance().getPopularMovie(callback);

    }


    private void updateView(Response<MovieBean> response) {
        ((ViewHolderAdapter) mRecyclerView.getAdapter()).swapListItems(response.body().results);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: id: "+ id);
        if (id == LOADER_ID){
            CursorLoader cursorLoader = new CursorLoader(
                    getContext(),
                    DbContract.Movie.buildDiscoveryQueryUri(),
                    null,
                    null,
                    null,
                    null);
            return cursorLoader;
        } else {
            Log.w(TAG, "onCreateLoader() *** UNKNOWN Loader id: " + id);
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, "onLoadFinished()"
                + " \n -- Thread.currentThread().getName(): "+Thread.currentThread().getName()
                + " \n -- loader: " + loader
                + " \n -- data: " + data
        );
        ((ViewHolderAdapter) mRecyclerView.getAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset"
                + " -- loader: " + loader
        );
        ((ViewHolderAdapter) mRecyclerView.getAdapter()).swapCursor(null);

    }

    /**
     *
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }

    /**
     *
     */
    static class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
        private List<ResultsItem> mItem;
        private Fragment mFragment;

        ViewHolderAdapter(Fragment fragment) {
            mFragment = fragment;
            mItem = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View gridViewItem = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.fragment_discover_grid_item, parent, false);
            return new ViewHolder(gridViewItem);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Log.i(TAG, "onBindViewHolder()"
                    +" -- position: "+position
                    +" -- mItem.get(position).poster_path: "+mItem.get(position).poster_path
            );
            Glide.with(mFragment)
                    .load(BASE_IMAGE_URL + mItem.get(position).poster_path)
                    .fitCenter()
//                    .centerCrop()
                    .placeholder(R.drawable.fading_ventilation)
                    .crossFade()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mItem.size();
        }

        public void swapListItems(ResultsItem[] resultsItems) {
            Log.i(TAG, "swapListItems()");
            mItem = Arrays.asList(resultsItems);
        }

        public void swapCursor(@Nullable Cursor data) {
            Log.d(TAG, "swapCursor"
                    +" -- data: "+data
            );
            ResultsItem[] resultsItems;
            if (data == null){
                resultsItems = new ResultsItem[0];
            } else {
                Log.d(TAG, "swapCursor"
                        +" -- data.getColumnCount(): "+data.getColumnCount()
                        +" -- data.getCount(): "+data.getCount()
                );
                resultsItems = new ResultsItem[data.getCount()];
                data.moveToFirst();
                int i=0;
                while (!data.isAfterLast() && i<resultsItems.length) {
                    resultsItems[i] = new ResultsItem();
                    resultsItems[i].poster_path = data.getString(0);
                    data.moveToNext();
                    i++;
                }
            }
            swapListItems(resultsItems);
        }
    }
}
