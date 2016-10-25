package com.sethu.p1contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickAddName(View view) {
        // Add a new student record
        ContentValues values = new ContentValues();

        values.put(StudentContract.StudentEntry.COLUMN_NAME,
                ("Sethu"));

        values.put(StudentContract.StudentEntry.COLUMN_GRADE,
                ("Vignesh"));

        Uri uri = getContentResolver().insert(
                StudentContract.StudentEntry.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
    public void onClickRetrieveStudents(View view) {


        Uri students= StudentContract.StudentEntry.CONTENT_URI;
        Cursor c = managedQuery(students, null, null, null, StudentContract.StudentEntry.COLUMN_NAME);

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(StudentContract.StudentEntry._ID)) +
                                ", " +  c.getString(c.getColumnIndex( StudentContract.StudentEntry.COLUMN_NAME)) +
                                ", " + c.getString(c.getColumnIndex( StudentContract.StudentEntry.COLUMN_GRADE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}
