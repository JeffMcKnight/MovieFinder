package com.example.jeffmcknight.moviefinder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoverActivityFragment extends Fragment {

    public static final int SPAN_COUNT = 2;
    public static final int VIEW_TYPE = 0;
    public static final String TAG = DiscoverActivityFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;

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
    public void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
        getPopularMovies();
    }

    void getPopularMovies(){
        Callback<MovieBean> callback = new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                Log.i(TAG, "onResponse()"
                        +"\n -- response.body(): " + response.body());
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

    /**
     *
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
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
                    .centerCrop()
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
    }
}
