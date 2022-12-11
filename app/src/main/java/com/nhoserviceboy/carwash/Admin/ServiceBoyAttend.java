package com.nhoserviceboy.carwash.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.DailyServiceHistory;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.List;

public class ServiceBoyAttend extends AppCompatActivity implements View.OnClickListener {
    TextView texttoolbar,serviceboyNotification;
    ImageView backprese;
    LinearLayout ll_bookinglist;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    String serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_boy_attend);
        context= ServiceBoyAttend.this;
        GetId();
    }


    private void GetId()
    {
        // logout_im=findViewById(R.id.logout_im);
        //logout_im.setOnClickListener(this);


        db = FirebaseFirestore.getInstance();
        ll_bookinglist=findViewById(R.id.ll_bookinglist);
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(this);
        serviceId=getIntent().getStringExtra("serviceId");
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
        db.collection("DailyServiceHistory").whereEqualTo("bookingId",serviceId).get()
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
                                final View mLinearView = inflater2.inflate(R.layout.daily_historylayout, null);

                                final TextView name, servisetype,booked_on,Booking_time,user_name_tv,usernumber,useraddress,userlandmark;
                                name =  mLinearView.findViewById(R.id.name);
                                servisetype =  mLinearView.findViewById(R.id.servisetype);
                                Booking_time =  mLinearView.findViewById(R.id.Booking_time);
                                booked_on =  mLinearView.findViewById(R.id.booked_on);
                                user_name_tv =  mLinearView.findViewById(R.id.user_name_tv);
                                usernumber =  mLinearView.findViewById(R.id.usernumber);
                                useraddress =  mLinearView.findViewById(R.id.useraddress);
                                userlandmark =  mLinearView.findViewById(R.id.userlandmark);
                                user_name_tv.setText(""+   d.getString("userName"));
                                usernumber.setText(""+   d.getString("userNumber"));
                                useraddress.setText(""+   d.getString("userAddress"));
                                userlandmark.setText(""+   d.getString("userLandmark"));
                                name.setText(d.getString("serviceName"));
                                booked_on.setText(d.getString("date"));
                                Booking_time.setText(d.getString("time"));
                                ll_bookinglist.addView(mLinearView);

                            }
                        }
                        else
                        {
                            Toast.makeText(ServiceBoyAttend.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        Toast.makeText(ServiceBoyAttend.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    public void onClick(View v) {
        finish();
    }

}