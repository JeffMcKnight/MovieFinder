package com.example.jeffmcknight.moviefinder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jeffmcknight on 11/22/16.
 */
public class DetailsFragment extends Fragment{

    /**
     *
     * @param activity
     */
    public static void launch(AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.details_fragment, new DetailsFragment())
                .commit();

    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_details, container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
