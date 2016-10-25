package com.sethu.p1contentprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sethugayu on 10/20/16.
 */
public class StudentContract {
    public static  final String CONTENT_AUTHOURITY="com.sethu.p1contentprovider";// CONTENT PROVIDER NAME
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+CONTENT_AUTHOURITY); // URL
    public static final String PATH_STUDENT="student";

    public static class StudentEntry implements BaseColumns {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHOURITY + "/" + PATH_STUDENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHOURITY + "/" + PATH_STUDENT;


        public static  final String TABLE_NAME="students";
        public static  final String COLUMN_NAME="name";
        public  static  final String COLUMN_GRADE="grade";

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }
}
