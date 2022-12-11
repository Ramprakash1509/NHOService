package com.nhoserviceboy.carwash.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nhoserviceboy.carwash.Fragment.AboutUsFragment;
import com.nhoserviceboy.carwash.Fragment.HomeFragment;
import com.nhoserviceboy.carwash.Fragment.ReviewsFragment;
import com.nhoserviceboy.carwash.Fragment.ServicesFragment;
import com.nhoserviceboy.carwash.R;

public class CompanyOverviewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView texttoolbar ,tv_Services ,tv_AboutUs ,tv_Review;
    ImageView backprese  , select_car_image;

    String selectcar_name , Select_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_overview);

        GetId();

    }

    private void GetId() {

        selectcar_name=getIntent().getStringExtra("selectcar_name");
        Select_url=getIntent().getStringExtra("Select_url");

        tv_Services=findViewById(R.id.tv_Services);
        tv_AboutUs=findViewById(R.id.tv_AboutUs);
        tv_Review=findViewById(R.id.tv_Review);

        tv_Services.setOnClickListener(this);
        tv_AboutUs.setOnClickListener(this);
        tv_Review.setOnClickListener(this);


        texttoolbar=findViewById(R.id.texttoolbar);
        select_car_image=findViewById(R.id.select_car_image);
        backprese=findViewById(R.id.backprese);

        Glide.with(CompanyOverviewActivity.this)
                .load(Select_url)
                .placeholder(R.drawable.carwashbanner)
                .into(select_car_image);



        backprese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        texttoolbar.setText(selectcar_name+"");

        loadFragment(new ServicesFragment());

       SetColour();

       tv_Services.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
       tv_Services.setTextColor(getResources().getColor(R.color.white));

    }

    private void SetColour() {

        tv_Services.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        tv_AboutUs.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        tv_Review.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

        tv_Services.setTextColor(getResources().getColor(R.color.black));
        tv_AboutUs.setTextColor(getResources().getColor(R.color.black));
        tv_Review.setTextColor(getResources().getColor(R.color.black));

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
    public void onClick(View view) {

                if(view==tv_Services){

                    loadFragment(new ServicesFragment());

                    SetColour();
                    tv_Services.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    tv_Services.setTextColor(getResources().getColor(R.color.white));

                }

                if(view==tv_AboutUs){

                    loadFragment(new AboutUsFragment());

                    SetColour();
                    tv_AboutUs.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    tv_AboutUs.setTextColor(getResources().getColor(R.color.white));



                }

               if(view==tv_Review){

                   loadFragment(new ReviewsFragment());

                   SetColour();
                   tv_Review.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                   tv_Review.setTextColor(getResources().getColor(R.color.white));

                }

    }
}