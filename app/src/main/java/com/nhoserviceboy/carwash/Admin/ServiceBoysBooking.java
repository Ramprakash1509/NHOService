package com.nhoserviceboy.carwash.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceBoysBooking extends AppCompatActivity implements View.OnClickListener
{
    TextView texttoolbar;
    ImageView backprese;
    LinearLayout ll_bookinglist;
    String name;
    String uid;
    ProgressBar loadingPB;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_boys_booking);
        texttoolbar=findViewById(R.id.texttoolbar);
        backprese=findViewById(R.id.backprese);
        ll_bookinglist=findViewById(R.id.ll_bookinglist);
        loadingPB=findViewById(R.id.idProgressBar);
        backprese.setOnClickListener(this);
        name=getIntent().getStringExtra("name");
        uid=getIntent().getStringExtra("uid");
        //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        texttoolbar.setText(name+"'s Services List");
        Hit_get_book();
    }

    @Override
    public void onClick(View view) {
        if(view==backprese){
            finish();
        }

    }
    private void Hit_get_book() {


        db = FirebaseFirestore.getInstance();
        db.collection("BookingHistory").whereEqualTo("assigned_to_uid",uid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            ll_bookinglist.removeAllViews();
                            for (DocumentSnapshot d : list) {

                                Log.e("Nodate",""+ "hitShow");

                                LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View mLinearView = inflater2.inflate(R.layout.admin_all_booking_abapter, null);
                                mLinearView.setPadding(0,15,0,15);
                                final TextView booked_on,user_email_tv,userlandmark,user_name_tv , usernumber , useraddress ,     payment_status, Payment_mode,tv_assigned,Booking_Date,name,payment_type ,servisetype, Booking_time , total_amount  , wash_Count;
                                CircleImageView im5;
                                RelativeLayout ri_main;

                                payment_type =  mLinearView.findViewById(R.id.payment_type);
                                ri_main =  mLinearView.findViewById(R.id.ri_main);
                                im5 =  mLinearView.findViewById(R.id.im5);
                                name =  mLinearView.findViewById(R.id.name);
                                servisetype =  mLinearView.findViewById(R.id.servisetype);
                                Booking_time =  mLinearView.findViewById(R.id.Booking_time);
                                booked_on =  mLinearView.findViewById(R.id.booked_on);
                                Booking_Date =  mLinearView.findViewById(R.id.Booking_Date);
                                total_amount =  mLinearView.findViewById(R.id.total_amount);
                                wash_Count =  mLinearView.findViewById(R.id.wash_Count);
                                tv_assigned =  mLinearView.findViewById(R.id.tv_assigned);
                                payment_status =  mLinearView.findViewById(R.id.payment_status);
                                Payment_mode =  mLinearView.findViewById(R.id.Payment_mode);
                                user_name_tv =  mLinearView.findViewById(R.id.user_name_tv);
                                usernumber =  mLinearView.findViewById(R.id.usernumber);
                                useraddress =  mLinearView.findViewById(R.id.useraddress);
                                userlandmark =  mLinearView.findViewById(R.id.userlandmark);
                                user_email_tv =  mLinearView.findViewById(R.id.user_email_tv);
                                tv_assigned.setVisibility(View.GONE);
                                user_name_tv.setText(""+   d.getString("u_name"));
                                usernumber.setText(""+   d.getString("u_phone"));
                                useraddress.setText(""+   d.getString("u_Address"));
                                userlandmark.setText(""+   d.getString("u_Landmark"));
                                user_email_tv.setText(""+   d.getString("u_email"));

                                wash_Count.setText("Wash Count : " + d.getString("wash_count")  );
                                servisetype.setText("" + d.getString("washname_tv")  );

                                booked_on.setText(" " + d.getString("service_date")  );
                                Booking_Date.setText(" " + d.getString("current_date_book") );
                                name.setText("" + d.getString("car_name")  );
                                Booking_time.setText(   " " + d.getString("service_time"));
                                total_amount.setText("" + d.getString("price_total_final")  );
                                if(d.getString("paymenttype").equalsIgnoreCase("offline")){
                                    Payment_mode.setText("Payment on Service");
                                    payment_status.setText("pending");
                                    payment_status.setTextColor(ServiceBoysBooking.this.getResources().getColor(R.color.yallow));
                                }else {

                                    Payment_mode.setText("Online");
                                    payment_status.setText("successful");
                                    payment_status.setTextColor(ServiceBoysBooking.this.getResources().getColor(R.color.green));

                                }


                                String status_assi=""+d.getString("assigned_to");
//
//                                if(!status_assi.trim().equalsIgnoreCase("0")){
//
//                                    tv_assigned.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.red));
//                                    tv_assigned.setText("   Assigned   ");
//                                }else {
//
//
//
//                                    tv_assigned.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.green));
//                                    tv_assigned.setText("   Assigned To   ");
//                                }


//                                tv_assigned.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//
//                                        String status_assi=""+d.getString("assigned_to");
//
//
//                                        if(status_assi.trim().equalsIgnoreCase("0")){
//
//                                            /*   user_name_tv.setText(""+   d.getString("u_name"));
//                                usernumber.setText(""+   d.getString("u_phone"));
//                                useraddress.setText(""+   d.getString("u_Address"));
//                                userlandmark.setText(""+   d.getString("u_Landmark"));
//                                user_email_tv.setText(""+   d.getString("u_email"));*/
//
//                                            Intent i =new Intent(getActivity(), AssignedBookingActivity.class);
//                                            i.putExtra("car_image","" +d.getString("car_image"));
//                                            i.putExtra("car_name","" +d.getString("car_name"));
//                                            i.putExtra("washname_tv","" +d.getString("washname_tv"));
//                                            i.putExtra("wash_count","" +d.getString("wash_count"));
//                                            i.putExtra("user_id","" +d.getString("user_id"));
//                                            i.putExtra("referenceId","" +d.getString("referenceId"));
//                                            i.putExtra("price_total_final","" +d.getString("price_total_final"));
//                                            i.putExtra("paymenttype","" +d.getString("paymenttype"));
//                                            i.putExtra("month_count","" +d.getString("month_count"));
//                                            i.putExtra("end_date","" +d.getString("end_date"));
//                                            i.putExtra("service_time","" +d.getString("service_time"));
//                                            i.putExtra("ed_name","" +d.getString("ed_name"));
//                                            i.putExtra("ed_mobile","" +d.getString("ed_mobile"));
//                                            i.putExtra("ed_landmark","" +d.getString("ed_landmark"));
//                                            i.putExtra("ed_address","" +d.getString("ed_address"));
//                                            i.putExtra("current_date_book","" +d.getString("current_date_book"));
//                                            i.putExtra("service_date","" +d.getString("service_date"));
//                                            i.putExtra("ed_email","" +d.getString("ed_email"));
//                                            i.putExtra("u_latitude","" +d.getString("u_latitude"));
//                                            i.putExtra("u_longitude","" +d.getString("u_longitude"));
//
//                                            i.putExtra("u_name","" +d.getString("u_name"));
//                                            i.putExtra("u_phone","" +d.getString("u_phone"));
//                                            i.putExtra("u_Address","" +d.getString("u_Address"));
//                                            i.putExtra("u_Landmark","" +d.getString("u_Landmark"));
//                                            i.putExtra("u_email","" +d.getString("u_email"));
//                                            i.putExtra("bookingid","" +d.getId());
//                                            startActivity(i);
//
//                                        }else {
//
//                                            Toast.makeText(getActivity(), "This booking already assigned to Mr. "+ status_assi, Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                });

                                if(d.getString("paymenttype").equalsIgnoreCase("offline")){
                                    payment_type.setText("" + d.getString("paymenttype")    );

                                }else {

                                    payment_type.setText("" + d.getString("paymenttype")  +"  Ref. Id : "+d.getString("referenceId")   );

                                }

                                String url= "" + d.getString("car_image");

                                Log.e("urlurl","AS : "+url);

                                Glide.with(ServiceBoysBooking.this)
                                        .load(url.trim()+"")
                                        .placeholder(R.drawable.rnd_logo)
                                        .into(im5);

                                ri_main.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });

                                ll_bookinglist.addView(mLinearView);

                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(ServiceBoysBooking.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ServiceBoysBooking.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}