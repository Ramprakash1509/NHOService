package com.nhoserviceboy.carwash.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhoserviceboy.carwash.Activity.AddCarModelNumber;
import com.nhoserviceboy.carwash.Activity.BookingAllActivity1;
import com.nhoserviceboy.carwash.Activity.TodayIncome;
import com.nhoserviceboy.carwash.Activity.TotalIncome;
import com.nhoserviceboy.carwash.Admin.SeviceBoyModule.ServiceListScreen;
import com.nhoserviceboy.carwash.R;


public class AdminDashboadFragment extends Fragment  implements View.OnClickListener {

    LinearLayout all_bookimg, all_Add_Service_Boy , li_Service_Boy_List , all_my_income,todayIncome,service_delete,addModel_Number;
    Button btn_locatin;
    TextView createPackage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_admin_dashboad, container, false);

        GetId(v);

        return v;

    }

    private void GetId(View v)
    {


        all_bookimg=v.findViewById(R.id.all_bookimg);
        all_bookimg.setOnClickListener(this);
        addModel_Number=v.findViewById(R.id.addModel_Number);
        addModel_Number.setOnClickListener(this);



        all_Add_Service_Boy=v.findViewById(R.id.all_Add_Service_Boy);
        all_Add_Service_Boy.setOnClickListener(this);


        li_Service_Boy_List=v.findViewById(R.id.li_Service_Boy_List);
        li_Service_Boy_List.setOnClickListener(this);

        all_my_income=v.findViewById(R.id.all_my_income);
        all_my_income.setOnClickListener(this);
//////////////////////////////////////////////////////////////////////////////
       // service_delete=v.findViewById(R.id.service_delete);
       // service_delete.setOnClickListener(this);
        //service_delete.setVisibility(View.GONE);

       // createPackage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
      if (view==addModel_Number)
        {
            startActivity(new Intent(getActivity(), AddCarModelNumber.class));
        }
        if(view==all_my_income)
        {

            startActivity(new Intent(getActivity(), TotalIncome.class));

        }

        if(view==li_Service_Boy_List)
        {

            startActivity(new Intent(getActivity(), ServiceListScreen.class));

        }


        if(view==all_Add_Service_Boy)
        {

          //  startActivity(new Intent(getActivity(), Admin_Addshop_SignupActivity.class));
            startActivity(new Intent(getActivity(), TodayIncome.class));

        }

        if(view==all_bookimg)
        {

            //startActivity(new Intent(getActivity(), BookingAllActivity.class));
            startActivity(new Intent(getActivity(), BookingAllActivity1.class));

        }





    }
}