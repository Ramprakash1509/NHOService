package com.nhoserviceboy.carwash.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhoserviceboy.carwash.Fragment.AssignBookingFragment;
import com.nhoserviceboy.carwash.R;

public class ServiceBoyHistoryInAdmin extends AppCompatActivity implements View.OnClickListener {
  LinearLayout all_bookimg,dailybooking;
    TextView texttoolbar;
    ImageView backprese;
    String name,uid;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_boy_history_in_admin);
        all_bookimg=findViewById(R.id.all_bookimg);
        dailybooking=findViewById(R.id.dailybooking);
        all_bookimg.setOnClickListener(this);
        dailybooking.setOnClickListener(this);
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(this);
        texttoolbar=findViewById(R.id.texttoolbar);
        name=getIntent().getStringExtra("name");
        uid=getIntent().getStringExtra("uid");
         bundle = new Bundle();
        bundle.putString("uid", uid);






        texttoolbar.setText(name+"'s Services List");

        Fragment fragment=new ServiceBoyHistoryFragement();
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void onClick(View v)
    {
        if (v==all_bookimg)
        {
            Fragment fragment=new ServiceBoyHistoryFragement();
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
        }
        if (v==dailybooking)
        {
            Fragment fragment=new ServiceBoyDailyHistoryInAdminPanal();
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();

        }
        if(v==backprese){
            finish();
        }

    }
}