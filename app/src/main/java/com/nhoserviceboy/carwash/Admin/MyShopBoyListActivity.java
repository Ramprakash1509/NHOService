package com.nhoserviceboy.carwash.Admin;

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
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyShopBoyListActivity extends AppCompatActivity  implements View.OnClickListener {

    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;
    ImageView backprese;
    TextView texttoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop_boy_list);
        //Toast.makeText(this, "hufdhfuygf", Toast.LENGTH_SHORT).show();
        GetId();

    }

    private void GetId()
    {


        backprese=findViewById(R.id.backprese);
        texttoolbar=findViewById(R.id.texttoolbar);

        backprese.setOnClickListener(this);

        texttoolbar.setText("Service Boy List");

        loadingPB =  findViewById(R.id.idProgressBar);
        ll_bookinglist =  findViewById(R.id.ll_bookinglist);

        db = FirebaseFirestore.getInstance();
        db.collection("Add_Service_Boy").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (!queryDocumentSnapshots.isEmpty())
                        {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list)
                            {


                                    Log.e("Nodate",""+ "hitShow");

                                    LayoutInflater inflater2 = null;
                                    inflater2 = (LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.shop_boy_abapter, null);

                                    final TextView email_id,Contact_number,name,address , landmark ;


                                address =  mLinearView.findViewById(R.id.address);

                                name =  mLinearView.findViewById(R.id.name);
                                RelativeLayout  ri_main =  mLinearView.findViewById(R.id.ri_main);
                                landmark =  mLinearView.findViewById(R.id.landmark);
                                email_id =  mLinearView.findViewById(R.id.email_id);
                                Contact_number =  mLinearView.findViewById(R.id.Contact_number);
                                email_id.setText(" " + d.getString("email")  );
                                Contact_number.setText("+91-" + d.getString("phone") );
                                name.setText("" + d.getString("username")  );
                                landmark.setText(   " " + d.getString("landmark"));
                                address.setText(   " " + d.getString("address"));
                                  ri_main.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View view)
                                      {
                                          Intent i=new Intent(MyShopBoyListActivity.this,ServiceBoysBooking.class);
                                            i.putExtra("name",name.getText().toString());
                                            i.putExtra("uid",d.getId());
                                             startActivity(i);
                                         // Toast.makeText(MyShopBoyListActivity.this, "sdbvslbvdjlb", Toast.LENGTH_SHORT).show();
                                        // startActivity(new Intent(MyShopBoyListActivity.this,ServiceBoysBooking.class).putExtra("name",name.getText().toString()));
                                      }
                                  });
                                ll_bookinglist.addView(mLinearView);



                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(MyShopBoyListActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MyShopBoyListActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    public void onClick(View view)
    {


        if(view==backprese){

            finish();

        }

    }
}