package com.example.arunathungathurthi.tracker;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<AdapterItems>    listnewsData = new ArrayList<AdapterItems>();
    MyCustomAdapter myadapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalInfo globalInfo = new GlobalInfo(this);
        globalInfo.LoadData();
        databaseReference=FirebaseDatabase.getInstance().getReference();

        CheckUserPermission();

        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView)findViewById(R.id.listView);
        lsNews.setAdapter(myadapter);
        Refresh();
        lsNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AdapterItems adapterItems=listnewsData.get(position);
                GlobalInfo.UpdatesInfo(adapterItems.PhoneNumber);
                Intent intent= new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("PhoneNumber",adapterItems.PhoneNumber);
                startActivity(intent);
            }
        });

    }

    @Override
    public  void onResume(){
        super.onResume();
        Refresh();
    }


    void Refresh() {
        listnewsData.clear();
        databaseReference.child("Users").child(GlobalInfo.PhoneNumber).
                child("Finders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                listnewsData.clear();
                if (td == null)  //no one allow you to find him
                {
                    listnewsData.add(new AdapterItems("NoTicket", "no_desc"));
                    myadapter.notifyDataSetChanged();
                    return;
                }
//                 List<Object> values = td.values();


                // get all contact to list
                ArrayList<AdapterItems> list_contact = new ArrayList<AdapterItems>();
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    list_contact.add(new AdapterItems(name, GlobalInfo.FormatPhoneNumber(phoneNumber)
                    ));


                }


// if the name is save chane his text
                // case who find me
                String tinfo;
                for (String Numbers : td.keySet()) {
                    for (AdapterItems cs : list_contact) {

                        //IsFound = SettingSaved.WhoIFindIN.get(cs.Detals);  // for case who i could find list
                        if (cs.PhoneNumber.length() > 0) {
                            if (Numbers.contains(cs.PhoneNumber)) {
                                listnewsData.add(new AdapterItems(cs.UserName, cs.PhoneNumber));
                                if (GlobalInfo.hasAlertLatLng()) {
                                    this.alertListener(cs.PhoneNumber);
                                }
                                break;

                            }

                        }






                    }
                    myadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }

            private void alertListener(String phoneNumber) {
                databaseReference.child("Users").child(phoneNumber).
                        child("Location").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        double lat = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                        double lon = Double.parseDouble(dataSnapshot.child("lon").getValue().toString());

                        //Display the alert message
                        //loop through the global data
                        //compare the coordinates
                        //Alert message
                        if ((lat <= GlobalInfo.NLat) && (lat >= GlobalInfo.SLat) &&
                            (lon >= GlobalInfo.WLon) && (lon <= GlobalInfo.ELon)) {
                            Toast.makeText(getApplicationContext(),"Destination Reached",Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {

                    }
                });
            }
        });
        myadapter.notifyDataSetChanged();
    }
    void CheckUserPermission(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_CONTACTS


                        },
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }

        }
        StartServices();// init the contact list

    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    StartServices();// init the contact list
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Permission Denied" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    void StartServices() {
        if (!TrackerLocation.isRunning) {
            TrackerLocation trackLocation = new TrackerLocation();
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, trackLocation);

        }

        if (!MyService.IsRunning) {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);

        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.addtracker:
                    Intent intent =new Intent(this,MyTrackers.class);
                    startActivity(intent);

                    return true;
                case R.id.help:
                    CheckUserPermission();
                    return true;
                case R.id.action_logout:
                    logout();
                    return true;
                case R.id.addalert:
                    Intent intent1 =new Intent(this,Alert.class);
                    startActivity(intent1);


                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    public void logout() {
        GlobalInfo globalInfo = new GlobalInfo(this);

        GlobalInfo.PhoneNumber=null;
        finish();
        globalInfo.ClearData();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }



    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
          LayoutInflater mInflater = getLayoutInflater();

            final AdapterItems s = listnewsDataAdpater.get(position);
            if (s.UserName.equals("NoTicket")) {
                View myView = mInflater.inflate(R.layout.single_row_conact, null);
                TextView tv_user_name = (TextView) myView.findViewById(R.id.tv_user_name);
                tv_user_name.setText(s.UserName);
                TextView tv_phone = (TextView) myView.findViewById(R.id.tv_phone);
                tv_phone.setText(s.PhoneNumber);

                return myView;
            }
            else {
                View myView = mInflater.inflate(R.layout.single_row_conact, null);

                TextView tv_user_name = (TextView) myView.findViewById(R.id.tv_user_name);
                tv_user_name.setText(s.UserName);
                TextView tv_phone = (TextView) myView.findViewById(R.id.tv_phone);
                tv_phone.setText(s.PhoneNumber);

                return myView;
            }
        }

    }

}



