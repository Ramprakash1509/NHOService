package com.nhoserviceboy.carwash.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.Orders_Details_Activity;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TodaysBookingFragment extends Fragment
{

    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;
    String user_id="";
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_booking, container, false);

        GetID(v);

        return v;
    }


    @Override
    public void onResume()
    {

        Hit_get_book();

        super.onResume();
    }

    private void GetID(View v)
    {

        SharedPreferences myPreferences =   getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,getActivity().MODE_PRIVATE);
        user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");

        loadingPB = v.findViewById(R.id.idProgressBar);
        ll_bookinglist = v.findViewById(R.id.ll_bookinglist);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
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

    private void Hit_get_book()
    {


        db = FirebaseFirestore.getInstance();
        db.collection("BookingHistory").whereEqualTo("booking_status","pending").whereEqualTo("dailybooking","").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
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

                                if (getActivity() != null)
                                {   LayoutInflater inflater2 = null;
                                    inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.admin_all_booking_abapter, null);
                                    mLinearView.setPadding(0, 15, 0, 15);
                                    final TextView booked_on,modelNuber, user_email_tv, userlandmark, user_name_tv, usernumber, useraddress, payment_status, Payment_mode, tv_assigned, Booking_Date, name, payment_type, servisetype, Booking_time, total_amount, wash_Count;
                                    CircleImageView im5;
                                    RelativeLayout ri_main;

                                    payment_type = mLinearView.findViewById(R.id.payment_type);
                                    modelNuber = mLinearView.findViewById(R.id.modelNuber);

                                    ri_main = mLinearView.findViewById(R.id.ri_main);
                                    im5 = mLinearView.findViewById(R.id.im5);
                                    name = mLinearView.findViewById(R.id.name);
                                    servisetype = mLinearView.findViewById(R.id.servisetype);
                                    Booking_time = mLinearView.findViewById(R.id.Booking_time);
                                    booked_on = mLinearView.findViewById(R.id.booked_on);
                                    Booking_Date = mLinearView.findViewById(R.id.Booking_Date);
                                    total_amount = mLinearView.findViewById(R.id.total_amount);
                                    wash_Count = mLinearView.findViewById(R.id.wash_Count);
                                    tv_assigned = mLinearView.findViewById(R.id.tv_assigned);
                                    payment_status = mLinearView.findViewById(R.id.payment_status);
                                    Payment_mode = mLinearView.findViewById(R.id.Payment_mode);
                                    user_name_tv = mLinearView.findViewById(R.id.user_name_tv);
                                    usernumber = mLinearView.findViewById(R.id.usernumber);
                                    useraddress = mLinearView.findViewById(R.id.useraddress);
                                    userlandmark = mLinearView.findViewById(R.id.userlandmark);
                                    user_email_tv = mLinearView.findViewById(R.id.user_email_tv);
                                    modelNuber.setText(d.getString("carModelNumber"));
                                    user_name_tv.setText("" + d.getString("u_name"));
                                    usernumber.setText("" + d.getString("u_phone"));
                                    useraddress.setText("" + d.getString("u_Address"));
                                    userlandmark.setText("" + d.getString("u_Landmark"));
                                    user_email_tv.setText("" + d.getString("u_email"));


                                    ///////u_name , u_email , u_Address , u_Landmark  , u_latitude   , u_longitude


                                    wash_Count.setText("Wash Count : " + d.getString("wash_count"));
                                    servisetype.setText("" + d.getString("washname_tv"));

                                    booked_on.setText(" " + d.getString("service_date"));
                                    Booking_Date.setText(" " + d.getString("current_date_book"));

                                    name.setText("" + d.getString("car_name"));
                                    Booking_time.setText(" " + d.getString("service_time"));
                                    total_amount.setText("" + d.getString("price_total_final"));


                                    if (d.getString("paymenttype").equalsIgnoreCase("offline")) {

                                        Payment_mode.setText("Payment on Service");
                                        payment_status.setText("pending");
                                        payment_status.setTextColor(getActivity().getResources().getColor(R.color.blue));


                                    } else {

                                        Payment_mode.setText("Online");
                                        payment_status.setText("Paid");
                                        payment_status.setTextColor(getActivity().getResources().getColor(R.color.green));


                                    }


                                    String status_assi = "" + d.getString("assigned_to");

                                    if (!status_assi.trim().equalsIgnoreCase("0")) {

                                        tv_assigned.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.red));
                                        tv_assigned.setText("   Assigned   ");
                                    } else {


                                        tv_assigned.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.green));
                                        tv_assigned.setText("   Assigned To   ");
                                    }


                                    tv_assigned.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view)
                                        {



                                            if (status_assi.trim().equalsIgnoreCase("0"))
                                            {

                                         Log.d("sdf34","1");
                                                Intent i = new Intent(getActivity(), AssignedBookingActivity.class);
                                                Log.d("","booking_status"+d.getString("booking_status"));
                                                i.putExtra("booking_status",d.getString("booking_status"));
                                                i.putExtra("car_image", "" + d.getString("car_image"));
                                                i.putExtra("car_name", "" + d.getString("car_name"));
                                                i.putExtra("washname_tv", "" + d.getString("washname_tv"));
                                                i.putExtra("wash_count", "" + d.getString("wash_count"));
                                                i.putExtra("user_id", "" + d.getString("user_id"));
                                                i.putExtra("dailybooking", "" +  d.getString("dailybooking"));
                                                i.putExtra("exdate", "" +  d.getString("exdate"));
                                                i.putExtra("referenceId", "" + d.getString("referenceId"));
                                                i.putExtra("price_total_final", "" + d.getString("price_total_final"));
                                                i.putExtra("paymenttype", "" + d.getString("paymenttype"));
                                                i.putExtra("month_count", "" + d.getString("month_count"));
                                                i.putExtra("end_date", "" + d.getString("end_date"));
                                                i.putExtra("service_time", "" + d.getString("service_time"));
                                                i.putExtra("ed_name", "" + d.getString("ed_name"));
                                                i.putExtra("ed_mobile", "" + d.getString("ed_mobile"));
                                                i.putExtra("ed_landmark", "" + d.getString("ed_landmark"));
                                                i.putExtra("ed_address", "" + d.getString("ed_address"));
                                                i.putExtra("current_date_book", "" + d.getString("current_date_book"));
                                                i.putExtra("service_date", "" + d.getString("service_date"));
                                                i.putExtra("ed_email", "" + d.getString("ed_email"));
                                                i.putExtra("u_latitude", "" + d.getString("u_latitude"));
                                                i.putExtra("u_longitude", "" + d.getString("u_longitude"));
                                                i.putExtra("u_name", "" + d.getString("u_name"));
                                                i.putExtra("u_phone", "" + d.getString("u_phone"));
                                                Log.d("", "werywteyr" + d.getString("u_phone"));
                                                i.putExtra("u_Address", "" + d.getString("u_Address"));
                                                i.putExtra("u_Landmark", "" + d.getString("u_Landmark"));
                                                i.putExtra("u_email", "" + d.getString("u_email"));
                                                i.putExtra("bookingid", "" + d.getId());
                                                i.putExtra("assigned_to_uid", d.getString("assigned_to_uid"));
                                                i.putExtra("assigned_to", d.getString("assigned_to"));
                                                i.putExtra("carModel", d.getString("carModelNumber"));
                                                i.putExtra("packageId", d.getString("packageId"));
                                                i.putExtra("carModelNumber",d.getString("carModelNumber"));
                                                Log.d("", "fdghjfdgdu" + d.getString("assigned_to_uid"));

                                                startActivity(i);
                                                getActivity().finish();

                                            }
                                            else {

                                                Toast.makeText(getActivity(), "This booking already assigned to Mr. " + status_assi, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                    if (d.getString("paymenttype").equalsIgnoreCase("offline"))
                                    {
                                        payment_type.setText("" + d.getString("paymenttype"));

                                    } else {

                                        payment_type.setText("" + d.getString("paymenttype") + "  Ref. Id : " + d.getString("referenceId"));

                                    }

                                    String url = "" + d.getString("car_image");

                                    Log.e("urlurl", "AS : " + url);

                                    Glide.with(getActivity())
                                            .load(url.trim() + "")
                                            .placeholder(R.drawable.rnd_logo)
                                            .into(im5);

                                    ri_main.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    });

                                    ll_bookinglist.addView(mLinearView);

                                }
                            }

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                            loadingPB.setVisibility(View.GONE);
                        }
                    }

                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                      Log.e("","feffs"+e);
                        Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}