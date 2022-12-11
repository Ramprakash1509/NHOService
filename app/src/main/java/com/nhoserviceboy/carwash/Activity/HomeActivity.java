package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Adapter.ModelAdapter;
import com.nhoserviceboy.carwash.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
 String carModel,carType;
 LinearLayout ll_car_wash_detail;
    private FirebaseFirestore db;
    private FirebaseFirestore dbnew;
    ProgressBar loadingPB;
    String Set_Basic_Wash="" ,carnameselect,get_carimage="";
    String Set_Special_Wash="";
    String Set_Water_Less_Wash="";
   TextView texttoolbar;
   ImageView backprese;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        carModel=getIntent().getStringExtra("carModel");
        carType=getIntent().getStringExtra("carName");
        texttoolbar=findViewById(R.id.texttoolbar);
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(this);
        texttoolbar.setText(carModel);
        ll_car_wash_detail=(LinearLayout)findViewById(R.id.ll_car_wash_detail);
        loadingPB =  findViewById(R.id.idProgressBar);
        Log.d("carType",carModel+"   "+carType);
        GetDetaCarWAsh(carType);
    }
    private void GetDetaCarWAsh(String carType)
    {




        db = FirebaseFirestore.getInstance();
        dbnew = FirebaseFirestore.getInstance();
       // orderBy("order", Query.Direction.ASCENDING)
        db.collection("CarDetail").whereEqualTo("carName",carType).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (queryDocumentSnapshots!=null)
                        {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                            for (DocumentSnapshot d : list)
                            {

                                if (this != null)
                                {
                                    LayoutInflater inflater2 = (LayoutInflater)getSystemService(HomeActivity.this.LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.car_wash_detail_abapter_item, null);

                                    final TextView carname   ;
                                    CircleImageView carimage;
                                    LinearLayout li_carwash;
                                    carname =  mLinearView.findViewById(R.id.carwashname);
                                    carimage =  mLinearView.findViewById(R.id.carimage);
                                    li_carwash =  mLinearView.findViewById(R.id.li_carwash);

                                    carname.setText("" + d.getString("carName")  );
                                    String url= "" + d.getString("car_detail_image");
                                    Glide.with(HomeActivity.this)
                                            .load(url)
                                            .placeholder(R.drawable.rnd_logo)
                                            .into(carimage);

                                    li_carwash.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                           /* carnameselect=""+d.getString("carName");
                                            get_carimage=""+d.getString("car_detail_image");*/

                                            Toast.makeText(HomeActivity.this,d.getString("carName"), Toast.LENGTH_SHORT).show();
                                            Intent i=new Intent(HomeActivity.this, WashTypeDetailActivity1.class);
                                            i.putExtra("carId",d.getId());
                                           i.putExtra("carType",carModel);
                                           i.putExtra("name", d.getString("carName"));
                                            startActivity(i);



                                        }
                                    });

                                    ll_car_wash_detail.addView(mLinearView);

                                }



                            }


                        }


                        else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(HomeActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(HomeActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });


    }


    @Override
    public void onClick(View v) {
        finish();
    }
}