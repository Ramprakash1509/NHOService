package com.nhoserviceboy.carwash.Fragment;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cashfree.pg.CFPaymentService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.BookingActivityNew;
import com.nhoserviceboy.carwash.Activity.BookingHis;
import com.nhoserviceboy.carwash.Activity.DailyBooking;
import com.nhoserviceboy.carwash.Activity.DashboadActivity;
import com.nhoserviceboy.carwash.Activity.Orders_Details_Activity;
import com.nhoserviceboy.carwash.Admin.MapuserActivity;
import com.nhoserviceboy.carwash.Admin.Model.Latlong;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.FragmentActivityMessage;
import com.nhoserviceboy.carwash.Utils.GlobalBus;
import com.nhoserviceboy.carwash.Utils.Loader;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingFragment extends Fragment
{

    private FirebaseFirestore db;
    ProgressBar loadingPB;
    LinearLayout ll_bookinglist,daily;
    String user_id="";
    Loader loader;
    TextView dailybooking;
    String  service_date , ed_name   , ed_mobile ,ed_date , service_time , ed_Address ,
            ed_Landmark , ed_email ,  Car_name , washname_tv , price_total_final ,  wash_count , month_count  , end_date ,car_image ;
    private String Document_id;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_booking, container, false);

        GetID(v);
        return v;
    }
    @Override
    public void onResume()
    {

       // Hit_get_book();
        super.onResume();
    }


    private void GetID(View v)
    {
        SharedPreferences myPreferences =   getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,getActivity().MODE_PRIVATE);
        user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");
        db = FirebaseFirestore.getInstance();
        loader = new Loader(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);

        loadingPB = v.findViewById(R.id.idProgressBar);
        daily = v.findViewById(R.id.daily);
        dailybooking = v.findViewById(R.id.dailybooking);
        ll_bookinglist = v.findViewById(R.id.ll_bookinglist);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        Hit_get_book();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);
                Hit_get_book();

            }
        });
        dailyBookingHis();

    }
    public void Hit_get_book()
    {
        db.collection("BookingHistory").whereEqualTo("user_id",user_id)
               .orderBy("modifyDate", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (!queryDocumentSnapshots.isEmpty())
                        {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            ll_bookinglist.removeAllViews();
                            for (DocumentSnapshot d : list)
                            {
                                Log.d("sybte",d.getString("dailybooking"));
                                if (d.getString("dailybooking").equalsIgnoreCase(""))
                                {
                                    if (getActivity() != null) {
                                        Log.e("Nodate", "" + "hitShow");
                                        LayoutInflater inflater2 = null;
                                        inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View mLinearView = inflater2.inflate(R.layout.booking_abapter_item, null);
                                        final TextView otp, onlinepayment, goLocation, payment_status, Payment_mode, booked_on, Booking_Date, name, booking_assigned, payment_type, servisetype, Booking_time, total_amount, wash_Count;
                                        CircleImageView im5;
                                        RelativeLayout ri_main;
                                        LinearLayout linea_user_detail,carmodel;
                                        TextView modelNumber;
                                        otp = mLinearView.findViewById(R.id.otp);
                                        carmodel = mLinearView.findViewById(R.id.carmodel);
                                        carmodel.setVisibility(View.VISIBLE);
                                        goLocation = mLinearView.findViewById(R.id.go);
                                        modelNumber = mLinearView.findViewById(R.id.modelNumber);
                                        payment_type = mLinearView.findViewById(R.id.payment_type);
                                        ri_main = mLinearView.findViewById(R.id.ri_main);
                                        im5 = mLinearView.findViewById(R.id.im5);
                                        name = mLinearView.findViewById(R.id.name);
                                        booking_assigned = mLinearView.findViewById(R.id.booking_assigned);
                                        servisetype = mLinearView.findViewById(R.id.servisetype);
                                        Booking_time = mLinearView.findViewById(R.id.Booking_time);
                                        booked_on = mLinearView.findViewById(R.id.booked_on);
                                        Booking_Date = mLinearView.findViewById(R.id.Booking_Date);
                                        total_amount = mLinearView.findViewById(R.id.total_amount);
                                        wash_Count = mLinearView.findViewById(R.id.wash_Count);
                                        linea_user_detail = mLinearView.findViewById(R.id.linea_user_detail);
                                        Payment_mode = mLinearView.findViewById(R.id.Payment_mode);
                                        payment_status = mLinearView.findViewById(R.id.payment_status);
                                        onlinepayment = mLinearView.findViewById(R.id.onlinepayment);
                                        linea_user_detail.setVisibility(View.GONE);
                                        modelNumber.setText(d.getString("carModelNumber"));
                                        wash_Count.setVisibility(View.GONE);
                                        if (d.getString("pin")!=null)
                                        {
                                            otp.setVisibility(View.VISIBLE);
                                            otp.setText(" OTP "+d.getString("pin"));
                                        }

                                        wash_Count.setText("Wash Count : " + d.getString("wash_count"));
                                        servisetype.setText("" + d.getString("washname_tv"));

                                        booked_on.setText(" " + d.getString("service_date"));
                                        Booking_Date.setText(" " + d.getString("current_date_book"));

                                        name.setText("" + d.getString("car_name"));
                                        Booking_time.setText(" " + d.getString("service_time"));
                                        total_amount.setText("₹." + d.getString("price_total_final"));
                                        String status_assi = "" + d.getString("assigned_to");
                                        String tracking_key = d.getString("traking_key");
                                        String date = d.getString("startDate");
                                        Log.d("", "jhgdhf" + date);
                                        //Log.d("","usidd"+tracking_key);


                                        if (tracking_key == null)
                                        {
                                            goLocation.setVisibility(View.GONE);
                                        } else {
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef = database.getReference("Location");
                                            myRef.child(tracking_key).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot)
                                                {
                                                    Latlong l = dataSnapshot.getValue(Latlong.class);
                                                    int status = l.getStatus();
                                                    Log.d("", "status" + status);
                                                    if (status == 1)
                                                    {
                                                        goLocation.setVisibility(View.VISIBLE);
                                                        onlinepayment.setVisibility(View.VISIBLE);
                                                         goLocation.setOnClickListener(new View.OnClickListener()
                                                         {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent i = new Intent(getActivity(), MapuserActivity.class);
                                                                i.putExtra("usertrckingkey", tracking_key);
                                                                startActivity(i);
                                                            }
                                                        });
                                                    }
                                              /* if(d.getString("paymenttype").equalsIgnoreCase("Done"))
                                              {
                                                  onlinepayment.setVisibility(View.GONE);
                                                }*/
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }


                                        if (status_assi.trim().equalsIgnoreCase("0")) {

                                            booking_assigned.setVisibility(View.GONE);

                                        } else {
                                            booking_assigned.setText("Assigned To : " + d.getString("assigned_to"));

                                        }


                                        /* String status_assi=""+d.getString("assigned_to");*/


                                        if (d.getString("paymenttype").equalsIgnoreCase("offline"))
                                        {
                                            Payment_mode.setText("Payment on Service");
                                            payment_status.setText("pending");
                                            payment_status.setTextColor(getActivity().getResources().getColor(R.color.blue));


                                        }
                                        else if (d.getString("paymenttype").equalsIgnoreCase("Assigned"))
                                        {
                                            Payment_mode.setText("Payment on Service");
                                            payment_status.setText("pending");
                                            payment_status.setTextColor(getActivity().getResources().getColor(R.color.blue));

                                        }
                                        else
                                        {

                                            onlinepayment.setVisibility(View.GONE);
                                            Payment_mode.setText("Online");
                                            payment_status.setText("Paid");
                                            payment_status.setTextColor(getActivity().getResources().getColor(R.color.green));

                                        }

                                        String url = "" + d.getString("car_image");

                                        Log.e("urlurl", "AS : " + url);

                                        Glide.with(getActivity())
                                                .load(url.trim() + "")
                                                .placeholder(R.drawable.rnd_logo)
                                                .into(im5);


                                        onlinepayment.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                //this cod eis written form payment successful
                                                loadingPB.setVisibility(View.GONE);
                                                String flag = "1";
                                                Map<String, Object> paymentstatus = new HashMap<>();
                                                paymentstatus.put("flag", flag);
                                                paymentstatus.put("st_paymenttype", "Done");

                                                db.collection("Booking_Assigned").whereEqualTo("booking_id", d.getId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                        if (!queryDocumentSnapshots.isEmpty()) {
                                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                            for (DocumentSnapshot doc : list)
                                                            {
                                                                db.collection("Booking_Assigned").document(doc.getId()).update(paymentstatus);
                                                               // Toast.makeText(getActivity(), "Payment Update", Toast.LENGTH_SHORT).show();
                                                              UtilMethods.INSTANCE.Success(getActivity(),"Your Payment Update");

                                                            }
                                                        }
                                                    }
                                                });
                                                db.collection("BookingHistory").document(d.getId()).update("paymenttype", "Done");

                                                /////////////////////////////Online Payment //////////////////////////////
                                         /*       Document_id = d.getId();
                                                Log.e("service_datevfvfv", "service_date  :  " + d.getString("service_date") + "   ,  car_name  :  " + d.getString("car_name"));
                                                service_date = "" + d.getString("service_date");
                                                ed_name = "" + d.getString("car_name");
                                                ed_mobile = "" + d.getString("ed_mobile");
                                                ed_date = "" + d.getString("ed_date");
                                                service_time = "" + d.getString("service_time");
                                                ed_Address = "" + d.getString("ed_address");
                                                ed_Landmark = "" + d.getString("ed_landmark");
                                                ed_email = "" + d.getString("ed_email");
                                                Car_name = "" + d.getString("Car_name");
                                                washname_tv = "" + d.getString("washname_tv");
                                                price_total_final = "Rs. " + d.getString("price_total_final");
                                                wash_count = "" + d.getString("wash_count");
                                                month_count = "" + d.getString("month_count");
                                                end_date = "" + d.getString("end_date");
                                                car_image = "" + d.getString("car_image");
                                                String exdate = d.getString("exdate");
                                                String dailybooking = d.getString("dailybooking");

                                         *//*   addDataToFirestore(service_date.toString().trim(),"Done",""+"bundle.getString(key)",
                                                    ed_name.toString().trim()+""  ,
                                                    ed_mobile.toString().trim()+"" ,
                                                    ed_date.toString().trim()+"" ,
                                                    service_time.toString().trim()+"" ,
                                                    ed_Address.toString().trim()+"" ,
                                                    ed_Landmark.toString().trim()+"" ,
                                                    ed_email.toString().trim()+""  ,
                                                    Car_name.toString().trim()+"" ,
                                                    washname_tv.toString().trim()+"" ,
                                                    price_total_final.toString().trim().replace("Rs. ","") +"",
                                                    user_id ,wash_count , month_count);*//*


                                                String ORDER_ID = "";
                                                final int min = 200;
                                                final int max = 8000;
                                                final int rendom = new Random().nextInt((max - min) + 1) + min;
                                                ORDER_ID = "" + rendom;
                                                SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
                                                String user_id = "" + myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");

                                                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity()))
                                                {   loader.show();
                                                    loader.setCancelable(false);
                                                    loader.setCanceledOnTouchOutside(false);
                                                    Date modifyDate = (Date) Calendar.getInstance().getTime();
                                                    String Booking_status = "pending";
                                                    addDataToFirestore(service_date, "Done", "TSESF!@#",
                                                            ed_name.toString().trim() + "",
                                                            ed_mobile.toString().trim() + "",
                                                            ed_date.toString().trim() + "",
                                                            service_time.toString().trim() + "",
                                                            ed_Address.toString().trim() + "",
                                                            ed_Landmark.toString().trim() + "",
                                                            ed_email.toString().trim() + "",
                                                            Car_name.toString().trim() + "",
                                                            washname_tv.toString().trim() + "",
                                                            price_total_final.toString().trim().replace("Rs. ", "") + "",
                                                            user_id, wash_count, month_count, modifyDate, Booking_status, dailybooking, exdate);
                                                }
                                                else
                                                {UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.network_error_message));
                                                 }*/
//                                            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity()))
//                                            {
//
//                                                Log.e("finalprice", "As : " + d.getString("price_total_final").trim().replace("Rs. ", ""));
//
//                                                UtilMethods.INSTANCE.getToken(getActivity(), "" + ORDER_ID, "" + d.getString("price_total_final").trim().replace("Rs. ", ""), ORDER_ID);
//
//
//                                            } else {
//                                                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.network_error_message));
//                                            }


                                                ///////////////////////////payment Dine ///////////////////////////////////


                                            }

                                        });

                                        ri_main.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent i = new Intent(getActivity(), Orders_Details_Activity.class);
                                                i.putExtra("carimage", "" + d.getString("car_image"));
                                                i.putExtra("carname", "" + d.getString("car_name"));
                                                i.putExtra("carprice", "" + d.getString("price_total_final"));
                                                i.putExtra("date", "" + d.getString("service_date"));
                                                i.putExtra("time", "" + d.getString("service_time"));
                                                i.putExtra("serviestype", "" + d.getString("washname_tv"));
                                                i.putExtra("user_id", "" + d.getString("user_id"));
                                                i.putExtra("mobile", "" + d.getString("ed_mobile"));
                                                i.putExtra("name", "" + d.getString("ed_name"));
                                                i.putExtra("Landmark", "" + d.getString("ed_landmark"));
                                                i.putExtra("Address", "" + d.getString("ed_address"));
                                                i.putExtra("email", "" + d.getString("ed_email"));
                                                i.putExtra("car_name_model", "" + d.getString("car_name"));
                                                i.putExtra("price_final", "" + d.getString("price_total_final"));
                                                i.putExtra("service", "" + d.getString("price_total_final"));
                                                i.putExtra("type_select", "" + d.getString("type_select"));
                                                startActivity(i);

                                            }
                                        });

                                        ll_bookinglist.addView(mLinearView);


                                    }
                                }
                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d("","exception"+e);
                        Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();

                    }
                });


    }

   /* public void update(String carId,String packageId) {
        db.collection("UserPackageDetails").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
*//*

                    if ()
                        Map<String, Object> redanddate = new HashMap<>();
                        redanddate.put("redeem", String.valueOf(value));
                        redanddate.put("modifyDate", ed_date.getText().toString().trim());
                        db.collection("UserPackageDetails").document(d.getId()).update(redanddate);
*//*


                            }
                        }
                    }
                });
    }*/

    public void dailyBookingHis()
      {

          db.collection("BookingHistory").whereEqualTo("dailybooking","dailyCleaning")
                  .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                      @Override
                      public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                          List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                          ll_bookinglist.removeAllViews();
                          for (DocumentSnapshot d : list)
                          {
                              Log.d("dailybooking      ",d.getString("dailybooking"));
                              if (d.getString("dailybooking").equalsIgnoreCase("dailyCleaning"))
                              {
                                  daily.setVisibility(View.VISIBLE);
                                  dailybooking.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v) {
                                          startActivity(new Intent(getActivity(), DailyBooking.class));
                                      }
                                  });


                              }

                          }
                      }
                  });
      }

    @Override
    public void onStart()
    {
        super.onStart();

        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage)
    {

        if (activityFragmentMessage.getFrom().equalsIgnoreCase("Cftoken")) {
            String Token_Api=""+activityFragmentMessage.getMessage();

            String[] recent;
            recent = Token_Api.split("@@#");

            initiatepayment( recent[0] , recent[1] );

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //Same request code for all payment APIs.
//        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Calendar calendar = Calendar.getInstance();
        Date modifyDate = (Date) calendar.getTime();
        String Booking_status="pending";

        Log.d("respkeykeyhitttt", "API Response : " +data +"  :   resultCode  "+resultCode );
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {

                    if (bundle.getString(key) != null) {
                        Log.e("respkeykey", key + " : " + bundle.getString(key));

                        if(key.equalsIgnoreCase("SUCCESS")){
                            Toast.makeText(getActivity(), "Payment SUCCESS", Toast.LENGTH_SHORT).show();
                        }else if(key.equalsIgnoreCase("referenceId")) {

                            SharedPreferences myPreferences =   getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,getActivity().MODE_PRIVATE);
                            String user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");

                            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(false);



                                addDataToFirestore(service_date,"Done","TSESF!@#",
                                        ed_name.toString().trim()+""  ,
                                        ed_mobile.toString().trim()+"" ,
                                        ed_date.toString().trim()+"" ,
                                        service_time.toString().trim()+"" ,
                                        ed_Address.toString().trim()+"" ,
                                        ed_Landmark.toString().trim()+"" ,
                                        ed_email.toString().trim()+""  ,
                                        Car_name.toString().trim()+"" ,
                                        washname_tv.toString().trim()+"" ,
                                        price_total_final.toString().trim().replace("Rs. ","") +"",
                                        user_id ,wash_count , month_count,modifyDate,Booking_status,"","","","");

                            } else {

                                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.network_error_message));

                            }
                        }
                    }
                }
        }
    }

    private void initiatepayment(String Token_Api,String order_id)
    {

        Log.e("getTokenActicity","As : "+Token_Api  +"   ,   ORDER_ID   :  "+ order_id);

        SharedPreferences myPreferences =   getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,  getActivity().MODE_PRIVATE);

        Map<String, String> params = new HashMap<>();
        params.put(PARAM_APP_ID, "137508c30d06c9b684b7c697b0805731");
        params.put(PARAM_ORDER_ID, ""+order_id);
        params.put(PARAM_ORDER_AMOUNT, ""+price_total_final.trim().replace("Rs. ","")); //+Price.trim().replace("Rs. ","")
        params.put(PARAM_ORDER_NOTE, "Order for Service");
        params.put(PARAM_CUSTOMER_NAME,""+myPreferences.getString(ApplicationConstant.INSTANCE.username, ""));
        params.put(PARAM_CUSTOMER_PHONE, ""+myPreferences.getString(ApplicationConstant.INSTANCE.phone, ""));
        params.put(PARAM_CUSTOMER_EMAIL, ""+myPreferences.getString(ApplicationConstant.INSTANCE.email, ""));
        params.put(PARAM_ORDER_CURRENCY, "INR");
        for(Map.Entry entry : params.entrySet()) {

            Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());

        }

        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);
        cfPaymentService.doPayment(getActivity(), params, Token_Api, "test", "#F8A31A", "#FFFFFF", false);



    }

    private void addDataToFirestore(String service_date, String paymenttype, String referenceId , String ed_name  ,
                                    String  ed_mobile , String ed_date , String service_time , String ed_Address ,
                                    String ed_Landmark , String ed_email  , String Car_name , String washname_tv ,
                                    String price_total_final, String user_id, String wash_count , String month_count, Date modifyDate,String Booking_status,String dailybooking,String exdate,String carModelNumber,String packageId) {




        SharedPreferences myPreferences =   getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,  getActivity().MODE_PRIVATE);


        String u_name=""+ myPreferences.getString(ApplicationConstant.INSTANCE.username, "") ;
        String u_email=""+ myPreferences.getString(ApplicationConstant.INSTANCE.email, "") ;
        String u_Address=""+ myPreferences.getString(ApplicationConstant.INSTANCE.Address, "") ;
        String u_Landmark=""+ myPreferences.getString(ApplicationConstant.INSTANCE.Landmark, "") ;
        String u_latitude=""+ myPreferences.getString(ApplicationConstant.INSTANCE.latitude, "") ;
        String u_longitude=""+ myPreferences.getString(ApplicationConstant.INSTANCE.longitude, "") ;
        String u_phone=""+ myPreferences.getString(ApplicationConstant.INSTANCE.phone, "") ;


        Log.e("service_datevfvfvnew","service_date  : "+ service_date  +" ed_name  "+  ed_name  );

        //  ed_mobile , ed_name , ed_Landmark, ed_Address , ed_email

        // adding our data to our courses object class.
        BookingHis courses = new BookingHis(service_date,paymenttype,referenceId, ed_name  ,  ed_mobile , service_date , service_time , ed_Address , ed_Landmark , ed_email  , ed_name ,
                washname_tv ,"Rs. "+ price_total_final, user_id,  wash_count ,  month_count ,car_image ,end_date ,"0" ,
                ""+ u_name, ""+ u_email, ""+ u_Address, ""+ u_Landmark, ""+ u_latitude, ""+ u_longitude ,""+ u_phone ,"0",modifyDate,Booking_status,dailybooking,exdate,carModelNumber,packageId);



        db.collection("BookingHistory").document(Document_id).set(courses)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {


                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        FragmentActivityMessage activityActivityMessage =
                                new FragmentActivityMessage("Done" , "BookingType");
                        GlobalBus.getBus().post(activityActivityMessage);

                        SharedPreferences prefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(ApplicationConstant.INSTANCE.page_select, "2");
                        editor.commit();

                        Intent home = new Intent(getActivity(), DashboadActivity.class);
                        startActivity(home);

                    }
                });







       /* // below method is use to add data to Firebase Firestore.
        db.collection("BookingHistory").document(Document_id).set(courses)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }











                FragmentActivityMessage activityActivityMessage =
                        new FragmentActivityMessage("Done" , "BookingType");
                GlobalBus.getBus().post(activityActivityMessage);

                SharedPreferences prefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ApplicationConstant.INSTANCE.page_select, "2");
                editor.commit();

                Intent home = new Intent(getActivity(), DashboadActivity.class);
                startActivity(home);
                //  finish();


*//*
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.sussesfull_place, null);

                Button okButton = (Button) view.findViewById(R.id.okButton);
                TextView msg = (TextView) view.findViewById(R.id.msg);
                TextView washtype = (TextView) view.findViewById(R.id.washtype);

                final Dialog dialog = new Dialog(getActivity());

                dialog.setCancelable(false);
                dialog.setContentView(view);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                msg.setText(""+Car_name);
                washtype.setText(""+washname_tv);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        //  sendEmail();
                        // Toast.makeText(BookingActivityNew.this, "User Booking", Toast.LENGTH_SHORT).show();


                        FragmentActivityMessage activityActivityMessage =
                                new FragmentActivityMessage("Done" , "BookingType");
                        GlobalBus.getBus().post(activityActivityMessage);

                        SharedPreferences prefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(ApplicationConstant.INSTANCE.page_select, "2");
                        editor.commit();

                        Intent home = new Intent(getActivity(), DashboadActivity.class);
                        startActivity(home);
                      //  finish();

                    }
                });

                dialog.show();

              *//*

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.

                Log.e("onFailure","onFailure : "+ e);

                Toast.makeText(getActivity(), "Fail to Booking \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

*/
    }


}