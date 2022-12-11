package com.nhoserviceboy.carwash.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhoserviceboy.carwash.Admin.BookingAllActivity;
import com.nhoserviceboy.carwash.Admin.DailyFragement;
import com.nhoserviceboy.carwash.Admin.TodaysBookingFragment;
import com.nhoserviceboy.carwash.Fragment.AssignBookingFragment;
import com.nhoserviceboy.carwash.Fragment.CompletededService;
import com.nhoserviceboy.carwash.R;

public class BookingAllActivity1 extends AppCompatActivity implements View.OnClickListener
{     TextView texttoolbar;
     ImageView backprese;
     LinearLayout allnewbookinb,allasignbooking,completed_bookimg,dailybooking;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_all1);
        GetId();

    }

    private void GetId()
    {
        completed_bookimg=findViewById(R.id.completed_bookimg);
        texttoolbar = findViewById(R.id.texttoolbar);
        allnewbookinb=findViewById(R.id.all_bookimg);
        dailybooking=findViewById(R.id.dailybooking);
        texttoolbar.setText("Booking List");
        allasignbooking=findViewById(R.id.all_assign_booking);
        completed_bookimg.setOnClickListener(this);
        allnewbookinb.setOnClickListener(this);
        allasignbooking.setOnClickListener(this);
        dailybooking.setOnClickListener(this);
        backprese = findViewById(R.id.backprese);
        Fragment fragment=new TodaysBookingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
        backprese.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                finish();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
       if (v==allnewbookinb)
       {
          // startActivity(new Intent(this, BookingAllActivity.class));
         Fragment fragment=new TodaysBookingFragment();
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.main_container, fragment)
                   .commit();
       }
       if (v==allasignbooking)
       {
           Fragment fragment=new AssignBookingFragment();
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.main_container, fragment)
                   .commit();
           //startActivity(new Intent(this, BookingAllActivity.class));
       }
       if (v==completed_bookimg)
       {
           Fragment fragment=new CompletededService();
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.main_container, fragment)
                   .commit();
       }  if (v== dailybooking)
    {
        Fragment fragment=new DailyFragement();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }



    }
}