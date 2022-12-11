package com.nhoserviceboy.carwash.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.nhoserviceboy.carwash.Fragment.MapyFragment;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.ServiesBoy.MapsFragment;

public class MapShowActivity extends AppCompatActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_show);

        Fragment fragment=new MapsFragment();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout1,fragment)
                .commit();
    }

}