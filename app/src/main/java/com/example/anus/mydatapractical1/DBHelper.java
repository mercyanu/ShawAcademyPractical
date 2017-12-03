package com.example.anus.mydatapractical1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ANU'S on 27/08/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    static final String TABLE_NAME = "WorkoutTable";
    static final String KEY_ROWID = "_id";
    static final String KEY_TITLE = "Title";
    static final String KEY_REPS = "Reps";
    static final String KEY_SETS = "Sets";
    static final String KEY_DESC =  "Description";

    private String TAG = "DBHelper";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "WorkoutDatabase";
    private static SQLiteDatabase DB;

    private static String CREATE_TABLE = "CREATE TABLE " +TABLE_NAME+" ("+KEY_ROWID+" interger PRIIMARY KEY, "
            +KEY_TITLE+", "+KEY_SETS+", "+KEY_REPS+", "+KEY_DESC+");";

    DBHelper (Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "Table created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }

    //access to this method is made package-private so we removed public modifier
    void insertValues(String title, String sets, String reps, String desc){
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, title);
        values.put(KEY_SETS, sets);
        values.put(KEY_REPS, reps);
        values.put(KEY_DESC, desc);

        //the method is called on declaration of this SQLiteOpenHelper
        DB = this.getWritableDatabase();

        //this line runs the insert SQL query and specify we do not want a null column
        DB.insert(TABLE_NAME, null, values);

        //pop message on user screen when this method is called
        Log.d(TAG, "Record inserted successfully");
    }

    public void deleteWorkout(int deleteID){
        String rowID = Integer.toString(deleteID);

        DB = this.getWritableDatabase();
        DB.delete(TABLE_NAME, KEY_ROWID+"="+rowID, null);

        Log.d(TAG, "Record deleted successfully");

    }

}
