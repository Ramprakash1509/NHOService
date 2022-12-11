package com.nhoserviceboy.carwash.Activity;

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
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.nhoserviceboy.carwash.Admin.DashBoadAdminActivity;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.ServiesBoy.ServiesBoyDashboadActivity;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.Loader;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    TextView login,tvSignup , texttoolbar , tv_Google;
    ImageView backprese;
    EditText et_Password,et_email;
    FirebaseFirestore db;
    Loader loader;
    GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 427;
    ImageView showpassword;
    int count=0;
    String st_Address="";
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    public static double latitude;
    public static double longitude;
    LocationRequest mLocationRequest;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int AUTOCOMPLETE_REQUEST_CODE = 1;

    String type="";

    LinearLayout Type_register , l1, l2;

    String typeResponse="";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GetId();

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


            //Toast.makeText(this, longitude+"   ,   :   "+ latitude, Toast.LENGTH_SHORT).show();
            // Log.e("GlobalData.latitude3", "" + GlobalData.latitude);
            // Log.e("GlobalData.longitude3 ", "" + GlobalData.longitude);

            GetLOcation();
        }

    }

    private void GetLOcation() {

        // GetLocation Location = new GetLocation(this);
        // latitude = Location.getLatitude();
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

        st_Address =cityName+" , "+ countryName+"";


    }



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(LoginActivity.this)
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
                            status.startResolutionForResult(LoginActivity.this, REQUEST_CHECK_SETTINGS);

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


    private void GetId()
    {

        Type_register=findViewById(R.id.Type_register);
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);


        type=getIntent().getStringExtra("type");



        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.type, type);
        editor.commit();




        if(type.equalsIgnoreCase("serviceboy"))
        {

            Type_register.setVisibility(View.GONE);
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);


        }else  if(type.equalsIgnoreCase("Admin"))
        {

            Type_register.setVisibility(View.GONE);
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);



        }else {


            Type_register.setVisibility(View.GONE);
        }




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        db= FirebaseFirestore.getInstance();

        showpassword=findViewById(R.id.showpassword);
        tv_Google=findViewById(R.id.tv_Google);
        et_email=findViewById(R.id.et_email);
        et_Password=findViewById(R.id.et_Password);
        et_Password.setVisibility(View.GONE);

        texttoolbar=findViewById(R.id.texttoolbar);
        backprese=findViewById(R.id.backprese);
        ///////////////////////////////////////////////////////////////
        backprese.setVisibility(View.INVISIBLE);

        tvSignup=findViewById(R.id.tvSignup);
        login=findViewById(R.id.login);

        login.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        tv_Google.setOnClickListener(this);
        showpassword.setOnClickListener(this);

        texttoolbar.setText("Service Boy ");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==427){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    //Relate to google login

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String id=account.getId();
                String f_name=account.getGivenName();
                String l_name=account.getFamilyName();
                String Email_st=account.getEmail();
              //  String phone_get=account.get();


//                 Toast.makeText(this, ""+Email_st, Toast.LENGTH_SHORT).show();
                
                
                HitCheckEmail_id(Email_st);



               /* UtilMethods.INSTANCE.setLoginrespose(LoginActivity.this,Email_st+"" ,
                        "123456" ,"**********"+"" ,f_name +" "+ l_name,"1" ,"" ,"","" , 545515,2222);

                Intent home = new Intent(LoginActivity.this, DashboadActivity.class);
                startActivity(home);
                finish();
*/
 
            }

        } catch (ApiException e) {
            Log.w("Error message", "signInResult:failed code=" + e.getStatusCode());
        }

    }

    private void HitCheckEmail_id(String email_st)
    {

        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
          typeResponse = myPreferences.getString(ApplicationConstant.INSTANCE.type, "");



        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            if (loader != null) {
                                if (loader.isShowing())
                                    loader.dismiss();
                            }

                            for(QueryDocumentSnapshot doc : task.getResult())
                            {

                                String a=""+doc.getString("email");
                                String a1=""+email_st.toString().trim();

                                if(a.equalsIgnoreCase(a1))
                                {

                                    String User_id=""+doc.getString("user_id");

                                    String loginType=""+doc.getString("loginType");

                                    Log.e("loginType","As :  "+loginType);


                                    UtilMethods.INSTANCE.setLoginrespose(LoginActivity.this,doc.getString("email")+"" ,
                                            doc.getString("phone") ,doc.getString("username") ,"1" ,User_id ,
                                            ""+doc.getString("address") ,""+ doc.getString("landmark") ,
                                            doc.getDouble("latitude"),doc.getDouble("longitude" ) );


                                    if(loginType.trim().equalsIgnoreCase("Admin"))
                                    {

                                        Intent home = new Intent(LoginActivity.this, DashBoadAdminActivity.class);
                                        startActivity(home);

                                    }
                                    else if(loginType.trim().equalsIgnoreCase("User"))
                                    {

                                        Intent home = new Intent(LoginActivity.this, DashboadActivity.class);
                                        startActivity(home);

                                    }
                                    else if(loginType.trim().equalsIgnoreCase("serviceboy"))
                                    {

                                        Intent home = new Intent(LoginActivity.this, ServiceBoyDasBoad.class);
                                        startActivity(home);

                                    }



                                    if(count==0)
                                    {
                                        Toast.makeText(LoginActivity.this, "Logged In" +User_id, Toast.LENGTH_SHORT).show();
                                        count=count+1;
                                    }


                                    break;

                                }else{
                                     if(count==0)
                                     {
                                         Toast.makeText(LoginActivity.this, "Can't login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                       count=count+1;
                                     }

                                }

                            }


                            if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {

                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(false);

                                addDataToFirestore(""+email_st.toString().trim(), "123456",
                                        "000000000" ,"xyx" ,
                                        "" +st_Address, "");




                   /* hitLoginApi(""+ed_email.getText().toString().trim(), ""+ed_Password.getText().toString().trim(),
                            ""+ed_phonenumber.getText().toString().trim() ,""+et_fullname.getText().toString().trim(),
                            ""+ed_Address.getText().toString().trim(),""+ed_Landmark.getText().toString().trim() );
*/

                            } else {
                                UtilMethods.INSTANCE.Error(LoginActivity.this, getResources().getString(R.string.network_error_message));
                            }


                        }else {


                          //  Toast.makeText(LoginActivity.this, "112121212", Toast.LENGTH_SHORT).show();
                            
                        }
                    }
                });

    }

    int passcount=0;
    private void addDataToFirestore(String email, String password, String phonenumber ,String username , String Address, String Landmark) {

        final int min = 200;
        final int max = 8000000;
        final int rendom = new Random().nextInt((max - min) + 1) + min;

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbCourses = db.collection("Users");
        String User_idcsc=""+rendom;


        // adding our data to our courses object class.

        RegisterModel courses = new RegisterModel(email, phonenumber, username  ,Address,  Landmark  ,latitude,longitude,"User" ,"0","");

        // below method is use to add data to Firebase Firestore.
        dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {

                if (loader != null)
                {
                    if (loader.isShowing())
                        loader.dismiss();
                }

                UtilMethods.INSTANCE.setLoginrespose(LoginActivity.this,email+"" ,phonenumber+"" ,
                        ""+username ,"1" ,User_idcsc ,Address ,Landmark ,latitude,longitude );
                if(count==0)
                {
                    Toast.makeText(LoginActivity.this, "Login In", Toast.LENGTH_SHORT).show();
                    count=count+1;
                }


                Intent home = new Intent(LoginActivity.this, DashboadActivity.class);
                startActivity(home);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }
                if(count==0)
                {
                    Toast.makeText(LoginActivity.this, "Fail to add User \n" + e, Toast.LENGTH_SHORT).show();
                    count=count+1;
                }


            }
        });
    }


    @Override
    public void onClick(View view)
    {


        if(view==showpassword){


            if(passcount==0){

                passcount=1;
                et_Password.setTransformationMethod(null);
                showpassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye));


            } else {
                passcount=0;
                et_Password.setTransformationMethod(new PasswordTransformationMethod());
                showpassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_invisible));

            }

        }

        if(view==tv_Google){

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);

        }



        if(view==tvSignup){

            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);

        }

        if(view==login)
        {

           count=0;
            if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {




               // if(!UtilMethods.INSTANCE.isValidEmail(et_email.getText().toString().trim()))
                if(et_email.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please enter valid Number ", Toast.LENGTH_SHORT).show();
                }
               /* else if( et_Password.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }*/
                else
                {

                    Log.e("typeuser","Asa :  "+  type);

                    if(type.trim().equalsIgnoreCase("serviceboy"))
                    {

                        Hit_Check_Servies_boy();


                    }else
                    {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);

                        db.collection("Users").whereEqualTo("email",et_email.getText().toString()).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                                    {
                                        if(task.getResult().isEmpty())
                                        {
                                            if (loader != null)
                                            {
                                                if (loader.isShowing())
                                                    loader.dismiss();
                                            }
                                            Toast.makeText(LoginActivity.this, "Can't login ,Incorrect Email or Password", Toast.LENGTH_SHORT).show();


                                        }
                                        else {
                                            if(task.isSuccessful()){
                                                for(QueryDocumentSnapshot doc : task.getResult()){
                                                    if (loader != null) {
                                                        if (loader.isShowing())
                                                            loader.dismiss();
                                                    }

                                                    String a=""+doc.getString("email");
                                                    String b=""+doc.getString("passsword");


                                                    String a1=""+et_email.getText().toString().trim();
                                                    String b1=""+et_Password.getText().toString().trim();

                                                    Log.e("docdocuzaai","A  "+ a);
                                                    Log.e("docdocuzaai1","B "+ b);
                                                    Log.e("docdocuzaai2","A1 "+ a1);
                                                    Log.e("docdocuzaai3","B1 "+ b1);

                                                    if(b.equalsIgnoreCase(b1)) {

                                                        String User_id=""+doc.getString("user_id");
                                                        String user_type=""+doc.getString("loginType");

                                                        Log.e("user_type","As : "+ user_type );

                                                        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = prefs.edit();
                                                        editor.putString(ApplicationConstant.INSTANCE.type, user_type);
                                                        editor.commit();



                                                        if(user_type.trim().equalsIgnoreCase("User")){

                                                            UtilMethods.INSTANCE.setLoginrespose(LoginActivity.this,doc.getString("email")+""
                                                                   ,doc.getString("phone") ,doc.getString("username") ,"1" ,doc.getId() ,
                                                                    ""+doc.getString("address") ,""+ doc.getString("landmark") ,
                                                                    doc.getDouble("latitude"),doc.getDouble("longitude") );

                                                            Intent home = new Intent(LoginActivity.this, DashboadActivity.class);
                                                            startActivity(home);

                                                        }
                                                        else
                                                        {

                                                            UtilMethods.INSTANCE.setLoginrespose(LoginActivity.this,"","","" ,"1" ,doc.getId() ,
                                                                    "","",0.00 , 0.00 );


                                                            Intent home = new Intent(LoginActivity.this, DashBoadAdminActivity.class);
                                                            startActivity(home);
                                                            if(count==0)
                                                            {
                                                                Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                                                count=count+1;
                                                            }

                                                        }

                                                    }else{
                                                        if(count==0)
                                                        {
                                                            Toast.makeText(LoginActivity.this, " Can't login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                                            count=count+1;
                                                        }

                                                    }

                                                }

                                            }
                                        }

                                    }
                                });

                    }

                }


            } else {
                UtilMethods.INSTANCE.Error(LoginActivity.this, getResources().getString(R.string.network_error_message));
            }



        }

    }

    /*private void Hit_Check_Servies_boy()
    {

        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

        db.collection("Add_Service_Boy").whereEqualTo("phone",et_email.getText().toString()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {


                        if(task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();

                            Log.e("savedatatask",""+ task.getResult());

                            for(QueryDocumentSnapshot doc : task.getResult())
                            {

                                *//*if (loader != null)
                                {
                                    if (loader.isShowing())
                                        loader.dismiss();
                                }

                                Log.e("savedata",""+ doc);

                                String a=""+doc.getString("phone");

                               // String b=""+doc.getString("passsword");


                                String a1=""+et_email.getText().toString().trim();
                               // String b1=""+et_Password.getText().toString().trim();

                                Log.e("docdocuzaai","A4355445  "+ a);
                                //Log.e("docdocuzaai1","B "+ b);
                                Log.e("docdocuzaai2","A1 "+ a1);
                                //Log.e("docdocuzaai3","B1 "+ b1);

                                if(a.equals(a1))
                                {*//*
                                    String User_id=""+doc.getString("user_id");
                                    String user_type=""+doc.getString("loginType");

                                    Log.e("typeuser_user_type","As : "+ user_type );

                                    SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.type, user_type);
                                    editor.commit();


                                    UtilMethods.INSTANCE.setLoginrespose(LoginActivity.this,doc.getString("email")+"" ,
                                          doc.getString("phone") ,doc.getString("username") ,"1" ,User_id ,
                                            ""+doc.getString("address") ,""+ doc.getString("landmark") ,
                                            doc.getDouble("latitude"),doc.getDouble("longitude") );

                                    Intent home = new Intent(LoginActivity.this, ServiesBoyDashboadActivity.class);
                                    startActivity(home);
                                    if(count==0)
                                    {
                                        Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                        count=count+1;
                                    }

                                    break;

                                }
                                else
                                {
                                    if(count==0)
                                {


                                    Toast.makeText(LoginActivity.this, "Cannot login,incorrect phone number", Toast.LENGTH_SHORT).show();
                                    count=count+1;

                                    }

                                   // Toast.makeText(LoginActivity.this, "eyrieryt", Toast.LENGTH_SHORT).show();
                                    //loader.dismiss();


                                }

                            }

                        }
                    }
                });*/




    //}
    private void Hit_Check_Servies_boy()
    {     SharedPreferences sharedPref = getSharedPreferences("application", MODE_PRIVATE);
        String tokenId=sharedPref.getString("tokenId", null);
        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        db.collection("Add_Service_Boy").whereEqualTo("phone",et_email.getText().toString()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.getResult().isEmpty())
                        {
                            if (loader != null)
                            {
                                if (loader.isShowing())
                                    loader.dismiss();
                             }
                             Toast.makeText(LoginActivity.this, "Can't login ,Incorrect Phone number", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (task.isSuccessful())
                            {
                                String a = null;
                                String user_type = null;
                                String User_id;
                                for (QueryDocumentSnapshot doc : task.getResult())
                                {
                                    if (loader != null)
                                    {
                                        if (loader.isShowing())
                                            loader.dismiss();
                                    }
                                    a = "" + doc.getString("phone");


                                    Log.e("docdocuzaai", "A  " + a);
                                    User_id = "" + doc.getString("uid");
                                    user_type = "" + doc.getString("loginType");
                                    Log.d("","reuwery"+User_id);


                                    String a1 = "" + et_email.getText().toString().trim();

                                    Log.e("docdocuzaai", "A  " + a);
                                    Log.e("docdocuzaai", "Aaaaaaa  " + a1);

                                    if (a.equals(a1))
                                    {
                                        //Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        Log.e("typeuser_user_type", "As : " + user_type);
                                        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString(ApplicationConstant.INSTANCE.type, user_type);
                                        editor.commit();

                                        Map<String, Object> tokenData = new HashMap<>();
                                        tokenData.put("uitokenId", tokenId);
                                        db.collection("Add_Service_Boy").document(doc.getId()).update(tokenData);


                                          UtilMethods.INSTANCE.setLoginrespose(LoginActivity.this,doc.getString("email")+"" ,
                                                doc.getString("phone") ,doc.getString("username") ,"1" ,User_id ,
                                                ""+doc.getString("address") ,""+ doc.getString("landmark") ,
                                                doc.getDouble("latitude"),doc.getDouble("longitude") );

                                      /*  Map<String,Object> tokenData=new HashMap<>();
                                        tokenData.put("token",tokenId);
                                        tokenData.put("serviceBoyId",doc.getId());
                                        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
                                        firebaseFirestore.collection("DeviceToken").document().set(tokenData);*/


                                        Intent home = new Intent(LoginActivity.this, ServiceBoyDasBoad.class);
                                        startActivity(home);
                                        finish();
                                        if (count == 0)
                                        {
                                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                            count = count + 1;
                                        }
                                        break;
                                    }
                                    else
                                    {
                                        // Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                        if(count==0)
                                        {
                                            //Toast.makeText(LoginActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                            count=count+1;
                                        }

                                    }
                                }
                            }

                        }}
                });
    }




    @Override
    public void onConnected(@Nullable Bundle bundle)
    {

    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }
}