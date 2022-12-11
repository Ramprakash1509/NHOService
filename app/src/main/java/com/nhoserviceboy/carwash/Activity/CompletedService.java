package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Admin.Model.AssignedUser;
import com.nhoserviceboy.carwash.Admin.Model.Latlong;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.ServiesBoy.ServiesBoyDashboadActivity;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.GPSTracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompletedService extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    ImageView backprese;
    LinearLayout ll_bookinglist;
    TextView serviceboyNotification;
    TextView toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_service);
        db = FirebaseFirestore.getInstance();
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(this);
        loadingPB =  findViewById(R.id.idProgressBar);
        ll_bookinglist=findViewById(R.id.ll_bookinglist);
        toolbar=findViewById(R.id.texttoolbar);
        toolbar.setOnClickListener(this);
        toolbar.setText("Completed services");
        serviceboyNotification=findViewById(R.id.serviceboyNotification);
        serviceboyNotification.setOnClickListener(this);
        serviceboyNotification.setVisibility(View.GONE);
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

    @Override
    public void onClick(View v)
    {
        if(v==backprese)
        {
            finish();
        }

    }
    private void GetServiesList()
    {

        SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,MODE_PRIVATE);
        String user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");
        Log.d("","cxmnvj"+user_id);

        db = FirebaseFirestore.getInstance();
        db.collection("Booking_Assigned").whereEqualTo("servies_boy_fId",user_id.toString().trim()).whereEqualTo("booking_status","Successful").get()
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
                           {   Log.e("serviesasasasa",""+ d.getString("servies_boy_user_id")  +" Serview _user_id  :  "+  user_id.toString().trim() );
                               LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View mLinearView = inflater2.inflate(R.layout.booking_abapter_item_servies, null);
                                final TextView start,model,notAttended, payment_update,Payment_mode, payment_status,user_email_tv,userlandmark,user_name_tv , usernumber ,goto_live, useraddress,booked_on,Booking_Date,name,booking_assigned,payment_type ,servisetype, Booking_time , total_amount  , wash_Count;
                                CircleImageView im5;
                                RelativeLayout ri_main;
                                notAttended=mLinearView.findViewById(R.id.notAttende);
                                start=mLinearView.findViewById(R.id.start);
                                start.setVisibility(View.VISIBLE);
                                start.setText("Completed");
                                payment_type =  mLinearView.findViewById(R.id.payment_type);
                                ri_main =  mLinearView.findViewById(R.id.ri_main);
                                im5 =  mLinearView.findViewById(R.id.im5);
                                name =  mLinearView.findViewById(R.id.name);
                               model =  mLinearView.findViewById(R.id.model);
                                booking_assigned =  mLinearView.findViewById(R.id.booking_assigned);
                                goto_live =  mLinearView.findViewById(R.id.goto_live);
                                goto_live.setVisibility(View.GONE);
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
                               model.setText(d.getString("carModelNumber"));

                                user_name_tv.setText(""+   d.getString("st_ed_name"));
                                usernumber.setText(""+   d.getString("st_ed_mobile"));
                                useraddress.setText(""+   d.getString("st_ed_address"));
                                userlandmark.setText(""+   d.getString("st_ed_landmark"));
                                /* user_email_tv.setText(""+   d.getString("u_email"));*/

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

                                ////////////////////////////////////////////////////////////////////////////////////
                                String serviceTime=d.getString("st_service_time");
                                Date date=new Date();
                                //SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");

                                /////////////////////////////////////////////////////////////////////
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





                                ////////////////////////////////////////////////////////////////////
                                String currentTime= sdf.format(date);


                                SimpleDateFormat sdfDATE=new SimpleDateFormat("dd-MM-yyyy");
                                String currentDate=sdfDATE.format(date);
                                String servicrDate=d.getString("st_service_date");
                                Calendar calendar = Calendar.getInstance();
                                Date modifyDate = (Date) calendar.getTime();
                                Log.d("","currentTime  "+currentTime+"   currentDate  "+currentDate+"   servicrDate   "+servicrDate+"   ServiceonehourbackTime   "+ServiceonehourbackTime+"   serviceTime  "+serviceTime);


                                /////////////////////////////////////////////////////////////////////////////////////////////
                                Log.d("","eerrt"+d.getString("flag"));
                                if(d.getString("flag").equals("1") && d.getString("st_paymenttype").equals("offline"))
                                {
                                    payment_update.setVisibility(View.VISIBLE);

                                }

                                total_amount.setText("" + d.getString("st_price_total_final"));


                                String status_assi="0";

                                if(status_assi.trim().equalsIgnoreCase("0"))
                                {

                                    booking_assigned.setVisibility(View.GONE);

                                }
                                else
                                {

                                    booking_assigned.setText("Assigned To : " + d.getString("assigned_to")   );

                                }

                                Log.e("paymenttype",""+ d.getString("st_paymenttype"));



                                if(d.getString("st_paymenttype").equalsIgnoreCase("offline"))
                                {

                                    Payment_mode.setText("Payment on Service");
                                    payment_status.setText("pending");
                                    payment_status.setTextColor(getResources().getColor(R.color.blue));

                                }
                                else  if(d.getString("st_paymenttype").equalsIgnoreCase("Done"))
                                {
                                    Payment_mode.setText("Payment on Service");
                                    payment_status.setText("Paid");
                                    payment_status.setTextColor(getResources().getColor(R.color.green));
                                    payment_update.setVisibility(View.VISIBLE);

                                }
                                else
                                {
                                    Payment_mode.setText("Online");
                                    payment_status.setText("Successful");
                                    payment_status.setTextColor(getResources().getColor(R.color.green));


                                }
                                if(d.getString("st_paymenttype").equalsIgnoreCase("Successful"))
                                {
                                    payment_update.setVisibility(View.GONE);
                                }

                                     ll_bookinglist.addView(mLinearView);



                            }
                           }

                        else
                        {

                            Toast.makeText(CompletedService.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        Toast.makeText(CompletedService.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}