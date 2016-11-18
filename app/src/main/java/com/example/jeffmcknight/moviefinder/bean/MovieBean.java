package com.example.jeffmcknight.moviefinder.bean;

import java.util.Arrays;

/**
 * Author: jeffmcknight
 * Created by: ModelGenerator on 11/17/16
 */
public class MovieBean {
    public int page;
    public ResultsItem[] results;
    public int totalResults;
    public int totalPages;

    @Override
    public String toString() {
        return "MovieBean{" +
                "page=" + page +
                ", results=" + Arrays.toString(results) +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                '}';
    }
}