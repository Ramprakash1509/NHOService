package com.nhoserviceboy.carwash.Admin.SeviceBoyModule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nhoserviceboy.carwash.Activity.GlobalData;
import com.nhoserviceboy.carwash.Activity.RegisterModel;
import com.nhoserviceboy.carwash.Admin.MyShopBoyListActivity;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.Loader;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateServiceBoy extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{      String id;
    TextView texttoolbar, tv_signup,tv_bar;
    ImageView backprese;
    EditText et_fullname, ed_phonenumber, ed_email, ed_Password, ed_Confirm_password  ,  ed_Landmark , ed_Address;
    private FirebaseFirestore db;
    Loader loader;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    public static double latitude;
    public static double longitude;
    LocationRequest mLocationRequest;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int AUTOCOMPLETE_REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        db = FirebaseFirestore.getInstance();
        Getid();
        if (checkPlayServices()) {

            buildGoogleApiClient();

        }


    }
    private void getLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (mLastLocation == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            GlobalData.latitude = mLastLocation.getLatitude();
            GlobalData.longitude = mLastLocation.getLongitude();


            //  Toast.makeText(this, longitude+"   ,   :   "+ latitude, Toast.LENGTH_SHORT).show();


            // Log.e("GlobalData.latitude3", "" + GlobalData.latitude);
            // Log.e("GlobalData.longitude3 ", "" + GlobalData.longitude);

            GetLOcation();
        }

    }
    private void GetLOcation() {

        // GetLocation Location = new GetLocation(this);
        //  latitude = Location.getLatitude();
        // longitude = Location.getLongitude();


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(GlobalData.latitude, GlobalData.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getCountryName();
        String countryName = addresses.get(0).getLocality();

        // Log.e("countryName","   cityName  "+  cityName  + "    stateName   "+ stateName  +"   countryName   "+countryName);


        //  location.setText(""+countryName);
        ed_Address.setText(cityName+" , "+ countryName);


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(UpdateServiceBoy.this)
                .addOnConnectionFailedListener(this)
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
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(UpdateServiceBoy.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
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
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                                "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }
    private void Getid()
    {
        Intent intent = getIntent();
        String Name = intent.getStringExtra("up_name");
        String phoneNumber = intent.getStringExtra("up_phone");
        String email = intent.getStringExtra("up_email");
        String Address = intent.getStringExtra("up_address");
        String Landmark = intent.getStringExtra("up_landmark");
        String password = intent.getStringExtra("up_passsword");
        id= intent.getStringExtra("id");




        tv_bar=findViewById(R.id.bar1);
        ed_Address = findViewById(R.id.ed_Address);
        ed_Landmark = findViewById(R.id.ed_Landmark);

        et_fullname = findViewById(R.id.et_fullname);
        ed_phonenumber = findViewById(R.id.ed_phonenumber);
        ed_email = findViewById(R.id.ed_email);
        ed_Password = findViewById(R.id.ed_Password);
        ed_Confirm_password = findViewById(R.id.ed_Confirm_password);
        tv_signup = findViewById(R.id.tv_signup);
        ////////////////////////////////////////////////////////////////////
        ed_Address.setText(Address);
        ed_Landmark.setText(Landmark);
        et_fullname.setText(Name);
        ed_phonenumber.setText(phoneNumber);
        ed_email.setText(email);
        ed_Password.setText(password);
        ed_Confirm_password.setText(password);
        tv_signup.setText("Update");
        tv_bar.setText("Update Service boy");
        //////////////////////////////////////////////////////////








        tv_signup.setOnClickListener(this);


        texttoolbar = findViewById(R.id.texttoolbar);
        backprese = findViewById(R.id.backprese);
        backprese.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        texttoolbar.setText("Update Service Boy");

    }
    @Override
    public void onClick(View view)
    {

        if (view == tv_signup)
        {

            if(et_fullname.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Enter Full Name", Toast.LENGTH_SHORT).show();

            }else  if(ed_Address.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();

            }else  if(ed_Landmark.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Enter Landmark", Toast.LENGTH_SHORT).show();

            }else  if(ed_phonenumber.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();

            }else  if( !isValidEmail(ed_email.getText().toString().trim())){

                Toast.makeText(this, " Enter email at this format ( abc@gmail.com )", Toast.LENGTH_SHORT).show();

            }else  if(!ed_Password.getText().toString().trim().equalsIgnoreCase(ed_Confirm_password.getText().toString().trim()))
            {

                Toast.makeText(this, "Password and Confirm Password Validation", Toast.LENGTH_SHORT).show();

            }else {

                if (UtilMethods.INSTANCE.isNetworkAvialable(UpdateServiceBoy.this))
                {
/*

                   loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
*/

                    addDataToFirestore(""+ed_email.getText().toString().trim(), ""+ed_Password.getText().toString().trim(),
                            ""+ed_phonenumber.getText().toString().trim() ,""+et_fullname.getText().toString().trim() ,
                            ""+ed_Address.getText().toString().trim() , ""+ ed_Landmark.getText().toString().trim() ,"serviceboy");


                } else {
                    UtilMethods.INSTANCE.Error(UpdateServiceBoy.this, getResources().getString(R.string.network_error_message));
                }

            }

        }
    }
    private boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }



    private void addDataToFirestore(String email, String password, String phonenumber ,String username , String Address, String Landmark,String loginType ) {

        final int min = 200;
        final int max = 8000000;
        final int rendom = new Random().nextInt((max - min) + 1) + min;
        SharedPreferences sharedPref = getSharedPreferences("application", MODE_PRIVATE);
        String tokenId=sharedPref.getString("tokenId", null);


        // creating a collection reference
        // for our Firebase Firetore database.

        CollectionReference dbCourses = db.collection("Users");
        String User_idcsc=""+rendom;


        // adding our data to our courses object class.
        RegisterModel courses = new RegisterModel(email, phonenumber, username  ,Address,  Landmark
                ,latitude,longitude, loginType ,"",tokenId);

        // below method is use to add data to Firebase Firestore.
        dbCourses.document(id).set(courses).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused)
            {

               Toast.makeText(UpdateServiceBoy.this, "Update Successful", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(UpdateServiceBoy.this, MyShopBoyListActivity.class));
                finish();

            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}