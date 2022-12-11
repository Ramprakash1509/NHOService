package com.nhoserviceboy.carwash.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

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
import com.nhoserviceboy.carwash.Admin.Model.Latlong;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.GPSTracker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    GPSTracker gps;
    private GoogleMap mMap;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context=MapsActivity.this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        //double destinationLatitude =26.449923;
        //double destinationLongitude =80.331871;*/
        // LatLng currentlocation = new LatLng( 26.850000, 80.949997);
          Intent i = getIntent();
        String trackerkey = i.getStringExtra("tracker_key");
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

                   /* LatLng currentlocation = new LatLng(Double.parseDouble(l.getLatitude()),Double.parseDouble(l.getLongitude()) );
                    LatLng destination = new LatLng(Double.parseDouble(l.getUlatitude()), Double.parseDouble(l.getUlongitude()));
                    Log.d("","userlatlong "+l.getUlatitude()+"hello"+l.getUlongitude());
                    Log.d("","uyuy"+l.getLongitude()+"hello"+l.getLatitude());

                    mMap.addMarker(new MarkerOptions().position(currentlocation).title("I am here :"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation, 20));

                    mMap.addMarker(new MarkerOptions().position(destination).title("Destination Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 20));
                  */

                    String uri = "http://maps.google.com/maps?saddr=" + Double.parseDouble(l.getLatitude()) + "," + Double.parseDouble(l.getLongitude()) + "&daddr=" + Double.parseDouble(l.getUlatitude()) + "," +  Double.parseDouble(l.getUlongitude());
                    Log.e("mapsurl","url : "+  uri);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);


                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
               // LatLng destination = new LatLng(destinationLatitude, destinationLongitude);

              /*  mMap.addMarker(new MarkerOptions().position(destination).title("destination"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
                */
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Add a marker in Sydney and move the camera

    }

}