package com.example.jeffmcknight.moviefinder.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Author: jeffmcknight
 * Created by: ModelGenerator on 11/17/16
 */
public class MovieBean implements Parcelable {
    public int page;
    public ResultsItem[] results;
    public int total_results;
    public int total_pages;

    protected MovieBean(Parcel in) {
        page = in.readInt();
        results = in.createTypedArray(ResultsItem.CREATOR);
        total_results = in.readInt();
        total_pages = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeTypedArray(results, flags);
        dest.writeInt(total_results);
        dest.writeInt(total_pages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieBean> CREATOR = new Creator<MovieBean>() {
        @Override
        public MovieBean createFromParcel(Parcel in) {
            return new MovieBean(in);
        }

        @Override
        public MovieBean[] newArray(int size) {
            return new MovieBean[size];
        }
    };

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