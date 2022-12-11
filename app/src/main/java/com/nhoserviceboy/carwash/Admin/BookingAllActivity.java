package com.nhoserviceboy.carwash.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
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
import com.nhoserviceboy.carwash.Activity.Orders_Details_Activity;
import com.nhoserviceboy.carwash.Fragment.AssignBookingFragment;
import com.nhoserviceboy.carwash.Fragment.HomeFragment;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingAllActivity extends AppCompatActivity  implements View.OnClickListener
{

    TextView texttoolbar;
    ImageView backprese;

    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;

    TextView next_tv  ,  Previous_tv  , Today_tv,assign_tv;
    View v1,v2,v3,v4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_all);
        getId();
    }

    private void getId()
    {
        next_tv = findViewById(R.id.next_tv);
        Previous_tv = findViewById(R.id.Previous_tv);
        Today_tv = findViewById(R.id.Today_tv);
        assign_tv=findViewById(R.id.assign_tv);

        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4=findViewById(R.id.v4);

        assign_tv.setOnClickListener(this);
        Today_tv.setOnClickListener(this);
        Previous_tv.setOnClickListener(this);
        next_tv.setOnClickListener(this);


        texttoolbar = findViewById(R.id.texttoolbar);
        backprese = findViewById(R.id.backprese);
        backprese.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                finish();
            }
        });

        texttoolbar.setText("Booking List");



        loadFragment(new TodaysBookingFragment());

        v3.setVisibility(View.GONE);
        v2.setVisibility(View.VISIBLE);
        v1.setVisibility(View.GONE);



    }

    private void GetSetValue()
    {


        loadingPB =  findViewById(R.id.idProgressBar);
        ll_bookinglist =  findViewById(R.id.ll_bookinglist);

        db = FirebaseFirestore.getInstance();
        db.collection("BookingHistory").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {


                                    Log.e("Nodate",""+ "hitShow");

                                    LayoutInflater inflater2 = null;
                                    inflater2 = (LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.booking_abapter_item, null);

                                    final TextView booked_on,Booking_Date,name,payment_type ,servisetype, Booking_time , total_amount  , wash_Count;
                                    CircleImageView im5;
                                    RelativeLayout ri_main;
                                    final ImageButton pdfbtn;
                                     pdfbtn=mLinearView.findViewById(R.id.pdf);

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

                                    wash_Count.setText("Wash Count : " + d.getString("wash_count"));
                                    servisetype.setText("" + d.getString("washname_tv")  );

                                    booked_on.setText(" " + d.getString("service_date")  );
                                    Booking_Date.setText(" " + d.getString("current_date_book") );

                                    name.setText("" + d.getString("car_name")  );
                                    Booking_time.setText(   " " + d.getString("service_time"));
                                    total_amount.setText("" + d.getString("price_total_final")   );





                                    if(d.getString("paymenttype").equalsIgnoreCase("offline")){
                                        payment_type.setText("" + d.getString("paymenttype")    );

                                    }else {

                                        payment_type.setText("" + d.getString("paymenttype")  +"  Ref. Id : "+d.getString("referenceId")   );

                                    }



                                    String url= "" + d.getString("car_image");

                                    Log.e("urlurl","AS : "+url);

                                    Glide.with(BookingAllActivity.this)
                                            .load(url.trim()+"")
                                            .placeholder(R.drawable.rnd_logo)
                                            .into(im5);



                                    ll_bookinglist.addView(mLinearView);



                            }

                        }
                        else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(BookingAllActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(BookingAllActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    @Override
    public void onClick(View view)
    {


        if(view==next_tv)
        {

            loadFragment(new NextBookingFragment());

            v4.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            v2.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);

        }


        if(view==Previous_tv)
        {

            loadFragment(new PreviesBookingFragment());

            v4.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v1.setVisibility(View.VISIBLE);

        }


        if(view==Today_tv)
        {

            loadFragment(new TodaysBookingFragment());

            v4.setVisibility(View.GONE);
            v2.setVisibility(View.VISIBLE);
            v1.setVisibility(View.GONE);

        }
        if(view==assign_tv)
        {

            loadFragment(new AssignBookingFragment());

            v4.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v2.setVisibility(View.VISIBLE);
            v1.setVisibility(View.GONE);

        }

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}