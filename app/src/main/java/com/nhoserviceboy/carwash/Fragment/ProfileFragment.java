package com.nhoserviceboy.carwash.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.BookingActivityNew;
import com.nhoserviceboy.carwash.Activity.BookingHis;
import com.nhoserviceboy.carwash.Activity.DashboadActivity;
import com.nhoserviceboy.carwash.Activity.RegisterModel;
import com.nhoserviceboy.carwash.Activity.SignupActivity;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.util.List;

public class ProfileFragment extends Fragment  implements View.OnClickListener {

    EditText et_fullname, ed_phonenumber, ed_email, ed_Confirm_password , ed_Address , ed_Landmark;
    ImageView im_edit;
    TextView tv_update;
    FirebaseFirestore db;
    String Password="";
    String User_id="";
    int count=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        GetId(v);

        return v;

    }

    private void GetId(View v) {

        db= FirebaseFirestore.getInstance();

        tv_update=v.findViewById(R.id.tv_update);
        im_edit=v.findViewById(R.id.im_edit);
        et_fullname=v.findViewById(R.id.et_fullname);
        ed_phonenumber=v.findViewById(R.id.ed_phonenumber);
        ed_email=v.findViewById(R.id.ed_email);
         ed_Address=v.findViewById(R.id.ed_Address);
        ed_Landmark=v.findViewById(R.id.ed_Landmark);


        SharedPreferences myPreferences =  getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
        et_fullname.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.username, "") );
        ed_phonenumber.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.phone,"") );
        ed_email.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.email, "") );

        ed_Address.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.Address, "") );
        ed_Landmark.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.Landmark, "") );

        User_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");

        im_edit.setOnClickListener(this);
        tv_update.setOnClickListener(this);

         tv_update.setVisibility(View.GONE);

    }

    private void hit_CheckValue() {

        db = FirebaseFirestore.getInstance();

        db.collection("Users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Log.e("loginType","As : "+ d.getString("email") );
                                Log.e("loginType","As : "+ d.getString("loginType") );

                                SharedPreferences myPreferences =  getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);

                                if(d.getString("email").toString().trim().equalsIgnoreCase(""+myPreferences.getString(ApplicationConstant.INSTANCE.email, "") )){

                                    RegisterModel p=new RegisterModel(  ""+ed_email.getText().toString().trim(),

                                            ""+ed_phonenumber.getText().toString().trim(),
                                            ""+et_fullname.getText().toString().trim(),""+ed_Address.getText().toString().trim(),
                                            ""+ed_Landmark.getText().toString().trim() ,d.getDouble("latitude"),
                                            d.getDouble("longitude") , d.getString("loginType")+"","","" );


                                    UtilMethods.INSTANCE.setLoginrespose(getActivity(),  ""+ed_email.getText().toString().trim(),
                                            ""+ed_phonenumber.getText().toString().trim(), ""+et_fullname.getText().toString().trim(),"1",
                                            ""+User_id,""+ed_Address.getText().toString().trim(), ""+ed_Landmark.getText().toString().trim() ,d.getDouble("latitude"),d.getDouble("longitude") );

                                    db.collection("Users").document(d.getId()).set(p)
                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                            {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    if(count==0)
                                                    {
                                                        Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
                                                       count=count+1;
                                                    }
                                                }

                                            });

                                }else {

                                }


                            }

                        } else {


                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();

            }
        });

    }



    int passcount=0;


    @Override
    public void onClick(View view) {



        if(view==tv_update){

            hit_CheckValue();

        }

        if(view==im_edit){


            if(tv_update.getVisibility()==View.VISIBLE)
            {

                tv_update.setVisibility(View.GONE);
                et_fullname.setEnabled(false);
                ed_phonenumber.setEnabled(false);
                ed_Address.setEnabled(false);
                ed_Landmark.setEnabled(false);

            }else if(tv_update.getVisibility()==View.GONE){

                tv_update.setVisibility(View.VISIBLE);
                et_fullname.setEnabled(true);
                ed_phonenumber.setEnabled(true);
                ed_Address.setEnabled(true);
                ed_Landmark.setEnabled(true);


            }
        }
    }

}