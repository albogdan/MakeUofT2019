package com.siddharth_vijay.fallnot;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String First;
    public String Last;
    int Id;
    double Temp;
    double Lat;
    double Lon;
    double AccelX;
    double AccelY;
    double AccelZ;
    int Touch;
    int Help;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String first, String last, int id, double temp, double lat, double lon, double accelX, double accelY, double accelZ, int touch, int help) {
        this.First = first;
        this.Last = last;
        this.Id = id;
        this.Lat = lat;
        this.Lon = lon;
        this.AccelX = accelX;
        this.AccelY = accelY;
        this.AccelZ = accelZ;
        this.Temp = temp;
        this.Touch = touch;
        this.Help = help;

    }

}


