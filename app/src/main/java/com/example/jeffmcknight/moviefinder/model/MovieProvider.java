package com.example.jeffmcknight.moviefinder.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.LoginFilter;
import android.util.Log;

import com.example.jeffmcknight.moviefinder.Dump;

/**
 * Created by jeffmcknight on 12/7/16.
 */

public class MovieProvider extends ContentProvider {
    private static final String TAG = MovieProvider.class.getSimpleName();
    private static final int URI_DISCOVERY = 100;
    private static final int URI_DETAILS = 101;
    private static final int URI_BULK_INSERT = 200;

    UriMatcher mUriMatcher;
    private SQLiteOpenHelper mOpenHelper;

    public MovieProvider() {
        mUriMatcher = buildUriMatcher();
    }

    /**
     *
     * @return
     */
    @NonNull
    private UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(
                DbContract.AUTHORITY,
                DbContract.Movie.buildDiscoveryQueryUri().getPath(),
                URI_DISCOVERY);
        uriMatcher.addURI(
                DbContract.AUTHORITY,
                DbContract.Movie.buildDetailsQueryUri().getPath(),
                URI_DETAILS);
        uriMatcher.addURI(
                DbContract.AUTHORITY,
                DbContract.Movie.buildBulkInsertUri().getPath(),
                URI_BULK_INSERT);
        return uriMatcher;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return false;
    }

    /**
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        Log.d(TAG, "query()"
                +" -- Thread.currentThread().getName(): "+Thread.currentThread().getName()
        );
        SQLiteDatabase readableDatabase = mOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        readableDatabase.beginTransaction();
        try {
            switch (mUriMatcher.match(uri)) {
                case URI_DETAILS:
                    cursor = readableDatabase.query(
                            DbContract.Movie.TABLE_NAME,
                            null,
                            DbContract.getSelection_Id(),
                            DbContract.Movie.getIdFromUri(uri),
                            null,
                            null,
                            null,
                            null);
                    readableDatabase.setTransactionSuccessful();
                    break;
                case URI_DISCOVERY:
                    cursor = readableDatabase.query(
                            DbContract.Movie.TABLE_NAME,
                        DbContract.Movie.getDiscoveryProjection(),
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                    Dump.cursor(cursor);
                    readableDatabase.setTransactionSuccessful();
                    break;
                default:
                    Log.w(TAG, "*** No match for uri: " + uri + " ***");
            }
        } finally {
            readableDatabase.endTransaction();
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType()");
        switch (mUriMatcher.match(uri)){
            case URI_DETAILS:
                return DbContract.Movie.TYPE_DETAILS;
            case URI_DISCOVERY:
                return DbContract.Movie.TYPE_DISCOVERY;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    /**
     *
     * @param uri
     * @param values
     * @return
     */
    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        Log.d(TAG, "bulkInsert"
            +" -- Thread.currentThread().getName(): "+Thread.currentThread().getName()
        );
        switch (mUriMatcher.match(uri)) {
            case URI_BULK_INSERT:
                int rowsInserted = 0;
                SQLiteDatabase database = null;
                try {
                    database = mOpenHelper.getWritableDatabase();
                    database.beginTransaction();
                    // FIXME: we should clear the table elsewhere, probably MovieDbHelper
                    database.delete(DbContract.Movie.TABLE_NAME, null, null);
                    rowsInserted = 0;
                    long rowId;
                    for (int i = 0; i < values.length; i++) {
                        rowId = database.insert(DbContract.Movie.TABLE_NAME, null, values[i]);
                        Log.v(TAG, "bulkInsert() -- rowId: " + rowId);
                        if (rowId != -1){
                            rowsInserted++;
                        }
                    }
                    database.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return rowsInserted;
            default:
                Log.w(TAG, "*** No match for uri: " + uri + " ***");
                return 0;
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        Log.d(TAG, "delete()"
                + "\t -- uri: "+uri
                + "\t -- s: "+s
        );
        return 0;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        Log.d(TAG, "update"
                + "\t -- uri: "+uri
                + "\t -- contentValues: "+contentValues
        );
        return 0;
    }

}
