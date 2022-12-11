package com.nhoserviceboy.carwash.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Adapter.CarNumberAdapter;
import com.nhoserviceboy.carwash.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SearchCarTypeFragement extends Fragment implements SearchView.OnQueryTextListener {

RecyclerView recyclerview;
private FirebaseFirestore db;
SearchView searchView;
CarNumberAdapter adapter;
TextView wishing;

    private List<DocumentSnapshot> courseModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_search_car_type_fragement, container, false);
        GetId(v);
        getTimeFromAndroid();
        return v;
    }
    private void GetId(View v)
    {
        recyclerview=v.findViewById(R.id.recyclerview);
        searchView=v.findViewById(R.id.simpleSearchView);
        wishing=v.findViewById(R.id.wishing);
        searchView.setOnQueryTextListener(this);
        db = FirebaseFirestore.getInstance();
        db.collection("CarModelDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {  if (queryDocumentSnapshots != null)
            {
                courseModelArrayList=queryDocumentSnapshots.getDocuments();

                adapter=new CarNumberAdapter(getActivity(),courseModelArrayList,0);
                adapter.notifyDataSetChanged();
                recyclerview.setHasFixedSize(false);
                recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(llm);
                recyclerview.setAdapter(adapter);

            }
            else
            {
                Toast.makeText(getActivity(), "Not fond data in database", Toast.LENGTH_SHORT).show();

            }

            }
        });



    }

   /* public void hitCheck(String value)
    {

        db.collection("CarModelDetails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {  if (queryDocumentSnapshots != null)
            {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();

                adapter=new CarNumberAdapter(getActivity(),list);
                adapter.notifyDataSetChanged();
                recyclerview.setHasFixedSize(false);
                recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(llm);
                recyclerview.setAdapter(adapter);

            }
            else
            {
                Toast.makeText(getActivity(), "Not fond data in database", Toast.LENGTH_SHORT).show();

            }

            }
        });

    }*/
   private void getTimeFromAndroid()
   {
       Date dt = new Date();
       int hours = dt.getHours();
       int min = dt.getMinutes();
       Log.d("","hours"+hours);

       if(hours>=1 && hours<=12)
       {
           wishing.setText("Good Morning");
       }else if(hours >=12 && hours<=16)
       {
           wishing.setText("Good Afternoon");
       }else if(hours>=16 && hours<=21){

           wishing.setText("Good Evening");
       }else if(hours>=21 && hours<=24){

           wishing.setText("Good Night");
       }
   }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        filter(newText);
        return false;
    }

    private void filter(String text)
    {

        ArrayList<DocumentSnapshot> filteredlist = new ArrayList<DocumentSnapshot>();
        for (DocumentSnapshot item : courseModelArrayList)
        {

            if (item.getString("carModel").toLowerCase().contains(text.toLowerCase()) )
            {
                filteredlist.add(item);
             }
          }
        if (filteredlist.isEmpty())
        {
            filteredlist.clear();
            adapter.filterList(filteredlist);
        }
        else
        {

            adapter.filterList(filteredlist);
        }
    }

















}