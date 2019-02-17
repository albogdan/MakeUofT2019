package com.siddharth_vijay.fallnot;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Latlon {

    public int lat;
    public int lon;

    public Latlon() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Latlon(int lat, int lon) {
        this.lat = lat;
        this.lon = lon;
    }

}