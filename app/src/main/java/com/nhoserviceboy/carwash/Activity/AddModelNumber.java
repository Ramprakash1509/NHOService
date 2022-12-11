package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.Loader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddModelNumber extends AppCompatActivity implements View.OnClickListener {
     ImageView addCarModel;
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;
    ImageView backprese;
    TextView texttoolbar;
    LinearLayout ll_car_wash_detail;
    Spinner spinner;
    SearchView simpleSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_model_number);
        loadingPB=findViewById(R.id.idProgressBar);
        addCarModel=findViewById(R.id.addserviceboy);
        ll_car_wash_detail=findViewById(R.id.ll_car_wash_detail);
        addCarModel.setOnClickListener(this);
        backprese=findViewById(R.id.backprese);
        texttoolbar=findViewById(R.id.texttoolbar);
        backprese.setOnClickListener(this);
        texttoolbar.setText("Car Model Details");
        spinner = findViewById(R.id.spinner);
        simpleSearchView =  findViewById(R.id.simpleSearchView);
        simpleSearchView.setOnClickListener(this);
        CharSequence queryHint = simpleSearchView.getQueryHint();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                db.collection("CarModelDetails").whereEqualTo("carName",String.valueOf(spinner.getSelectedItem())).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                        {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                            {

                                if (queryDocumentSnapshots != null)
                                {

                                    ll_car_wash_detail.removeAllViews();
                                    loadingPB.setVisibility(View.GONE);
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list)
                                    {
                                        LayoutInflater inflater2=null;
                                        inflater2 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View mLinearView = inflater2.inflate(R.layout.car, null);
                                        final TextView carname ,modelNumber  ;

                                        carname =  mLinearView.findViewById(R.id.CarName);
                                         modelNumber=  mLinearView.findViewById(R.id.modelNumber);

                                        Log.d("sguetiu","OJH"+ d.getString("carName")  );
                                        carname.setText("" + d.getString("carName")  );
                                        modelNumber.setText("" + d.getString("carModel")  );
                                        ll_car_wash_detail.addView(mLinearView);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(AddModelNumber.this, "Not fond data in database", Toast.LENGTH_SHORT).show();
                                    loadingPB.setVisibility(View.GONE);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(AddModelNumber.this, "Fail to download", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(AddModelNumber.this, "Fail to download", Toast.LENGTH_SHORT).show();
            }
        });

        GetData();

    //   GetDetaCarWAsh();
    }






    public void GetData()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("CarDetail").orderBy("order", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (queryDocumentSnapshots!=null)
                        {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            ArrayList<String> listView= new ArrayList<>();
                            for (DocumentSnapshot d : list)
                            {
                                //listView.add(d.getId());
                                listView.add(d.getString("carName"));

                            }
                            showDataInSpinner(listView);
                        }
                        else {

                            //loadingPB.setVisibility(View.GONE);
                            Toast.makeText(AddModelNumber.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        Toast.makeText(AddModelNumber.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });


    }
    public void showDataInSpinner(ArrayList<String> data)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
    }







    @Override
    public void onClick(View v)
    {
        if (v==backprese)
        {
            finish();
        }
        if (v==addCarModel)
        {
            startActivity(new Intent(AddModelNumber.this,AddCarModelNumber.class));

        }
        if (v==simpleSearchView)
        {

        }

    }
}