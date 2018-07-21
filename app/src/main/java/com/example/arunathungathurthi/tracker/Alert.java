package com.example.arunathungathurthi.tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Alert extends AppCompatActivity {

public static EditText Lat,Lon;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
       Lat  = (EditText) findViewById(R.id.Lat);
       Lon = (EditText) findViewById(R.id.Lon);

    }

    public void SetAlert(View view) {

     DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
      Database.child("Users").child(GlobalInfo.PhoneNumber).child("Finders").child("Alert").child("Slat").setValue(Lat.getText().toString());
       Database.child("Users").child(GlobalInfo.PhoneNumber).child("Finders").child("Alert").child("Slon").setValue(Lon.getText().toString());
        GlobalInfo globalInfo = new GlobalInfo(this);

        finish();

        globalInfo.SaveData();
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);



    }


}
