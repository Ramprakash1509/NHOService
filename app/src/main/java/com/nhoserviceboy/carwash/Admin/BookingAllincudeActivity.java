package com.nhoserviceboy.carwash.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingAllincudeActivity extends AppCompatActivity    {

    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;
    String user_id="";
    ImageView backprese;
    TextView texttoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_incode);
        getId();
    }
    private void getId() {

        texttoolbar=findViewById(R.id.texttoolbar);

        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                finish();
            }
        });

        texttoolbar.setText("User Details");


        SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");

        loadingPB =  findViewById(R.id.idProgressBar);
        ll_bookinglist = findViewById(R.id.ll_bookinglist);


        GetSetValue();
    }

    private void GetSetValue() {

      String userId=getIntent().getStringExtra("userId");
      String packageId=getIntent().getStringExtra("packageId");

        db = FirebaseFirestore.getInstance();
        db.collection("BookingHistory").whereEqualTo("user_id",userId).whereEqualTo("packageId",packageId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (!queryDocumentSnapshots.isEmpty())
                        {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list)
                            {



                                    Log.e("Nodate", "" + "hitShow");


                                    LayoutInflater inflater2 = null;
                                    inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.booking_payment_abapter_item, null);

                                    final TextView user_email_tv, userlandmark, user_name_tv,
                                            usernumber, useraddress, payment_status, name,
                                            servisetype, total_amount,carModel;

                                    CircleImageView im5;


                                    im5 = mLinearView.findViewById(R.id.im5);
                                    carModel = mLinearView.findViewById(R.id.carModel);

                                    payment_status = mLinearView.findViewById(R.id.payment_status);

                                    total_amount = mLinearView.findViewById(R.id.total_amount);
                                    servisetype = mLinearView.findViewById(R.id.servisetype);
                                    name = mLinearView.findViewById(R.id.name);
                                    total_amount.setVisibility(View.GONE);


                                    user_name_tv = mLinearView.findViewById(R.id.user_name_tv);
                                    usernumber = mLinearView.findViewById(R.id.usernumber);
                                    useraddress = mLinearView.findViewById(R.id.useraddress);
                                    userlandmark = mLinearView.findViewById(R.id.userlandmark);
                                    user_email_tv = mLinearView.findViewById(R.id.user_email_tv);
                                    carModel.setText(d.getString("carModelNumber"));

                                    user_name_tv.setText("" + d.getString("u_name"));
                                    usernumber.setText("" + d.getString("u_phone"));
                                    useraddress.setText("" + d.getString("u_Address"));
                                    userlandmark.setText("" + d.getString("u_Landmark"));
                                    user_email_tv.setText("" + d.getString("u_email"));


                                    servisetype.setText("" + d.getString("washname_tv"));
                                    name.setText("" + d.getString("car_name"));
                                    total_amount.setText("" + d.getString("price_total_final"));
                                    Log.d("", "sdjfjfgrur" + total_amount);
                                    payment_status.setTextColor(getResources().getColor(R.color.green));
                                    payment_status.setText(d.getString("booking_status"));

                                  /*  if(d.getString("paymenttype").equalsIgnoreCase("offline")){

                                        payment_status.setText("pending");
                                        payment_status.setTextColor(getResources().getColor(R.color.green));

                                    }
                                    else
                                    {   payment_status.setText("successful");
                                        payment_status.setTextColor(getResources().getColor(R.color.green));
                                    }*/
                                    String status_assi = "" + d.getString("assigned_to");
                                    String url = "" + d.getString("car_image");
                                    Log.e("urlurl", "AS : " + url);
                                    Glide.with(BookingAllincudeActivity.this)
                                            .load(url.trim() + "")
                                            .placeholder(R.drawable.rnd_logo)
                                            .into(im5);
                                    ll_bookinglist.addView(mLinearView);
                                }

                        }
                                 else
                                 {
                                     loadingPB.setVisibility(View.GONE);
                                     Toast.makeText(BookingAllincudeActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                 }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //Toast.makeText(BookingAllincudeActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                       Log.d("","ndsfbjh"+e);
                    }
                });

    }




}