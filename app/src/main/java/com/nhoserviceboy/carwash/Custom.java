package com.nhoserviceboy.carwash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhoserviceboy.carwash.Activity.BookingAllActivity1;
import com.nhoserviceboy.carwash.Activity.SplashActivity;
import com.nhoserviceboy.carwash.Admin.TodaysBookingFragment;
import com.nhoserviceboy.carwash.Fragment.BookingFragment;
import com.nhoserviceboy.carwash.ServiesBoy.ServiesBoyDashboadActivity;

public class Custom extends AppCompatActivity
{
    TextView name,message,expanded_notification_title;
    ImageView imageView;
    CardView cardView;
    String messagetype;


    @Override
    protected void onCreate(Bundle savedInstanceState)


    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        message=findViewById(R.id.messagenotification);
        expanded_notification_title=findViewById(R.id.expanded_notification_title);
        //name.setText(getIntent().getStringExtra("NotificationName"));
        messagetype=getIntent().getStringExtra("NotificationName");
        message.setText(getIntent().getStringExtra("NotificationMessage"));
        expanded_notification_title.setText(messagetype);

        Log.d("","sfdksjdfhhb"+getIntent().getStringExtra("NotificationName"));
        cardView=findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Custom.this, SplashActivity.class));

              /*  if (messagetype.equalsIgnoreCase("#Admin"))
                {
                    //startActivity(new Intent(Custom.this, SplashActivity.class));
                    startActivity(new Intent(Custom.this, BookingAllActivity1.class));
                    finish();
                }
                if(messagetype.equalsIgnoreCase("_admin"))
                {
                    startActivity(new Intent(Custom.this, SplashActivity.class));
                }
                if(messagetype.equalsIgnoreCase("_user"))
                { //check code
                    startActivity(new Intent(Custom.this, BookingFragment.class));
                }
                if(messagetype.equalsIgnoreCase("_serviceboy"))
                {
                    startActivity(new Intent(Custom.this, ServiesBoyDashboadActivity.class));
                }*/

            }
        });
      imageView=findViewById(R.id.backoption);
      imageView.setOnClickListener(new View.OnClickListener()
      {
          @Override
          public void onClick(View v)
          {
           finish();
          }
      });


    }
}