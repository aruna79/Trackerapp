package com.example.arunathungathurthi.tracker;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class TrackerLocation implements LocationListener{
    public static Location location;
    public static boolean isRunning=false;
    public TrackerLocation(){
        isRunning=true;
        location= new Location("not defined");
        location.setLatitude(0);
        location.setLongitude(0);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
