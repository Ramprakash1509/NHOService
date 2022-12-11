package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
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
import com.nhoserviceboy.carwash.R;

import java.util.List;

public class AdminNotification extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db;
    TextView texttoolbar;
    ImageView backprese;
    LinearLayout ll_bookinglist;
    ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);
        db = FirebaseFirestore.getInstance();
        texttoolbar=findViewById(R.id.texttoolbar);
        texttoolbar.setText("Notification");
        backprese=findViewById(R.id.backprese);
        ll_bookinglist =  findViewById(R.id.ll_bookinglist);
        loadingPB = findViewById(R.id.idProgressBar);
        backprese.setOnClickListener(this);
        selectNotification();
    }

    private void selectNotification()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("Notification").whereEqualTo("loginType","Admin").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty())
                {  loadingPB.setVisibility(View.GONE);
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list)
                    {
                        String s=d.getString("username");
                        Log.d("","sfsdfsd"+s);

                        LayoutInflater inflater2 = null;
                        inflater2 = (LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View mLinearView = inflater2.inflate(R.layout.notification, null);


                        final TextView Contact_number,name,message ,type,time;
                        final ImageButton deleteNotification;
                        name =  mLinearView.findViewById(R.id.name);
                        Contact_number =  mLinearView.findViewById(R.id.Notification_num);
                        message =  mLinearView.findViewById(R.id.notification_message);
                        type =  mLinearView.findViewById(R.id.messanger_type);
                        time =  mLinearView.findViewById(R.id.Notification_time);
                        deleteNotification=mLinearView.findViewById(R.id.delete);
                        deleteNotification.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {


                                db.collection("Notification").document(d.getId())
                                        .delete()

                                        .addOnSuccessListener(new OnSuccessListener<Void>()
                                        {
                                            @Override
                                            public void onSuccess(Void unused)
                                            {
                                                Toast.makeText(AdminNotification.this, "Deleted   ", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AdminNotification.this, AdminNotification.class));
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener()
                                        {
                                            @Override
                                            public void onFailure(@NonNull Exception e)
                                            {

                                            }
                                        });


                            }
                        });



                            Log.d("","sdfjhgsd"+ d.getString("username"));
                            Log.d("","sdfjhfgsd"+d.getString("type"));
                            Log.d("","dddd"+ d.getString("contact_number"));
                            Log.d("","sdfvjhgsd"+d.getString("Message"));
                            //Log.d("","sdfvjfehgsd"+d.getString("time"));

                        name.setText(" " + d.getString("username"));
                        type.setText(" " + d.getString("type")  );
                        Contact_number.setText("+91-" + d.getString("contact_number"));
                        message.setText(" "+ d.getString("Message") );
                        time.setText( " "+ d.getString("time") );

                        ll_bookinglist.addView(mLinearView);
                    }
                }
                else
                {
                    loadingPB.setVisibility(View.GONE);
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
        if(v==backprese){

            finish();

        }
    }
}