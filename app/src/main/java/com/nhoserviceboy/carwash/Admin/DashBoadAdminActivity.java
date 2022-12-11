package com.nhoserviceboy.carwash.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhoserviceboy.carwash.Activity.AdminNotification;
import com.nhoserviceboy.carwash.Admin.SeviceBoyModule.ServiceListScreen;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

public class DashBoadAdminActivity extends AppCompatActivity  implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    RelativeLayout rlSideList;
    FrameLayout main_container;
    private Toolbar mToolbar;
    TextView home_tv, tv_add_shop , tv_add_shop_list , id_Logout,adminNotification;


    TextView city_name;
    ImageView booking;
    private static long back_pressed;
    public static int countBackstack = 0;
    private static final int TIME_DELAY = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_boad_admin);
        GetID();
        initToolbar();

    }



    public void closeDrawer()
    {
        mDrawerLayout.closeDrawer(rlSideList);
    }

    public void openDrawer() {

        mDrawerLayout.openDrawer(rlSideList);
    }



    private void initToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");

        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                openDrawer();

            }
        });

    }




    private void GetID()
    {
        adminNotification=findViewById(R.id.adminNotification);
        adminNotification.setOnClickListener(this);
        tv_add_shop_list=findViewById(R.id.tv_add_shop_list);
        tv_add_shop_list.setOnClickListener(this);

        id_Logout=findViewById(R.id.id_Logout);
        id_Logout.setOnClickListener(this);


       tv_add_shop=findViewById(R.id.tv_add_shop);
        tv_add_shop.setOnClickListener(this);


        home_tv=findViewById(R.id.home_tv);
        home_tv.setOnClickListener(this);

        mDrawerLayout=findViewById(R.id.drawer_layout);
        rlSideList=findViewById(R.id.side_list);

        loadFragment(new AdminDashboadFragment());


    }


    private void Logout_popup()
    {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewpop = inflater.inflate(R.layout.logout_pop, null);

        Button okButton = (Button) viewpop.findViewById(R.id.okButton);
        Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
        TextView msg = (TextView) viewpop.findViewById(R.id.msg);

        final Dialog dialog = new Dialog(DashBoadAdminActivity.this);

        dialog.setCancelable(false);
        dialog.setContentView(viewpop);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  msg.setText("");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UtilMethods.INSTANCE.logout(DashBoadAdminActivity.this);

                dialog.dismiss();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }



    @Override
    public void onClick(View view)
    {

          if (view==adminNotification)
          {
              startActivity(new Intent(this, AdminNotification.class));
          }
        if(view==id_Logout)
        {
            Logout_popup();
        }

        if(view==tv_add_shop_list){


          //  startActivity(new Intent(this, MyShopBoyListActivity.class));
          startActivity(new Intent(this, ServiceListScreen.class));



        }

  if(view==tv_add_shop)
  {
      //startActivity(new Intent(this, Admin_Addshop_SignupActivity.class));
      startActivity(new Intent(this, PhoneAuthServiceboy.class));
  }


        if(view==home_tv){


            closeDrawer();
            loadFragment(new AdminDashboadFragment());


        }


    }




    private boolean loadFragment(Fragment fragment) {
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