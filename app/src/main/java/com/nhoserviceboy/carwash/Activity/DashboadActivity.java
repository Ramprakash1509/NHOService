package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.nhoserviceboy.carwash.Fragment.BookingFragment;
import com.nhoserviceboy.carwash.Fragment.HomeFragment;
import com.nhoserviceboy.carwash.Fragment.HomeFragment1;
import com.nhoserviceboy.carwash.Fragment.MapyFragment;
import com.nhoserviceboy.carwash.Fragment.ProfileFragment;
import com.nhoserviceboy.carwash.Fragment.SearchCarTypeFragement;
import com.nhoserviceboy.carwash.Fragment.SearchFragment;
import com.nhoserviceboy.carwash.Fragment.SearchLocationMapsFragment;
import com.nhoserviceboy.carwash.Fragment.UserNotification;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.FragmentActivityMessage;
import com.nhoserviceboy.carwash.Utils.GlobalBus;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DashboadActivity extends AppCompatActivity implements View.OnClickListener , BottomNavigationView.OnNavigationItemSelectedListener   {


    TextView city_name,userNotification;
    ImageView booking;
    private static long back_pressed;
    public static int countBackstack = 0;
    private static final int TIME_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboad);
        Getid();

    }



    @Override
    public void onStart() {
        super.onStart();

        if (!EventBus.getDefault().isRegistered(this))
        {
            GlobalBus.getBus().register(this);
        }
    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage)
    {

        if (activityFragmentMessage.getFrom().equalsIgnoreCase("BookingType"))
        {
            Toast.makeText(this, ""+activityFragmentMessage.getMessage(), Toast.LENGTH_SHORT).show();
            loadFragment(new BookingFragment());


        }

    }


    private void Getid()
    {

       userNotification=findViewById(R.id.userNotification);
       userNotification.setOnClickListener(this);
        booking=findViewById(R.id.booking);
        city_name=findViewById(R.id.city_name);


        city_name.setOnClickListener(this);
        booking.setOnClickListener(this);

        city_name.setText("Services");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String page_select_res = myPreferences.getString(ApplicationConstant.INSTANCE.page_select, "");

         Log.e("page_select_res","As : "+ page_select_res);

         if(page_select_res.toString().trim().equalsIgnoreCase("1")){

           //  loadFragment(new HomeFragment1());
             loadFragment(new SearchCarTypeFragement());

         }else
         {

             loadFragment(new BookingFragment());

         }
    }

    protected void sendEmail()
    {
        Log.i("Send email", "");
        String[] TO = {"khanuzair@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("khanuzair970@gmail.com"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
         } catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(DashboadActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:

                city_name.setText("Services");
              // fragment = new HomeFragment1();
                fragment = new SearchCarTypeFragement();
                break;

            case R.id.booking:
                //sendEmail();
                city_name.setText("Booking");
                fragment = new BookingFragment();
                break;



            case R.id.Search:

                city_name.setText("Search");
                fragment = new SearchFragment();

                break;

            case R.id.Profile:

                city_name.setText("Profile");
                fragment = new ProfileFragment();

                break;

            case R.id.logout_it:

                Logout_popup();

                break;





        }

        return loadFragment(fragment);

    }

    private void Logout_popup()
    {



        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewpop = inflater.inflate(R.layout.logout_pop, null);

        Button okButton = (Button) viewpop.findViewById(R.id.okButton);
        Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
        TextView msg = (TextView) viewpop.findViewById(R.id.msg);

        final Dialog dialog = new Dialog(DashboadActivity.this);

        dialog.setCancelable(false);
        dialog.setContentView(viewpop);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  msg.setText("");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UtilMethods.INSTANCE.logout(DashboadActivity.this);

                dialog.dismiss();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }

    private boolean loadFragment(Fragment fragment)
    {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }



    @Override
    public void onClick(View v)
    {
        if (v==userNotification)
        {    city_name.setText("Notification");
            Fragment fragment=new UserNotification();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();

        }
        if(v==booking)
        {
            startActivity(new Intent(this, BookingActivity.class));

        }
    }



    @Override
    public void onBackPressed() {
        if (countBackstack > 0) {
            countBackstack = 0;
//            fm.beginTransaction().replace(R.id.main_container, new ServiceFragment()).commit();

        } else if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}