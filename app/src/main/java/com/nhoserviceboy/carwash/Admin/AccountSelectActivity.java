package com.nhoserviceboy.carwash.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nhoserviceboy.carwash.Activity.LoginActivity;
import com.nhoserviceboy.carwash.LoginScreen;
import com.nhoserviceboy.carwash.R;

public class AccountSelectActivity extends AppCompatActivity implements View.OnClickListener
{
    TextView tv_login_admin  , tv_login_user , tv_login_service_boy ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_select);


        GetID();

    }

    private void GetID()
    {



      //  tv_login_admin=findViewById(R.id.tv_login_admin);
        tv_login_user=findViewById(R.id.tv_login_user);
        tv_login_service_boy=findViewById(R.id.tv_login_service_boy);


    //    tv_login_admin.setOnClickListener(this);
        tv_login_user.setOnClickListener(this);
        tv_login_service_boy.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {

        if(view==tv_login_service_boy)
        {


            Intent intent = new Intent(AccountSelectActivity.this, LoginActivity.class);
            intent.putExtra("type","serviceboy");
            startActivity(intent);
            finish();



        }

       if(view==tv_login_user)
       {


            Intent intent = new Intent(AccountSelectActivity.this, LoginScreen.class);
            intent.putExtra("type","User");
            startActivity(intent);
            finish();



        }

      /* if(view==tv_login_admin){

           Intent intent = new Intent(AccountSelectActivity.this, PhoneAuth.class);
           intent.putExtra("type","Admin");
           startActivity(intent);
           finish();
*/
       // }






    }
}