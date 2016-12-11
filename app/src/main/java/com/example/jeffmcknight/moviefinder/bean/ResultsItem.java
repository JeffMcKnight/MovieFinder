package com.example.jeffmcknight.moviefinder.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Author: jeffmcknight
 * Created by: ModelGenerator on 11/17/16
 */
public class ResultsItem implements Parcelable{
    public String poster_path;
    public boolean adult;
    public String overview;
    public String release_date;
    // FIXME: try to parcelize this field
    public Integer[] genre_ids;
    public int id;
    public String original_title;
    public String original_language;
    public String title;
    public String backdrop_path;
    public double popularity;
    public int vote_count;
    public boolean video;
    public double vote_average;

    public ResultsItem(Parcel in) {
        poster_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        release_date = in.readString();
        id = in.readInt();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        vote_count = in.readInt();
        video = in.readByte() != 0;
        vote_average = in.readDouble();
    }

    public ResultsItem() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeInt(id);
        dest.writeString(original_title);
        dest.writeString(original_language);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeDouble(popularity);
        dest.writeInt(vote_count);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(vote_average);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultsItem> CREATOR = new Creator<ResultsItem>() {
        @Override
        public ResultsItem createFromParcel(Parcel in) {
            return new ResultsItem(in);
        }

        @Override
        public ResultsItem[] newArray(int size) {
            return new ResultsItem[size];
        }
    };

    @Override
    public String toString() {
        return "ResultsItem{" +
                "poster_path='" + poster_path + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", genre_ids=" + Arrays.toString(genre_ids) +
                ", id=" + id +
                ", original_title='" + original_title + '\'' +
                ", original_language='" + original_language + '\'' +
                ", title='" + title + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", popularity=" + popularity +
                ", vote_count=" + vote_count +
                ", video=" + video +
                ", vote_average=" + vote_average +
                '}';
    }
}