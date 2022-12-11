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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.List;

public class CarWashActivity extends AppCompatActivity implements View.OnClickListener {

    TextView texttoolbar ,carwash_price, tv_Continue ;
    ImageView backprese;
    LinearLayout ll_carlist;
    ProgressBar loadingPB;
    private FirebaseFirestore db;
    String type="";
    String selectcar="";
    String selectcar_name="";
    String Select_url="";

    String Select_carprice="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash);

        Getid();


    }

    private void Getid() {

        type=getIntent().getStringExtra("type");

         tv_Continue=findViewById(R.id.tv_Continue);
        texttoolbar=findViewById(R.id.texttoolbar);
        backprese=findViewById(R.id.backprese);
        carwash_price =   findViewById(R.id.carwash_price);

        backprese.setOnClickListener(this);
        tv_Continue.setOnClickListener(this);

        texttoolbar.setText(""+type);

        GetCarlist();
    }

    private void GetCarlist() {

        // initializing our variables.
        loadingPB =  findViewById(R.id.idProgressBar);
        ll_carlist =  findViewById(R.id.ll_carlist);

        db = FirebaseFirestore.getInstance();

        db.collection("CarList").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View mLinearView = inflater2.inflate(R.layout.carlist_abapter_item, null);

                                final TextView carname   ;
                                ImageView carimage;
                                RadioButton rd1;

                                carname =  mLinearView.findViewById(R.id.carname);
                                carimage =  mLinearView.findViewById(R.id.carimage);
                                rd1 =  mLinearView.findViewById(R.id.rd1);

                                carname.setText("" + d.getString("carname") +"\n"+ d.getString("carmodel"));


                                String CarServies= "" + d.getString("CarServies");
                                String CarServiesjson = "" + new Gson().toJson(CarServies).toString();

                                Log.e("CarServies","As: "+ CarServies +"  CarServiesjson  : "+ CarServiesjson);

                                String url= "" + d.getString("carimage");

                                Glide.with(CarWashActivity.this)
                                        .load(url)
                                        .placeholder(R.drawable.rnd_logo)
                                        .into(carimage);

                                rd1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ///////////////////////////////////////

                                       // Toast.makeText(CarWashActivity.this, ""+d.getString("carname")+"   , carprice :     "+   Select_carprice, Toast.LENGTH_SHORT).show();

                                        if(d.getString("carname").equalsIgnoreCase(Select_carprice)){

                                            rd1.setChecked(false);

                                            Select_carprice  = "";
                                            selectcar="";


                                        }else {

                                            rd1.setChecked(true);
                                            Select_carprice  =    d.getString("carname");

                                            Log.e("serviestype",""+d.getString("carprice") +"  ,  serviestype  :  "+  d.getString("serviestype") );



                                            selectcar_name="" + d.getString("carname") +" "+ d.getString("carmodel");
                                            selectcar=""+d.getString("price");
                                            Select_url= "" + d.getString("carimage");
                                            carwash_price.setText("Rs. "+ d.getString("price") );

                                            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = prefs.edit();
                                            editor.putString("about_us", ""+d.getString("About_us"));
                                            editor.putString("timing", ""+d.getString("timeing"));
                                            editor.commit();
                                        }





                                        // //////////////////////////////////////







                                    }
                                });

                                ll_carlist.addView(mLinearView);

                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(CarWashActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CarWashActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

            }
        });

    }



    @Override
    public void onClick(View view) {

        if(view==tv_Continue){

            if(!selectcar.toString().trim().equalsIgnoreCase("")){

                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("selectcar_name", selectcar_name);
                editor.commit();


                Intent i= new Intent(this, CompanyOverviewActivity.class);
                i.putExtra("selectcar_name",""+ selectcar_name);
                i.putExtra("Select_url",""+ Select_url);
                startActivity(i);

                finish();

            }else {

                Toast.makeText(this, "Select Any Car/Bus/ Truck", Toast.LENGTH_SHORT).show();
            }

        }

       if(view==backprese){

           onBackPressed();
        }

    }
}