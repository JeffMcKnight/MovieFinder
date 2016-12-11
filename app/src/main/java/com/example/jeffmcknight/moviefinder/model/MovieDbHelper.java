package com.example.jeffmcknight.moviefinder.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeffmcknight on 12/6/16.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    /**
     *
     * @param context
     */
    public MovieDbHelper(Context context) {
        super(context, DbContract.DATATBASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MovieDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL(DbContract.buildCreateTable());
    }

    /**
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
