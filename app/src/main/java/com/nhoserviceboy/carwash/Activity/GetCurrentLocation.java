package com.nhoserviceboy.carwash.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhoserviceboy.carwash.Admin.CurrentLoacton;
import com.nhoserviceboy.carwash.Admin.Model.Latlong;
import com.nhoserviceboy.carwash.Fragment.SearchFragment;
import com.nhoserviceboy.carwash.Fragment.SearchLocationMapsFragment;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.GPSTracker;


public class GetCurrentLocation extends AppCompatActivity {
    //Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 101;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
    Context context;
    Latlong latlong;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_current_location);
        context = GetCurrentLocation.this;
        Intent i = getIntent();
        String destinationLatitude = i.getStringExtra("destinationLatitude");
        String destinationLongitude = i.getStringExtra("destinationLongitude");
        Log.d("", "" + destinationLatitude + "jhdsfusgdf" + destinationLongitude);
      //  gps = new GPSTracker(context);
        FirebaseDatabase.getInstance().getReference("Location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double lat, longi;
                Latlong l = dataSnapshot.getValue(Latlong.class);
               // getlocation(l.getLatitude(), l.getLongitude(), destinationLatitude, destinationLongitude);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void getlocation(double sourcelat, double sourcelongi, String destlat, String destilongi) {
        String uri = "http://maps.google.com/maps?saddr=" + sourcelat + "," + sourcelongi + "&daddr=" + destlat + "," + destilongi;
        Log.e("mapsurl", "url : " + uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
        finish();
    }


}