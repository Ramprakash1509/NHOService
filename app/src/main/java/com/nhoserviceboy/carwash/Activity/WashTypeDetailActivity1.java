package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Admin.AssignedBookingActivity;
import com.nhoserviceboy.carwash.Admin.Model.BookingModels;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WashTypeDetailActivity1 extends AppCompatActivity implements View.OnClickListener
{
    LinearLayout ll_basicwash,ll_specialwash,ll_waterlesswash,deaalyCleaning;
    private FirebaseFirestore db;
    String carId;
    TextView texttoolbar;
    ImageView backprese;
    String user_id="";
    ProgressBar loadingPB;
    String carModel;
    String carNmae,modelId;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_type_detail1);
  getId();

    }

    private void getId()
    {   texttoolbar=findViewById(R.id.texttoolbar);


        ll_basicwash=findViewById(R.id.basicwash);
        ll_specialwash=findViewById(R.id.specialwash);
        deaalyCleaning=findViewById(R.id.deaalyCleaning);
        ll_waterlesswash=findViewById(R.id.waterLesswash);
        loadingPB =  findViewById(R.id.idProgressBar);
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(this);
        hit_booking();
    }
    @Override
    public void onClick(View v)
    {
        if (v == backprese) {

            //onBackPressed();

            Intent home = new Intent(WashTypeDetailActivity1.this, DashboadActivity.class);
            startActivity(home);
            finishAffinity();
            finish();

        }
    }
    public void hit_booking()
    {
         carId = getIntent().getStringExtra("carId");
         carModel = getIntent().getStringExtra("carType");
         modelId = getIntent().getStringExtra("modelId");
          texttoolbar.setText(carModel);
        carNmae= getIntent().getStringExtra("name");
         Log.d( "iddshfdfg",  carModel);
        Log.d( "iddshfg",  carId);
        db = FirebaseFirestore.getInstance();

        SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");

        Log.d("userIde",user_id);
        db.collection("package").whereEqualTo("carId", carId).whereEqualTo("packageGroup", "Basic Wash").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {

                if (queryDocumentSnapshots != null)
                {   loadingPB.setVisibility(View.GONE);
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list)
                    {

                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View mLinearView = inflater2.inflate(R.layout.layoutcarshow, null);

                        final CircleImageView carimage;
                        final TextView carwashname,wash_price;
                        final RelativeLayout relative;
                        relative=mLinearView.findViewById(R.id.relative);
                        wash_price=mLinearView.findViewById(R.id.Price_bw_1);
                        carimage = mLinearView.findViewById(R.id.carimage);
                        carwashname = mLinearView.findViewById(R.id.ValidityMonth_bw_1);

                        carwashname.setText(d.getString("washCount")+" wash package(Validity "+d.getString("validity")+" "+d.getString("duration")+")");
                        wash_price.setText("Rs."+d.getString("price"));


                        relative.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent i=new Intent(WashTypeDetailActivity1.this,BookingActivityNew.class);
                                i.putExtra("user_id",user_id);
                                i.putExtra("package",d.getString("washCount")+" wash package(Validity "+d.getString("validity")+" "+d.getString("duration")+")");
                                i.putExtra("carname",carNmae);
                                i.putExtra("carId",d.getString("carId"));
                                i.putExtra("packageId",d.getId());
                                i.putExtra("duration",d.getString("duration"));
                                i.putExtra("price",d.getString("price"));
                                i.putExtra("validity",d.getString("validity"));
                                i.putExtra("washCount",d.getString("washCount"));
                                i.putExtra("packageName",d.getString("packageName"));
                                i.putExtra("carModel",carModel);
                                i.putExtra("modelId",modelId);
                                startActivity(i);
                            }
                        });


                        ll_basicwash.addView(mLinearView);

                    }
                }
                else {

                    loadingPB.setVisibility(View.GONE);

                    Toast.makeText(WashTypeDetailActivity1.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {

            }
        });
        db.collection("package").whereEqualTo("carId", carId).whereEqualTo("packageGroup", "Special wash").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {

                if (queryDocumentSnapshots != null)
                {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list)
                    {

                        Log.d("", "dsdsfdf" + d.getString("carId"));

                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                         final View mLinearView = inflater2.inflate(R.layout.layoutcarshow, null);

                         final CircleImageView carimage;
                        final TextView carwashname,wash_price;
                        final RelativeLayout relative;
                        relative=mLinearView.findViewById(R.id.relative);
                        wash_price=mLinearView.findViewById(R.id.Price_bw_1);
                        carimage = mLinearView.findViewById(R.id.carimage);
                        carwashname = mLinearView.findViewById(R.id.ValidityMonth_bw_1);

                        carwashname.setText(d.getString("washCount")+" wash package(Validity "+d.getString("validity")+" "+d.getString("duration")+")");
                        wash_price.setText("Rs."+d.getString("price"));
                        relative.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {

                                Intent i=new Intent(WashTypeDetailActivity1.this,BookingActivityNew.class);
                                i.putExtra("user_id",user_id);
                                i.putExtra("package",d.getString("washCount")+" wash package(Validity "+d.getString("validity")+" "+d.getString("duration")+")");
                                i.putExtra("carname",carNmae);
                                i.putExtra("carId",d.getString("carId"));
                                i.putExtra("packageId",d.getId());
                                i.putExtra("duration",d.getString("duration"));
                                i.putExtra("price",d.getString("price"));
                                i.putExtra("validity",d.getString("validity"));
                                i.putExtra("washCount",d.getString("washCount"));
                                i.putExtra("packageName",d.getString("packageName"));
                                i.putExtra("carModel",carModel);

                                startActivity(i);
                            }
                        });
                        ll_specialwash.addView(mLinearView);
                    }

                }
                else {

                    loadingPB.setVisibility(View.GONE);

                    Toast.makeText(WashTypeDetailActivity1.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        db.collection("package").whereEqualTo("carId", carId).whereEqualTo("packageGroup", "Water Less Wash").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {

                if (queryDocumentSnapshots != null)
                {    loadingPB.setVisibility(View.GONE);
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {

                        Log.d("", "dsdsfdf" + d.getString("carId"));
                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View mLinearView = inflater2.inflate(R.layout.layoutcarshow, null);

                        final CircleImageView carimage;
                        final TextView carwashname,wash_price;
                        final RelativeLayout relative;
                        relative=mLinearView.findViewById(R.id.relative);
                        wash_price=mLinearView.findViewById(R.id.Price_bw_1);
                        carimage = mLinearView.findViewById(R.id.carimage);
                        carwashname = mLinearView.findViewById(R.id.ValidityMonth_bw_1);

                        carwashname.setText(d.getString("washCount")+" wash package(Validity "+d.getString("validity")+" "+d.getString("duration")+")");
                        wash_price.setText("Rs."+d.getString("price"));
                        relative.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent i=new Intent(WashTypeDetailActivity1.this,BookingActivityNew.class);
                                i.putExtra("user_id",user_id);
                                i.putExtra("package",d.getString("washCount")+" wash package(Validity "+d.getString("validity")+" "+d.getString("duration")+")");

                                i.putExtra("carname",carNmae);
                                i.putExtra("carId",d.getString("carId"));
                                i.putExtra("packageId",d.getId());
                                i.putExtra("duration",d.getString("duration"));
                                i.putExtra("price",d.getString("price"));
                                i.putExtra("validity",d.getString("validity"));
                                i.putExtra("washCount",d.getString("washCount"));
                                i.putExtra("packageName",d.getString("packageName"));
                                i.putExtra("carModel",carModel);
                                startActivity(i);
                            }
                        });

                        ll_waterlesswash.addView(mLinearView);

                    }
                }
                else {

                    loadingPB.setVisibility(View.GONE);

                    Toast.makeText(WashTypeDetailActivity1.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        db.collection("package").whereEqualTo("carId", carId).whereEqualTo("packageGroup", "Daily Cleaning").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {

                if (queryDocumentSnapshots != null)
                {
                    loadingPB.setVisibility(View.GONE);
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {

                        Log.d("", "dsdsfdf" + d.getString("carId"));
                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View mLinearView = inflater2.inflate(R.layout.layoutcarshow, null);

                        final CircleImageView carimage;
                        final TextView carwashname,wash_price;
                        final RelativeLayout relative;
                        relative=mLinearView.findViewById(R.id.relative);
                        wash_price=mLinearView.findViewById(R.id.Price_bw_1);
                        carimage = mLinearView.findViewById(R.id.carimage);
                        carwashname = mLinearView.findViewById(R.id.ValidityMonth_bw_1);
                        carwashname.setText("Daily wash (Validity "+d.getString("validity")+" "+d.getString("duration")+")");
                        wash_price.setText("Rs."+d.getString("price"));
                        relative.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent i=new Intent(WashTypeDetailActivity1.this,BookingActivityNew.class);
                                i.putExtra("user_id",user_id);
                                i.putExtra("package",d.getString("washCount")+" wash package(Validity "+d.getString("validity")+" "+d.getString("duration")+")");
                                i.putExtra("carname",carNmae);
                                i.putExtra("carId",d.getString("carId"));
                                i.putExtra("packageId",d.getId());
                                i.putExtra("duration",d.getString("duration"));
                                i.putExtra("price",d.getString("price"));
                                i.putExtra("validity",d.getString("validity"));
                                i.putExtra("washCount",d.getString("washCount"));
                                i.putExtra("packageName",d.getString("packageName"));
                                i.putExtra("carModel",carModel);
                                startActivity(i);
                            }
                        });

                        deaalyCleaning.addView(mLinearView);

                    }
                }
                else {

                    loadingPB.setVisibility(View.GONE);

                    Toast.makeText(WashTypeDetailActivity1.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }













   /* public void adddatafirebase(String userId,String packageId,String redeem,String date,String modifyDate,String expireDate,String washCount)
    {
        //BookingModels(String userId, String packageId, String redeem, String date, String modifyDate, String expireDate, String washCount)
        BookingModels bookingdetails=new BookingModels();
        CollectionReference dbCourses = db.collection("BookingDetails");
        dbCourses.add(bookingdetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {




            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {


            }
        });


    }*/


    }
