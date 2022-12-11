package com.nhoserviceboy.carwash.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.nhoserviceboy.carwash.Adapter.CarNumberAdapter;
import com.nhoserviceboy.carwash.Admin.Adapter.CustomAdapter;
import com.nhoserviceboy.carwash.Admin.Model.CarModel;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.Loader;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCarModelNumber extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    TextView submit;
    EditText modelNumber;
    List<View> categories;
    private FirebaseFirestore db;
    ImageView backprese;
    TextView texttoolbar;
    Loader loader;
    String packageId = "";
    String value;

    ProgressBar progressBar;
    //Car List
    RecyclerView recyclerview;
    private List<DocumentSnapshot> courseModelArrayList;
    CarNumberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_model_number);

        GetId();
    }

    private void GetId() {
        recyclerview = findViewById(R.id.recyclerview);
        spinner = findViewById(R.id.spinner);
        progressBar = findViewById(R.id.PB);
        submit = findViewById(R.id.submit);
        modelNumber = findViewById(R.id.modelNumber);
        backprese = findViewById(R.id.backprese);
        texttoolbar = findViewById(R.id.texttoolbar);
        texttoolbar.setText("Add Car Model ");
        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
        submit.setOnClickListener(this);
        backprese.setOnClickListener(this);
        GetData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                GetListData(spinner.getSelectedItem());
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    private void GetListData(Object selectedItem) {
        db = FirebaseFirestore.getInstance();
        db.collection("CarModelDetails").whereEqualTo("carName", selectedItem).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null) {
                    courseModelArrayList = queryDocumentSnapshots.getDocuments();

                    adapter = new CarNumberAdapter(AddCarModelNumber.this, courseModelArrayList,1);
                    adapter.notifyDataSetChanged();
                    recyclerview.setHasFixedSize(false);
                    recyclerview.setLayoutManager(new LinearLayoutManager(AddCarModelNumber.this));
                    LinearLayoutManager llm = new LinearLayoutManager(AddCarModelNumber.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerview.setLayoutManager(llm);
                    recyclerview.setAdapter(adapter);

                } else {
                    Toast.makeText(AddCarModelNumber.this, "Not fond data in database", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    ArrayList<String> listView = new ArrayList<>();
    ArrayList<String> listView2 = new ArrayList<>();

    public void GetData() {
        listView2.clear();
        listView.clear();
        db = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        db.collection("CarDetail").orderBy("order", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressBar.setVisibility(View.GONE);
                        if (queryDocumentSnapshots != null) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {
                                //listView.add(d.getId());
                                listView.add(d.getString("carName"));
                                listView2.add(d.getId());


                            }
                            showDataInSpinner(listView, listView2);
                        } else {

                            //loadingPB.setVisibility(View.GONE);
                            Toast.makeText(AddCarModelNumber.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(AddCarModelNumber.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    public void showDataInSpinner(ArrayList<String> data, ArrayList<String> data2) {
       /* CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),data);
        spinner.setAdapter(customAdapter);

*/

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v == backprese) {
            finish();
        }
        if (v == submit) {
            if (modelNumber.getText().toString().trim().isEmpty()) {
                modelNumber.setError("Enter Car Model ");
                modelNumber.setFocusable(true);
            } else {
                if (UtilMethods.INSTANCE.isNetworkAvialable(AddCarModelNumber.this)) {  /* loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    */
                    listView2.get(listView.indexOf(spinner.getSelectedItem()));

                    addDataFireBase(modelNumber.getText().toString().trim(), String.valueOf(spinner.getSelectedItem()), listView2.get(listView.indexOf(spinner.getSelectedItem())), modelNumber);
                } else {
                    UtilMethods.INSTANCE.Error(AddCarModelNumber.this, getResources().getString(R.string.network_error_message));
                }

            }
        }

    }

    private void addDataFireBase(String modelNumber, String carName, String packageId, TextView modelNumber1) {
        Log.e("sdasd", modelNumber);
        CollectionReference dbCourses = db.collection("CarModelDetails");
        CarModel model = new CarModel(carName, modelNumber, packageId);
        dbCourses.whereEqualTo("carModel", modelNumber).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            dbCourses.add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddCarModelNumber.this, " Added Successfully  ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddCarModelNumber.this, AddCarModelNumber.class));
                                    finish();
                                    modelNumber1.setText(" ");
                                }
                            });
                            return;
                        } else {
                            Toast.makeText(AddCarModelNumber.this, "Document already exists", Toast.LENGTH_SHORT).show();
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.

                        }
                    }
                });


    }

/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
     {
        return super.onOptionsItemSelected(item);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }*/


}