package com.nhoserviceboy.carwash.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.ServiesBoy.ServiesBoyDashboadActivity;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

public class ServiceBoyDasBoad extends AppCompatActivity implements View.OnClickListener {
  LinearLayout assignService,completedService,dailyservices;
  TextView texttoolbar,serviceboyNotification;
  ImageView logout_im;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_boy_das_boad);
        GetId();

    }
   public void GetId()
    {
        serviceboyNotification=findViewById(R.id.serviceboyNotification);
        serviceboyNotification.setOnClickListener(this);
        logout_im=findViewById(R.id.logout_im);
        logout_im.setOnClickListener(this);
        texttoolbar=findViewById(R.id.texttoolbar);
        texttoolbar.setText("Service List");
        assignService=findViewById(R.id.assignService);
        dailyservices=findViewById(R.id.dailyservices);
        completedService=findViewById(R.id.completedService);
        assignService.setOnClickListener(this);
        dailyservices.setOnClickListener(this);
        completedService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
       if (v==dailyservices)
       {
           startActivity(new Intent(ServiceBoyDasBoad.this,DailyServices.class));
       }


     if (v==assignService)
     {
         startActivity(new Intent(ServiceBoyDasBoad.this, ServiesBoyDashboadActivity.class));
     }
       if (v==completedService)
       {
           startActivity(new Intent(ServiceBoyDasBoad.this, CompletedService.class));
       }
       if (v==logout_im)
       {
           Logout_popup();
       }
        if (v==serviceboyNotification)
        {
            startActivity(new Intent(this, ServiceBoyNotification.class));
        }
    }
    private void Logout_popup()
    {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewpop = inflater.inflate(R.layout.logout_pop, null);

        Button okButton = (Button) viewpop.findViewById(R.id.okButton);
        Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
        TextView msg = (TextView) viewpop.findViewById(R.id.msg);

        final Dialog dialog = new Dialog(ServiceBoyDasBoad.this);

        dialog.setCancelable(false);
        dialog.setContentView(viewpop);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  msg.setText("");

        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                UtilMethods.INSTANCE.logout(ServiceBoyDasBoad.this);

                dialog.dismiss();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.show();


    }
}