package com.nhoserviceboy.carwash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cashfree.pg.CFPaymentService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nhoserviceboy.carwash.Activity.BookingActivityNew;

import java.util.HashMap;
import java.util.Map;

public class PaymentGatway extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gatway);


        HashMap<String, String> params = new HashMap<>();
        params.put(CFPaymentService.PARAM_APP_ID,"TEST251001a2798eca1257f0292156100152");
        params.put(CFPaymentService.PARAM_ORDER_ID, "1200");
        params.put(CFPaymentService.PARAM_ORDER_AMOUNT, "3000");
        params.put(CFPaymentService.PARAM_CUSTOMER_NAME, "Ram");
        params.put(CFPaymentService.PARAM_CUSTOMER_PHONE, "9374634664");
        params.put(CFPaymentService.PARAM_CUSTOMER_EMAIL, "ra@gamil.com");
        params.put(CFPaymentService.PARAM_ORDER_CURRENCY, "INR");
        params.put(CFPaymentService.PARAM_NOTIFY_URL, " https://your.backend.webhook");
        String token="";

         CFPaymentService.getCFPaymentServiceInstance()
         .doPayment(PaymentGatway.this, params, token, "test", "#F8A31A", "#FFFFFF", false);



    }

    @Override
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode==CFPaymentService.REQ_CODE && data != null)
        {
            Bundle  bundle = data.getExtras();
            if (bundle != null)
            {
                showResponse(transformBundelToString(bundle));
            }
        }
    }

    private void showResponse(String data)
    {
         new MaterialAlertDialogBuilder(this)
                 .setTitle(data)
                 .setMessage("payment")
                 .setPositiveButton("ok",((dialog1, which) ->
                 {
                     dialog1.dismiss();
                 }
                         ))
                 .show();


    }
    private String  transformBundelToString(Bundle bundle)
    {
        String response="";
           for (String  key  :  bundle.keySet())
                {

                    response=response.concat(String.format("%s :%s\n",key,bundle.getString(key)) );
                }

          return response;
    }



}