package com.sethu.p1contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by sethugayu on 10/20/16.
 */
public class StudentProvider extends ContentProvider {

    static final int STUDENT=1;
    static final int STUDENT_ID=2;

    private  StudentDBHelper mDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher= new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = StudentContract.CONTENT_AUTHOURITY;
        matcher.addURI(authority,StudentContract.PATH_STUDENT,STUDENT);
        matcher.addURI(authority,StudentContract.PATH_STUDENT+"/#",STUDENT_ID);
        return  matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper= new StudentDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor retCursor;
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(StudentContract.StudentEntry.TABLE_NAME);


        switch (sUriMatcher.match(uri)){
            case STUDENT:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;

            case STUDENT_ID:
                qb.appendWhere( StudentContract.StudentEntry._ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);

        }
        if (s1 == null || s1 == ""){
            /**
             * By default sort on student names
             */
            s1 = StudentContract.StudentEntry.COLUMN_NAME;
        }
         retCursor = qb.query(db,	strings,	s, strings1,null, null, s1);

        /**
         * register to watch a content URI for changes
         */
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;


    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case STUDENT:
                return StudentContract.StudentEntry.CONTENT_TYPE;
            case STUDENT_ID:
                return StudentContract.StudentEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long rowID = db.insert(StudentContract.StudentEntry.TABLE_NAME, "", contentValues);

        /**
         * If record is added successfully
         */
        Uri returnUri;
        if (rowID > 0)
        {
            returnUri = StudentContract.StudentEntry.buildWeatherUri(rowID);
        }else{
            throw new SQLException("Failed to add a record into " + uri);
            }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match){
            case STUDENT:
                rowsDeleted = db.delete(StudentContract.StudentEntry.TABLE_NAME, s, strings);
                break;
            case STUDENT_ID:
                String id = uri.getPathSegments().get(1);
                rowsDeleted=db.delete(StudentContract.StudentEntry.TABLE_NAME, StudentContract.StudentEntry._ID +  " = " + id +
                        (!TextUtils.isEmpty(s) ? " AND (" + s + ')' : ""), strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated=0;

        switch (match){
            case STUDENT:
                rowsUpdated = db.update(StudentContract.StudentEntry.TABLE_NAME,contentValues, s, strings);
                break;
            case STUDENT_ID:
                String id = uri.getPathSegments().get(1);
                rowsUpdated=db.update(StudentContract.StudentEntry.TABLE_NAME,contentValues, StudentContract.StudentEntry._ID +  " = " + id +
                        (!TextUtils.isEmpty(s) ? " AND (" + s + ')' : ""), strings);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
