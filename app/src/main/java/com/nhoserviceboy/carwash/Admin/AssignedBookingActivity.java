package com.nhoserviceboy.carwash.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.BookingActivityNew;
import com.nhoserviceboy.carwash.Activity.BookingHis;
import com.nhoserviceboy.carwash.Admin.Model.AssignedUser;
import com.nhoserviceboy.carwash.Admin.Model.Latlong;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.Loader;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AssignedBookingActivity extends AppCompatActivity implements View.OnClickListener
{

    String packageId,carModelNumber,assigned_to,exdate,dailybooking,St_car_image, current_date_book, St_car_name  , St_washname_tv , St_wash_count ,St_user_id ,St_referenceId ,St_price_total_final,St_paymenttype,St_month_count,
            St_end_date,ed_email,St_service_time, St_ed_name,St_ed_mobile , St_ed_landmark , St_ed_address , St_current_date_book,bookingid , St_service_date,booking_status;

    TextView booked_on, modelNuber,tv_assigned,Booking_Date,name,payment_type ,servisetype, Booking_time , total_amount  , wash_Count;
    CircleImageView im5;
    RelativeLayout ri_main;
    ImageView backprese;
    TextView texttoolbar,tv_submit,tv_Service_boy;
    LinearLayout ll_booking_check;
    List<String> filterdate=new ArrayList<>();
    List<String> filtertime=new ArrayList<>();
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist;
    Loader loader;
    String u_latitude="";
    String u_longitude="";
    String u_name , u_phone , u_Address , u_Landmark, u_email;

    String user_id_lg;
    TextView tv_Slots;


    String servies_boy_fId, servies_boy_email ,servies_boy_phone , servies_boy_user_id , servies_boy_username,assigned_to_uid;
    int status=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_booking);


        Getid();


    }

    private void Getid()
    {

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);



        tv_Slots=findViewById(R.id.tv_Slots);
        tv_submit=findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        modelNuber = findViewById(R.id.modelNuber);

        tv_Service_boy=findViewById(R.id.tv_Service_boy);
        tv_Service_boy.setOnClickListener(this);
        ll_booking_check=findViewById(R.id.ll_booking_check);



        backprese=findViewById(R.id.backprese);
        texttoolbar=findViewById(R.id.texttoolbar);
        texttoolbar.setText("Assigned Booking");

        backprese.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        booking_status=getIntent().getStringExtra("booking_status");
        packageId=getIntent().getStringExtra("packageId");
        St_ed_mobile=getIntent().getStringExtra("ed_mobile");
        St_ed_address=getIntent().getStringExtra("ed_address");
        St_ed_landmark=getIntent().getStringExtra("ed_landmark");
         St_ed_name=getIntent().getStringExtra("ed_name");
        St_service_time=getIntent().getStringExtra("service_time");
        St_end_date=getIntent().getStringExtra("end_date");
        St_month_count=getIntent().getStringExtra("month_count");
        St_paymenttype=getIntent().getStringExtra("paymenttype");
        St_price_total_final=getIntent().getStringExtra("price_total_final");
        St_referenceId=getIntent().getStringExtra("referenceId");
        St_user_id=getIntent().getStringExtra("user_id");
        assigned_to=getIntent().getStringExtra("assigned_to");
        modelNuber.setText(getIntent().getStringExtra("carModelNumber"));

        Log.d("","wuiryuey"+assigned_to);

        St_wash_count=getIntent().getStringExtra("wash_count");
        St_washname_tv=getIntent().getStringExtra("washname_tv");
        St_car_name=getIntent().getStringExtra("car_name");
        St_car_image=getIntent().getStringExtra("car_image");
        dailybooking=getIntent().getStringExtra("dailybooking");
        exdate=getIntent().getStringExtra("exdate");
        carModelNumber=getIntent().getStringExtra("carModelNumber");
      //  carModelNumber=getIntent().getStringExtra("carModel");
        St_current_date_book=getIntent().getStringExtra("current_date_book");
        Log.d("","sddshfur"+St_current_date_book);
        St_service_date=getIntent().getStringExtra("service_date");
        bookingid=getIntent().getStringExtra("bookingid");
        ed_email=getIntent().getStringExtra("ed_email");
        u_latitude=getIntent().getStringExtra("u_latitude");
        u_longitude=getIntent().getStringExtra("u_longitude");
        assigned_to_uid=getIntent().getStringExtra("assigned_to_uid");
        Log.d("","assigned_to_uid"+assigned_to_uid);

        u_name=getIntent().getStringExtra("u_name");
        Log.d("","u_name"+u_name);
        u_phone=getIntent().getStringExtra("u_phone");
        u_Address=getIntent().getStringExtra("u_Address");
        u_Landmark=getIntent().getStringExtra("u_Landmark");
        u_email=getIntent().getStringExtra("u_email");
        Log.e("mobilemobilemobile",""+ St_ed_mobile  +"  St_ed_address : "+ St_ed_address +"  St_ed_address : "+ St_ed_address+"  St_ed_address : "+ St_ed_address);

        SetVlaue();

    }

    private void SetVlaue()
    {

        payment_type =  findViewById(R.id.payment_type);
        ri_main =  findViewById(R.id.ri_main);
        im5 =  findViewById(R.id.im5);
        name =  findViewById(R.id.name);
        servisetype =  findViewById(R.id.servisetype);
        Booking_time =  findViewById(R.id.Booking_time);
        booked_on =  findViewById(R.id.booked_on);
        Booking_Date =  findViewById(R.id.Booking_Date);
        total_amount =  findViewById(R.id.total_amount);
        wash_Count =  findViewById(R.id.wash_Count);
        tv_assigned =  findViewById(R.id.tv_assigned);

        wash_Count.setText("Wash Count : " + St_wash_count);
        servisetype.setText("" + St_washname_tv);

        booked_on.setText(" " + St_service_date);
        Booking_Date.setText(" " + St_current_date_book);

        name.setText("" + St_car_name);
        Booking_time.setText(   " " + St_service_time);
        total_amount.setText("" + St_price_total_final);


    }

    private void  GetLoadBookingList(String username , String email)
    {




        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        SharedPreferences myPreferences =   this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,this.MODE_PRIVATE);
        user_id_lg=""+ myPreferences.getString(ApplicationConstant.INSTANCE.username, "");

        loadingPB =  findViewById(R.id.idProgressBar);

        db = FirebaseFirestore.getInstance();
        db.collection("BookingHistory").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (!queryDocumentSnapshots.isEmpty())
                        {

                            loadingPB.setVisibility(View.GONE);

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                            filterdate.clear();
                            filtertime.clear();
                            ll_booking_check.removeAllViews();
                            for (DocumentSnapshot d : list)
                            {

                                if(d.getString("assigned_to").equalsIgnoreCase(username.toString().trim()))
                                {
                                    filterdate.add(d.getString("service_date"));
                                    Log.d("","jdhfjhgf"+d.getString("service_date"));
                                    filtertime.add(d.getString("service_time"));
                                    Log.e("Nodate",""+ "hitShow");
                                    LayoutInflater inflater2 = null;
                                    inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View mLinearView = inflater2.inflate(R.layout.booking_abapter_slate, null);

                                    final TextView   slate_booktime , slate_book_date ;

                                    slate_booktime =  mLinearView.findViewById(R.id.slate_booktime);
                                    slate_book_date =  mLinearView.findViewById(R.id.slate_book_date);

                                    slate_booktime.setText(""+ d.getString("service_time") );
                                    slate_book_date.setText(""+ d.getString("service_date") );
                                    Log.d("","fdgdhfugre");
                                    ll_booking_check.addView(mLinearView);

                                }
                                else
                                {

                                    Log.e("Nodate",""+ "Nodate");

                                }

                            }

                        } else
                        {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(AssignedBookingActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(AssignedBookingActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });











    }


    private void ServiceListBoy()
    {
         Log.d("","dgdf");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewnew = inflater.inflate(R.layout.servies_boy_popup_list, null);


        ImageView   dismiss_im = (ImageView) viewnew.findViewById(R.id.dismiss_im);
        ll_bookinglist =  viewnew.findViewById(R.id.ll_bookinglist);
        loadingPB = viewnew.findViewById(R.id.idProgressBar);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(viewnew);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        db = FirebaseFirestore.getInstance();

        db.collection("Users").whereEqualTo("loginType","serviceboy").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty())
                        {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                           ll_bookinglist.removeAllViews();
                            for (DocumentSnapshot d : list)
                            {

                                LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View mLinearView = inflater2.inflate(R.layout.select_servies_boy_abapter, null);

                                final TextView service_boy_name  ;
                                service_boy_name =  mLinearView.findViewById(R.id.service_boy_name);
                                service_boy_name.setText(" " + d.getString("username")  );

                                service_boy_name.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                                servies_boy_fId=""+d.getId();
                                                servies_boy_email=""+d.getString("email");
                                                servies_boy_phone=""+d.getString("phone");
                                                servies_boy_user_id=""+d.getString("user_id");
                                                servies_boy_username=""+d.getString("username");



                                        Log.e("username",""+ d.getString("username") +" :   Id : :   "+ d.getId());

                                        tv_Service_boy.setText(""+ d.getString("username" ));
                                        ll_booking_check.setVisibility(View.VISIBLE);
                                        tv_Slots.setVisibility(View.VISIBLE);




                                        GetLoadBookingList(d.getString("username" )+"",""+d.getString("email" ));

                                        dialog.dismiss();

                                    }
                                });


                                ll_bookinglist.addView(mLinearView);

                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(AssignedBookingActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(AssignedBookingActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });


        /* Show List  Start end   */

        dismiss_im.setOnClickListener(new View.OnClickListener()
        {
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

        if(view==tv_Service_boy)
        {


            ServiceListBoy();


        }

    if(view==tv_submit)
    {


        if(tv_Service_boy.getText().toString().trim().equalsIgnoreCase("Select Service Boy"))
        {  Toast.makeText(this, "Select Service Boy", Toast.LENGTH_SHORT).show();
        }
       else if(filterdate.contains(St_service_date) && filtertime.contains(St_service_time))
        {Toast.makeText(this, "This Service Boy already book on this slot", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            //SendNotification();
            sendNotificationServiceBoy(servies_boy_fId,St_ed_mobile,St_ed_name);

            hit_Assigned(St_car_image,  St_car_name  , St_washname_tv , St_wash_count ,St_user_id ,St_referenceId ,St_price_total_final,St_paymenttype,St_month_count,
                    St_end_date,St_service_time, St_ed_name, St_ed_mobile , St_ed_landmark , St_ed_address , St_current_date_book ,
                    St_service_date,servies_boy_fId, servies_boy_email ,servies_boy_phone ,
                    servies_boy_user_id , servies_boy_username,u_longitude,u_latitude,booking_status,dailybooking,exdate,carModelNumber);


        }

        }

    }

    private void hit_Assigned(String  car_image, String st_car_name, String st_washname_tv, String st_wash_count, String st_user_id,
                              String st_referenceId, String st_price_total_final, String st_paymenttype, String st_month_count,
                              String st_end_date, String st_service_time, String st_ed_name, String st_ed_mobile, String st_ed_landmark,
                              String st_ed_address, String st_current_date_book, String st_service_date, String servies_boy_fId,
                              String servies_boy_email, String servies_boy_phone, String servies_boy_user_id, String servies_boy_username
                ,String u_longitude, String u_latitude,String booking_status ,String dailybooking,String exdate ,String carModelNumber )
    {

       Log.e("Aasasasa",""+car_image  +"  St_car_image  "+ St_car_image);
      CollectionReference dbCourses = db.collection("Booking_Assigned");
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Location");
            Latlong latlong=new Latlong();
            latlong.setLatitude("26.8002405");
            latlong.setLongitude("80.8974137");
            latlong.setUlongitude(u_longitude);
            latlong.setUlatitude(u_latitude);
            latlong.setService_id(bookingid);
            latlong.setService_boy_id(servies_boy_fId);
            Log.d("","gffdg"+servies_boy_user_id);
            String key=myRef.push().getKey();
            myRef.child(key).setValue(latlong);
            Log.d("","dshgd"+key);
            Log.d("","yutwtyyt"+latlong.getLongitude());

        // adding our data to our courses object class.
            Calendar calendar = Calendar.getInstance();
            Date modifyDate = (Date) calendar.getTime();
            String Booking_status="Assigned";
            String flag="0";
            AssignedUser courses = new AssignedUser(St_car_image,  St_car_name  , St_washname_tv ,
                    St_wash_count ,St_user_id ,St_referenceId ,St_price_total_final,St_paymenttype,St_month_count,
                St_end_date,St_service_time, St_ed_name, St_ed_mobile , St_ed_landmark , St_ed_address , St_current_date_book ,
                St_service_date,servies_boy_fId, servies_boy_email ,servies_boy_phone , servies_boy_user_id , servies_boy_username ,
                    u_longitude ,u_latitude,bookingid,key,modifyDate,flag,booking_status,dailybooking,exdate,carModelNumber);

        // below method is use to add data to Firebase Firestore.

        dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {

                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }


                hit_AssignedBookingUpdate( "" +servies_boy_username,""+servies_boy_fId,key,modifyDate,Booking_status,dailybooking,exdate,carModelNumber);

                /*Toast.makeText(AssignedBookingActivity.this, "This booking Assigned to mr. " +servies_boy_username, Toast.LENGTH_SHORT).show();
                finish();*/

            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {

                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }

                Toast.makeText(AssignedBookingActivity.this, "Fail to add User \n" + e, Toast.LENGTH_SHORT).show();

            }
        });





    }
    }




public  void sendNotificationServiceBoy(String servies_boy_fId,String userNumber,String userName)
{   Calendar calendar = Calendar.getInstance();
    Date time = (Date) calendar.getTime();
    String currentime= String.valueOf(time);
    Map<String, Object> userNotification = new HashMap<>();
           Log.d("uwry834765",servies_boy_fId+"   "+userName);
    db.collection("Users").whereEqualTo("loginType","serviceboy").whereEqualTo("uid",servies_boy_fId)
            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots)
        {
            if (!queryDocumentSnapshots.isEmpty())
            {
                queryDocumentSnapshots.getDocuments();
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot doc : list)
                {
                    Log.d("","serviceBoytoken"+doc.getString("uitokenId"));
                    String message=doc.getString("username")+" you are ready for assign this bookin "+userName;
                    String title="Assigned New Service";
                    UtilMethods.INSTANCE.sendNotifications(AssignedBookingActivity.this, doc.getString("uitokenId"), title, message );
                    userNotification.put("ServiceBoy","Service_boy");
                    userNotification.put("uid",doc.getString("uid"));
                    userNotification.put("username",doc.getString("username"));
                    userNotification.put("message",message);
                    userNotification.put("time",currentime);
                    userNotification.put("Number",userNumber);
                    Log.d("","fdfxdf"+doc.getString("uid"));
                    db.collection("Notification").document().set(userNotification);

                }}
        }
    }).addOnFailureListener(new OnFailureListener()
    {
        @Override
        public void onFailure(@NonNull Exception e)
        {
  Log.d("",""+e);
        }
    });
}



    private void hit_AssignedBookingUpdate(String servies_boy_username,String serviceBoyUid,String key,Date modifyDate,String Booking_status,String dailybooking,String exdate,String carModelNumber)
    {

        db = FirebaseFirestore.getInstance();

        db.collection("BookingHistory").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list)
                            {

                                /*
                                if(d.getString("email").toString().trim().equalsIgnoreCase(""+myPreferences.getString(ApplicationConstant.INSTANCE.email, "") )) {
*/



                                if(St_car_name.toString().trim().equalsIgnoreCase(d.getString("car_name"))  && St_washname_tv.toString().trim().equalsIgnoreCase(d.getString("washname_tv")) && bookingid.equalsIgnoreCase(d.getId()) )
                                {

                                    BookingHis p=new BookingHis( St_current_date_book,St_paymenttype,St_referenceId, St_ed_name  ,
                                            St_ed_mobile , St_service_date , St_service_time , St_ed_address , St_ed_landmark , ed_email  , St_car_name ,
                                            St_washname_tv , St_price_total_final, St_user_id,  St_wash_count ,  St_month_count ,St_car_image ,
                                            St_end_date  ,servies_boy_username+"" ,""+ u_name, ""+ u_email, ""+ u_Address, ""+ u_Landmark, ""+ u_latitude,
                                            ""+ u_longitude ,""+u_phone,"0",modifyDate,Booking_status,dailybooking,exdate,carModelNumber,packageId);

                                    db.collection("BookingHistory").document(d.getId()).set(p)
                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                            {
                                                @Override
                                                public void onSuccess(Void aVoid)
                                                {

                                                    db.collection("BookingHistory").document(d.getId()).update("traking_key",key);
                                                    db.collection("BookingHistory").document(d.getId()).update("assigned_to_uid",serviceBoyUid).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused)
                                                        {
                                                        }
                                                    });
                                                    Log.d("","sdfhshdgf");
                                                    Toast.makeText(AssignedBookingActivity.this,  "This booking assigned to Mr. " +servies_boy_username, Toast.LENGTH_SHORT).show();
                                                    Intent home = new Intent(AssignedBookingActivity.this, DashBoadAdminActivity.class);
                                                    home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(home);

                                                }
                                            });

                                }
                                else
                                {


                                }


                            }

                        } else {


                            Toast.makeText(AssignedBookingActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AssignedBookingActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}