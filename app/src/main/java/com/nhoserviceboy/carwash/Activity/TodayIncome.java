package com.nhoserviceboy.carwash.Activity;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Admin.BookingAllincudeActivity;
import com.nhoserviceboy.carwash.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TodayIncome extends AppCompatActivity implements View.OnClickListener {
    TextView texttoolbar;
    ImageView backprese;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;
    String user_id="";
    TextView todayIncome;
    final int sum = 0;
    LinearLayout list;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_income);
        ll_bookinglist=findViewById(R.id.ll_bookinglist);
        todayIncome=findViewById(R.id.todayIncome);
        list=findViewById(R.id.list);
        list.setOnClickListener(this);
        todayIncome.setOnClickListener(this);
        loadingPB =  findViewById(R.id.idProgressBar);

        texttoolbar=findViewById(R.id.texttoolbar);
        texttoolbar.setText("Today Income");
        backprese=findViewById(R.id.backprese);

        Hit_book();
        backprese.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                finish();
            }
        });


    }
    public void Hit_book()
    {

        Date date=new Date();
        SimpleDateFormat sdfDATE=new SimpleDateFormat("dd-MM-yyyy");
        String currentDate=sdfDATE.format(date);
        Log.d("","hdffygf"+currentDate);
        db = FirebaseFirestore.getInstance();
        db.collection("UserPackageDetails").whereEqualTo("currentDate",currentDate).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {

                if (!queryDocumentSnapshots.isEmpty())
                {

                   loadingPB.setVisibility(View.GONE);
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    int value=0;
                    for (DocumentSnapshot d : list)
                    {

                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View mLinearView = inflater2.inflate(R.layout.todayicome, null);
                        final TextView todayIncome,userName,bookingDate;
                        final LinearLayout ll_bookinglistt;
                        final RelativeLayout ttt;
                        ttt=mLinearView.findViewById(R.id.ttt);
                        todayIncome=mLinearView.findViewById(R.id.todayIncome);

                        todayIncome.setText("Rs :"+d.getString("packageAmount"));
                        String income=  d.getString("packageAmount");
                        int a= Integer.parseInt(income);
                        value=value+a;
                        Log.d("","vjdsfhuds"+value);
                        bookingDate=mLinearView.findViewById(R.id.bookingDate);
                        ll_bookinglistt=mLinearView.findViewById(R.id.ll_bookinglist);
                        userName=mLinearView.findViewById(R.id.userName);
                        bookingDate.setText("  "+d.getString("bookingDate"));
                        todayIncome.setText("  ₹. " + d.getString("packageAmount")+"");
                        final ImageView button;
                        button=mLinearView.findViewById(R.id.btn);
                        button.setVisibility(View.VISIBLE);
                        ttt.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent=new Intent(TodayIncome.this, BookingAllincudeActivity.class);
                                intent.putExtra("userId",d.getString("userId"));
                                intent.putExtra("packageId",d.getString("packageId"));
                                startActivity(intent);
                                //  startActivity(new Intent(getActivity(), BookingAllincudeActivity.class));
                            }

                        });

                        ll_bookinglist.addView(mLinearView);
                    }
                    todayIncome.setText("₹. "+value+" ");


                }
                else
                {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(TodayIncome.this, "No data fond in database ", Toast.LENGTH_SHORT).show();
                }


                    }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


    @Override
    public void onClick(View v)
    {

    }
}