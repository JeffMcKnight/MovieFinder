package com.example.jeffmcknight.moviefinder;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

/**
 * Created by jeffmcknight on 12/10/16.
 */

public class Dump {

    private static final String TAG = Dump.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void cursor(Cursor cursor) {
        boolean moveSucceeded = false;
        int columnCount = cursor.getColumnCount();
        int rowCount = cursor.getCount();
        Log.i(TAG, "cursor()"
                +"\t -- columnCount: "+columnCount
                +"\t -- rowCount: "+rowCount
        );
        if (cursor.isBeforeFirst() || cursor.isAfterLast()){
            cursor.moveToFirst();
        }
        StringBuilder rowAsString = new StringBuilder("cursor():");
        for (int j=0; j<rowCount; j++) {
            rowAsString.append("\n");
            for (int i = 0; i < columnCount; i++) {
                rowAsString.append("\t - ")
                        .append(cursor.getColumnName(i))
                        .append(": ")
                ;
                switch (cursor.getType(i)){
                    case Cursor.FIELD_TYPE_BLOB:
                        rowAsString.append("BLOB");
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        rowAsString.append(cursor.getFloat(i));
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        rowAsString.append(cursor.getInt(i));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        rowAsString.append(cursor.getString(i));
                        break;
                    case Cursor.FIELD_TYPE_NULL:
                        rowAsString.append("NULL");
                        break;
                    default:
                        break;
                }
                moveSucceeded = cursor.moveToNext();
                if (!moveSucceeded){
                    cursor.moveToFirst();
                }
            }
        }
        Log.d(TAG, rowAsString.toString());
    }

}
