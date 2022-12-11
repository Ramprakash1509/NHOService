package com.nhoserviceboy.carwash.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.dailyService.DailyServiceDataModel;
import com.nhoserviceboy.carwash.Admin.Model.AssignedUser;
import com.nhoserviceboy.carwash.Admin.Model.Latlong;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.ServiesBoy.ServiesBoyDashboadActivity;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.GPSTracker;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import in.aabhasjindal.otptextview.OtpTextView;

public class DailyServices extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    TextView texttoolbar,serviceboyNotification;
    ImageView backprese;
    LinearLayout ll_bookinglist;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    // ImageView logout_im;
    ///////////////////////////////////////////
    private static final int REQUEST_CODE_PERMISSION = 101;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    SwipeRefreshLayout swipeRefreshLayout;

    // GPSTracker class
    GPSTracker gps;
    Context  context;
    Latlong latlong;
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
        setContentView(R.layout.activity_daily_services);
        context=DailyServices.this;
        GetId();
         if (checkPlayServices())
    {

        buildGoogleApiClient();

    }

}

    private void GetId()
    {
        // logout_im=findViewById(R.id.logout_im);
        //logout_im.setOnClickListener(this);
        serviceboyNotification=findViewById(R.id.serviceboyNotification);
        serviceboyNotification.setOnClickListener(this);
        serviceboyNotification.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();

        ll_bookinglist=findViewById(R.id.ll_bookinglist);

        texttoolbar=findViewById(R.id.texttoolbar);
        texttoolbar.setText("Service List");
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(this);


        loadingPB =  findViewById(R.id.idProgressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);
                GetServiesList();

            }
        });
        GetServiesList();



    }

    private void GetServiesList()
    {

        SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,MODE_PRIVATE);
        String user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");
        Log.d("","cxmnvj"+user_id);
        db = FirebaseFirestore.getInstance();
        db.collection("Booking_Assigned").whereEqualTo("servies_boy_fId",user_id.toString().trim()).whereEqualTo("booking_status","pending").whereEqualTo("dailybooking","dailyCleaning").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        loadingPB.setVisibility(View.GONE);
                        if (!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            ll_bookinglist.removeAllViews();
                            for (DocumentSnapshot d : list)
                            {

                                LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View mLinearView = inflater2.inflate(R.layout.booking_abapter_item_servies, null);

                                final TextView validdate,model, start,notAttended, payment_update,Payment_mode, payment_status,user_email_tv,userlandmark,user_name_tv , usernumber ,goto_live, useraddress,booked_on,Booking_Date,name,booking_assigned,payment_type ,servisetype, Booking_time , total_amount  , wash_Count;
                                CircleImageView im5;
                                RelativeLayout ri_main;
                                Button updateStatus=mLinearView.findViewById(R.id.updateStatus);
                                notAttended=mLinearView.findViewById(R.id.notAttende);
                                start=mLinearView.findViewById(R.id.start);
                                payment_type =  mLinearView.findViewById(R.id.payment_type);
                                ri_main =  mLinearView.findViewById(R.id.ri_main);
                                im5 =  mLinearView.findViewById(R.id.im5);
                                name =  mLinearView.findViewById(R.id.name);
                                model =  mLinearView.findViewById(R.id.model);

                                booking_assigned =  mLinearView.findViewById(R.id.booking_assigned);
                                goto_live =  mLinearView.findViewById(R.id.goto_live);
                                servisetype =  mLinearView.findViewById(R.id.servisetype);
                                Booking_time =  mLinearView.findViewById(R.id.Booking_time);
                                booked_on =  mLinearView.findViewById(R.id.booked_on);
                                Booking_Date =  mLinearView.findViewById(R.id.Booking_Date);
                                total_amount =  mLinearView.findViewById(R.id.total_amount);
                                wash_Count =  mLinearView.findViewById(R.id.wash_Count);
                                user_name_tv =  mLinearView.findViewById(R.id.user_name_tv);
                                usernumber =  mLinearView.findViewById(R.id.usernumber);
                                useraddress =  mLinearView.findViewById(R.id.useraddress);
                                userlandmark =  mLinearView.findViewById(R.id.userlandmark);
                                user_email_tv =  mLinearView.findViewById(R.id.user_email_tv);
                                Payment_mode =  mLinearView.findViewById(R.id.Payment_mode);
                                payment_status =  mLinearView.findViewById(R.id.payment_status);
                                payment_update =  mLinearView.findViewById(R.id.payment_update);
                                payment_update.setVisibility(View.GONE);
                                start.setVisibility(View.GONE);
                                model.setText(d.getString("carModelNumber"));
                                user_name_tv.setText(""+   d.getString("st_ed_name"));
                                usernumber.setText(""+   d.getString("st_ed_mobile"));
                                useraddress.setText(""+   d.getString("st_ed_address"));
                                userlandmark.setText(""+   d.getString("st_ed_landmark"));
                                wash_Count.setText("Wash Count : " + d.getString("st_wash_count"));
                                servisetype.setText("" + d.getString("st_washname_tv")  );
                                Booking_Date.setText(" " + d.getString("st_current_date_book") );
                                name.setText("" + d.getString("st_car_name")  );
                                booked_on.setText(" " + d.getString("st_service_date")  );
                                Booking_time.setText(   " " + d.getString("st_service_time"));
                                String userId=d.getString("st_user_id");
                                String servies_boy_username=d.getString("servies_boy_username");
                                String servies_boy_phone=d.getString("servies_boy_phone");
                                String st_ed_name=d.getString("st_ed_name");
                                String serviceTime=d.getString("st_service_time");

                                String exdate=d.getString("exdate");
                                String serviceDate=d.getString("st_service_date");
                                //////////////////////////////////////////////////////////////////////////////////////////////
                                Log.d("yrweiu47643",serviceDate+"  "+exdate);
                                Date date1=new Date();
                                SimpleDateFormat sdfDATE1=new SimpleDateFormat("dd-MM-yyyy");
                                String currentDate1=sdfDATE1.format(date1);


                                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
                                //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                                Date time1= null;
                                Date time2= null;
                                try {
                                    time1 = newFormat.parse(currentDate1);
                                    time2 = newFormat.parse(exdate);
                                    Date startDateValue = new Date(String.valueOf(time1));
                                    Date endDateValue = new Date(String.valueOf(time2));
                                    long diff = endDateValue.getTime() - startDateValue.getTime();
                                    long seconds = diff / 1000;
                                    long minutes = seconds / 60;
                                    long hours = minutes / 60;
                                    long days = (hours / 24)-1;
                                    Log.d("days", "" + days);

                                    time1 = newFormat.parse(currentDate1);
                                    time2 = newFormat.parse(serviceDate);
                                    Date startDateValue1 = new Date(String.valueOf(time1));
                                    Date endDateValue1 = new Date(String.valueOf(time2));
                                    long diff1 = endDateValue1.getTime() - startDateValue1.getTime();
                                    long seconds1= diff1 / 1000;
                                    long minutes1 = seconds1 / 60;
                                    long hours1 = minutes1 / 60;
                                    long days1 = (hours1 / 24)-1;
                                    Log.d("yuwq6465",""+days1);
                                    validdate =  mLinearView.findViewById(R.id.validdate);
                                    validdate.setVisibility(View.VISIBLE);
                                    if (days1<=1)
                                    {

                                        if (days>=0)
                                        {
                                            validdate.setText("This package is  booked for  " + serviceDate + "  and  valid for  " + days + "  days");
                                            updateStatus.setVisibility(View.VISIBLE);
                                            updateStatus.setOnClickListener(new View.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(View v)
                                                {
                                                    Random r = new Random();
                                                    String randomNumber = String.format("%06d", r.nextInt(999999));
                                                    Date date1=new Date();
                                                    SimpleDateFormat sdf1=new SimpleDateFormat("hh:mm a");
                                                    String currentTime1= sdf1.format(date1);
                                                    DailyServiceDataModel dailyServiceDataModel=new DailyServiceDataModel( d.getString("servies_boy_fId"), d.getString("servies_boy_username"), d.getString("booking_id"), d.getString("st_car_name"), d.getId(), currentDate1, currentTime1, "0", randomNumber,
                                                            false, d.getString("u_latitude"), d.getString("u_longitude"),
                                                            d.getString("st_ed_name"), d.getString("st_ed_mobile"), d.getString("st_ed_address"),d.getString("st_ed_landmark"),"",d.getString("st_user_id"));
                                                    Map<String, Object> pin = new HashMap<>();
                                                    pin.put("pin",randomNumber);
                                                    db.collection("BookingHistory").document(d.getString("booking_id")).update(pin);
                                                    Log.d("uweyr4664",randomNumber);
                                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                    View viewPup = inflater.inflate(R.layout.daily_service_update, null);
                                                    Button okButton = (Button) viewPup.findViewById(R.id.okButton);
                                                    Button cancelButton = (Button) viewPup.findViewById(R.id.cancelButton);
                                                    TextView verify =  viewPup.findViewById(R.id.verify);
                                                    OtpTextView otpverify=viewPup.findViewById(R.id.otp_view);
                                                    final Dialog dialog = new Dialog(DailyServices.this);
                                                    dialog.setCancelable(false);
                                                    dialog.setContentView(viewPup);
                                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                                    okButton.setOnClickListener(new View.OnClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(View v)
                                                        {
                                                            try
                                                            {
                                                                Log.d("7655e",otpverify.getOTP());
                                                                if (otpverify.getOTP().equalsIgnoreCase(randomNumber))
                                                                {
                                                                    int washCount= Integer.parseInt(d.getString("st_wash_count"))-1;
                                                                    Log.d("washcount",""+washCount);
                                                                    db.collection("BookingHistory").document(d.getString("booking_id")).update("wash_count",String.valueOf(washCount));
                                                                    db.collection("Booking_Assigned").document(d.getId()).update("st_wash_count",String.valueOf(washCount));
                                                                    db.collection("BookingHistory").document(d.getString("booking_id")).update("paymenttype", "Successful");
                                                                    db.collection("BookingHistory").document(d.getString("booking_id")).update("pin",null);
                                                                    CollectionReference dbCourses = db.collection("DailyServiceHistory");
                                                                    dbCourses.add(dailyServiceDataModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                                                    {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference)
                                                                        {
                                                                            Toast.makeText(DailyServices.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                    dialog.dismiss();
                                                                    Toast.makeText(DailyServices.this, "verify Otp", Toast.LENGTH_SHORT).show();




                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(DailyServices.this, "Wrong Otp", Toast.LENGTH_SHORT).show();
                                                                }



                                                            }catch (Exception e)
                                                            {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });

                                                    cancelButton.setOnClickListener(new View.OnClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(View v)
                                                        {



                                                            dialog.dismiss();


                                                        }
                                                    });





                                                    dialog.show();












                                                }
                                            });




                                        }
                                        else
                                        {
                                            validdate.setText("This package validity is expired ");
                                        }


                                    }
                                    else
                                    {
                                        validdate.setVisibility(View.GONE);
                                    }

                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                 //////////////////////////////////////////////////////////////////////////////////////////////
                                ri_main.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        Intent i=new Intent(DailyServices.this,DailyServiceHistory.class);
                                        i.putExtra("serviceId",d.getId());
                                        startActivity(i);
                                    }
                                });

                                Date date=new Date();
                                SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                                Calendar cal = Calendar.getInstance();
                                Date startDate = null;
                                try {
                                    startDate = sdf.parse(serviceTime);
                                    cal.setTime(startDate);
                                    cal.add(Calendar.HOUR, -1);

                                } catch (ParseException e)
                                {
                                    e.printStackTrace();
                                }
                                Date oneHourBack = cal.getTime();
                                String ServiceonehourbackTime= sdf.format(oneHourBack);
                                String currentTime= sdf.format(date);

                                SimpleDateFormat sdfDATE=new SimpleDateFormat("dd-MM-yyyy");
                                String currentDate=sdfDATE.format(date);
                                String servicrDate=d.getString("st_service_date");
                                Calendar calendar = Calendar.getInstance();
                                Date modifyDate = (Date) calendar.getTime();
                                Log.d("","currentTime  "+currentTime+"   currentDate  "+currentDate+"   servicrDate   "+servicrDate+"   ServiceonehourbackTime   "+ServiceonehourbackTime+"   serviceTime  "+serviceTime);
                               if(currentDate.compareTo(servicrDate)==0 &&  currentTime.compareTo(ServiceonehourbackTime)>=0 )
                                {
                                    start.setVisibility(View.VISIBLE);
                                    start.setText("  Start  ");
                                   }
                                start.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {

                                        Toast.makeText(context, "sdkjfbsdbvjhdfv", Toast.LENGTH_SHORT).show();
                                        Log.d("shgaduhhf",d.getString("servies_boy_username"));
                                        SendNotification(userId,servies_boy_username,servies_boy_phone,st_ed_name);
                                        //  SendNotificationServiceBoy();
                                        int status=1;
                                        Map hm=new HashMap<String ,String >();
                                        hm.put("status",status);
                                        hm.put("modifyDate",modifyDate);
                                        FirebaseFirestore.getInstance().collection("BookingHistory").document(d.getString("booking_id")).update(hm);
                                        gps = new GPSTracker(context ,d.getString("traking_key"),status);
                                                /*Intent intent=new Intent(ServiesBoyDashboadActivity.this, MapsActivity.class);
                                                intent.putExtra("tracker_key",d.getString("traking_key"));
                                                Log.d("","trackingid"+d.getString("traking_key"));
                                                startActivity(intent);*/
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Location");
                                        myRef.child(d.getString("traking_key")).addValueEventListener(new ValueEventListener()
                                        {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot)
                                            {
                                                try
                                                {
                                                    Latlong l = dataSnapshot.getValue(Latlong.class);
                                                    String uri = "http://maps.google.com/maps?saddr=" + Double.parseDouble(l.getLatitude()) + "," + Double.parseDouble(l.getLongitude()) + "&daddr=" + Double.parseDouble(l.getUlatitude()) + "," +  Double.parseDouble(l.getUlongitude());
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                                    startActivity(intent);

                                                }
                                                catch (NumberFormatException e)
                                                {
                                                    e.printStackTrace();
                                                }}

                                            @Override
                                            public void onCancelled(DatabaseError error)
                                            {
                                                // Failed to read value
                                                Log.w(TAG, "Failed to read value.", error.toException());
                                            }
                                        });

                                    }
                                });


                                total_amount.setText("" + d.getString("st_price_total_final"));
                                String status_assi="0";
                                if(status_assi.trim().equalsIgnoreCase("0"))
                                {booking_assigned.setVisibility(View.GONE);
                                }
                                else
                                {
                                    booking_assigned.setText("Assigned To : " + d.getString("assigned_to")   );
                                }



                                if(d.getString("st_paymenttype").equalsIgnoreCase("offline"))
                                {   Payment_mode.setText("Payment on Service");
                                    payment_status.setText("pending");
                                    payment_status.setTextColor(getResources().getColor(R.color.blue));
                                }
                                else  if(d.getString("st_paymenttype").equalsIgnoreCase("Done"))
                                {
                                    Payment_mode.setText("Online");
                                    payment_status.setText("Paid");
                                    payment_status.setTextColor(getResources().getColor(R.color.green));
                                    start.setVisibility(View.VISIBLE);
                                }
                                else
                                {   Payment_mode.setText("Online");
                                    payment_status.setText("Successful");
                                    payment_status.setTextColor(getResources().getColor(R.color.green));
                                    start.setVisibility(View.GONE);
                                }
                                if(d.getString("st_paymenttype").equalsIgnoreCase("Successful"))
                                {
                                    payment_update.setVisibility(View.GONE);
                                }
                                String url= "" + d.getString("st_car_image");
                                Log.e("urlurl","AS : "+url);
                                Glide.with(DailyServices.this)
                                        .load(url.trim()+"")
                                        .placeholder(R.drawable.rnd_logo)
                                        .into(im5);





                                payment_update.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View  view)
                                    {

                                        ///////////////////////////////////   Open . dialog..................

                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View viewPup = inflater.inflate(R.layout.payment_update, null);
                                        Button okButton = (Button) viewPup.findViewById(R.id.okButton);
                                        Button cancelButton = (Button) viewPup.findViewById(R.id.cancelButton);
                                        TextView name = (TextView) viewPup.findViewById(R.id.name);
                                        TextView total_amount = (TextView) viewPup.findViewById(R.id.total_amount);
                                        TextView servisetype = (TextView) viewPup.findViewById(R.id.servisetype);
                                        final Dialog dialog = new Dialog(DailyServices.this);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(viewPup);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        name.setText(""+d.getString("st_car_name"));
                                        total_amount.setText(""+d.getString("st_price_total_final") );
                                        servisetype.setText(""+d.getString("st_washname_tv") );
                                        okButton.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                try
                                                {

                                                    String key = d.getString("traking_key");
                                                    Log.d("", "key" + key);
                                                    int status = 0;
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference myRef = database.getReference("Location");
                                                    Latlong latlong = new Latlong();
                                                    Map hmm = new HashMap<String, String>();
                                                    hmm.put("status", status);
                                                    start.setVisibility(View.GONE);
                                                    myRef.child(key).updateChildren(hmm);
                                                    payment_status.setText("Successful");


                                                    ////////////////////  Payment Update  /////////////////////
                                                    AssignedUser p = new AssignedUser("" + d.getString("st_car_image"), "" + d.getString("st_car_name"),
                                                            "" + d.getString("st_washname_tv"), "" + d.getString("st_wash_count"),
                                                            "" + d.getString("st_user_id"), "" + d.getString("st_referenceId"),
                                                            "" + d.getString("st_price_total_final"), "Successful", "" + d.getString("st_month_count"),
                                                            "" + d.getString("st_end_date"), "" + d.getString("st_service_time"), "" + d.getString("st_ed_name")
                                                            , "" + d.getString("st_ed_mobile"), "" + d.getString("st_ed_landmark"),
                                                            "" + d.getString("st_ed_address"), "" + d.getString("st_current_date_book"),
                                                            "" + d.getString("st_service_date"), "" + d.getString("servies_boy_fId"),
                                                            "" + d.getString("servies_boy_email"), "" + d.getString("servies_boy_phone"),
                                                            "" + d.getString("servies_boy_user_id"), "" + d.getString("servies_boy_username"),
                                                            "" + d.getString("u_longitude"), "" + d.getString("u_latitude"), d.getString("booking_id"), "", null,"0","Successful","","","");

                                                    db.collection("Booking_Assigned").document(d.getId()).set(p)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                                            {
                                                                @Override
                                                                public void onSuccess(Void aVoid)
                                                                {
                                                                    db.collection("BookingHistory").document(d.getString("booking_id")).update("booking_status", "Successful");
                                                                    db.collection("BookingHistory").document(d.getString("booking_id")).update("paymenttype", "Successful").addOnSuccessListener(new OnSuccessListener<Void>()
                                                                    {
                                                                        @Override
                                                                        public void onSuccess(Void unused)
                                                                        {
                                                                            // Toast.makeText(LoginActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                    Toast.makeText(DailyServices.this, "Payment Update", Toast.LENGTH_SHORT).show();
                                                                    Intent home = new Intent(DailyServices.this, SplashActivity.class);
                                                                    startActivity(home);
                                                                    finish();

                                                                }
                                                            });
                                                    /////////////////////////// End payment Update////////////
                                                    dialog.dismiss();
                                                    start.setVisibility(View.GONE);

                                                }catch (Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                                        cancelButton.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {



                                                dialog.dismiss();


                                            }
                                        });





                                        dialog.show();


///////////////////////////////////close. dialog..................


                                    }
                                });


                                goto_live.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {


                                        String destinationLatitude=""+ d.getString("u_latitude");
                                        String destinationLongitude=""+ d.getString("u_longitude");
                                        String sourceLatitude=""+latitude;
                                        String sourceLongitude=""+ longitude;
                                        String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
                                        Log.e("mapsurl","url : "+  uri);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                        startActivity(intent);

                                    }
                                });

                                ll_bookinglist.addView(mLinearView);

                            }
                        }
                        else
                        {
                            Toast.makeText(DailyServices.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        Toast.makeText(DailyServices.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });

    }



    private  void SendNotification(String userid,String servies_boy_username,String contectNum,String username)
    {
        Calendar calendar = Calendar.getInstance();
        Date time = (Date) calendar.getTime();
        String currentime= String.valueOf(time);
        Map<String, Object> userNotification = new HashMap<>();
        SharedPreferences sharedPref = getSharedPreferences("application", MODE_PRIVATE);
        String tokenId=sharedPref.getString("tokenId", null);
        Log.d("","dvjhusd"+tokenId);

        //UtilMethods.INSTANCE.sendNotifications(ServiesBoyDashboadActivity.this, tokenId, "serviceboyName", "Confirmation message");


        db.collection("Users").whereEqualTo("uid",userid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : list)
                    {       String Message=servies_boy_username+" on the way for car washing";
                        String title="_user";
                        Log.d("","zfjhjg"+doc.getString("uitokenId"));
                        UtilMethods.INSTANCE.sendNotifications(DailyServices.this, doc.getString("uitokenId"), title, Message);
                        userNotification.put("loginType",doc.getString("loginType"));
                        userNotification.put("uid",doc.getString("uid"));
                        userNotification.put("time",currentime);
                        userNotification.put("username",servies_boy_username);
                        userNotification.put("contact_number",contectNum);
                        userNotification.put("Message",Message);
                        db.collection("Notification").document(doc.getId()).set(userNotification);

                    }}
            }
        });

       /* db.collection("Users").whereEqualTo("loginType","Admin").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {

                if (!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : list)
                    {
                        Log.d("","");
                        //send notification admin
                        String title="_admin";
                        String Message=servies_boy_username+" on the way for car washing to "+username;
                        UtilMethods.INSTANCE.sendNotifications(ServiesBoyDashboadActivity.this, doc.getString("uitokenId"), title, Message);
                        userNotification.put("loginType",doc.getString("loginType"));
                        userNotification.put("uid",doc.getString("uid"));
                        userNotification.put("username",servies_boy_username);
                        userNotification.put("contact_number",contectNum);
                        userNotification.put("time",currentime);
                        userNotification.put("Message",Message);
                        userNotification.put("type","Service Boy");
                        db.collection("Notification").document().set(userNotification);

                    }
                }
            }
        });
 */   }
  /*  private  void SendNotificationServiceBoy()
    {
        SharedPreferences sharedPref = getSharedPreferences("application", MODE_PRIVATE);
        String tokenId=sharedPref.getString("tokenId", null);

        db.collection("Add_Service_Boy").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
    {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots)
        {
            if (!queryDocumentSnapshots.isEmpty())
            {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : list)
                 {
                     if (d.getString("uitokenId").equals(tokenId))
                     {
                         UtilMethods.INSTANCE.sendNotifications(ServiesBoyDashboadActivity.this, tokenId, "jdfhdfg", "dshfiudf");

                     }
                }
            }
                }
    }).addOnFailureListener(new OnFailureListener()
        {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d("","exception"+e);

        }
    });

    }*/


    private void Logout_popup()
    {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewpop = inflater.inflate(R.layout.logout_pop, null);

        Button okButton = (Button) viewpop.findViewById(R.id.okButton);
        Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
        TextView msg = (TextView) viewpop.findViewById(R.id.msg);

        final Dialog dialog = new Dialog(DailyServices.this);

        dialog.setCancelable(false);
        dialog.setContentView(viewpop);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  msg.setText("");

        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                UtilMethods.INSTANCE.logout(DailyServices.this);

                dialog.dismiss();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    @Override
    public void onClick(View view)
    {
       /* if (view==serviceboyNotification)
        {
            startActivity(new Intent(this, ServiceBoyNotification.class));
        }*/

        /*if(view==logout_im)
        {



            Logout_popup();


        }
*/

        if(view==backprese)
        {
            finish();
        }





    }


    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(DailyServices.this)
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
                            status.startResolutionForResult(DailyServices.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e)
                        {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode)
                {

                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;


                    default:
                        break;
                }
                break;
        }

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = (Place) Autocomplete.getPlaceFromIntent(data);

                // location.setText(""+ place.getName());

                ////   ed_Address.setText( place.getAddress()+" , "+place.getName());


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                // Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

                // The user canceled the operation.

            }
        }
    }
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private void getLocation()
    {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e)
        {
            e.printStackTrace();
        }
        if (mLastLocation == null)
        {
            mLocationRequest = new LocationRequest();
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
            {

                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        } else {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            GetLOcation();
        }

    }

    private void GetLOcation() {


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(GlobalData.latitude, GlobalData.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }



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