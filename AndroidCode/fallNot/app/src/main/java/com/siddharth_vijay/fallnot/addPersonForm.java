package com.siddharth_vijay.fallnot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class addPersonForm extends AppCompatActivity {
    private DatabaseReference mDatabase;
    //ListView listview;

        // Array of strings...
        ListView simpleList;
        ArrayList<String> userList;

        //References to Text Views and Buttons
        Button addButton;
        Button backButton;

        EditText firstNameInput;
        EditText lastNameInput;
        EditText studentIDInput;

        @Override   protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_person_form);

            //button and edit text references
            addButton = (Button) findViewById(R.id.confirmButton);
            backButton = (Button)findViewById(R.id.backButton);

            firstNameInput = (EditText) findViewById(R.id.firstNameText);
            lastNameInput = (EditText) findViewById(R.id.lastNameText);
            studentIDInput = (EditText) findViewById(R.id.studentIDText);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            //DatabaseReference users = mDatabase.child("users");

            mDatabase.addValueEventListener(addListener);

            userList = new ArrayList<String>();
            userList.add("Supervisor: Siddharth Vijay");

            simpleList = (ListView)findViewById(R.id.simpleListView);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView, userList);
            simpleList.setAdapter(arrayAdapter);

            writeNewUser("Supervisor", "Siddharth Vijay", "000");

            backButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent i = new Intent(addPersonForm.this, com.siddharth_vijay.fallnot.MainActivity.class);
                    startActivity(i);
                }
            });

            addButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //create new user and write to the database
                    writeNewUser(firstNameInput.getText().toString(), lastNameInput.getText().toString(), studentIDInput.getText().toString());
                    String output = studentIDInput.getText().toString() + ": " + firstNameInput.getText().toString() + " " + lastNameInput.getText().toString();
                    userList.add(output);
                }
            });


        }

    private void writeNewUser( String first, String last, String userId) {
            // String temp, String lat, double lon, double accelX,
        //  double accelY, double accelZ, int touch
        double accelY = 0;
        double accelX = 0;
        double accelZ = 0;
        int touch = 0;
        double temp = 0;
        int help = 0;

        //generate lat lon near toronto
        double lat = 35 + (100*Math.random())%15;
        double lon = -90 + (100*Math.random())%15;
        User user = new User(first, last, Integer.parseInt(userId), temp, lat, lon, accelX, accelY, accelZ, touch, help);
        mDatabase.child(userId).setValue(user);
    }


    //Use this function to update the list interface
    ValueEventListener addListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            //if (dataSnapshot.exists()) {
                userList.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {

                    //iterate through database and store in list to display

                    User userRead = users.getValue(User.class);

                    String firstName = userRead.First;
                    String lastName = userRead.Last;
                    int id = userRead.Id;

                    String output = id + ": " + firstName + " " + lastName;

                    userList.add(output);
                    //outText.setText(userEmail);
               // }
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };



}
