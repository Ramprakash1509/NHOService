package com.nhoserviceboy.carwash.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.BookingActivity;
import com.nhoserviceboy.carwash.Activity.BookingActivityNew;
import com.nhoserviceboy.carwash.Activity.CarWashActivity;
import com.nhoserviceboy.carwash.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServicesFragment extends Fragment implements View.OnClickListener {

    LinearLayout ll_carservies;
    ProgressBar loadingPB;
    private FirebaseFirestore db;
    TextView tv_Continue;
    String in_serviestype="" , in_carprice="" , in_carimage ;
    String Select_carprice="";

    ArrayList<String> car_price_count = new ArrayList<String>();
    ArrayList<String> car_name_count = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_servies, container, false);

        Getid(v);

        return v;
    }

    private void Getid(View v) {

        ll_carservies=v.findViewById(R.id.ll_carservies);
        loadingPB =  v.findViewById(R.id.idProgressBar);
        tv_Continue =  v.findViewById(R.id.tv_Continue);
        tv_Continue.setOnClickListener(this);

        GetCarlist();
    }


    private void GetCarlist() {

        db = FirebaseFirestore.getInstance();
        db.collection("CarService").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                LayoutInflater inflater2 = null;
                                inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View mLinearView = inflater2.inflate(R.layout.carservies_abapter_item, null);


                                final TextView carservies  , Priceval  ;
                                CircleImageView carimage;
                                RadioButton rd1;

                                Priceval =  mLinearView.findViewById(R.id.Price);
                                carservies =  mLinearView.findViewById(R.id.carservies);

                                carimage =  mLinearView.findViewById(R.id.carimage);
                                rd1 =  mLinearView.findViewById(R.id.rd1);

                                carservies.setText("" + d.getString("serviestype") );
                                Priceval.setText("Price :  Rs." + d.getString("carprice") );

                                String url= "" + d.getString("carimage");

                                Glide.with(getActivity())
                                        .load(url)
                                        .placeholder(R.drawable.rnd_logo)
                                        .into(carimage);


                                 rd1.setChecked(false);


                                rd1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        tv_Continue.setVisibility(View.VISIBLE);

                                     //   Toast.makeText(getActivity(), "price :"+d.getString("carprice")  +"  Select_carprice  : "+ Select_carprice, Toast.LENGTH_SHORT).show();


                                        if(d.getString("carprice").equalsIgnoreCase(Select_carprice)){

                                            rd1.setChecked(false);

                                            Select_carprice  = "";

                                            car_price_count.remove(""+d.getString("carprice"));
                                            car_name_count.remove(""+d.getString("serviestype"));


                                            for(int i=0;i<car_name_count.size();i++) {

                                                if(car_name_count.get(i) == ""+d.getString("serviestype")) {

                                                    car_name_count.remove(i);
                                                    car_price_count.remove(i);

                                                }

                                            }

                                        }else {

                                            rd1.setChecked(true);
                                            Select_carprice  =    d.getString("carprice");

                                            car_price_count.add(""+d.getString("carprice"));
                                            car_name_count.add(""+d.getString("serviestype"));


                                            for(int i=0;i<car_name_count.size();i++) {

                                                if(car_name_count.get(i) == ""+d.getString("serviestype")) {

                                                    car_name_count.remove(i);
                                                    car_price_count.remove(i);

                                                }

                                            }




                                            Log.e("serviestype",""+d.getString("carprice") +"  ,  serviestype  :  "+  d.getString("serviestype") );




                                        }



                                        in_serviestype =   d.getString("serviestype");
                                        in_carprice  =    d.getString("carprice");
                                        in_carimage = d.getString("carimage");







                                    }
                                });

                                ll_carservies.addView(mLinearView);

                            }

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onClick(View view) {

if(view==tv_Continue){

  //  Toast.makeText(getActivity(), "Price : "+car_price_count  +"     , Name :     "+ car_name_count, Toast.LENGTH_SHORT).show();

    Log.e("PricePricePrice",""+ "Price : "+car_price_count  +"     , Name :     "+ car_name_count );

    int total_price=0;
    int total_price_api=0;

    for(int i=0;i<car_price_count.size();i++) {



        total_price_api= Integer.parseInt(car_price_count.get(i));



        total_price =  total_price + total_price_api  ;

        Log.e("car_name","As : "+ car_price_count.get(i));

    }


    if(!in_serviestype.equalsIgnoreCase("")){

        Intent i =new Intent(getActivity(),BookingActivityNew.class);
        i.putExtra("serviestype","" +in_serviestype );
        i.putExtra("carprice","" +in_carprice  );
        i.putExtra("carimage","" +in_carimage  );
        i.putExtra("carname","Car" );
        i.putExtra("car_price_count",""+car_price_count );
        i.putExtra("car_name_count",""+car_name_count );
        i.putExtra("total_price",""+total_price );
        startActivity(i);


    }else {
        Toast.makeText(getActivity(), "Select Any One", Toast.LENGTH_SHORT).show();
    }
}
    }
}