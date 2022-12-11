package com.nhoserviceboy.carwash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;



import com.google.firebase.messaging.FirebaseMessaging;
import com.nhoserviceboy.carwash.Activity.SplashActivity;

public class Notification extends AppCompatActivity
{

    //FirebaseFunctions mFunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Button btn=findViewById(R.id.notification);




/*
 btn.setOnClickListener(new View.OnClickListener()
 {
            @Override
            public void onClick(View v)
            {
                mFunctions = FirebaseFunctions.getInstance()
                .getHttpsCallable("")
                    .call(optionalObject)
                        .addOnSuccessListener()

            }
        });


*/}

}