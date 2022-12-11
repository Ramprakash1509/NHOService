package com.nhoserviceboy.carwash.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nhoserviceboy.carwash.Activity.AddCarModelNumber;
import com.nhoserviceboy.carwash.Activity.BookingActivityNew;
import com.nhoserviceboy.carwash.Activity.ServiceBoyDasBoad;
import com.nhoserviceboy.carwash.Activity.SplashActivity;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.firebaseNotification.APIService;
import com.nhoserviceboy.carwash.firebaseNotification.Client;
import com.nhoserviceboy.carwash.firebaseNotification.Data;
import com.nhoserviceboy.carwash.firebaseNotification.MyResponse;
import com.nhoserviceboy.carwash.firebaseNotification.NotificationSender;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public enum UtilMethods {

    INSTANCE;


    public void locationreposeval(Context context, String locationreposeval ) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.locationreposeval, locationreposeval);
        editor.commit();

    }


    public void logout(Context context)
    {

        UtilMethods.INSTANCE.setLoginrespose(context,  "","","" ,"","" ,"" ,"",1,1);
        Intent startIntent = new Intent(context, SplashActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(startIntent);

    }

    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void Successfulnew(final Context context, final String message, final int i, final Activity activity) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.poprespose, null);

        Button okButton = (Button) view.findViewById(R.id.okButton);
        TextView msg = (TextView) view.findViewById(R.id.msg);

        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        msg.setText(""+message);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i==1){


                    dialog.dismiss();

                }  else {

                    dialog.dismiss();

                }

            }
        });

        dialog.show();



    }

    public void Error(final Context context,final String message) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.poprespose, null);
        Button okButton = (Button) view.findViewById(R.id.okButton);
        TextView msg = (TextView) view.findViewById(R.id.msg);
        TextView   resposestatus = (TextView) view.findViewById(R.id.resposestatus);

        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        msg.setText(""+message);
        resposestatus.setText("Failed");


        resposestatus.setTextColor(context.getResources().getColor(R.color.red));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();


    }
    public void Success(final Context context,final String message) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.poprespose, null);


        Button okButton = (Button) view.findViewById(R.id.okButton);
        TextView msg = (TextView) view.findViewById(R.id.msg);
        TextView   resposestatus = (TextView) view.findViewById(R.id.resposestatus);

        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        msg.setText(""+message);
        resposestatus.setText("Update");


        resposestatus.setTextColor(context.getResources().getColor(R.color.red));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();


    }


    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void setLoginrespose(Context context,  String email,String  phone,String username,String one  ,String User_idcsc , String Address , String Landmark  ,double latitude, double longitude) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
         editor.putString(ApplicationConstant.INSTANCE.one, one);
        editor.putString(ApplicationConstant.INSTANCE.email, email);

        editor.putString(ApplicationConstant.INSTANCE.phone, phone);
        editor.putString(ApplicationConstant.INSTANCE.username, username);
        editor.putString(ApplicationConstant.INSTANCE.User_id, User_idcsc);
        editor.putString(ApplicationConstant.INSTANCE.Address, Address);
        editor.putString(ApplicationConstant.INSTANCE.Landmark, Landmark);
        editor.putString(ApplicationConstant.INSTANCE.latitude, String.valueOf(latitude));
        editor.putString(ApplicationConstant.INSTANCE.longitude, String.valueOf(longitude));
        editor.commit();

    }
    public void sendNotifications( Context context ,String usertoken, String title, String message)
    {
        Log.d("",""+title);
        Data data = new Data(title, message);
        Log.d("","checkmessage"+data.getTitle().toString());

        NotificationSender sender = new NotificationSender(data, usertoken);
        APIService apiService= Client.getClient().create(APIService.class);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>()
        {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response)
            {
                Log.d("","eryhdfb"+response);

                if (response.code() == 200)
                {
                    if (response.body().success != 1)
                    {
                        Toast.makeText(context, "Failed "+response.body(), Toast.LENGTH_LONG);

                    }
                    Log.d("","sdfsdf"+response.message());
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t)
            {
                Log.d("","exce"+t);
            }
        });
    }






    public void getToken(final Context context, final String orderId , String orderAmount ,String  ORDER_ID)
    {

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("orderId", orderId);
        jsonObj.addProperty("orderAmount", orderAmount);
        jsonObj.addProperty("orderCurrency", "INR");



        /*  @Field("orderId") String orderId,
                                  @Field("orderAmount") String orderAmount,
                                  @Field("orderCurrency") String orderCurrency);*/


        Log.d("getToken", "orderId : " + orderId +"   ,  orderAmount  :  "+ orderAmount  +"   ,  jsonObj  : "+ jsonObj);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CarwashRespose> call = git.getToken("137508c30d06c9b684b7c697b0805731","689996b5562baa344ba59745bec6c23b87f7d833", jsonObj);
            call.enqueue(new Callback<CarwashRespose>() {
                @Override
                public void onResponse(Call<CarwashRespose> call, retrofit2.Response<CarwashRespose> response) {

                    Log.e("getTokenres", "Banner : " + new Gson().toJson(response.body()).toString());

                    if (response.body() !=null )
                    {
                        if (response.body().getStatus().equalsIgnoreCase("OK")) {
                         ((BookingActivityNew) context).getTokenOrder(response.body().getCftoken(),ORDER_ID);
//                            FragmentActivityMessage activityActivityMessage =
//                                    new FragmentActivityMessage("" + response.body().getCftoken()+"@@#"+ORDER_ID , "Cftoken");
//                            GlobalBus.getBus().post(activityActivityMessage);

                        }
                        else
                        {

                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                }

                @Override
                public void onFailure(Call<CarwashRespose> call, Throwable t) {

                    Log.e("responseewr", "Banner "+t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.e("responseewr", "Banner "+e.getMessage());

            e.printStackTrace();
        }

    }




    public String dateForamterer(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(date);
        return currentDateandTime;
    }
    public String TimeForamterer(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String timess = sdf.format(time);
        return timess;
    }

    public void sweetAlertBoxFailed(Context context, String message, String openspage) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("")
                .setContentText("" + "" + message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                }).show();
    }





    public void pop(Activity activity, String Message, String id,int flag)
    {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewpop = inflater.inflate(R.layout.logout_pop, null);

        Button okButton = (Button) viewpop.findViewById(R.id.okButton);
        Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
        TextView msg = (TextView) viewpop.findViewById(R.id.msg);
        msg.setText(Message);
        final Dialog dialog = new Dialog(activity);

        dialog.setCancelable(false);
        dialog.setContentView(viewpop);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  msg.setText("");

        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {     FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                // 1 coming from CardNumberAdapter
                if(flag==1){
                    db.collection("CarModelDetails").document(id).delete();
                    activity.startActivity(new Intent(activity, AddCarModelNumber.class));
                    activity.finish();
                }


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


    }





}