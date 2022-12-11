package com.nhoserviceboy.carwash.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;

public class AboutUsFragment extends Fragment {
    TextView about_us ,tims;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_about_us, container, false);


        Getid(v);

        return v;
    }

    private void Getid(View v) {


        about_us=v.findViewById(R.id.about_us);
        tims=v.findViewById(R.id.tims);

        SharedPreferences myPreferences =  getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
        String  about_usResponse = myPreferences.getString("about_us", "");
        String timingResponse = myPreferences.getString("timing", "");


        about_us.setText(""+ about_usResponse );
        tims.setText(""+ timingResponse );
    }
}