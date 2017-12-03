package com.example.anus.mydatapractical1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ANU'S on 28/08/2017.
 */

public class Workouts extends AppCompatActivity {

    public static final String EXTRA_ID = "id";
    private static final String TAG = "Workouts";

    private SQLiteDatabase db;

    private Cursor cursor;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts);

        //save the extra data attached to the intent i.e KEY_ROWID

        final int workoutID = (Integer)getIntent().getExtras().get(EXTRA_ID);

        final DBHelper helper = new DBHelper(this);

        try{
            db = helper.getReadableDatabase();

            String [] tableCols = {DBHelper.KEY_ROWID, DBHelper.KEY_TITLE, DBHelper.KEY_SETS,
            DBHelper.KEY_REPS, DBHelper.KEY_DESC};

            String whereClause = DBHelper.KEY_ROWID+" = ";
            String [] whereArgs = {Integer.toString(workoutID)};

            cursor = db.query(DBHelper.TABLE_NAME, tableCols, whereClause, whereArgs, null, null, null);

            //check if the result isn't empty and move cursor to the first row of records
            cursor.moveToFirst();

            //create a string to store data in each of the column we are to display..zero-index
            //we skipped 0 as KEY_ROWID will not displayed on the screen
            String t = cursor.getString(1);
            String s = cursor.getString(2);
            String r = cursor.getString(3);
            String d = cursor.getString(4);

            //create textview objects to get id's of the display elements in the workouts layout
            TextView txt1 = (TextView)findViewById(R.id.title);
            TextView txt2 = (TextView)findViewById(R.id.sets);
            TextView txt3 = (TextView)findViewById(R.id.reps);
            TextView txt4 = (TextView)findViewById(R.id.desc);

            //set the text for the textview
            txt1.setText(t);
            txt2.setText(s);
            txt3.setText(r);
            txt4.setText(d);

            cursor.close();
            db.close();

        }
        catch(SQLiteException e){
            e.printStackTrace();
        }

        Button b1 = (Button)findViewById(R.id.delete);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.deleteWorkout(workoutID);
                finish();
            }
        });

    }
}
