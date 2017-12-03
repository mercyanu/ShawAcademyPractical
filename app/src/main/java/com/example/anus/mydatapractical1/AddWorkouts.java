package com.example.anus.mydatapractical1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ANU'S on 27/08/2017.
 */

public class AddWorkouts extends AppCompatActivity{


    private String title, sets, reps, desc;
    EditText t, d, s, r;

    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.add_workouts);

        //find all the EditText input field in the layout add_Workouts
        t = (EditText)findViewById(R.id.inputTitle);
        s = (EditText)findViewById(R.id.inputSets);
        r = (EditText)findViewById(R.id.inputReps);
        d = (EditText)findViewById(R.id.inputDesc);
        //we will not grab what is typed on this field just yet because this is our onCreate method
        //and only empty field will be gotten and given to DB, so we rather create a diff method to
        //grab the input text in the field

        Button b1 = (Button)findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToDB();
            }
        });

    }

    private void insertToDB() {
        //onCreate method run and user has input in the field, now we can get the input
        title = t.getText().toString();
        sets = s.getText().toString();
        reps = r.getText().toString();
        desc = d.getText().toString();

        if (title != "" && sets != "" && reps != "" && desc != "") {
            //instantiate our DBHelper class to help us create/open/query SQLite database
            DBHelper helper = new DBHelper(this);

            helper.insertValues(title, sets, reps, desc);

            this.finish();
        }
        else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
    }

}
