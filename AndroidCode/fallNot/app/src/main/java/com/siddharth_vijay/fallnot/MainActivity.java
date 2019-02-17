package com.siddharth_vijay.fallnot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;
//import com.google.firebase.database.IgnoreExtraProperties;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView outText;
    private Button mapsButton;
    private Button listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outText = (TextView) findViewById(R.id.outText);
        mapsButton = (Button) findViewById(R.id.maps);
        listButton = (Button) findViewById(R.id.list);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference users = mDatabase.child("users");

        //writeNewUser("1234", "Sidd", "siddharth.vijay333@gmail.com");

        mDatabase.addValueEventListener(UserListener);


        mapsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, com.siddharth_vijay.fallnot.MapsActivity.class);
                startActivity(i);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MainActivity.this, com.siddharth_vijay.fallnot.addPersonForm.class);
                startActivity(i);
            }
        });


    }

   /* private void writeNewUser(String userId, String first, String last, ) {
        User user = new User(first, last, Integer.parseInt(userId));
        mDatabase.child("users").child(userId).setValue(user);
    }*/


    ValueEventListener UserListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI

           for (DataSnapshot users: dataSnapshot.getChildren()) {

                //Log.i(TAG, zoneSnapshot.child("ZNAME").getValue(String.class);
                User userRead = users.getValue(User.class);

                String firstName = userRead.First;
                //outText.setText(userEmail);

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


