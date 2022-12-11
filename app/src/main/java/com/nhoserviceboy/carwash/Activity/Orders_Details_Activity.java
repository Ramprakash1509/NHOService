package com.nhoserviceboy.carwash.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nhoserviceboy.carwash.R;

public class Orders_Details_Activity extends AppCompatActivity  implements View.OnClickListener {

    String carimage="" ,type_select,date ,time, serviestype,carprice,carname,user_id  ,  mobile , name , service,Landmark, Address , email,price_final , Car_name;
    TextView texttoolbar ;
    ImageView backprese;

    ImageView banner_image;
    TextView txt_ordername,txt_orderdate,txt_ordertime,txt_ordertotal,txt_orderbillingadd;
    TextView txt_orderlandmark  , txt_product_price,txt_productname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders__details_);

        Getid();

    }

    private void Getid() {

        backprese =findViewById(R.id.backprese);
        texttoolbar =findViewById(R.id.texttoolbar);

        txt_ordername =findViewById(R.id.txt_ordername);
        txt_orderdate =findViewById(R.id.txt_orderdate);
        txt_ordertime =findViewById(R.id.txt_ordertime);
        txt_ordertotal =findViewById(R.id.txt_ordertotal);
        txt_orderbillingadd =findViewById(R.id.txt_orderbillingadd);
        txt_orderlandmark =findViewById(R.id.txt_orderlandmark);
        txt_productname =findViewById(R.id.txt_productname);
        txt_product_price =findViewById(R.id.txt_product_price);

        banner_image =findViewById(R.id.banner_image);
        backprese.setOnClickListener(this);

        texttoolbar.setText("Booking Detail");

        carimage=getIntent().getStringExtra("carimage");
        date=getIntent().getStringExtra("date");
        time=getIntent().getStringExtra("time");
        serviestype=getIntent().getStringExtra("serviestype");
        carprice=getIntent().getStringExtra("carprice");
        carname=getIntent().getStringExtra("carname");
        user_id  =getIntent().getStringExtra("user_id");
        mobile =getIntent().getStringExtra("mobile");
        name =getIntent().getStringExtra("name");
        Landmark=getIntent().getStringExtra("Landmark");
        Address =getIntent().getStringExtra("Address");
        email =getIntent().getStringExtra("email");
        Car_name=getIntent().getStringExtra("car_name_model");
        price_final=getIntent().getStringExtra("price_final");
        service=getIntent().getStringExtra("service");
        type_select=getIntent().getStringExtra("type_select");

        Log.e("Car_nam122","service : "+  service  +"  , price_final : "+price_final );

        String  asas = ""+price_final.replace("]","");
        String ass =""+ asas.replace("[","Rs. ");
        String  name_as = ""+service.replace("]","");
        String das =""+ name_as.replace("[","");

        txt_productname.setText(""+ das.replace(",","\n"));
        txt_product_price.setText(""+ ass.replace(",","\n  Rs. "));

        Log.e("carimageasa","As : "+ carimage.trim());

        Glide.with(Orders_Details_Activity.this)
                .load(carimage.trim()+"")
                .placeholder(R.drawable.rnd_logo)
                .into(banner_image);

        txt_ordername.setText(""+ Car_name );
        txt_orderdate.setText(""+ date );
        txt_ordertime.setText(""+ time );
        txt_ordertotal.setText(""+ carprice );
        txt_orderbillingadd.setText(""+ Address );
        txt_orderlandmark.setText(""+ Landmark );



      /*  position=getIntent().getStringExtra("position");
        Gson gson = new Gson();
        OrderResSubRes model = gson.fromJson(position, OrderResSubRes.class);


        String ordername =model.getName();
        String id = String.valueOf(model.getId());
        String unitprice =model.getUnitPrice();
        String desc     =   model.getProductDescription();
        String unit     =     model.getUnit();
        String slug = model.getSlug();
        String bannerimg =model.getThumbnailImg();
        String orderdate=model.getCreatedAt();
        String orderno= model.getSku();
        String ordertotal= model.getPurchasePrice();
        String itemprice =model.getPurchasePrice();
        String quantity =String.valueOf(model.getMinQty()) ;
        String shippinfee = model.getShippingCost();
        String billing_address =model.getBillingAddress();
        String  shipping_address=model.getShippingAddress();



        txt_ordername.setText(ordername);
        txt_orderdate.setText(orderdate);

        Glide.with(co.civilguruji.rkdf.Activity.Orders_Details_Activity.this)
                .load("http://innovbit.com/hardware/storage/app/"+bannerimg)
                .placeholder(R.drawable.faileimage)
                .into(banner_image);


        txt_orderdate.setText(orderdate);
        txt_ordernumber.setText(orderno);
        txt_ordertotal.setText(ordertotal);
        order_itemprice.setText(itemprice);
        order_itemquantity.setText(quantity);
        txt_ordershippingfee.setText(shippinfee);
        txt_totalorderamt.setText(ordertotal);
        txt_ordershipping.setText(shipping_address);
        txt_orderbillingadd.setText(billing_address);*/




    }

    @Override
    public void onClick(View view) {


        if(view==backprese){

            onBackPressed();

        }

    }
}