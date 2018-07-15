package com.example.arunathungathurthi.tracker;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        Bundle b=getIntent().getExtras();
//        databaseReference= FirebaseDatabase.getInstance().getReference();
//        LoadLocation(b.getString("PhoneNumber"));
//
//    }
//
//    void  LoadLocation(String PhoneNumber){
//
//        databaseReference.child("Users").child(PhoneNumber).
//                child("Location").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
//                if (td==null)return;
//                double lat = Double.parseDouble(td.get("lat").toString());
//                double lag = Double.parseDouble(td.get("lag").toString());
//                /** Make sure that the map has been initialised **/
//                sydney = new LatLng(lat, lag);
//                LastDateOnline= td.get("LastOnlineDate").toString();
//                LoadMap();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                // Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//    }
//
//
//    void LoadMap(){
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    LatLng sydney ;
//    String LastDateOnline;
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//
//        mMap.addMarker(new MarkerOptions().position(sydney).title("last online:"+ LastDateOnline));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
//    }
//
//
//
//
//
//}
//
//
//
//

//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private LocationManager locationManager;
//    private LocationListener locationListener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//
//
//    }
//
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                Log.d("Location: ", location.toString());
//                mMap.clear();
//
//                LatLng newLocation = new LatLng(location.getLatitude(),location.getLongitude());
//                //LatLng sydney = new LatLng(-34, 151);
//                mMap.addMarker(new MarkerOptions().position(newLocation).title("Marker in newLocation"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
//
//
//                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//
//                try {
//                    List<Address> addresslist = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                    if(addresslist!= null && addresslist.size() > 0){
//                        Log.d("Address: ", addresslist.get(0).toString());
//                        Toast.makeText(MapsActivity.this,addresslist.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
//
//                    }
//                    else{
//                        Log.d("Address:", "Cpuldn't find the address");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//            }
//        };
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//
//        }
//        else {
//            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
//        }
//
//
//
//
//        // Add a marker in Sydney and move the camera
//
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//            {
//                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//            }
//        }
//
//    }
//}