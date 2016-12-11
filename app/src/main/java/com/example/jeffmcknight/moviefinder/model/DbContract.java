package com.example.jeffmcknight.moviefinder.model;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jeffmcknight.moviefinder.DiscoverFragment;

/**
 * Created by jeffmcknight on 12/5/16.
 */

public class DbContract {
    private static final String TAG = DbContract.class.getSimpleName();
    public static final String DATATBASE_NAME = "movie.db";
    private static final String SCHEME_CONTENT = "content";
    public static final String AUTHORITY = "com.example.jeffmcknight.moviefinder";
    public static final String SORT_ORDER_ASCENDING = "ASC";
    public static final String DECENDING = "DESC";
    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS";
    private static final String WHITE_SPACE = " ";
    private static final String START_DEFINE_COLUMNS = " (";
    private static final String END_DEFINE_COLUMNS = ") ";
    private static final String COLUMN_SEPARATOR = ", ";
    public static final String TYPE_STRING = "STRING";

    /**
     *
     * @return
     */
    public static final Uri buildBaseUrl(){
        return new Uri.Builder()
                .scheme(SCHEME_CONTENT)
                .authority(AUTHORITY)
                .build();
    }

    public static String buildCreateTable() {
        return new StringBuilder()
                .append(CREATE_TABLE_IF_NOT_EXISTS)
                .append(WHITE_SPACE).append(Movie.TABLE_NAME)
                .append(START_DEFINE_COLUMNS)
                .append(Movie._ID)
                .append(COLUMN_SEPARATOR).append(Movie.COLUMN_OVERVIEW).append(WHITE_SPACE).append(TYPE_STRING)
                .append(COLUMN_SEPARATOR).append(Movie.COLUMN_POSTER_PATH).append(WHITE_SPACE).append(TYPE_STRING)
                .append(COLUMN_SEPARATOR).append(Movie.COLUMN_RELEASE_DATE).append(WHITE_SPACE).append(TYPE_STRING)
                .append(COLUMN_SEPARATOR).append(Movie.COLUMN_TITLE).append(WHITE_SPACE).append(TYPE_STRING)
                .append(COLUMN_SEPARATOR).append(Movie.COLUMN_VOTE_AVERAGE).append(WHITE_SPACE).append(TYPE_STRING)
                .append(END_DEFINE_COLUMNS)
                .toString();
    }

    @NonNull
    public static String getSelection_Id() {
        return Movie._ID + " = ?";
    }

    public static String buildDropTableStatement() {
        return new StringBuilder()
                .append(DROP_TABLE_IF_EXISTS)
                .append(WHITE_SPACE).append(Movie.TABLE_NAME)
                .toString();
    }

    /**
     *
     */
    public static class Movie implements BaseColumns{
        public static final String TABLE_NAME = "movie";
        public static final String PATH = "movie";
        public static final String PATH_DETAILS = "details";
        public static final String PATH_DISCOVER = "discover";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String TYPE_DETAILS = "movie_details"; // TODO: what should it really be?
        public static final String TYPE_DISCOVERY = "movie_discovery"; // TODO: what should it really be?
        private static String[] sColumns;

        /**
         *
         * @return "content://com.example.jeffmcknight.moviefinder.model/movie"
         */
        public static final Uri buildBulkInsertUri(){
            return buildBaseUrl().buildUpon().appendPath(PATH).build();
        }

        /**
         *
         * @return "content://com.example.jeffmcknight.moviefinder.model/movie/discover"
         */
        public static final Uri buildDiscoveryQueryUri(){
            return buildBaseUrl().buildUpon().appendPath(PATH_DISCOVER).build();
        }

        /**
         *
         * @return "content://com.example.jeffmcknight.moviefinder.model/movie/details"
         */
        public static final Uri buildDetailsQueryUri(){
            return buildBaseUrl().buildUpon().appendPath(PATH_DETAILS).build();
        }

        public static ContentValues buildContentValues(String title,
                                                       String overview,
                                                       String posterPath,
                                                       String releaseDate,
                                                       String voteAverage){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_POSTER_PATH, posterPath);
            contentValues.put(COLUMN_OVERVIEW, overview);
            contentValues.put(COLUMN_TITLE, title);
            contentValues.put(COLUMN_VOTE_AVERAGE, voteAverage);
            contentValues.put(COLUMN_RELEASE_DATE, releaseDate);
            return contentValues;
        }


        public static String[] getIdFromUri(Uri uri) {
            return new String[]{uri.getLastPathSegment()};
        }

        /**
         * Returns the columns used by {@link DiscoverFragment},
         * which is just the path to the movie poster.
         * @return
         */
        public static String[] getDiscoveryProjection() {
            return new String[]{COLUMN_POSTER_PATH};
        }
    }
}
