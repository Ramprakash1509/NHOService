package com.nhoserviceboy.carwash.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.nhoserviceboy.carwash.Activity.BookingActivity;
import com.nhoserviceboy.carwash.Activity.CarWashActivity;
import com.nhoserviceboy.carwash.Activity.WashTypeDetailActivity;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment  implements View.OnClickListener
{

    ImageView booking;
    LinearLayout li_carwash  , li_carpolish , li_interiorwash ,  li_carservices, li_enginewash ,li_parking ;
    LinearLayout ll_car_wash_detail;

    ProgressBar loadingPB;
    private FirebaseFirestore db;
    private FirebaseFirestore dbnew;

    String Set_Basic_Wash="" ,carnameselect,get_carimage="";
    String Set_Special_Wash="";
    String Set_Water_Less_Wash="";
    TextView tv_Continue,wishing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        GetId(v);

        return v;


    }

    private void GetId(View v)
    {
        wishing=v.findViewById(R.id.wishing);
        getTimeFromAndroid();

        ll_car_wash_detail=v.findViewById(R.id.ll_car_wash_detail);
        li_parking=v.findViewById(R.id.li_parking);
        li_enginewash=v.findViewById(R.id.li_enginewash);
        li_carservices=v.findViewById(R.id.li_carservices);
        li_interiorwash=v.findViewById(R.id.li_interiorwash);
        tv_Continue=v.findViewById(R.id.tv_Continue);

        li_carpolish=v.findViewById(R.id.li_carpolish);
        li_carwash=v.findViewById(R.id.li_carwash);
        booking=v.findViewById(R.id.booking);

        loadingPB =  v.findViewById(R.id.idProgressBar);



        li_parking.setOnClickListener(this);
        li_enginewash.setOnClickListener(this);
        li_carservices.setOnClickListener(this);
        li_interiorwash.setOnClickListener(this);
        li_carpolish.setOnClickListener(this);

        li_carwash.setOnClickListener(this);
        booking.setOnClickListener(this);
        tv_Continue.setOnClickListener(this);


        GetDetaCarWAsh();


// setVale();

    }
    private void getTimeFromAndroid()
    {
        Date dt = new Date();

        int hours = dt.getHours();
        int min = dt.getMinutes();
        Log.d("","hours"+hours);

        if(hours>=1 && hours<=12)
        {
            wishing.setText("Good Morning");
        }else if(hours >=12 && hours<=16)
        {
            wishing.setText("Good Afternoon");
        }else if(hours>=16 && hours<=21){

            wishing.setText("Good Evening");
        }else if(hours>=21 && hours<=24){

            wishing.setText("Good Night");
        }
    }



    private void GetDetaCarWAsh()
    {


        // initializing our variables.

        db = FirebaseFirestore.getInstance();
        dbnew = FirebaseFirestore.getInstance();

        db.collection("CarDetail").orderBy("order", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (queryDocumentSnapshots!=null) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list)
                            {

                                if (getActivity() != null) {
                                    LayoutInflater inflater2 = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.car_wash_detail_abapter_item, null);

                                    final TextView carname   ;
                                    CircleImageView carimage;
                                    LinearLayout li_carwash;
                                    carname =  mLinearView.findViewById(R.id.carwashname);
                                    carimage =  mLinearView.findViewById(R.id.carimage);
                                    li_carwash =  mLinearView.findViewById(R.id.li_carwash);

                                    carname.setText("" + d.getString("carName")  );

                                    String url= "" + d.getString("car_detail_image");
                                    Glide.with(getActivity())
                                            .load(url)
                                            .placeholder(R.drawable.rnd_logo)
                                            .into(carimage);

                                    li_carwash.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {

                                            carnameselect=""+d.getString("carName");
                                            get_carimage=""+d.getString("car_detail_image");


                                            Map<String, Object> friendsMap = d.getData();

                                            Log.e("friendsMap2",""+ friendsMap);

                                            for (Map.Entry<String, Object> entry : friendsMap.entrySet())
                                            {

                                                Log.e("friendsMap3",""+ entry);
                                                Log.e("friendsMap4",""+ entry.getValue());
                                                Log.e("friendsMap5",""+ entry.getKey());
                                                Log.e("friendsMap6",""+ entry.getClass());

                                                if (entry.getKey().equals("Basic Wash"))
                                                {

                                                    Set_Basic_Wash=""+ entry.getValue().toString();
                                                    //   tv_Continue.setVisibility(View.VISIBLE);

                                                    Intent i= new Intent(getActivity(), WashTypeDetailActivity.class);

                                                    i.putExtra("set_basic_wash"," "+Set_Basic_Wash);
                                                    i.putExtra("special_wash"," "+Set_Special_Wash);
                                                    i.putExtra("water_less"," "+Set_Water_Less_Wash);
                                                    i.putExtra("carnameselect"," "+carnameselect);
                                                    i.putExtra("car_image"," "+get_carimage);
                                                    startActivity(i);



                                                }else  if (entry.getKey().equals("Special Wash"))
                                                {

                                                    Set_Special_Wash=""+ entry.getValue().toString();
                                                    //  tv_Continue.setVisibility(View.VISIBLE);

                                                    Intent i= new Intent(getActivity(), WashTypeDetailActivity.class);
                                                    i.putExtra("set_basic_wash"," "+Set_Basic_Wash);
                                                    i.putExtra("special_wash"," "+Set_Special_Wash);
                                                    i.putExtra("water_less"," "+Set_Water_Less_Wash);
                                                    i.putExtra("carnameselect"," "+carnameselect);
                                                    i.putExtra("car_image"," "+get_carimage);
                                                    startActivity(i);



                                                }else   if (entry.getKey().equals("Water Less Wash"))
                                                {

                                                    Set_Water_Less_Wash=""+ entry.getValue().toString();
                                                    //  tv_Continue.setVisibility(View.VISIBLE);

                                                    Intent i= new Intent(getActivity(), WashTypeDetailActivity.class);
                                                    i.putExtra("set_basic_wash"," "+Set_Basic_Wash);
                                                    i.putExtra("special_wash"," "+Set_Special_Wash);
                                                    i.putExtra("water_less"," "+Set_Water_Less_Wash);
                                                    i.putExtra("carnameselect"," "+carnameselect);
                                                    i.putExtra("car_image"," "+get_carimage);
                                                    startActivity(i);



                                                }

                                            }


                                            Log.e("Washing_Category1",""+ d.getDocumentReference(d.getId()));
                                            Log.e("Washing_Category2",""+ d.getData());
                                            Log.e("Washing_Category5",""+ d);
                                            Log.e("Washing_Category6",""+ d.getId());
                                            Log.e("Washing_Category6",""+ d.getMetadata());



                                        }
                                    });

                                    ll_car_wash_detail.addView(mLinearView);
// Code goes here.
                                }



                                }


                                }




                         else {

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

    @Override
    public void onClick(View view) {


        if(view==tv_Continue){

            if(!Set_Basic_Wash.toString().equalsIgnoreCase("")){


                Intent i= new Intent(getActivity(), WashTypeDetailActivity.class);
                i.putExtra("set_basic_wash"," "+Set_Basic_Wash);
                i.putExtra("special_wash"," "+Set_Special_Wash);
                i.putExtra("water_less"," "+Set_Water_Less_Wash);
                i.putExtra("carnameselect"," "+carnameselect);
                i.putExtra("car_image"," "+get_carimage);
                startActivity(i);

            }else {


                Toast.makeText(getActivity(), "Aasasa", Toast.LENGTH_SHORT).show();
            }

        }

        if(view==li_parking){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Parking");
            startActivity(i);

        }






        if(view==li_enginewash){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Engine Wash");
            startActivity(i);


        }

        if(view==li_carservices){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Car Services");
            startActivity(i);


        }

        if(view==li_interiorwash){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Interior Wash");
            startActivity(i);


        }

        if(view==li_carwash){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Car Wash");
            startActivity(i);


        }

        if(view==li_carpolish){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Car Polish");
            startActivity(i);


        }



        if(view==booking){

            startActivity(new Intent(getActivity(), BookingActivity.class));

        }







    }


}



/*
public class HomeFragment extends Fragment  implements View.OnClickListener {

    ImageView booking;
    LinearLayout li_carwash  , li_carpolish , li_interiorwash ,  li_carservices, li_enginewash ,li_parking ;
    LinearLayout ll_car_wash_detail;

    ProgressBar loadingPB;
    private FirebaseFirestore db;
    private FirebaseFirestore dbnew;

    String Set_Basic_Wash="" ,carnameselect,get_carimage="";
    String Set_Special_Wash="";
    String Set_Water_Less_Wash="";
    TextView tv_Continue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        GetId(v);

        return v;


    }

    private void GetId(View v) {

        ll_car_wash_detail=v.findViewById(R.id.ll_car_wash_detail);
         li_parking=v.findViewById(R.id.li_parking);
        li_enginewash=v.findViewById(R.id.li_enginewash);
        li_carservices=v.findViewById(R.id.li_carservices);
        li_interiorwash=v.findViewById(R.id.li_interiorwash);
        tv_Continue=v.findViewById(R.id.tv_Continue);

        li_carpolish=v.findViewById(R.id.li_carpolish);
        li_carwash=v.findViewById(R.id.li_carwash);
        booking=v.findViewById(R.id.booking);

        loadingPB =  v.findViewById(R.id.idProgressBar);



        li_parking.setOnClickListener(this);
        li_enginewash.setOnClickListener(this);
        li_carservices.setOnClickListener(this);
        li_interiorwash.setOnClickListener(this);
        li_carpolish.setOnClickListener(this);

        li_carwash.setOnClickListener(this);
        booking.setOnClickListener(this);
        tv_Continue.setOnClickListener(this);


     GetDetaCarWAsh();


// setVale();

    }



    private void GetDetaCarWAsh() {


        // initializing our variables.

        db = FirebaseFirestore.getInstance();
        dbnew = FirebaseFirestore.getInstance();

        db.collection("CarDetail").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View mLinearView = inflater2.inflate(R.layout.car_wash_detail_abapter_item, null);

                                final TextView carname   ;
                                CircleImageView carimage;
                                LinearLayout li_carwash;

                                carname =  mLinearView.findViewById(R.id.carwashname);
                                carimage =  mLinearView.findViewById(R.id.carimage);
                                li_carwash =  mLinearView.findViewById(R.id.li_carwash);

                                carname.setText("" + d.getString("carName")  );

                                String url= "" + d.getString("car_detail_image");
                                Glide.with(getActivity())
                                        .load(url)
                                        .placeholder(R.drawable.rnd_logo)
                                        .into(carimage);

                                li_carwash.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        carnameselect=""+d.getString("carName");
                                        get_carimage=""+d.getString("car_detail_image");


                                        Map<String, Object> friendsMap = d.getData();

                                        Log.e("friendsMap2",""+ friendsMap);

                                        for (Map.Entry<String, Object> entry : friendsMap.entrySet()) {

                                            Log.e("friendsMap3",""+ entry);
                                            Log.e("friendsMap4",""+ entry.getValue());
                                            Log.e("friendsMap5",""+ entry.getKey());
                                            Log.e("friendsMap6",""+ entry.getClass());

                                            if (entry.getKey().equals("Basic Wash")) {

                                                  Set_Basic_Wash=""+ entry.getValue().toString();
                                               //   tv_Continue.setVisibility(View.VISIBLE);

                                                Intent i= new Intent(getActivity(), WashTypeDetailActivity.class);
                                                i.putExtra("set_basic_wash"," "+Set_Basic_Wash);
                                                i.putExtra("special_wash"," "+Set_Special_Wash);
                                                i.putExtra("water_less"," "+Set_Water_Less_Wash);
                                                i.putExtra("carnameselect"," "+carnameselect);
                                                i.putExtra("car_image"," "+get_carimage);
                                                startActivity(i);



                                            }else   if (entry.getKey().equals("Special Wash")) {

                                                  Set_Special_Wash=""+ entry.getValue().toString();
                                              //  tv_Continue.setVisibility(View.VISIBLE);

                                                Intent i= new Intent(getActivity(), WashTypeDetailActivity.class);
                                                i.putExtra("set_basic_wash"," "+Set_Basic_Wash);
                                                i.putExtra("special_wash"," "+Set_Special_Wash);
                                                i.putExtra("water_less"," "+Set_Water_Less_Wash);
                                                i.putExtra("carnameselect"," "+carnameselect);
                                                i.putExtra("car_image"," "+get_carimage);
                                                startActivity(i);



                                            }else   if (entry.getKey().equals("Water Less Wash")) {

                                                  Set_Water_Less_Wash=""+ entry.getValue().toString();
                                              //  tv_Continue.setVisibility(View.VISIBLE);

                                                Intent i= new Intent(getActivity(), WashTypeDetailActivity.class);
                                                i.putExtra("set_basic_wash"," "+Set_Basic_Wash);
                                                i.putExtra("special_wash"," "+Set_Special_Wash);
                                                i.putExtra("water_less"," "+Set_Water_Less_Wash);
                                                i.putExtra("carnameselect"," "+carnameselect);
                                                i.putExtra("car_image"," "+get_carimage);
                                                startActivity(i);



                                            }

                                            }


                                        Log.e("Washing_Category1",""+ d.getDocumentReference(d.getId()));
                                        Log.e("Washing_Category2",""+ d.getData());
                                        Log.e("Washing_Category5",""+ d);
                                        Log.e("Washing_Category6",""+ d.getId());
                                        Log.e("Washing_Category6",""+ d.getMetadata());



                                    }
                                });

                                ll_car_wash_detail.addView(mLinearView);

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

    @Override
    public void onClick(View view) {

        if(view==tv_Continue){

            if(!Set_Basic_Wash.toString().equalsIgnoreCase("")){


                Intent i= new Intent(getActivity(), WashTypeDetailActivity.class);
                i.putExtra("set_basic_wash"," "+Set_Basic_Wash);
                i.putExtra("special_wash"," "+Set_Special_Wash);
                i.putExtra("water_less"," "+Set_Water_Less_Wash);
                i.putExtra("carnameselect"," "+carnameselect);
                i.putExtra("car_image"," "+get_carimage);
                startActivity(i);

            }else {


                Toast.makeText(getActivity(), "Aasasa", Toast.LENGTH_SHORT).show();
            }

        }

         if(view==li_parking){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Parking");
            startActivity(i);

        }






 if(view==li_enginewash){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Engine Wash");
            startActivity(i);


        }

 if(view==li_carservices){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Car Services");
            startActivity(i);


        }

 if(view==li_interiorwash){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Interior Wash");
            startActivity(i);


        }

 if(view==li_carwash){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Car Wash");
            startActivity(i);


        }

 if(view==li_carpolish){

            Intent i= new Intent(getActivity(), CarWashActivity.class);
            i.putExtra("type","Car Polish");
            startActivity(i);


        }



        if(view==booking){

            startActivity(new Intent(getActivity(), BookingActivity.class));

        }







    }
}*/
