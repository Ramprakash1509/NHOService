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
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

import java.util.List;

public class ServiceBoyNotification extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore db;
    TextView texttoolbar;
    ImageView backprese;
    LinearLayout ll_bookinglist;
    ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_boy_notification);
        db = FirebaseFirestore.getInstance();
        texttoolbar=findViewById(R.id.texttoolbar);
        texttoolbar.setText("Notification");
        backprese=findViewById(R.id.backprese);
        ll_bookinglist =  findViewById(R.id.ll_bookinglist);
        loadingPB =findViewById(R.id.idProgressBar);
        backprese.setOnClickListener(this);
        selectNotification();
    }
    private void selectNotification()
    {     SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,MODE_PRIVATE);
        String user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");
        Log.d("","dsjfbusdfyu"+user_id);
        db = FirebaseFirestore.getInstance();
        db.collection("Notification").whereEqualTo("uid",user_id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
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


                        final TextView Contact_number,name,message ,type,time,type1;
                        ImageButton deleteNotification;
                        name =  mLinearView.findViewById(R.id.name);
                        Contact_number =  mLinearView.findViewById(R.id.Notification_num);
                        message =  mLinearView.findViewById(R.id.notification_message);
                        type =  mLinearView.findViewById(R.id.messanger_type);
                        type1 =  mLinearView.findViewById(R.id.type);
                        type1.setVisibility(View.GONE);
                        type.setVisibility(View.GONE);
                        time =  mLinearView.findViewById(R.id.Notification_time);
                        Contact_number.setText(d.getString("Number"));
                        name.setText(" " + d.getString("username"));
                        message.setText(" " + d.getString("message"));
                        time.setText(" "+d.getString("time"));


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
                                                Toast.makeText(ServiceBoyNotification.this, "Deleted   ", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ServiceBoyNotification.this, AdminNotification.class));
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

                        // type.setText(" " + d.getString("")  );
                        //Contact_number.setText("+91-" + d.getString("phone") );

                        ll_bookinglist.addView(mLinearView);
                    }
                }else
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