package com.nhoserviceboy.carwash.Admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.GeoApiContext;
import com.nhoserviceboy.carwash.Admin.Model.Latlong;
import com.nhoserviceboy.carwash.R;

import com.nhoserviceboy.carwash.Utils.GPSTracker;

import java.util.concurrent.TimeUnit;


public class MapuserActivity extends FragmentActivity implements OnMapReadyCallback {
    GPSTracker gps;
    private GoogleMap mMap;
    Context context;

    Polyline map_line;
    private String TAG = "so47492459";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = MapuserActivity.this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent i = getIntent();
        String trackerkey = i.getStringExtra("usertrckingkey");
        Log.d("key","ajfsjhsd"+trackerkey);
        mMap = googleMap;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Location");
           myRef.child(trackerkey).addValueEventListener(new ValueEventListener()
           {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                try
                {
                    Latlong l = dataSnapshot.getValue(Latlong.class);
                  //  DateTime dateTime= new DateTime();

                    LatLng currentlocation = new LatLng(Double.parseDouble(l.getLatitude()),Double.parseDouble(l.getLongitude()) );
                    LatLng destination = new LatLng(Double.parseDouble(l.getUlatitude()), Double.parseDouble(l.getUlongitude()));

                    String uri = "http://maps.google.com/maps?saddr=" +Double.parseDouble(l.getLatitude()) + "," +Double.parseDouble(l.getLongitude()) + "&daddr=" + Double.parseDouble(l.getUlatitude())+ "," +  Double.parseDouble(l.getUlongitude());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);



              /*     mMap.addMarker(new MarkerOptions().position(currentlocation).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.motorcycle1)).title("Service Boy :"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation, 20));

                    mMap.addMarker(new MarkerOptions().position(destination).title("  USER "));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 20));


                    mMap.addMarker(new MarkerOptions().position(currentlocation).icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.motorcycle1)).title("Service Boy :"));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));

                    mMap.addMarker(new MarkerOptions().position(destination).icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.motorcycle1)).title("User :"));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
*/


                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }



            }


            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        GoogleMap mMap = googleMap;
        //19.266413075452437, 72.43788897585725
        //26.89302976887856, 80.931509760953
        //String origin = "Avinguda Diagonal, 101, 08005 Barcelona, Spain";
        // String destination = "Carrer de París, 67, 08029 Barcelona, Spain";
    }
}



























