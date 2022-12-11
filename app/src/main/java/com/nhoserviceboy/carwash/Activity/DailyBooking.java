package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Admin.MapuserActivity;
import com.nhoserviceboy.carwash.Admin.Model.Latlong;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class DailyBooking extends AppCompatActivity
{
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist,daily;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_booking);
        db = FirebaseFirestore.getInstance();
        loadingPB = findViewById(R.id.idProgressBar);
        ll_bookinglist = findViewById(R.id.ll_bookinglist);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);
                Hit_get_book();

            }
        });
        Hit_get_book();
    }
    public void Hit_get_book()
    {


        db.collection("BookingHistory").whereEqualTo("dailybooking","dailyCleaning")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (!queryDocumentSnapshots.isEmpty())
                        {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            ll_bookinglist.removeAllViews();
                            for (DocumentSnapshot d : list)
                            {


                                if (getApplicationContext() != null )
                                {


                                    Log.e("Nodate", "" + "hitShow");
                                    LayoutInflater inflater2 = null;
                                    inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.daily_boking_layout, null);
                                    final TextView otp,carmodel,onlinepayment, goLocation,payment_status, Payment_mode, booked_on, Booking_Date, name, booking_assigned,daily_booking_count, payment_type, servisetype, Booking_time, total_amount, wash_Count;
                                    CircleImageView im5;
                                    RelativeLayout ri_main;
                                    LinearLayout linea_user_detail;
                                    goLocation=mLinearView.findViewById(R.id.go);
                                    payment_type = mLinearView.findViewById(R.id.payment_type);
                                    ri_main = mLinearView.findViewById(R.id.ri_main);
                                    im5 = mLinearView.findViewById(R.id.im5);
                                    name = mLinearView.findViewById(R.id.name);
                                    otp = mLinearView.findViewById(R.id.otp);
                                    booking_assigned = mLinearView.findViewById(R.id.booking_assigned);
                                    carmodel = mLinearView.findViewById(R.id.carmodel);

                                    servisetype = mLinearView.findViewById(R.id.servisetype);
                                    Booking_time = mLinearView.findViewById(R.id.Booking_time);
                                    booked_on = mLinearView.findViewById(R.id.booked_on);
                                    Booking_Date = mLinearView.findViewById(R.id.Booking_Date);
                                    total_amount = mLinearView.findViewById(R.id.total_amount);
                                    wash_Count = mLinearView.findViewById(R.id.wash_Count);
                                    linea_user_detail = mLinearView.findViewById(R.id.linea_user_detail);
                                    Payment_mode = mLinearView.findViewById(R.id.Payment_mode);
                                    payment_status = mLinearView.findViewById(R.id.payment_status);
                                    onlinepayment = mLinearView.findViewById(R.id.onlinepayment);
                                    linea_user_detail.setVisibility(View.GONE);
                                    wash_Count.setVisibility(View.GONE);
                                    carmodel.setText(d.getString("carModelNumber"));
                                    wash_Count.setText("Wash Count : " + d.getString("wash_count"));
                                    servisetype.setText("" + d.getString("washname_tv"));
                                    booked_on.setText(" " + d.getString("service_date"));
                                    Booking_Date.setText(" " + d.getString("current_date_book"));
                                    name.setText("" + d.getString("car_name"));
                                    Booking_time.setText(" " + d.getString("service_time"));
                                    total_amount.setText("₹. " + d.getString("price_total_final"));
                                    String status_assi = "" + d.getString("assigned_to");
                                    String tracking_key=d.getString("traking_key");
                                    String date=d.getString("startDate");
                                    String servicedate=d.getString("service_date");
                                    String exdate=d.getString("exdate");
                                    Log.d("yrweiu47643",servicedate+"  "+exdate);
                                    Date date1=new Date();
                                    SimpleDateFormat sdfDATE=new SimpleDateFormat("dd-MM-yyyy");
                                    String currentDate=sdfDATE.format(date1);
                                    SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");

                                    //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

                                    Date time1= null;
                                    Date time2= null;
                                    try {
                                        time1 = newFormat.parse(currentDate);
                                        time2 = newFormat.parse(exdate);
                                        Date startDateValue = new Date(String.valueOf(time1));
                                        Date endDateValue = new Date(String.valueOf(time2));
                                        long diff = endDateValue.getTime() - startDateValue.getTime();
                                        long seconds = diff / 1000;
                                        long minutes = seconds / 60;
                                        long hours = minutes / 60;
                                        long days = (hours / 24)-1;



                                        Date startdate= null;
                                        Date enddate= null;
                                        startdate = newFormat.parse(currentDate);
                                        enddate = newFormat.parse(servicedate);
                                        Date startDateValue1 = new Date(String.valueOf(startdate));
                                        Date endDateValue1 = new Date(String.valueOf(enddate));
                                        long diff1 = endDateValue1.getTime() - startDateValue1.getTime();
                                        long seconds1= diff1 / 1000;
                                        long minutes1 = seconds1 / 60;
                                        long hours1 = minutes1 / 60;
                                        long days1 = (hours1 / 24);
                                        Log.d("yuw",""+days1);

                                        daily_booking_count = mLinearView.findViewById(R.id.daily_booking_count);
                                        if (days1<=0)
                                        {
                                            if (days>=0)
                                            {
                                                daily_booking_count.setText("This package is  booked for  " + servicedate + "  and  valid for  " + days + "  days");

                                                if (d.getString("pin")!=null)
                                                {  otp.setVisibility(View.VISIBLE);
                                                   otp.setText(" OTP "+d.getString("pin"));
                                                }


                                            }
                                            else
                                            {
                                                daily_booking_count.setText("This package Validity is expired ");
                                            }
                                        }
                                        else
                                        {
                                            daily_booking_count.setVisibility(View.GONE);
                                        }

                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                    if(tracking_key==null)
                                    {
                                        goLocation.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Location");
                                        myRef.child(tracking_key).addValueEventListener(new ValueEventListener()
                                        {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot)
                                            {
                                                Latlong l = dataSnapshot.getValue(Latlong.class);
                                                int status =l.getStatus();
                                                Log.d("","status"+status);
                                                if(status==1)
                                                {
                                                    goLocation.setVisibility(View.VISIBLE);
                                                    onlinepayment.setVisibility(View.VISIBLE);
                                                    goLocation.setOnClickListener(new View.OnClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(View v)
                                                        {
                                                            Intent i=new Intent(DailyBooking.this, MapuserActivity.class);
                                                            i.putExtra("usertrckingkey",tracking_key);
                                                            startActivity(i);
                                                        }
                                                    });
                                                }
                                              /* if(d.getString("paymenttype").equalsIgnoreCase("Done"))
                                              {
                                                  onlinepayment.setVisibility(View.GONE);
                                                }*/
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error)
                                            {

                                            }
                                        });

                                    }




                                    if (status_assi.trim().equalsIgnoreCase("0"))
                                    {

                                        booking_assigned.setVisibility(View.GONE);

                                    }
                                    else
                                    {
                                        booking_assigned.setText("Assigned To : " + d.getString("assigned_to"));

                                    }


                                    /* String status_assi=""+d.getString("assigned_to");*/


                                    if (d.getString("paymenttype").equalsIgnoreCase("offline"))
                                    {
                                        Payment_mode.setText("Payment on Service");
                                        payment_status.setText("pending");
                                        payment_status.setTextColor(getResources().getColor(R.color.blue));


                                    }
                                    else
                                    {

                                        onlinepayment.setVisibility(View.GONE);
                                        Payment_mode.setText("Online");
                                        payment_status.setText("Paid");
                                        payment_status.setTextColor(getResources().getColor(R.color.green));

                                    }

                                    onlinepayment.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            //this cod eis written form payment successful
                                            loadingPB.setVisibility(View.GONE);
                                            String flag = "1";
                                            Map<String, Object> paymentstatus = new HashMap<>();
                                            paymentstatus.put("flag", flag);
                                            paymentstatus.put("st_paymenttype", "Done");

                                            db.collection("Booking_Assigned").whereEqualTo("booking_id", d.getId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                    if (!queryDocumentSnapshots.isEmpty())
                                                    {
                                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                        for (DocumentSnapshot doc : list)
                                                        {  db.collection("Booking_Assigned").document(doc.getId()).update(paymentstatus);
                                                            Toast.makeText(DailyBooking.this, "Payment Update", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });


                                        }

                                    });


                                    String url = "" + d.getString("car_image");

                                    Log.e("urlurl", "AS : " + url);

                                    Glide.with(DailyBooking.this)
                                            .load(url.trim() + "")
                                            .placeholder(R.drawable.rnd_logo)
                                            .into(im5);




                                    ll_bookinglist.addView(mLinearView);



                                }
                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(DailyBooking.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d("","exception"+e);
                        Toast.makeText(DailyBooking.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });

      /*  db.collection("BookingHistory").whereEqualTo("dailybooking","dailyCleaning")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        ll_bookinglist.removeAllViews();
                        for (DocumentSnapshot d : list)
                        {
                            Log.d("dailybooking      ",d.getString("dailybooking"));
                            if (d.getString("dailybooking").equalsIgnoreCase("dailyCleaning"))
                            {
                                daily.setVisibility(View.VISIBLE);
                                daily.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(getActivity(), DailyBooking.class));
                                    }
                                });


                            }

                        }
                    }
                });*/
    }



}