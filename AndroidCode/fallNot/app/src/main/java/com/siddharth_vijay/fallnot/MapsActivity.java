package com.siddharth_vijay.fallnot;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.helpsound);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference points = mDatabase.child("users");
        mDatabase.addValueEventListener(LatLonListener);


    }

    private void writeNewLatlon(String pointRead, int lat, int lon) {
        //Latlon point = new Latlon(lat, lon);
        //mDatabase.child("Users").child(pointRead).setValue(point);
    }

    ValueEventListener LatLonListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            mMap.clear();

            for (DataSnapshot points: dataSnapshot.getChildren()) {

                //Log.i(TAG, zoneSnapshot.child("ZNAME").getValue(String.class);
               // DatabaseReference pointLat = mDatabase.child("Latlon").child("Lat");

               // String LAT = pointLat.


                User userRead = points.getValue(User.class);


                double lat = userRead.Lat;
                double lon = userRead.Lon;
                int touch = userRead.Touch;

                //if(touch == 1)
                //int latInt = Integer.parseInt(lat);
                //int lonInt = Integer.parseInt(lon);
                //System.out.println(lon);

                LatLng point = new LatLng((int)lat, (int)lon);

            if(touch == 1){
                mp.start();
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng((int)lat, (int)lon))
                        .title("This is my title")
                        .snippet("and snippet")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }

                else{
                    
                    mMap.addMarker(new MarkerOptions().position(point).title(userRead.First));}



                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(43.6599082, -79.3967416);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
