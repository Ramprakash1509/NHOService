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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.Orders_Details_Activity;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NextBookingFragment extends Fragment {

    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;
    String user_id="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_booking, container, false);

        GetID(v);

        return v;
    }


    private void GetID(View v) {

        SharedPreferences myPreferences =   getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,getActivity().MODE_PRIVATE);
        user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");

        loadingPB = v.findViewById(R.id.idProgressBar);
        ll_bookinglist = v.findViewById(R.id.ll_bookinglist);

        db = FirebaseFirestore.getInstance();
        db.collection("BookingHistory").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                              /*  if(d.getString("user_id").equalsIgnoreCase(user_id.toString().trim())){*/

                                    Log.e("Nodate",""+ "hitShow");

                                    LayoutInflater inflater2 = null;
                                    inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.booking_abapter_item, null);

                                    final TextView booked_on,Booking_Date,name,payment_type ,servisetype, Booking_time , total_amount  , wash_Count;
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

                                    wash_Count.setText("Wash Count : " + d.getString("wash_count")  );
                                    servisetype.setText("" + d.getString("washname_tv")  );

                                    booked_on.setText(" " + d.getString("service_date")  );
                                    Booking_Date.setText(" " + d.getString("current_date_book") );

                                    name.setText("" + d.getString("car_name")  );
                                    Booking_time.setText(   " " + d.getString("service_time"));
                                    total_amount.setText("" + d.getString("price_total_final")   );


                                   /* if(d.getString("paymenttype").equalsIgnoreCase("null")){



                                    }*/


                                    if(d.getString("paymenttype").equalsIgnoreCase("offline")){
                                        payment_type.setText("" + d.getString("paymenttype")    );

                                    }else {

                                        payment_type.setText("" + d.getString("paymenttype")  +"  Ref. Id : "+d.getString("referenceId")   );

                                    }



                                    String url= "" + d.getString("car_image");

                                    Log.e("urlurl","AS : "+url);

                                    Glide.with(getActivity())
                                            .load(url.trim()+"")
                                            .placeholder(R.drawable.rnd_logo)
                                            .into(im5);

                                    ri_main.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i =new Intent (getActivity(), Orders_Details_Activity.class);
                                            i.putExtra("carimage","" +d.getString("car_image"));
                                            i.putExtra("carname","" +d.getString("car_name"));
                                            i.putExtra("carprice","" +d.getString("price_total_final"));
                                            i.putExtra("date","" +d.getString("service_date"));
                                            i.putExtra("time","" +d.getString("service_time"));
                                            i.putExtra("serviestype","" +d.getString("washname_tv"));
                                            i.putExtra("user_id","" +d.getString("user_id"));
                                            i.putExtra("mobile","" +d.getString("ed_mobile"));
                                            i.putExtra("name","" +d.getString("ed_name"));
                                            i.putExtra("Landmark","" +d.getString("ed_landmark"));
                                            i.putExtra("Address","" +d.getString("ed_address"));
                                            i.putExtra("email","" +d.getString("ed_email"));
                                            i.putExtra("car_name_model","" +d.getString("car_name"));
                                            i.putExtra("price_final","" +d.getString("price_total_final"));
                                            i.putExtra("service","" +d.getString("price_total_final"));
                                            i.putExtra("type_select","" +d.getString("type_select"));
                                            startActivity(i);

                                        }
                                    });

                                    ll_bookinglist.addView(mLinearView);

                               /* }else {

                                    Log.e("Nodate",""+ "Nodate");

                                }*/

                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();

            }
        });

    }


}