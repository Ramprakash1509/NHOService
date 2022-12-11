package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.messaging.FirebaseMessaging;
import com.nhoserviceboy.carwash.Admin.AccountSelectActivity;
import com.nhoserviceboy.carwash.Admin.DashBoadAdminActivity;
import com.nhoserviceboy.carwash.MyFirebaseInstanceIDService;
import com.nhoserviceboy.carwash.Notification;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.ServiesBoy.ServiesBoyDashboadActivity;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

public class SplashActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private GoogleApiClient mGoogleApiClient;

    private static String key="2";
    MyFirebaseInstanceIDService service;
    Notification notification;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
          service=new MyFirebaseInstanceIDService();

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        String msg = "done";

                        Log.d("","jsdhjhgfjhs"+task);
                        if (!task.isSuccessful())
                        {
                            msg = " failed";
                          //  Toast.makeText(SplashActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
      /*  FirebaseMessaging.getInstance().subscribeToTopic("orders_comming")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        String msg = "done";
                        Toast.makeText(SplashActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful())
                        {
                            msg = " failed";
                            Toast.makeText(SplashActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        }

                    }
                });*/





        if (checkPlayServices()) {

            buildGoogleApiClient();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
            }
        }
    }



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(SplashActivity.this)
                .addOnConnectionFailedListener(SplashActivity.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                        key = ""+myPrefs.getString(ApplicationConstant.INSTANCE.locationreposeval, "");

                        if(key.equalsIgnoreCase("1")){

                            loginpage();

                        }else{


                            // Toast.makeText(SplashActivity.this, "permition allow ", // Toast.LENGTH_SHORT).show();
                        }


                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {

                            // // Toast.makeText(SplashActivity.this, "RESOLUTION_REQUIRED", // // Toast.LENGTH_SHORT).show();

                            status.startResolutionForResult(SplashActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_REQUEST).show();


                // // Toast.makeText(getApplicationContext(), "PLAY_SERVICES_REQUEST", // // Toast.LENGTH_LONG).show();


            } else {
                // // Toast.makeText(getApplicationContext(), "This device is not supported.", // // Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }



    public void loginpage() {

        Thread timerThread = new Thread()
        {
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    DashboardLogin();
                }
            }
        };
        timerThread.start();
    }

    public void DashboardLogin() {

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.page_select, "1");
        editor.commit();


        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, "");

        String Email = ""+balanceResponse;

        if ( Email.equalsIgnoreCase("1")){

           String  typeResponse = ""+myPreferences.getString(ApplicationConstant.INSTANCE.type, "");

           Log.e("typeResponse","type : "+ typeResponse );

           if(typeResponse.trim().equalsIgnoreCase("serviceboy")){

                Intent intent = new Intent(SplashActivity.this, ServiceBoyDasBoad.class);
                startActivity(intent);
                finish();

            }else if(typeResponse.trim().equalsIgnoreCase("User")){

                Intent intent = new Intent(SplashActivity.this, DashboadActivity.class);
                startActivity(intent);
                finish();

            }else {

                Intent intent = new Intent(SplashActivity.this, DashBoadAdminActivity.class);
                startActivity(intent);
                finish();
            }

        }else{

            Intent intent = new Intent(SplashActivity.this, AccountSelectActivity.class);
            startActivity(intent);
            finish();

        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ASK_MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean FINE_LOCATIONPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean COARSE_LOCATIONPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (FINE_LOCATIONPermission && COARSE_LOCATIONPermission)
                    {

                        // // Toast.makeText(Splash.this, "SUCCESS  Done ", // // Toast.LENGTH_SHORT).show();
                        UtilMethods.INSTANCE.locationreposeval(this, "1");


                        loginpage();


                    }
                    else
                    {

                        Snackbar.make(this.findViewById(android.R.id.content),
                                "Please Grant Permissions to start service",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

                                    }
                                }).show();
                    }
                }
                break;
        }
    }


}