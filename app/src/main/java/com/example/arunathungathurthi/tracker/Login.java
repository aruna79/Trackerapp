package com.example.arunathungathurthi.tracker;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText EditNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditNumber = (EditText) findViewById(R.id.EditNumber);
    }

    public void BuNext(View view) {
        GlobalInfo globalInfo= new GlobalInfo(this);
        GlobalInfo.PhoneNumber = GlobalInfo.FormatPhoneNumber(EditNumber.getText().toString());
        GlobalInfo.UpdatesInfo(GlobalInfo.PhoneNumber);
        finish();
        globalInfo.SaveData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




    }






