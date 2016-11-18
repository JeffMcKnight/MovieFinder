package com.example.jeffmcknight.moviefinder.bean;

import java.util.Arrays;

/**
 * Author: jeffmcknight
 * Created by: ModelGenerator on 11/17/16
 */
public class ResultsItem {
    public String posterPath;
    public boolean adult;
    public String overview;
    public String releaseDate;
    public Integer[] genreIds;
    public int id;
    public String originalTitle;
    public String originalLanguage;
    public String title;
    public String backdropPath;
    public double popularity;
    public int voteCount;
    public boolean video;
    public double voteAverage;

    @Override
    public String toString() {
        return "ResultsItem{" +
                "posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genreIds=" + Arrays.toString(genreIds) +
                ", id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", title='" + title + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                '}';
    }
}