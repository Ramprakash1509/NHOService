package com.nhoserviceboy.carwash.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.BookingCAr;
import com.nhoserviceboy.carwash.Utils.Loader;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingActivity extends AppCompatActivity  implements View.OnClickListener  , AdapterView.OnItemSelectedListener {

    TextView texttoolbar , tv_Submit;
    ImageView backprese;
    EditText et_name , et_mobile ,ed_date ,service_time , ed_Address , ed_Landmark  , ed_total_amount  ;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    TimePickerDialog timepickerdialog;
    String format;
    Spinner Select_Your_Car,Select_Your_Wash;
    Loader loader;
    private FirebaseFirestore db;

    final String  Devicetoken="dX8pbGgURMik60qYGn3eD1:APA91bFBUn7i0uQ8w1Sdea3zu7pPnUi_kFcfbg_q7SP37YqUkqerv99-F-TPZ42Imsz_-l0eEK9B36FUqL8ahrRAljt2Qsg0CaZBrkMUUau5kQtxdGbHK_mC9QdoFEAiIkRaCfMxCHkc";

    String[] Selectcar = { "Select Your Car", "Hatchback / Small Car", "Sedan car / Convertible / Hybrid","MUV/SUV / Off Road","Wagon / Mini Van / Pickup","Luxury / Super / Coupe" };
    String[] Selectwashtype = { "Select Your Wash", "Basic Wash", "Special Wash", "Water Less Wash" };
    String[] Selectwashtotala = { "0", "250", "300", "350" };

    String Selectcartype="";
    String Selectwash="";
    String Selectwashtext="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getToken();
        GetId();
    }

    private void GetId() {

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);

        db = FirebaseFirestore.getInstance();

        Select_Your_Car=findViewById(R.id.Select_Your_Car);
        Select_Your_Wash=findViewById(R.id.Select_Your_Wash);
        tv_Submit=findViewById(R.id.tv_Submit);

        et_name=findViewById(R.id.et_name);
        et_mobile=findViewById(R.id.et_mobile);
        ed_date=findViewById(R.id.ed_date);
        service_time=findViewById(R.id.service_time);
        ed_Address=findViewById(R.id.ed_Address);
        ed_Landmark=findViewById(R.id.ed_Landmark);
        ed_total_amount=findViewById(R.id.ed_total_amount);


        tv_Submit.setOnClickListener(this);
        service_time.setOnClickListener(this);
        ed_date.setOnClickListener(this);
        Select_Your_Car.setOnItemSelectedListener(this);
        Select_Your_Wash.setOnItemSelectedListener(this);


        texttoolbar=findViewById(R.id.texttoolbar);
        backprese=findViewById(R.id.backprese);
        backprese.setOnClickListener(this);

        texttoolbar.setText("Booking");


         ArrayAdapter aa = new ArrayAdapter(this,R.layout.simple_spinner_item,Selectcar);
       // aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        Select_Your_Car.setAdapter(aa);


         ArrayAdapter ww = new ArrayAdapter(this,R.layout.simple_spinner_item,Selectwashtype);
      //  ww.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         Select_Your_Wash.setAdapter(ww);


    }

    public void Datepicker() {

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
                dialog.dismiss();
                ed_date.setText( datePicker.getYear() + "-" +  (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth() );

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





    @Override
    public void onClick(View view)

    {
        if(view==tv_Submit)
        {


            if(et_name.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Enter Full Name", Toast.LENGTH_SHORT).show();

            }else  if(et_mobile.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();

            }else  if(service_time.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Select Time", Toast.LENGTH_SHORT).show();

            }else  if(ed_Address.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();

            } else  if(ed_Landmark.getText().toString().trim().isEmpty()){

                Toast.makeText(this, "Enter Landmark", Toast.LENGTH_SHORT).show();

            }else
            {

                if (UtilMethods.INSTANCE.isNetworkAvialable(BookingActivity.this))
                {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    String BookingStatus="pendind";

                    addDataToFirestore(""+et_name.getText().toString().trim(), ""+et_mobile.getText().toString().trim(),
                            ""+service_time.getText().toString().trim() ,""+ed_date.getText().toString().trim(),
                            ""+ed_Address.getText().toString().trim()  , ""+ed_Landmark.getText().toString().trim() ,
                            ""+ ed_total_amount.getText().toString().trim()  );

                }
                else
                {
                    UtilMethods.INSTANCE.Error(BookingActivity.this, getResources().getString(R.string.network_error_message));
                }

            }
           // UtilMethods.INSTANCE.sendNotifications(BookingActivity.this,Devicetoken,"hfdsfhd","sdfshdfjhfgd");
            getToken();

        }

        if(view==Select_Your_Car){

        }

        if(view==service_time){

            calendar = Calendar.getInstance();
            CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
            CalendarMinute = calendar.get(Calendar.MINUTE);


            timepickerdialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            if (hourOfDay == 0) {

                                hourOfDay += 12;
                                format = " AM";
                            }
                            else if (hourOfDay == 12) {

                                format = " PM";

                            }
                            else if (hourOfDay > 12) {

                                hourOfDay -= 12;
                                format = " PM";

                            }
                            else {

                                format = " AM";
                            }


                            service_time.setText(hourOfDay + ":" + minute + format);

                        }
                    }, CalendarHour, CalendarMinute, false);
            timepickerdialog.show();

        }

   if(view==ed_date){


            Datepicker();



        }
   if(view==backprese)
   {

            onBackPressed();

        }

    }

    /*name ,mobile, time,date,Address,
                              Landmark ,total_amount   */



    private void addDataToFirestore( String et_name ,String  et_mobile, String service_time,String  ed_date,String  ed_Address,
                                     String ed_Landmark ,String ed_total_amount )
    {


        // creating a collection reference
        // for our Firebase Firetore database.


        CollectionReference dbCourses = db.collection("BookingCar");

        // adding our data to our courses object class.
        BookingCAr courses = new BookingCAr(et_name ,et_mobile, service_time,ed_date,ed_Address, ed_Landmark ,ed_total_amount,Selectcartype,Selectwashtext );

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
                // after the data addition is successful
                // we are displaying a success toast message.

                Toast.makeText(BookingActivity.this, "User Booking", Toast.LENGTH_SHORT).show();

                Intent home = new Intent(BookingActivity.this, DashboadActivity.class);
                startActivity(home);
                finish();

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

                Toast.makeText(BookingActivity.this, "Fail to Booking \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
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
                        String tokenId=d.getString("token");
                        String userId=d.getString("userId");

                        Log.d("","tokenidjkdfhg"+tokenId);

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

        if(adapterView.getId() == R.id.Select_Your_Car)
        {
            //do this
            Selectcartype=Selectcar[i];


        }
        else if(adapterView.getId() == R.id.Select_Your_Wash)
        {
            //do this

            Selectwash=Selectwashtotala[i];
            Selectwashtext=Selectwashtype[i];

            ed_total_amount.setText(""+ Selectwash);


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}