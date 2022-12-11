package com.nhoserviceboy.carwash.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.AdminNotification;
import com.nhoserviceboy.carwash.Activity.DashboadActivity;
import com.nhoserviceboy.carwash.Activity.ServiceBoyNotification;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.Loader;

import java.util.List;


public class UserNotification extends Fragment
{
    private FirebaseFirestore db;
    Loader loader;
    String user_id="";
    LinearLayout ll_bookinglist;
    ProgressBar loadingPB;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View v= inflater.inflate(R.layout.fragment_user_notification, container, false);
        GetID(v);
        return v;
    }
    private void GetID(View v)
    {     ll_bookinglist = v.findViewById(R.id.ll_bookinglist);
        loadingPB = v.findViewById(R.id.idProgressBar);
        loader = new Loader(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);
        SharedPreferences myPreferences =   getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,getActivity().MODE_PRIVATE);
        user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");
        db = FirebaseFirestore.getInstance();
        db.collection("Notification").whereEqualTo("uid",user_id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                if (!queryDocumentSnapshots.isEmpty())
                {


                    loadingPB.setVisibility(View.GONE);
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : list)
                    {

                       String  s=d.getString("uid");
                       Log.d("","dbfh"+s);

                        if (getActivity() != null)
                        {
                            LayoutInflater inflater2 = null;
                            inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View mLinearView = inflater2.inflate(R.layout.notification, null);
                            final TextView Contact_number,name,message ,type,type1,time;
                            name =  mLinearView.findViewById(R.id.name);
                            Contact_number =  mLinearView.findViewById(R.id.Notification_num);
                            message =  mLinearView.findViewById(R.id.notification_message);
                            type =  mLinearView.findViewById(R.id.messanger_type);
                            type1=mLinearView.findViewById(R.id.type);
                            type1.setVisibility(View.GONE);
                            time =  mLinearView.findViewById(R.id.Notification_time);
                            type.setVisibility(View.GONE);
                            ImageButton deleteNotification;
                            name.setText(" "+ d.getString("username"));
                            deleteNotification=mLinearView.findViewById(R.id.delete);
                            deleteNotification.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {


                                    db.collection("Notification").document(d.getId())
                                            .delete()

                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                            {
                                                @Override
                                                public void onSuccess(Void unused)
                                                {
                                                    Toast.makeText(getActivity(), "Deleted   ", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getActivity(), DashboadActivity.class));
                                                    getActivity().finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener()
                                            {
                                                @Override
                                                public void onFailure(@NonNull Exception e)
                                                {

                                                }
                                            });


                                }
                            });


                            name.setText(" "+ d.getString("username"));
                            Contact_number.setText(" "+ d.getString("contact_number"));
                            message.setText(" "+ d.getString("Message"));
                            time.setText(" "+ d.getString("time"));
                            ll_bookinglist.addView(mLinearView);

                        }
                    }
                }
                else
                {
                    loadingPB.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {

            }
        });

    }
}