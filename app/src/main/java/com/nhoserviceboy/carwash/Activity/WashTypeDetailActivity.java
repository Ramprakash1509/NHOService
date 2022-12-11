package com.nhoserviceboy.carwash.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nhoserviceboy.carwash.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class WashTypeDetailActivity extends AppCompatActivity  implements View.OnClickListener
{

    String set_basic_wash ,carnameselect, car_image , special_wash , water_less;

    LinearLayout ll_carservies;
    ImageView backprese;
    TextView texttoolbar;

    TextView ValidityMonth_bw_1, ValidityMonth_bw_2,ValidityMonth_bw_3;
    TextView Price_bw_1, Price_bw_2,Price_bw_3;
    RadioButton rd_bw_1,rd_bw_2,rd_bw_3;

    TextView ValidityMonth_sw_1, ValidityMonth_sw_2,ValidityMonth_sw_3;
    TextView Price_sw_1, Price_sw_2,Price_sw_3;
    RadioButton rd_sw_1,rd_sw_2,rd_sw_3;
    private RelativeLayout relativeLayout,relativeLayout2,relativeLayout3,relativeLayout4,relativeLayout5,relativeLayout6,relativeLayout7,relativeLayout8,relativeLayout9;


    TextView ValidityMonth_wlw_1, ValidityMonth_wlw_2,ValidityMonth_wlw_3;
    TextView Price_wlw_1, Price_wlw_2,Price_wlw_3;
    RadioButton rd_wlw_1,rd_wlw_2,rd_wlw_3;

    ImageView carimage , carimage2 ,carimage3 , carimage23 , carimage5 , carimage6 , carimage7 , carimage8 , carimage9  ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_type_detail);

        GetId();

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed()
    {

        Intent home = new Intent(WashTypeDetailActivity.this, DashboadActivity.class);
        startActivity(home);
        finishAffinity();
        finish();


       /* if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);*/


    }

    private void GetId()
    {

        carimage=findViewById(R.id.carimage);
        carimage2=findViewById(R.id.carimage2);
        carimage3=findViewById(R.id.carimage3);
        carimage23=findViewById(R.id.carimage23);
        carimage5=findViewById(R.id.carimage5);
        carimage6=findViewById(R.id.carimage6);
        carimage7=findViewById(R.id.carimage7);
        carimage8=findViewById(R.id.carimage8);
        carimage9=findViewById(R.id.carimage9);

        ValidityMonth_wlw_1=findViewById(R.id.ValidityMonth_wlw_1);
        ValidityMonth_wlw_2=findViewById(R.id.ValidityMonth_wlw_2);
        ValidityMonth_wlw_3=findViewById(R.id.ValidityMonth_wlw_3);
        Price_wlw_1=findViewById(R.id.Price_wlw_1);
        Price_wlw_2=findViewById(R.id.Price_wlw_2);
        Price_wlw_3=findViewById(R.id.Price_wlw_3);
        rd_wlw_1=findViewById(R.id.rd_wlw_1);
        rd_wlw_2=findViewById(R.id.rd_wlw_2);
        rd_wlw_3=findViewById(R.id.rd_wlw_3);

        ValidityMonth_sw_1=findViewById(R.id.ValidityMonth_sw_1);
        ValidityMonth_sw_2=findViewById(R.id.ValidityMonth_sw_2);
        ValidityMonth_sw_3=findViewById(R.id.ValidityMonth_sw_3);
        Price_sw_1=findViewById(R.id.Price_sw_1);
        Price_sw_2=findViewById(R.id.Price_sw_2);
        Price_sw_3=findViewById(R.id.Price_sw_3);
        rd_sw_1=findViewById(R.id.rd_sw_1);
        rd_sw_2=findViewById(R.id.rd_sw_2);
        rd_sw_3=findViewById(R.id.rd_sw_3);

        ValidityMonth_bw_1=findViewById(R.id.ValidityMonth_bw_1);
        ValidityMonth_bw_2=findViewById(R.id.ValidityMonth_bw_2);
        ValidityMonth_bw_3=findViewById(R.id.ValidityMonth_bw_3);
        Price_bw_1=findViewById(R.id.Price_bw_1);
        Price_bw_2=findViewById(R.id.Price_bw_2);
        Price_bw_3=findViewById(R.id.Price_bw_3);
        rd_bw_1=findViewById(R.id.rd_bw_1);
        rd_bw_2=findViewById(R.id.rd_bw_2);
        rd_bw_3=findViewById(R.id.rd_bw_3);
        ////////////////////////////////////////
        relativeLayout=findViewById(R.id.relative);
        relativeLayout2=findViewById(R.id.relative2);
        relativeLayout3=findViewById(R.id.relative3);
        relativeLayout4=findViewById(R.id.relative4);
        relativeLayout5=findViewById(R.id.relative5);
        relativeLayout6=findViewById(R.id.relative6);
        relativeLayout7=findViewById(R.id.relative7);
        relativeLayout8=findViewById(R.id.relative8);
        relativeLayout9=findViewById(R.id.relative9);

        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4.setOnClickListener(this);
        relativeLayout5.setOnClickListener(this);
        relativeLayout6.setOnClickListener(this);
        relativeLayout7.setOnClickListener(this);
        relativeLayout8.setOnClickListener(this);
        relativeLayout9.setOnClickListener(this);





        set_basic_wash=getIntent().getStringExtra("set_basic_wash");
        special_wash=getIntent().getStringExtra("special_wash");
        water_less=getIntent().getStringExtra("water_less");
        carnameselect=getIntent().getStringExtra("carnameselect");
        car_image=getIntent().getStringExtra("car_image");

        backprese=findViewById(R.id.backprese);
        ll_carservies=findViewById(R.id.ll_carservies);
        texttoolbar=findViewById(R.id.texttoolbar);
       // texttoolbar.setText("Wash Detail");
        texttoolbar.setText(carnameselect);


        rd_bw_1.setOnClickListener(this);
        rd_bw_2.setOnClickListener(this);
        rd_bw_3.setOnClickListener(this);

        rd_sw_1.setOnClickListener(this);

        rd_sw_2.setOnClickListener(this);
        rd_sw_3.setOnClickListener(this);

        rd_wlw_1.setOnClickListener(this);
        rd_wlw_2.setOnClickListener(this);
        rd_wlw_3.setOnClickListener(this);
        backprese.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);


        ///////////////////////////////////////////Basic Wash /////////////////////


        String  bw = ""+set_basic_wash.replace("{","");
        String bw1 =""+ bw.replace("}","");
        Log.d("jbdskvbkbvj: ",bw);
        String[] recent;
        recent = bw1.split(",");

        String[] bwname1;
        bwname1 = recent[0].split("=");

        String[] bwname2;
        bwname2 = recent[1].split("=");

        String[] bwname3;
        bwname3 = recent[2].split("=");




        ValidityMonth_bw_1.setText(""+bwname1[0] );
        ValidityMonth_bw_2.setText(""+bwname2[0] );
        ValidityMonth_bw_3.setText(""+bwname3[0] );

        Price_bw_1.setText("Rs. "+bwname1[1] );
        Price_bw_2.setText("Rs. "+bwname2[1] );
        Price_bw_3.setText("Rs. "+bwname3[1] );


        String[] bwname4;
        bwname4 = recent[3].split("=");




        String url= "" + bwname4[1];

Glide.with(WashTypeDetailActivity.this)
                .load(url)
                .placeholder(R.drawable.rnd_logo)
                .into(carimage);

 Glide.with(WashTypeDetailActivity.this)
                .load(url)
                .placeholder(R.drawable.rnd_logo)
                .into(carimage2);

 Glide.with(WashTypeDetailActivity.this)
                .load(url)
                .placeholder(R.drawable.rnd_logo)
                .into(carimage3);


        ///////////////////////////////////////////Special Wash /////////////////////

        String  sw = ""+special_wash.replace("{","");
        String sw1 =""+ sw.replace("}","");
        String[] sw_recent;
        sw_recent = sw1.split(",");

        String[] swname1;
        swname1 = sw_recent[0].split("=");

        String[] swname2;
        swname2 = sw_recent[1].split("=");

        String[] swname3;
        swname3 = sw_recent[2].split("=");

        ValidityMonth_sw_1.setText(""+swname1[0] );
        ValidityMonth_sw_2.setText(""+swname2[0] );
        ValidityMonth_sw_3.setText(""+swname3[0] );

        Price_sw_1.setText("Rs. "+swname1[1] );
        Price_sw_2.setText("Rs. "+swname2[1] );
        Price_sw_3.setText("Rs. "+swname3[1] );



        String[] swname4;
        swname4 = sw_recent[3].split("=");





        Glide.with(WashTypeDetailActivity.this)
                .load(swname4[1]+"")
                .placeholder(R.drawable.rnd_logo)
                .into(carimage23);

        Glide.with(WashTypeDetailActivity.this)
                .load(swname4[1]+"")
                .placeholder(R.drawable.rnd_logo)
                .into(carimage5);

        Glide.with(WashTypeDetailActivity.this)
                .load(swname4[1]+"")
                .placeholder(R.drawable.rnd_logo)
                .into(carimage6);


///////////////////////////////////////////Water Less Wash	 /////////////////////

        String  wlw = ""+water_less.replace("{","");
        String wlw1 =""+ wlw.replace("}","");
        String[] wlw_recent;
        wlw_recent = wlw1.split(",");

        String[] wlwname1;
        wlwname1 = wlw_recent[0].split("=");

        String[] wlwname2;
        wlwname2 = wlw_recent[1].split("=");

        String[] wlwname3;
        wlwname3 = wlw_recent[2].split("=");

        ValidityMonth_wlw_1.setText(""+wlwname1[0] );
        ValidityMonth_wlw_2.setText(""+wlwname2[0] );
        ValidityMonth_wlw_3.setText(""+wlwname3[0] );

        Price_wlw_1.setText("Rs. "+wlwname1[1] );
        Price_wlw_2.setText("Rs. "+wlwname2[1] );
        Price_wlw_3.setText("Rs. "+wlwname3[1] );



        String[] wlwname4;
        wlwname4 = wlw_recent[3].split("=");

        Glide.with(WashTypeDetailActivity.this)
                .load(wlwname4[1]+"")
                .placeholder(R.drawable.rnd_logo)
                .into(carimage7);

        Glide.with(WashTypeDetailActivity.this)
                .load(wlwname4[1]+"")
                .placeholder(R.drawable.rnd_logo)
                .into(carimage8);

        Glide.with(WashTypeDetailActivity.this)
                .load(wlwname4[1]+"")
                .placeholder(R.drawable.rnd_logo)
                .into(carimage9);


//////////////////////////////////////////////Water Less Wash	End //////////////////////////////


        SetRadioButton();

    }

    private void SetRadioButton()
    {


        rd_bw_1.setChecked(false);
        rd_bw_2.setChecked(false);
        rd_bw_3.setChecked(false);

        rd_sw_1.setChecked(false);
        rd_sw_2.setChecked(false);
        rd_sw_3.setChecked(false);

        rd_wlw_1.setChecked(false);
        rd_wlw_2.setChecked(false);
        rd_wlw_3.setChecked(false);



    }


    @Override
    public void onClick(View view)
    {
        if (view==relativeLayout || view==rd_bw_1)
        {    SetRadioButton();
            rd_bw_1.setChecked(true);
            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_bw_1.getText().toString().trim() );
            i.putExtra("Price","" +Price_bw_1.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"3" );
            i.putExtra("month_count","" +"3" );
            i.putExtra("car_image","" +""+car_image );
            startActivity(i);


        }


       /* if(view==rd_bw_1)
        {
            SetRadioButton();
            rd_bw_1.setChecked(true);


            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_bw_1.getText().toString().trim() );
            i.putExtra("Price","" +Price_bw_1.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"3" );
            i.putExtra("month_count","" +"3" );
            i.putExtra("car_image","" +""+car_image );
            startActivity(i);



        }
       if(view==relativeLayout2)
        {
            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_bw_2.getText().toString().trim() );
            i.putExtra("Price","" +Price_bw_2.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"5" );
            i.putExtra("month_count","" +"3" );
            i.putExtra("car_image","" +""+car_image );

            startActivity(i);
        }*/

        if(view==rd_bw_2 || view==relativeLayout2)
        {

            SetRadioButton();
            rd_bw_2.setChecked(true);


            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_bw_2.getText().toString().trim() );
            i.putExtra("Price","" +Price_bw_2.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"5" );
            i.putExtra("month_count","" +"3" );
            i.putExtra("car_image","" +""+car_image );

            startActivity(i);


        }



        if(view==rd_bw_3 || view==relativeLayout3){


            SetRadioButton();
            rd_bw_3.setChecked(true);

            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_bw_3.getText().toString().trim() );
            i.putExtra("Price","" +Price_bw_3.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"0" );
            i.putExtra("month_count","" +"0" );
            i.putExtra("car_image","" +""+car_image );

            startActivity(i);

        }




        if(view==rd_sw_1 || view==relativeLayout4){


            SetRadioButton();
            rd_sw_1.setChecked(true);


            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_sw_1.getText().toString().trim() );
            i.putExtra("Price","" +Price_sw_1.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"3" );
            i.putExtra("month_count","" +"3" );
            i.putExtra("car_image","" +""+car_image );

            startActivity(i);
        }


        if(view==rd_sw_2 || view==relativeLayout5)
        {


            SetRadioButton();
            rd_sw_2.setChecked(true);


            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_sw_2.getText().toString().trim() );
            i.putExtra("Price","" +Price_sw_2.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"5" );
            i.putExtra("month_count","" +"3" );
            i.putExtra("car_image","" +""+car_image );

            startActivity(i);

        }


        if(view==rd_sw_3 || view==relativeLayout6)
        {

            SetRadioButton();
            rd_sw_3.setChecked(true);

            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_sw_3.getText().toString().trim() );
            i.putExtra("Price","" +Price_sw_3.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"0" );
            i.putExtra("month_count","" +"0" );
            i.putExtra("car_image","" +""+car_image );

            startActivity(i);

        }



        if(view==rd_wlw_1 ||view==relativeLayout7){

            SetRadioButton();
            rd_wlw_1.setChecked(true);


            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_wlw_1.getText().toString().trim() );
            i.putExtra("Price","" +Price_wlw_1.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"3" );
            i.putExtra("month_count","" +"3" );
            i.putExtra("car_image","" +""+car_image );

            startActivity(i);

        }


        if(view==rd_wlw_2 || view==relativeLayout8){

            SetRadioButton();
            rd_wlw_2.setChecked(true);


            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_wlw_2.getText().toString().trim() );
            i.putExtra("Price","" +Price_wlw_2.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"5" );
            i.putExtra("month_count","" +"3" );
            i.putExtra("car_image","" +""+car_image );

            startActivity(i);
        }


        if(view==rd_wlw_3 || view==relativeLayout9)
        {

            SetRadioButton();
            rd_wlw_3.setChecked(true);

            Intent i =new Intent(this,BookingActivityNew.class);
            i.putExtra("washname","" +ValidityMonth_wlw_3.getText().toString().trim() );
            i.putExtra("Price","" +Price_wlw_3.getText().toString().trim()  );
            i.putExtra("carnameselect","" +carnameselect.trim() );
            i.putExtra("wash_count","" +"0" );
            i.putExtra("month_count","" +"0" );
            i.putExtra("car_image","" +""+car_image );
            startActivity(i);

        }






        if(view==backprese)
        {

            //onBackPressed();

            Intent home = new Intent(WashTypeDetailActivity.this, DashboadActivity.class);
            startActivity(home);
              finishAffinity();
           finish();

        }

    }
}