package com.nhoserviceboy.carwash.Admin.SeviceBoyModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Admin.MyShopBoyListActivity;
import com.nhoserviceboy.carwash.Admin.PhoneAuthServiceboy;
import com.nhoserviceboy.carwash.Admin.ServiceBoyHistoryInAdmin;
import com.nhoserviceboy.carwash.R;

import java.util.List;

public class ServiceListScreen extends AppCompatActivity  implements View.OnClickListener
{


    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;
    ImageView backprese;
    TextView texttoolbar;
    EditText editText;
    ImageView addserviceboy;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        GetId();
        editText=findViewById(R.id.et_fullname);
    }
    private void GetId()
    {  /*img_btn_edit=findViewById(R.id.editing);
        img_btn_delete=findViewById(R.id.delete);
        img_btn_edit.setOnClickListener(this);
        img_btn_delete.setOnClickListener(this);
        */
        addserviceboy=findViewById(R.id.addserviceboy);
        addserviceboy.setOnClickListener(this);
        backprese=findViewById(R.id.backprese);
        texttoolbar=findViewById(R.id.texttoolbar);

        backprese.setOnClickListener(this);

        texttoolbar.setText("Service Boy Details");

        loadingPB =  findViewById(R.id.idProgressBar);
        ll_bookinglist =  findViewById(R.id.ll_bookinglist);

        db = FirebaseFirestore.getInstance();
        db.collection("Users").whereEqualTo("loginType","serviceboy").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (!queryDocumentSnapshots.isEmpty())
                        {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list)
                            {


                                Log.e("Nodate",""+ "hitShow");

                                LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View mLinearView = inflater2.inflate(R.layout.shop_boyadaapter1, null);

                                final TextView email_id,Contact_number,name,address , landmark ;
                                final ImageButton delete_bt,edit_btn;
                                delete_bt=mLinearView.findViewById(R.id.delete);
                                edit_btn=mLinearView.findViewById(R.id.editing);



                                delete_bt.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        ///////////////////////////////////////
                                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            View viewpop = inflater.inflate(R.layout.delete_services_boy_pop, null);
                                            Button okButton = (Button) viewpop.findViewById(R.id.okButton);
                                            Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
                                            TextView msg = (TextView) viewpop.findViewById(R.id.msg);
                                            msg.setText("Do you want to Sure Delete Details ?");
                                            final Dialog dialog = new Dialog(ServiceListScreen.this);
                                            dialog.setCancelable(false);
                                            dialog.setContentView(viewpop);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                            //  msg.setText("");

                                            okButton.setOnClickListener(new View.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(View v)
                                                {
                                                    //String id= String.valueOf(mLinearView.getId());

                                                     FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                                    firestore.collection("Users").document(d.getId())
                                                            .delete()

                                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                                            {
                                                                @Override
                                                                public void onSuccess(Void unused)
                                                                {
                                                                    Toast.makeText(ServiceListScreen.this, "Deleted boy services  ", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(ServiceListScreen.this, MyShopBoyListActivity.class));
                                                                   finish();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener()
                                                            {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e)
                                                                {

                                                                }
                                                            });


                                                   // Toast.makeText(Delete.this, "hiii", Toast.LENGTH_SHORT).show();

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




                                        //////////////////////////////////////

                                    }
                                });

                                edit_btn.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                       /* /////////////////////////////////////
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View viewpop = inflater.inflate(R.layout.delete_services_boy_pop, null);

                                        Button okButton = (Button) viewpop.findViewById(R.id.okButton);
                                        Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
                                        TextView msg = (TextView) viewpop.findViewById(R.id.msg);

                                        final Dialog dialog = new Dialog(Delete.this);
                                        msg.setText("Do you want to update boy services ?");

                                        dialog.setCancelable(false);
                                        dialog.setContentView(viewpop);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                        //  msg.setText("");

                                        okButton.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                //Toast.makeText(Delete.this, "hiii", Toast.LENGTH_SHORT).show();
*/


                                                  Intent i=new Intent(ServiceListScreen.this, UpdateServiceBoy.class);
                                                   i.putExtra("up_name",d.getString("username") );
                                                    i.putExtra("up_phone",d.getString("phone"));
                                                    i.putExtra("up_address",d.getString("address"));
                                                    i.putExtra("up_landmark",d.getString("landmark"));
                                                    i.putExtra("up_email", d.getString("email"));
                                                    //i.putExtra("up_password",d.getString("password"));
                                                    i.putExtra("id",d.getId());
                                                    startActivity(i);

/*

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
*/

                                       /// dialog.show();







                                        ///////////////////////////////////////


                                    }
                                });

                                address =  mLinearView.findViewById(R.id.address);

                                name =  mLinearView.findViewById(R.id.name);
                                RelativeLayout ri_main =  mLinearView.findViewById(R.id.ri_main);
                                landmark =  mLinearView.findViewById(R.id.landmark);
                                email_id =  mLinearView.findViewById(R.id.email_id);
                                Contact_number =  mLinearView.findViewById(R.id.Contact_number);

                                email_id.setText(" " + d.getString("email")  );
                                Contact_number.setText("+91-" + d.getString("phone") );
                                name.setText("" + d.getString("username")  );
                                landmark.setText(   " " + d.getString("landmark"));
                                address.setText(   " " + d.getString("address"));
                                ri_main.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view)
                                    {
                                       // Intent i=new Intent(Delete.this,ServiceBoysBooking.class);
                                        Intent i=new Intent(ServiceListScreen.this, ServiceBoyHistoryInAdmin.class);
                                        i.putExtra("name",name.getText().toString());
                                        i.putExtra("uid",d.getId());
                                        startActivity(i);
                                        // Toast.makeText(MyShopBoyListActivity.this, "sdbvslbvdjlb", Toast.LENGTH_SHORT).show();
                                        // startActivity(new Intent(MyShopBoyListActivity.this,ServiceBoysBooking.class).putExtra("name",name.getText().toString()));
                                    }
                                });
                                ll_bookinglist.addView(mLinearView);



                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(ServiceListScreen.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ServiceListScreen.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    public void onClick(View view)
    {



        if(view==backprese)
        {

            finish();

        }
        if (view==addserviceboy)
        {
            startActivity(new Intent(ServiceListScreen.this, PhoneAuthServiceboy.class));
        }

    }

}