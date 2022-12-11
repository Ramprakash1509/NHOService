package com.nhoserviceboy.carwash.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cashfree.pg.CFPaymentService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Admin.Model.BookingModels;
import com.nhoserviceboy.carwash.Admin.Model.PaymentModel;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.ApplicationConstant;
import com.nhoserviceboy.carwash.Utils.FragmentActivityMessage;
import com.nhoserviceboy.carwash.Utils.GlobalBus;
import com.nhoserviceboy.carwash.Utils.Loader;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static java.time.LocalDate.parse;

public class BookingActivityNew extends AppCompatActivity  implements View.OnClickListener
{     String st = "",st1= "";
     String Booking_status="pending";
    TextView texttoolbar , tv_Submit , tv_washname_top,tv_avilabilit;
    ImageView backprese;
    EditText  ed_date  ,  service_time    ;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    TimePickerDialog timepickerdialog;
    String format  ;
    Loader loader;
    String count_page ="";
    RadioButton pos , onlinepayment;
    String payment_type="pos";
    TextView service;
    TextView price_final, price_total_final ,washname_tv, Car_name;
    private FirebaseFirestore db;
    EditText ed_mobile , ed_name , ed_Landmark, ed_Address , ed_email  ;
    RadioButton radioButtonPicker , radioButtonDropper;
    int status=0;
    int redeem=1;
    String type_select="";
/*    String washname="";
    String Price="";
    String carnameselect="";
    String wash_count="";*/
   /* String carId="";

    String packageId="";

    String valid="";*/
    //String month_count="";
    //String car_image="";
     String carId="";
     String packageId="";
     String valid="";
    String wash_count="";
    String month_count="";
    String Price="";
    String user_id="";
    String end_date="";
    String ORDER_ID="";
    String service_date="";
    String packageName="";
    int w_count;
    RadioGroup pay;
    TextView mode;
    LinearLayout details;
    String carModel,modelId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_new);
        GetId();

    }

    private void GetId()
    {

        pay=findViewById(R.id.pay);
        details=findViewById(R.id.details);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM.dd 'at' HH:mm:ss  ");
        String currentDateandTime = sdf.format(new Date());
        mode=findViewById(R.id.mode);
        String[] end_date;
        end_date = currentDateandTime.split("at");

        service_date=UtilMethods.INSTANCE.dateForamterer(new Date());

        Log.e("service_date_time","As : "+ service_date);

        onlinepayment=findViewById(R.id.onlinepayment);
        pos=findViewById(R.id.pos);

        onlinepayment.setOnClickListener(this);
        pos.setOnClickListener(this);


      /*  onlinepayment=findViewById(R.id.onlinepayment);
        pos=findViewById(R.id.pos);*/
        db = FirebaseFirestore.getInstance();
        price_total_final=findViewById(R.id.price_total_final);
        Car_name=findViewById(R.id.Car_name);
        washname_tv=findViewById(R.id.washname_tv);
        service=findViewById(R.id.service);
        price_final=findViewById(R.id.price_final);
        tv_avilabilit=findViewById(R.id.tv_avilabilit);
        radioButtonDropper=findViewById(R.id.radioButtonDropper);
        radioButtonPicker=findViewById(R.id.radioButtonPicker);
        type_select="Picker";
        radioButtonPicker.setOnClickListener(this);
        radioButtonDropper.setOnClickListener(this);
        ed_mobile=findViewById(R.id.ed_mobile);
        ed_name=findViewById(R.id.ed_name);
        ed_Landmark=findViewById(R.id.ed_Landmark);
        ed_Address=findViewById(R.id.ed_Address);
        ed_email=findViewById(R.id.ed_email);
        tv_washname_top=findViewById(R.id.tv_washname_top);
        SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        ed_name.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.username, "") );
        ed_mobile.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.phone,"") );
        ed_email.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.email, "") );
        ed_Address.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.Address, "") );
        ed_Landmark.setText(""+ myPreferences.getString(ApplicationConstant.INSTANCE.Landmark, "") );

      /*  washname=getIntent().getStringExtra("washname");
        Price=getIntent().getStringExtra("Price");
        //carnameselect=getIntent().getStringExtra("carnameselect");
        wash_count=getIntent().getStringExtra("wash_count");
        month_count=getIntent().getStringExtra("month_count");
        //car_image=getIntent().getStringExtra("car_image");
       ;*/


        carModel = getIntent().getStringExtra("carModel");
        Log.d("jksfdsjf47",carModel);
        carId=getIntent().getStringExtra("carId");
        Price=getIntent().getStringExtra("price");
       packageId=getIntent().getStringExtra("packageId");
        wash_count=getIntent().getStringExtra("washCount");
        month_count=getIntent().getStringExtra("validity");
        valid = getIntent().getStringExtra("validity");
        user_id = getIntent().getStringExtra("user_id");
        packageName = getIntent().getStringExtra("packageName");
        modelId = getIntent().getStringExtra("modelId");

        Log.d("dsfbksdvdfjgmdfn435",""+packageName+"    "+user_id);


        price_total_final.setText(""+ Price);
        Car_name.setText(""+ getIntent().getStringExtra("carname"));
        washname_tv.setText(""+getIntent().getStringExtra("package") );

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        tv_Submit=findViewById(R.id.tv_Submit);

        ed_date=findViewById(R.id.ed_date);
        service_time=findViewById(R.id.service_time);

        tv_Submit.setOnClickListener(this);
        service_time.setOnClickListener(this);
        ed_date.setOnClickListener(this);

        texttoolbar=findViewById(R.id.texttoolbar);
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(this);
        texttoolbar.setText(carModel);
    }



    private void hit_CheckValue()
    {


        SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");

        db = FirebaseFirestore.getInstance();

        db.collection("BookingHistory").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list)
                            {
                                Confirmation_popup(d);

                        }}
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookingActivityNew.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void Confirmation_popup(DocumentSnapshot d)
    {


                SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String u_name=""+ myPreferences.getString(ApplicationConstant.INSTANCE.username, "") ;
                String u_email=""+ myPreferences.getString(ApplicationConstant.INSTANCE.email, "") ;
                String u_Address=""+ myPreferences.getString(ApplicationConstant.INSTANCE.Address, "") ;
                String u_Landmark=""+ myPreferences.getString(ApplicationConstant.INSTANCE.Landmark, "") ;
                String u_latitude=""+ myPreferences.getString(ApplicationConstant.INSTANCE.latitude, "") ;
                String u_longitude=""+ myPreferences.getString(ApplicationConstant.INSTANCE.longitude, "") ;
                String u_phone=""+ myPreferences.getString(ApplicationConstant.INSTANCE.phone, "") ;
                BookingHis p=new BookingHis(UtilMethods.INSTANCE.dateForamterer(new Date())+"",
                        ""+d.getString("paymenttype"),""+d.getString("referenceId"),
                        ""+d.getString("ed_name"),""+d.getString("ed_mobile"),""+d.getString("service_date"),
                        ""+UtilMethods.INSTANCE.TimeForamterer(new Date()),""+d.getString("ed_address"),
                        ""+d.getString("ed_landmark"),""+d.getString("ed_email"),""+d.getString("car_name"),
                        ""+d.getString("washname_tv"),""+d.getString("price_total_final"),
                        ""+d.getString("user_id"),
                        String.valueOf(wash_count)+"",
                        ""+d.getString("month_count"),
                        ""+d.getString("car_image"),""+d.getString("end_date") , "0"  ,
                        ""+ u_name, ""+ u_email, ""+ u_Address,
                        ""+ u_Landmark, ""+ u_latitude,
                        ""+ u_longitude ,""+u_phone,"0",null,Booking_status,"","","","");


                db.collection("BookingHistory").document(d.getId()).set(p)
                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {

                                FragmentActivityMessage activityActivityMessage =
                                                new FragmentActivityMessage("Done" , "BookingType");
                                GlobalBus.getBus().post(activityActivityMessage);



                                    }
                                });






    }



    public void Datepicker()
    {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datepicker_pop, null);

        Button tvLater =  view.findViewById(R.id.tv_later);
        Button tv_ok =  view.findViewById(R.id.tv_ok);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);

        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        datePicker.setMinDate(now);

        Date currentTime = Calendar.getInstance().getTime();

        String timewah=currentTime.toString().replace(" ",",");

        String[] recent;
        recent = timewah.split(",");

        Log.e("currentTime","currentTime :   "+ recent[3] );

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                dialog.dismiss();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                String dateString = UtilMethods.INSTANCE.dateForamterer(calendar.getTime());
               // ed_date.setText( datePicker.getYear() + "-" +  (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth() );

                ed_date.setText(dateString);
                int up_mounth_it;
                int month_count_int= Integer.parseInt(month_count);

                up_mounth_it=(datePicker.getMonth()+1);
                up_mounth_it = up_mounth_it + month_count_int;

                if( up_mounth_it >= 12 ){

                    // more the 12

                    int mo_up_mounth_it= up_mounth_it - 12;

                    Log.e("end_date_vasa","AAA : "+ up_mounth_it +"  : month_count_int :  "+ month_count_int +"  : mo_up_mounth_it : "+ mo_up_mounth_it);

                    final Calendar calendarss = Calendar.getInstance();
                    calendarss.set(2023,mo_up_mounth_it,datePicker.getDayOfMonth());
                    String dateStrings = UtilMethods.INSTANCE.dateForamterer(calendarss.getTime());
                    end_date=dateStrings;


                }else
                {
                    final Calendar calendarss1= Calendar.getInstance();
                    calendarss1.set(datePicker.getYear(),up_mounth_it,datePicker.getDayOfMonth());
                    String dateStrings1 = UtilMethods.INSTANCE.dateForamterer(calendarss1.getTime());
                    end_date=dateStrings1;


                    Log.e("end_date_vasa","BBB : "+ up_mounth_it +"  : month_count_int :  "+ month_count_int);

                }

            }
        });



        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });



        dialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {

        if(view==radioButtonPicker)
        {

            Toast.makeText(this, "Picker", Toast.LENGTH_SHORT).show();

            type_select="Picker";

        }

        if(view==radioButtonDropper)
        {

            type_select="Dropper";

       }

        if(view==onlinepayment)
        {

            payment_type="online";

       }

        if(view==pos)
        {
            payment_type="pos";

   }

        if(view==tv_Submit)
        {

            getToken();
            SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,MODE_PRIVATE);
            String st_phone_number=""+ myPreferences.getString(ApplicationConstant.INSTANCE.phone, "");

            Calendar calendar = Calendar.getInstance();
            Date modifyDate = null;

            if(ed_date.getText().toString().trim().isEmpty())
            {

        Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();

    }else  if(service_time.getText().toString().trim().isEmpty()){

        Toast.makeText(this, "Select Time", Toast.LENGTH_SHORT).show();

    }else  if(ed_mobile.getText().toString().trim().isEmpty()){

        Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();

    }else  if(ed_name.getText().toString().trim().isEmpty()){

        Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

    }else  if(ed_Landmark.getText().toString().trim().isEmpty()){

        Toast.makeText(this, "Enter Landmark ", Toast.LENGTH_SHORT).show();

    }else  if(ed_Address.getText().toString().trim().isEmpty()){

        Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();

    }else  if(ed_email.getText().toString().trim().isEmpty()){

        Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();

      }

    else
    {
        if(payment_type.equalsIgnoreCase("pos"))
        {

           // String st = "";
            String user_id=""+ myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");
            if (UtilMethods.INSTANCE.isNetworkAvialable(BookingActivityNew.this))
            {
              try {

                        String date=ed_date.getText().toString().trim();
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date ddd = format.parse(date);
                        Log.d("","ewrtrdt   "+ddd);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(ddd);
                        cal.add(Calendar.MONTH, Integer.parseInt(valid));
                        st=format.format( cal.getTime() );
                        System.out.println(format.format( cal.getTime() ));
                        Log.d("","ewrtt"+ddd);
                        if(status==0)
                        {
                        AddDataIncomeDetails(user_id, packageId, "1", ed_date.getText().toString().trim(), ed_date.getText().toString().trim(), st, wash_count, "PENDING", Price,modelId,"offline");
                        }
                         else
                        {
                        try {
                        db.collection("UserPackageDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                            {
                                if (queryDocumentSnapshots != null) {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list)
                                    {
                                        redeem = 1;
                                        int value = Integer.parseInt(d.getString("redeem")) + redeem;
                                        Map<String, Object> redanddate = new HashMap<>();
                                        redanddate.put("redeem", String.valueOf(value));
                                        redanddate.put("modifyDate", ed_date.getText().toString().trim());
                                        db.collection("UserPackageDetails").document(d.getId()).update(redanddate);

                                        if ( d.getString("washCount").equalsIgnoreCase(d.getString("redeem")))
                                        {
                                            db.collection("UserPackageDetails").document(d.getId()).update("status","EXPIRED");
                                        }
                                    }
                                }
                            }
                        });
                         }catch (Exception e)
                         {
                          Log.d("",""+e);
                         }
                    }
                }
                  catch (Exception e)
                {
                    Log.d("","sdkjfhfg"+e);
                }

                }
                if (UtilMethods.INSTANCE.isNetworkAvialable(BookingActivityNew.this))
                {
                if (status==0)
                {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    addDataToFirestore(service_date, "offline", "",
                            ed_name.getText().toString().trim() + "",
                            ed_mobile.getText().toString().trim() + "",
                            ed_date.getText().toString().trim() + "",
                            service_time.getText().toString().trim() + "",
                            ed_Address.getText().toString().trim() + "",
                            ed_Landmark.getText().toString().trim() + "",
                            ed_email.getText().toString().trim() + "",
                            Car_name.getText().toString().trim() + "",
                            washname_tv.getText().toString().trim() + ""
                            , price_total_final.getText().toString().trim() + "",
                            user_id, wash_count, month_count, modifyDate, Booking_status, st);
                }
                else
                {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    addDataToFirestore(service_date, "Done", "",
                            ed_name.getText().toString().trim() + "",
                            ed_mobile.getText().toString().trim() + "",
                            ed_date.getText().toString().trim() + "",
                            service_time.getText().toString().trim() + "",
                            ed_Address.getText().toString().trim() + "",
                            ed_Landmark.getText().toString().trim() + "",
                            ed_email.getText().toString().trim() + "",
                            Car_name.getText().toString().trim() + "",
                            washname_tv.getText().toString().trim() + ""
                            , price_total_final.getText().toString().trim() + "",
                            user_id, wash_count, month_count, modifyDate, Booking_status, st);

                }

            } else
            {
                UtilMethods.INSTANCE.Error(BookingActivityNew.this, getResources().getString(R.string.network_error_message));
            }
        }
        else
        {
            try {
                String date=ed_date.getText().toString().trim();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date ddd = format.parse(date);
                Log.d("","ewrtrdt   "+ddd);
                Calendar cal = Calendar.getInstance();
                cal.setTime(ddd);
                cal.add(Calendar.MONTH, Integer.parseInt(valid));
                st1=format.format( cal.getTime() );
                System.out.println(format.format( cal.getTime() ));


            if (UtilMethods.INSTANCE.isNetworkAvialable(BookingActivityNew.this))
            {

                Log.d("eewrtt",st1);

                 /*   if(status==0)
                    {
                        AddDataIncomeDetails(user_id, packageId, "1", ed_date.getText().toString().trim(), ed_date.getText().toString().trim(), st1, wash_count, "PENDING", Price);
                    }
                    else
                    {
                        try {
                            db.collection("UserPackageDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                                {
                                    if (queryDocumentSnapshots != null) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list)
                                        {
                                            redeem = 1;
                                            int value = Integer.parseInt(d.getString("redeem")) + redeem;
                                            Map<String, Object> redanddate = new HashMap<>();
                                            redanddate.put("redeem", String.valueOf(value));
                                            redanddate.put("modifyDate", ed_date.getText().toString().trim());
                                            db.collection("UserPackageDetails").document(d.getId()).update(redanddate);

                                            if ( d.getString("washCount").equalsIgnoreCase(d.getString("redeem")))
                                            {
                                                db.collection("UserPackageDetails").document(d.getId()).update("status","EXPIRED");
                                            }
                                        }
                                    }
                                }
                            });
                        }catch (Exception e)
                        {
                            Log.d("",""+e);
                        }
                    }
*/



                final int min = 10000;
                final int max = 15000;
                final int rendom = new Random().nextInt((max - min) + 1) + min;
                ORDER_ID = "" + rendom;

                String user_id = "" + myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");
                if (UtilMethods.INSTANCE.isNetworkAvialable(BookingActivityNew.this))
                {
                    try {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("eewrtttt",st1);
                    UtilMethods.INSTANCE.getToken(this,""+ORDER_ID, ""+Price.trim().replace("Rs. ","") ,ORDER_ID);

                   /* addDataToFirestore(service_date, "Done", "TSESF!@#", ed_name.getText().toString().trim() + "", ed_mobile.getText().toString().trim() + "", ed_date.getText().toString().trim() + "", service_time.getText().toString().trim() + "", ed_Address.getText().toString().trim() + "", ed_Landmark.getText().toString().trim() + "", ed_email.getText().toString().trim() + "", Car_name.getText().toString().trim() + "", washname_tv.getText().toString().trim() + "", price_total_final.getText().toString().trim() +
                             "", user_id, wash_count, month_count, modifyDate, Booking_status, st1);
             */
                }
                else
                {
                    UtilMethods.INSTANCE.Error(BookingActivityNew.this, getResources().getString(R.string.network_error_message));

                }
            }
            }
            catch (Exception e)
            {
                Log.d("","sdkjfhfg"+e);
            }
// Payment Gateway
//            if (UtilMethods.INSTANCE.isNetworkAvialable(BookingActivityNew.this))
//            {   loader.show();
//                loader.setCancelable(false);
//                loader.setCanceledOnTouchOutside(false);
//                loader.dismiss();
//                UtilMethods.INSTANCE.getToken(this,""+ORDER_ID, ""+Price.trim().replace("Rs. ","") ,ORDER_ID);
//            }
//            else
//            {
//                UtilMethods.INSTANCE.Error(BookingActivityNew.this, getResources().getString(R.string.network_error_message));
//            }
                // initiatepayment();

        }
    
        }




        }

        if(view==service_time)
        {
            if(!ed_date.getText().toString().isEmpty())
            {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                            String currentDateandTime = sdf.format(Calendar.getInstance().getTime());
                            SimpleDateFormat sdfdd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            String cudhbjhdv = sdfdd.format(Calendar.getInstance().getTime());


                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;
                                    format = " AM";
                                } else if (hourOfDay == 12) {

                                    format = " PM";

                                } else if (hourOfDay > 12) {

                                    hourOfDay -= 12;
                                    format = " PM";

                                } else {

                                    format = " AM";
                                }
                                if (cudhbjhdv.equalsIgnoreCase(ed_date.getText().toString())) {
                                    if (compareTime(currentDateandTime, hourOfDay + ":" + minute + format)) {
                                        service_time.setText(hourOfDay + ":" + minute + format);
                                    } else {
                                            //Toast.makeText(BookingActivityNew.this, "currentDate"+cudhbjhdv, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(BookingActivityNew.this, "Please Select Future time", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    service_time.setText(hourOfDay + ":" + minute + format);
                                }
                            }

                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();


            }
            else {
                Toast.makeText(BookingActivityNew.this, "First ,Please Select Date", Toast.LENGTH_SHORT).show();

                ed_date.setError("Please Select Date");
                ed_date.requestFocus();
            }


        }

        if(view==ed_date){
            Datepicker();
        }

        if(view==backprese){
            onBackPressed();
        }
    }

    private  void getToken()
    {

        db.collection("DeviceToken").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {

                if (!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list)
                    {
                        String tokenId=d.getString("userDevicetoken");
                        String userId=d.getString("userId");



                    }

                }}
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("","kefdksjdfjdfds"+e);
            }
        });
    }



    @Override
    public void onStart()
    {super.onStart();

        if (!EventBus.getDefault().isRegistered(this))
        {
            GlobalBus.getBus().register(this);
        }
        try {
            Date date=new Date();
            SimpleDateFormat sdfDATE=new SimpleDateFormat("dd-MM-yyyy");
            String currentDate=sdfDATE.format(date);
             db.collection("UserPackageDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                if (queryDocumentSnapshots != null)
                {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list)
                    {
                        if (d.getString("userId").equalsIgnoreCase(user_id)&& d.getString("packageId").equalsIgnoreCase(packageId)&& d.getString("modelId").equalsIgnoreCase(modelId))
                         {
                            if (packageName.equalsIgnoreCase("Daily Cleaning"))
                            {   tv_avilabilit.setVisibility(View.VISIBLE);
                                status = 1;
                                mode.setVisibility(View.GONE);
                                pay.setVisibility(View.GONE);
                                tv_washname_top.setVisibility(View.VISIBLE);
                                int value=Integer.parseInt(d.getString("redeem"));
                                int redeem=value;
                                int remainingWashcount= Integer.parseInt(d.getString("washCount"))-redeem;
                                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
                             //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                                Date time1= null;
                                Date time2= null;
                                try {
                                    time1 = newFormat.parse(d.getString("bookingDate"));
                                    time2=newFormat.parse(d.getString("expireDate"));
                                    Date startDateValue = new Date(String.valueOf(time1));
                                    Date endDateValue = new Date(String.valueOf(time2));
                                    long diff = endDateValue.getTime() - startDateValue.getTime();
                                    long seconds = diff / 1000;
                                    long minutes = seconds / 60;
                                    long hours = minutes / 60;
                                    long days = (hours / 24) ;
                                    Log.d("days", "" + days);
                                    tv_avilabilit.setText("this package is already booked " +
                                            "and  validity for  "+days +"  days");
                                 // hit_CheckValue();
                                    db.collection("UserPackageDetails").document(d.getId()).update("status","ACTIVE");
                                    if (days>0)
                                   {
                                    db.collection("UserPackageDetails").document(d.getId()).update("status", "EXPIRED");
                                    tv_washname_top.setTextColor(getResources().getColor(R.color.red));
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View viewpop = inflater.inflate(R.layout.delete_services_boy_pop, null);
                                    Button okButton = (Button) viewpop.findViewById(R.id.okButton);
                                    Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
                                    TextView msg = (TextView) viewpop.findViewById(R.id.msg);
                                    msg.setText("This car wash package  is already booked :");
                                    final Dialog dialog = new Dialog(BookingActivityNew.this);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(viewpop);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                       Cancel.setVisibility(View.GONE);
                                       okButton.setText("Ok");
                                    okButton.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            dialog.dismiss();
                                            status = 0;
                                            finish();
                                            tv_washname_top.setVisibility(View.VISIBLE);
                                            pay.setVisibility(View.VISIBLE);
                                            mode.setVisibility(View.VISIBLE);
                                            details.setVisibility(View.GONE);

                                        }
                                    });

                                    Cancel.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            finish();

                                        }
                                    });

                                    dialog.show();

                                }
                                }
                                catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            else
                            {
                            status = 1;
                            mode.setVisibility(View.GONE);
                            pay.setVisibility(View.GONE);
                            tv_washname_top.setVisibility(View.VISIBLE);
                            int value=Integer.parseInt(d.getString("redeem"));
                            int redeem=value;
                            int remainingWashcount= Integer.parseInt(d.getString("washCount"))-redeem;
                            tv_washname_top.setText("Your total car wash booking : "+d.getString("washCount")+"  and Remaining car wash booking : "+remainingWashcount);
                          //  hit_CheckValue();
                             db.collection("UserPackageDetails").document(d.getId()).update("status","ACTIVE");
                                Date date1=new Date();
                                SimpleDateFormat sdfDATE=new SimpleDateFormat("dd-MM-yyyy");
                                String currentDate=sdfDATE.format(date1);
                                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");

                                //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

                                Date time1= null;
                                Date time2= null;
                                try {
                                   time1 = newFormat.parse(currentDate);
                                  //  time1 = newFormat.parse("11-10-2022");
                                   time2 = newFormat.parse(d.getString("expireDate"));
                                   // time2 = newFormat.parse("11-09-2022");
                                    Date startDateValue = new Date(String.valueOf(time1));
                                    Date endDateValue = new Date(String.valueOf(time2));
                                    long diff = endDateValue.getTime() - startDateValue.getTime();
                                    long seconds = diff / 1000;
                                    long minutes = seconds / 60;
                                    long hours = minutes / 60;
                                    long days = (hours / 24);
                                    Log.d("daysee",""+days);
                                    if (days < 1 || d.getString("washCount").equalsIgnoreCase(d.getString("redeem")))
                                    {
                                   db.collection("UserPackageDetails").document(d.getId()).update("status", "EXPIRED");
                                   tv_washname_top.setTextColor(getResources().getColor(R.color.red));
                                   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                   View viewpop = inflater.inflate(R.layout.delete_services_boy_pop, null);
                                   Button okButton = (Button) viewpop.findViewById(R.id.okButton);
                                   Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
                                   TextView msg = (TextView) viewpop.findViewById(R.id.msg);
                                   msg.setText("Your package validity are expired , so  continue to new booking is confirms");
                                   final Dialog dialog = new Dialog(BookingActivityNew.this);
                                   dialog.setCancelable(false);
                                   dialog.setContentView(viewpop);
                                   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                   okButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog.dismiss();
                                           status = 0;
                                           tv_washname_top.setVisibility(View.VISIBLE);
                                           pay.setVisibility(View.VISIBLE);
                                           mode.setVisibility(View.VISIBLE);
                                           tv_washname_top.setVisibility(View.GONE);
                                       }
                                   });

                                   Cancel.setOnClickListener(new View.OnClickListener()
                                   {
                                       @Override
                                       public void onClick(View v) {
                                           dialog.dismiss();
                                           finish();

                                       }
                                   });

                                   dialog.show();

                               }

                           }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }




                    }}}
        });







        }catch (Exception e)
    {
        Log.d("",""+e);
    }


    }


    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage)
    {

//        if (activityFragmentMessage.getFrom().equalsIgnoreCase("Cftoken"))
//        {
//            String Token_Api=""+activityFragmentMessage.getMessage();
//            Log.d("oewiruiur",Token_Api);
//
//            String[] recent;
//            recent = Token_Api.split("@@#");
//
//            initiatepayment( recent[0] , recent[1] );
//
//        }

 }
      public void getTokenOrder(String token, String OrderId){
        Log.d("dfhf",token+" "+OrderId);
        initiatepayment( token , OrderId);
      }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Calendar calendar = Calendar.getInstance();
        Date modifyDate = null;

        Log.d("respkeykeyhitttt", "API Response : " +data +"  :   resultCode  "+resultCode );
        String check= String.valueOf(resultCode);
         if (check.equalsIgnoreCase("-1"))
         {
             if (data != null) {
                 Bundle bundle = data.getExtras();

                 if (bundle != null)
                     for (String key : bundle.keySet())
                     {

                         if (bundle.getString(key) != null)
                         {
                             Log.e("respkeykeyee", key + " : " + bundle.getString(key));


                             if (key.equalsIgnoreCase("SUCCESS"))
                             {
                                 Toast.makeText(this, "Payment SUCCESS", Toast.LENGTH_SHORT).show();
                             }
                             else if (key.equalsIgnoreCase("referenceId"))
                             {

                                 Toast.makeText(this, "Payment SUCCESS", Toast.LENGTH_SHORT).show();
                                 try {
                                     SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                                     String user_id = "" + myPreferences.getString(ApplicationConstant.INSTANCE.User_id, "");
                                     String username = "" + myPreferences.getString(ApplicationConstant.INSTANCE.username, "");
                                     String date = ed_date.getText().toString().trim();
                                     SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                     Date ddd = format.parse(date);
                                     Log.d("", "ewrtrdt   " + ddd);
                                     payment( bundle.getString("referenceId"),bundle.getString("paymentMode"),bundle.getString("orderId"),bundle.getString("txMsg"),bundle.getString("orderAmount"),user_id,username);

                                     Calendar cal = Calendar.getInstance();
                                     cal.setTime(ddd);
                                     cal.add(Calendar.MONTH, Integer.parseInt(valid));
                                     st1 = format.format(cal.getTime());
                                     System.out.println(format.format(cal.getTime()));

                                     if (UtilMethods.INSTANCE.isNetworkAvialable(BookingActivityNew.this))
                                     {
                                         loader.show();
                                         loader.setCancelable(false);
                                         loader.setCanceledOnTouchOutside(false);


                                         if (status == 0)
                                         {
                                             AddDataIncomeDetails(user_id, packageId, "1", ed_date.getText().toString().trim(), ed_date.getText().toString().trim(), st1, wash_count, "PENDING", Price,modelId,"Done");
                                         }
                                         else
                                         {
                                             try
                                             {
                                                 db.collection("UserPackageDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                     @Override
                                                     public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                         if (queryDocumentSnapshots != null) {
                                                             List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                             for (DocumentSnapshot d : list) {
                                                                 redeem = 1;
                                                                 int value = Integer.parseInt(d.getString("redeem")) + redeem;
                                                                 Map<String, Object> redanddate = new HashMap<>();
                                                                 redanddate.put("redeem", String.valueOf(value));
                                                                 redanddate.put("modifyDate", ed_date.getText().toString().trim());
                                                                 db.collection("UserPackageDetails").document(d.getId()).update(redanddate);

                                                                 if (d.getString("washCount").equalsIgnoreCase(d.getString("redeem"))) {
                                                                     db.collection("UserPackageDetails").document(d.getId()).update("status", "EXPIRED");
                                                                 }
                                                             }
                                                         }
                                                     }
                                                 });
                                             } catch (Exception e)
                                             {
                                                 Log.d("", "" + e);
                                             }
                                         }
                                         addDataToFirestore(service_date, "Done", "" + bundle.getString(key), ed_name.getText().toString().trim() + "", ed_mobile.getText().toString().trim() + "", ed_date.getText().toString().trim() + "", service_time.getText().toString().trim() + "", ed_Address.getText().toString().trim() + "", ed_Landmark.getText().toString().trim() + "", ed_email.getText().toString().trim() + "", Car_name.getText().toString().trim() + "", washname_tv.getText().toString().trim() + "", price_total_final.getText().toString().trim() +
                                                 "", user_id, wash_count, month_count, modifyDate, Booking_status, st1);
                                     } else {
                                         UtilMethods.INSTANCE.Error(BookingActivityNew.this, getResources().getString(R.string.network_error_message));

                                     }
                                 } catch (Exception e)
                                 {
                                     e.printStackTrace();

                                 }
                             }


                         }
                     }
             }
         }else
         {
             if (loader != null)
             {
                 if (loader.isShowing())
                     loader.dismiss();
             }
           UtilMethods.INSTANCE.sweetAlertBoxFailed(BookingActivityNew.this,"Your transaction are failed","");
         }
    }
    public void payment(String transactionId, String paymentMode, String orderId, String status, String orderAmount ,String userId,String userName)
    {
        Log.d("sfnmdg",""+userName);
        Log.d("ushfdbmnsdfy",transactionId+"   "+paymentMode +"   "+orderId);
        PaymentModel model=new  PaymentModel( transactionId,  paymentMode,  orderId,  status,  orderAmount,userId,userName);

        CollectionReference dbCourses = db.collection("PaymentHistory");
        dbCourses.add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {
                Toast.makeText(BookingActivityNew.this, " Successful", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initiatepayment(String Token_Api, String order_id) {

        Log.e("getTokenActicity","As : "+Token_Api  +"   ,   ORDER_ID   :  "+ order_id);

        SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,  MODE_PRIVATE);

        Map<String, String> params = new HashMap<>();
        params.put(PARAM_APP_ID, "137508c30d06c9b684b7c697b0805731");
        params.put(PARAM_ORDER_ID, ""+order_id);
        params.put(PARAM_ORDER_AMOUNT, ""+Price.trim().replace("Rs. ","")); //+Price.trim().replace("Rs. ","")
        params.put(PARAM_ORDER_NOTE, "Order for Service");
        params.put(PARAM_CUSTOMER_NAME,""+myPreferences.getString(ApplicationConstant.INSTANCE.username, ""));
        params.put(PARAM_CUSTOMER_PHONE, ""+myPreferences.getString(ApplicationConstant.INSTANCE.phone, ""));
        params.put(PARAM_CUSTOMER_EMAIL, ""+myPreferences.getString(ApplicationConstant.INSTANCE.email, ""));
        params.put(PARAM_ORDER_CURRENCY, "INR");
        for(Map.Entry entry : params.entrySet())
        {

            Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());

        }

        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);
        cfPaymentService.doPayment(BookingActivityNew.this, params, Token_Api, "test", "#F8A31A", "#FFFFFF", false);

    }


    private void addDataToFirestore(String service_date,String paymenttype,String referenceId ,String ed_name  ,String  ed_mobile ,String ed_date ,String service_time ,String ed_Address ,String ed_Landmark ,String ed_email  ,String Car_name ,String washname_tv ,String price_total_final,String user_id, String wash_count , String month_count,Date modifyDate,String Booking_status,String expiredate)
    {


        Log.d("uytr4376eryewur  ",expiredate);
        CollectionReference dbCourses = db.collection("BookingHistory");
        SharedPreferences myPreferences =   getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref,  MODE_PRIVATE);
        String u_name=""+ myPreferences.getString(ApplicationConstant.INSTANCE.username, "") ;
        String u_email=""+ myPreferences.getString(ApplicationConstant.INSTANCE.email, "") ;
        String u_Address=""+ myPreferences.getString(ApplicationConstant.INSTANCE.Address, "") ;
        String u_Landmark=""+ myPreferences.getString(ApplicationConstant.INSTANCE.Landmark, "") ;
        String u_latitude=""+ myPreferences.getString(ApplicationConstant.INSTANCE.latitude, "") ;
        String u_longitude=""+ myPreferences.getString(ApplicationConstant.INSTANCE.longitude, "") ;
        String u_phone=""+ myPreferences.getString(ApplicationConstant.INSTANCE.phone, "") ;
         // ed_mobile , ed_name , ed_Landmark, ed_Address , ed_email
        // adding our data to our courses object class.
         String dailyCleaning="dailyCleaning";

            if (packageName.equalsIgnoreCase("Daily Cleaning"))
            {

                CollectionReference dbc= db.collection("DailyBooking");
                BookingHis courses = new BookingHis(service_date,paymenttype,referenceId, ed_name  ,  ed_mobile , ed_date , service_time , ed_Address , ed_Landmark , ed_email  , Car_name ,
                        washname_tv , price_total_final, user_id,  wash_count ,  month_count ,"" ,end_date ,"0" ,
                        ""+ u_name, ""+ u_email, ""+ u_Address, ""+ u_Landmark, ""+ u_latitude, ""+ u_longitude ,""+ u_phone,"0" ,modifyDate,Booking_status,dailyCleaning,expiredate,carModel,packageId);


                dbc.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {   @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
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

                        Log.e("onFailure", "onFailure : " + e);
                        Toast.makeText(BookingActivityNew.this, "Fail to Booking \n" + e, Toast.LENGTH_SHORT).show();
                    }
                });
                dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        // after the data addition is successful
                        // we are displaying a success toast message.


                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.sussesfull_place, null);

                        Button okButton = (Button) view.findViewById(R.id.okButton);
                        TextView msg = (TextView) view.findViewById(R.id.msg);
                        TextView washtype = (TextView) view.findViewById(R.id.washtype);

                        final Dialog dialog = new Dialog(BookingActivityNew.this);

                        dialog.setCancelable(false);
                        dialog.setContentView(view);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        msg.setText("" + Car_name);
                        washtype.setText("" + washname_tv);

                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                                String s = user_id;
                                Log.d("", "fbjdsjhgvhj" + s);

                                SendNotification(user_id, u_name, u_phone, carModel);


                                //  sendEmail();
                                // Toast.makeText(BookingActivityNew.this, "User Booking", Toast.LENGTH_SHORT).show();


                              /*  FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("Done", "BookingType");
                                GlobalBus.getBus().post(activityActivityMessage);

                                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();*/
                              /*  editor.putString(ApplicationConstant.INSTANCE.page_select, "2");
                                editor.commit();*/
                                Intent home = new Intent(BookingActivityNew.this, DashboadActivity.class);
                                startActivity(home);
                                finish();


                            }
                        });

                        dialog.show();


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

                        Log.e("onFailure", "onFailure : " + e);
                        Toast.makeText(BookingActivityNew.this, "Fail to Booking \n" + e, Toast.LENGTH_SHORT).show();
                    }
                });


            }
            else
            {
                BookingHis courses = new BookingHis(service_date,paymenttype,referenceId, ed_name  ,  ed_mobile , ed_date , service_time , ed_Address , ed_Landmark , ed_email  , Car_name ,
                        washname_tv , price_total_final, user_id,  wash_count ,  month_count ,"" ,end_date ,"0" ,
                        ""+ u_name, ""+ u_email, ""+ u_Address, ""+ u_Landmark, ""+ u_latitude, ""+ u_longitude ,""+ u_phone,"0" ,modifyDate,Booking_status,"",expiredate,carModel,packageId);


                dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        // after the data addition is successful
                        // we are displaying a success toast message.


                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.sussesfull_place, null);

                        Button okButton = (Button) view.findViewById(R.id.okButton);
                        TextView msg = (TextView) view.findViewById(R.id.msg);
                        TextView washtype = (TextView) view.findViewById(R.id.washtype);

                        final Dialog dialog = new Dialog(BookingActivityNew.this);

                        dialog.setCancelable(false);
                        dialog.setContentView(view);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        msg.setText("" + Car_name);
                        washtype.setText("" + washname_tv);

                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                                String s = user_id;
                                Log.d("", "fbjdsjhgvhj" + s);

                                SendNotification(user_id, u_name, u_phone, carModel);


                                //  sendEmail();
                                // Toast.makeText(BookingActivityNew.this, "User Booking", Toast.LENGTH_SHORT).show();


                              /*  FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("Done", "BookingType");
                                GlobalBus.getBus().post(activityActivityMessage);
                                      */


                                Intent home = new Intent(BookingActivityNew.this, DashboadActivity.class);
                                startActivity(home);
                                finish();


                            }
                        });

                        dialog.show();


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

                        Log.e("onFailure", "onFailure : " + e);
                        Toast.makeText(BookingActivityNew.this, "Fail to Booking \n" + e, Toast.LENGTH_SHORT).show();
                    }
                });

            }
    }
    private void AddDataIncomeDetails(String userId, String packageId, String redeem, String date, String modifyDate, String expireDate, String washCount,String status,String price,String modelId,String paymentstatus)
    {   Calendar calendar = Calendar.getInstance();
        Date time = (Date) calendar.getTime();
        SimpleDateFormat sdfDATE=new SimpleDateFormat("dd-MM-yyyy");
        String currentDate=sdfDATE.format(time);

        CollectionReference dbCourses = db.collection("UserPackageDetails");

        BookingModels models=new BookingModels( userId,  packageId,  redeem,  date,  modifyDate,  expireDate,  washCount, status,price,currentDate,modelId,paymentstatus) ;
        dbCourses.add(models).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference) {


            }
        });

    }






    private  void SendNotification(String user_id,String u_name,String contact_number,String carName)
    {
        Calendar calendar = Calendar.getInstance();
        Date time = (Date) calendar.getTime();
        String currentime= String.valueOf(time);
        SharedPreferences sharedPref = getSharedPreferences("application", MODE_PRIVATE);
          String tokenId=sharedPref.getString("tokenId", null);
        Map<String, Object> userNotification = new HashMap<>();
        UtilMethods.INSTANCE.sendNotifications(BookingActivityNew.this,tokenId, "Booking Confirms", " Your booking carwash/ "+carName+" is confirms");
        db.collection("Users").whereEqualTo("loginType","Admin").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list)
                    {    Log.d("","fkjsdfh"+d.getString("uitokenId"));
                        String Message=" You got one booking for "+carName+" from "+u_name;
                        String title="New Service Booking";
                        //send notification admin
                        UtilMethods.INSTANCE.sendNotifications(BookingActivityNew.this, d.getString("uitokenId"), title,Message );
                        userNotification.put("loginType",d.getString("loginType"));
                        userNotification.put("uid",d.getString("uid"));
                        userNotification.put("username",u_name);
                        userNotification.put("contact_number",contact_number);
                        userNotification.put("time",currentime);
                        userNotification.put("Message",Message);
                        userNotification.put("type","User");
                        db.collection("Notification").document().set(userNotification);

                    }

                }}
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.d("","kefdksjdfjdfds"+e);
            }
        });
    }



    protected void sendEmail()
    {
        Log.i("Send email", "");
        String[] TO = {"khanuzair@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("khanuzair970@gmail.com"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
         } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BookingActivityNew.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
 boolean  compareTime(String currenttime,String todate){
     SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
     try {
         Date time1=sdf.parse(currenttime);
         Date time2=sdf.parse(todate);
         if(time1.before(time2))
         {
             return true;
         }
         else {
             return  false;
         }

     } catch (ParseException e) {
         e.printStackTrace();
         return false;
     }

 }
}