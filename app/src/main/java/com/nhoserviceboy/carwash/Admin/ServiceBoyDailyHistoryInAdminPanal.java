package com.nhoserviceboy.carwash.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.nhoserviceboy.carwash.Activity.DailyServiceHistory;
import com.nhoserviceboy.carwash.Activity.DailyServices;
import com.nhoserviceboy.carwash.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ServiceBoyDailyHistoryInAdminPanal extends Fragment {
    TextView texttoolbar;
    ImageView backprese;
    LinearLayout ll_bookinglist;
    String name;
    ProgressBar loadingPB;
    private FirebaseFirestore db;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_service_boy_daily_history_in_admin_panal, container, false);
    GetId(v);
       return v;
    }

    private void GetId(View v)
    {  //dailyCleaning

        ll_bookinglist=v.findViewById(R.id.ll_bookinglist);
        loadingPB=v.findViewById(R.id.idProgressBar);
        uid = getArguments().getString("uid");

        Hit_get_book();
    }
    private void Hit_get_book()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("BookingHistory").whereEqualTo("assigned_to_uid",uid).whereEqualTo("booking_status","Successful").whereEqualTo("dailybooking","dailyCleaning").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            ll_bookinglist.removeAllViews();
                            for (DocumentSnapshot d : list)
                            {
                                Log.e("Nodate",""+ "hitShow");
                                LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater)getActivity(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                                    payment_status.setTextColor(getActivity().getResources().getColor(R.color.yallow));
                                }else
                                {
                                    Payment_mode.setText("Online");
                                    payment_status.setText("successful");
                                    payment_status.setTextColor(getActivity().getResources().getColor(R.color.green));
                                }
                                String status_assi=""+d.getString("assigned_to");
                               if(d.getString("paymenttype").equalsIgnoreCase("offline"))
                               {
                                    payment_type.setText("" + d.getString("paymenttype"));
                               }
                               else
                               {
                                   payment_type.setText("" + d.getString("paymenttype")  +"  Ref. Id : "+d.getString("referenceId")   );
                               }

                                String url= "" + d.getString("car_image");

                                Log.e("urlurl","AS : "+url);

                                Glide.with(getActivity())
                                        .load(url.trim()+"")
                                        .placeholder(R.drawable.rnd_logo)
                                        .into(im5);
                                ri_main.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                       // Intent i=new Intent(getActivity(), DailyServiceHistory.class);
                                        Intent i=new Intent(getActivity(), ServiceBoyAttend.class);
                                        i.putExtra("serviceId",d.getId());
                                        startActivity(i);
                                    }
                                });
                                ll_bookinglist.addView(mLinearView);

                            }

                        } else
                        {
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                      Toast.makeText(getActivity(), "Fail to get the data .", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}