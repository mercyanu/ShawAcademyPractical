package com.example.anus.mydatapractical1;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by ANU'S on 27/08/2017.
 */

public class WorkoutList extends ListActivity {
//we will not specify a layout for this class as it extends ListActivity
//ListActivity hosts a ListView object that displays a list of items bouded to a data source be
// it array or cursor...It has a default layout which displays the list but you can specify yours
    private static final String TAG = "WorkoutList";
    private SimpleCursorAdapter cursorAdapter;
    private ListView listView;
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        listView = getListView(); //again ListActivity does not need a layout just get list view

        //to display a list in the default layout
        int layout = android.R.layout.simple_expandable_list_item_1;

        //an array of columns to fetch and an array of columns to display
        String [] colsToFetch = {DBHelper.KEY_ROWID, DBHelper.KEY_TITLE};
        String [] colsToDisplay = {DBHelper.KEY_TITLE};

        //where we want to put the info temp
        int [] to = {android.R.id.text1};

        //cursor = null;

        //expose data in the cursor to the ListView widget
        cursorAdapter = new SimpleCursorAdapter(this, layout, cursor, colsToDisplay, to, 0);

        //set the adapter to the earlier created listView
        listView.setAdapter(cursorAdapter);

        /*try {
            DBHelper helper = new DBHelper(this);
            //to read data only from DB, we use our SQLiteOpenHelper object
            db = helper.getReadableDatabase();
            //cursor to hold our query results
            cursor = db.query(DBHelper.TABLE_NAME, colsToFetch, null, null, null, null, null);
            //could= db.rawQuery("SELECT DBHelper.KEY_ROWID, DBHelper.KEY_TITLE FROM DBHelper.TABLE_NAME")
        }
        catch(SQLiteException e){
            Log.e(TAG, "Something gone wrong"); //own own error message
            e.printStackTrace(); //print out the exception gotten
        } */

    }

    @Override
    protected void onResume(){
        super.onResume();

        cursor = updateCursor();
        cursorAdapter.changeCursor(cursor);
    }

    private Cursor updateCursor() {

        cursor = null;

        try{
            DBHelper helper = new DBHelper(this);

            String [] colsToFetch = {DBHelper.KEY_ROWID, DBHelper.KEY_TITLE};

            db = helper.getReadableDatabase();

            cursor = db.query(DBHelper.TABLE_NAME, colsToFetch, null, null, null, null, null);
        }
        catch(SQLiteException e){
            e.printStackTrace();
        }
        return cursor;
    }

    //because this is a ListActivity, we need to override the onListItemClick()
    @Override
    public void onListItemClick(ListView listview, View itemView, int position, long id){

        Intent intent = new Intent(this, Workouts.class);

        intent.putExtra(Workouts.EXTRA_ID, (int) id);
        //this has not shown how the KEY_ROWID was attached to the intent sent to
        //workouts class...how will the class know ID of the item clicked???

        startActivity(intent);

    }

    @Override
    protected void onPause(){
        super.onPause();

        if(cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
    }
}

