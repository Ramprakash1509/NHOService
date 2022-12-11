package com.nhoserviceboy.carwash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nhoserviceboy.carwash.Activity.DashboadActivity;
import com.nhoserviceboy.carwash.Activity.GlobalData;
import com.nhoserviceboy.carwash.Activity.ServiceBoyDasBoad;
import com.nhoserviceboy.carwash.Activity.SignupActivity;
import com.nhoserviceboy.carwash.Admin.AccountSelectActivity;
import com.nhoserviceboy.carwash.Admin.DashBoadAdminActivity;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.Loader;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private OtpTextView otpTextView;
    ImageView backprese;
    TextView et_mobile,login;
    private FirebaseAuth mAuth;
    private static final String TAG = "PhoneAuthActivity";
    private String mVerificationId;
    String st_Address="";
    Loader loader;
    GoogleSignInClient mGoogleSignInClient;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    ////////////////////////////////////////
    private ProgressDialog progress;
    public static double latitude;
    public static double longitude;
    LocationRequest mLocationRequest;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int AUTOCOMPLETE_REQUEST_CODE = 1;
    LinearLayout li_loginn,li_otp;
    private PhoneAuthCredential credential;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    TextView detectingTv;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, AccountSelectActivity.class));
                finish();
            }
        });
        progress=new ProgressDialog(this);
        progress.setMessage("please wait...");
        progress.setCancelable(false);
        TextView toolbar=findViewById(R.id.texttoolbar);
         detectingTv=findViewById(R.id.detectingTv);
         pb=findViewById(R.id.pb);
        LinearLayout otpProgressbar=findViewById(R.id.otpProgressbar);
        toolbar.setText("NHOService");

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential)
            {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                progress.dismiss();
                Toast.makeText(LoginScreen.this, "Please check your number", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException)
                {
                    Log.w(TAG, "onVerificationFailed", e);
                }
                else if (e instanceof FirebaseTooManyRequestsException)
                {
                    Log.w(TAG, "onVerificationFailedete", e);
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                Log.d(TAG, "onCodeSent:" + verificationId);
                li_loginn.setVisibility(View.GONE);
                progress.dismiss();
                li_otp.setVisibility(View.VISIBLE);
                otpProgressbar.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                mVerificationId = verificationId;
                mResendToken = token;

            }
        };
        // [END phone_auth_callbacks]
        otpTextView = findViewById(R.id.otp_view);
        et_mobile = findViewById(R.id.et_mobile);
        li_loginn = findViewById(R.id.li_loginn);
        li_otp = findViewById(R.id.li_otp);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        otpTextView.getOtpListener();  // retrieves the current OTPListener (null if nothing is set)
        otpTextView.requestFocusOTP();	//sets the focus to OTP box (does not open the keyboard)
       // otpTextView.setOTP(otpString);	// sets the entered otpString in the Otp box (for case when otp is retreived from SMS)
        otpTextView.getOTP();	// retrieves the OTP entered by user (works for partial otp input too)

        otpTextView.showSuccess();	// shows the success state to the user (can be set a bar color or drawable)
        otpTextView.showError();	// shows the success state to the user (can be set a bar color or drawable)
        otpTextView.resetState();
        otpTextView.setOtpListener(new OTPListener()
        {
            @Override
            public void onInteractionListener()
            {

                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp)
            {


                //otpProgressbar.setVisibility(View.GONE);
                try {
                    Thread.sleep(500);
                    progress.show();
                    verifyPhoneNumberWithCode(mVerificationId, otp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




            }

        });
        if (checkPlayServices())
        {

            buildGoogleApiClient();

        }

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
    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(LoginScreen.this)
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

        result.setResultCallback(new ResultCallback<LocationSettingsResult>()
        {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult)
            {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        //getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(LoginScreen.this, REQUEST_CHECK_SETTINGS);

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
    private void getLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (mLastLocation == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
          //  GetLOcation();
        }

    }
    @Override
    public void onClick(View view)
    {
        if(view==login)
        {

            sendVerificationCode(et_mobile.getText().toString().trim());


        }


    }
    private void sendVerificationCode(String number)
    {
      //  Toast.makeText(this, et_mobile.getText().toString(), Toast.LENGTH_SHORT).show();

        progress.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(LoginScreen.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)  // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);





    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        SharedPreferences sharedPref = getSharedPreferences("application", MODE_PRIVATE);
        String tokenId=sharedPref.getString("tokenId", null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            pb.setVisibility(View.INVISIBLE);
                            detectingTv.setText("OTP DETECTED");
                            detectingTv.setTextColor(getResources().getColor(R.color.green));
                            FirebaseUser user = task.getResult().getUser();
                            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                            DocumentReference docIdRef = rootRef.collection("Users").document(user.getUid());
                            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();

                                    RedirectScreenTo(document,user,tokenId,rootRef);
                                    }
                                    else
                                    {
                                        Log.d(TAG, "Failed with: ", task.getException());
                                    }
                                }
                            });;

                            // Update UI
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                progress.dismiss();
                                pb.setVisibility(View.INVISIBLE);
                                detectingTv.setText("INVALID OTP");
                                detectingTv.setTextColor(getResources().getColor(R.color.red));
                                Toast.makeText(LoginScreen.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void RedirectScreenTo(DocumentSnapshot document, FirebaseUser user, String tokenId, FirebaseFirestore rootRef) {
        if (document.exists())
        {
            if(document.get("loginType").equals("User"))
            {
                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ApplicationConstant.INSTANCE.type, "User");
                editor.commit();

                UtilMethods.INSTANCE.setLoginrespose(LoginScreen.this,document.get("email")+"" ,document.get("phone")+"" ,
                        ""+document.get("username") ,"1" ,document.get("uid")+""  ,document.get("address")+"",document.get("landmark")+""
                        ,document.getDouble("latitude"),document.getDouble("longitude") );
                //   Toast.makeText(PhoneAuth.this, "Login In", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPref = getSharedPreferences("applicationn", MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPref.edit();
                edit.putString("currentUserId", document.getId());
                edit.apply();
                Map<String, Object> uid = new HashMap<>();
                uid.put("uid",user.getUid() );
                uid.put("uitokenId",tokenId);

                rootRef.collection("Users").document(user.getUid()).update(uid);
                Intent home = new Intent(LoginScreen.this, DashboadActivity.class);
                startActivity(home);
                finish();
                Log.d(TAG, "Document exists!");
            }
            else if(document.get("loginType").equals("Admin"))
            {
                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ApplicationConstant.INSTANCE.type, "Admin");
                editor.commit();


                UtilMethods.INSTANCE.setLoginrespose(LoginScreen.this,"","","" ,"1" ,document.getId() ,
                        "","",0.00 , 0.00 );
                Log.d("","jdshfhfieoi9r"+user.getUid());

                Map<String, Object> tokenData = new HashMap<>();
                tokenData.put("uid",user.getUid() );
                tokenData.put("uitokenId", tokenId);

                rootRef.collection("Users").document(user.getUid()).update(tokenData);
                Intent home = new Intent(LoginScreen.this, DashBoadAdminActivity.class);
                startActivity(home);
                finish();
                //  Toast.makeText(PhoneAuth.this, "Logged In", Toast.LENGTH_SHORT).show();
            }
            else if(document.get("loginType").equals("serviceboy"))
            {
                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ApplicationConstant.INSTANCE.type, "serviceboy");
                editor.commit();
                UtilMethods.INSTANCE.setLoginrespose(LoginScreen.this,document.get("email")+"" ,document.get("phone")+"" ,
                        ""+document.get("username") ,"1" ,document.get("uid")+""  ,document.get("address")+"",document.get("landmark")+""
                        ,document.getDouble("latitude"),document.getDouble("longitude") );

                Map<String, Object> uid = new HashMap<>();
                uid.put("uid",user.getUid());
                uid.put("uitokenId",tokenId);
                rootRef.collection("Users").document(user.getUid()).update(uid);
                Intent home = new Intent(LoginScreen.this, ServiceBoyDasBoad.class);
                startActivity(home);
                finish();
                //   Toast.makeText(PhoneAuth.this, "Logged In", Toast.LENGTH_SHORT).show();
            }

        } else
        {
            progress.dismiss();
            Intent i=new Intent(LoginScreen.this, SignupActivity.class);
            i.putExtra("phone",et_mobile.getText().toString().trim());
            startActivity(i);
            finish();
            Log.d(TAG, "Document does not exist!");
        }
    }


    private void verifyPhoneNumberWithCode(String verificationId, String code)
    {

        // [START verify_with_code]

        credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);
        // [END verify_with_code]


    }
    private void GetLOcation()
    {

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

        String cityName = addresses.get(0).getAddressLine(0)==null?"":addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getCountryName()==null?"":addresses.get(0).getCountryName();
        String countryName = addresses.get(0).getLocality()==null?"":addresses.get(0).getLocality();

        // Log.e("countryName","   cityName  "+  cityName  + "    stateName   "+ stateName  +"   countryName   "+countryName);


        //  location.setText(""+countryName);

        st_Address =cityName+" , "+ countryName+"";


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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

