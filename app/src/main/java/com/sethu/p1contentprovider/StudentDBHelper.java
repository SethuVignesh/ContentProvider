package com.sethu.p1contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sethugayu on 10/20/16.
 */
public class StudentDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "students.db";

    public StudentDBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_STUDENT_TABLE="CREATE TABLE "+StudentContract.StudentEntry.TABLE_NAME +"("
                + StudentContract.StudentEntry._ID+" INTEGER PRIMARY KEY, "
                + StudentContract.StudentEntry.COLUMN_NAME+" TEXT NOT NULL, "
                + StudentContract.StudentEntry.COLUMN_GRADE+" TEXT NOT NULL "
                +");";

        sqLiteDatabase.execSQL(SQL_CREATE_STUDENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ StudentContract.StudentEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
