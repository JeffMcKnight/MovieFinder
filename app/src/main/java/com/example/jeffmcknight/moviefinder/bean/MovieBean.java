package com.example.jeffmcknight.moviefinder.bean;

import android.os.Parcelable;

import java.util.Arrays;

/**
 * Author: jeffmcknight
 * Created by: ModelGenerator on 11/17/16
 */
public class MovieBean {
    public int page;
    public ResultsItem[] results;
    public int total_results;
    public int total_pages;

    @Override
    public String toString() {
        return "MovieBean{" +
                "page=" + page +
                ", results=" + Arrays.toString(results) +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                '}';
    }
}