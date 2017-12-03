package com.example.anus.mydatapractical1;

        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;

    DBHelper helper = new DBHelper(this);
    SQLiteDatabase db;
    final static String TAG = "Gideon 1st App Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //following lines will check if records are present in database
        db = helper.getReadableDatabase();
        String [] colsToFetch = {DBHelper.KEY_ROWID, DBHelper.KEY_TITLE};
        cursor = db.query(DBHelper.TABLE_NAME, colsToFetch, null, null, null, null, null);

        //instantiate class ListView to get the view showing the displayed menu on
        //mainactivity layout called 'mainList'
        ListView list = (ListView)findViewById(R.id.mainList);

        //sets a listener to help with item clicks
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //start corresponding activity on click of each position item list from pos 0 to 2
                if(position == 0){
                    if(cursor.moveToFirst()) { //returns true if there's record for cursor to move to
                        Intent intent = new Intent(getApplicationContext(), WorkoutList.class);
                        startActivity(intent);
                    }
                    else{ //false when cursor tries to move to first record i.e no record
                        Toast.makeText(MainActivity.this, "NO workouts records, please insert workouts", Toast.LENGTH_LONG).show();

                    }
                }
                else if(position == 1){
                    Intent intent = new Intent(getApplicationContext(), AddWorkouts.class);
                    startActivity(intent);
                }
            }
        });

    }


    //when user leaves this activity this method runs.
    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(this, "Main activty paused", Toast.LENGTH_SHORT).show();

        cursor.close(); //terminates line 26(might change)
        db.close(); //terminates line 30(might change)
    }

    //this method runs when user gets back to this activity
    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(this, "resumes main activity", Toast.LENGTH_SHORT).show();
        String [] colsToFetch = {DBHelper.KEY_ROWID, DBHelper.KEY_TITLE};

        try {
            cursor = db.query(DBHelper.TABLE_NAME, colsToFetch, null, null, null, null, null);
        }
        catch(SQLiteException e){
            Log.d(TAG, "Cannot query database");
        }
        //String [] colsToFetch = {DBHelper.KEY_ROWID, DBHelper.TABLE_NAME};

        //db = helper.getReadableDatabase();

        //cursor = db.query(DBHelper.TABLE_NAME, colsToFetch, null, null, null, null, null);



    }
}