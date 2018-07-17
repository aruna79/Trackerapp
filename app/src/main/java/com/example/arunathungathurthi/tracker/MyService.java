package com.example.arunathungathurthi.tracker;


import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyService extends IntentService{
    public static boolean IsRunning=false;
    DatabaseReference databaseReference;
    public MyService(){
        super("MyService");
        IsRunning=true;
        databaseReference=FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Updates").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Location location=TrackerLocation.location;
            databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Location").child("lat").setValue((TrackerLocation.location.getLatitude()));

            databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Location").child("lon").setValue((TrackerLocation.location.getLongitude()));

            DateFormat df = new SimpleDateFormat("YYYY/MM/DD HH:MM:SS");
            Date date=new Date();
            databaseReference.child("Users").child(GlobalInfo.PhoneNumber).child("Location").child("lastOnlineDate").setValue((df.format(date).toString()));

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }
}



