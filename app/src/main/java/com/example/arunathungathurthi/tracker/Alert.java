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

public EditText NLat,WLon,SLat,ELon;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        this.NLat  = (EditText) findViewById(R.id.NLat);
        this.WLon = (EditText) findViewById(R.id.WLon);
        this.SLat  = (EditText) findViewById(R.id.SLat);
        this.ELon  = (EditText) findViewById(R.id.ELon);

    }

    public void SetAlert(View view) {

        double NLat = Double.parseDouble(this.NLat.getText().toString());
        double SLat = Double.parseDouble(this.SLat.getText().toString());
        double WLon = Double.parseDouble(this.WLon.getText().toString());
        double ELon = Double.parseDouble(this.ELon.getText().toString());



        DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
      Database.child("Users").child(GlobalInfo.PhoneNumber).child("Alert").child("NLat").setValue(NLat);
       Database.child("Users").child(GlobalInfo.PhoneNumber).child("Alert").child("SLat").setValue(SLat);
        Database.child("Users").child(GlobalInfo.PhoneNumber).child("Alert").child("WLon").setValue(WLon);
        Database.child("Users").child(GlobalInfo.PhoneNumber).child("Alert").child("ELon").setValue(ELon);


        GlobalInfo globalInfo = new GlobalInfo(this);
        GlobalInfo.NLat =NLat;
        GlobalInfo.SLat = SLat;
        GlobalInfo.WLon = WLon;
        GlobalInfo.ELon= ELon;

        finish();

        globalInfo.SaveData();
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);



    }


}
