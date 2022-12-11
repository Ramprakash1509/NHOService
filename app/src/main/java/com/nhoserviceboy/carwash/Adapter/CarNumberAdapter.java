package com.nhoserviceboy.carwash.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.AddModelNumber;
import com.nhoserviceboy.carwash.Activity.HomeActivity;
import com.nhoserviceboy.carwash.Activity.WashTypeDetailActivity1;
import com.nhoserviceboy.carwash.R;
import com.nhoserviceboy.carwash.Utils.UtilMethods;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarNumberAdapter extends  RecyclerView.Adapter<CarNumberAdapter.ViewHolder>
{

    Activity context;
    List<DocumentSnapshot> list=new ArrayList<>();
    private FirebaseFirestore db;
    String urlImg;
    int flag=0;

    public void filterList(ArrayList<DocumentSnapshot> filterlist)
    {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    public CarNumberAdapter(Activity context, List<DocumentSnapshot> list,int flag)
    {
        this.context = context;
        this.list = list;
        this.flag=flag;


    }

    @NonNull
    @Override
    public CarNumberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.car_model_drop_down, parent, false);
        return  new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CarNumberAdapter.ViewHolder holder, int position)
    {
        DocumentSnapshot data=list.get(position);

        Log.d("eru87y55",data.getString("carModel"));
        holder.modelNumber.setText(data.getString("carModel"));
        holder.li_carwash.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                 Intent i=new Intent(context, WashTypeDetailActivity1.class);
                 i.putExtra("carType",data.getString("carModel"));
                 i.putExtra("name",data.getString("carName"));
                 i.putExtra("carId",data.getString("packageId"));
                 i.putExtra("modelId",data.getId());
                 context.startActivity(i);

            }
        });     holder.iv_deleteModel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                UtilMethods.INSTANCE.pop(context,"are you sure want to delete?",data.getId(),1);

//                db.collection("CarModelDetails").whereEqualTo("ca").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                db.collection("CarModelDetails").document(document.getId()).delete();
//                            }
//                        } else {
//                            Log.d("TAG", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
            }
        });



    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView modelNumber;
        LinearLayout li_carwash;
        ImageView iv_deleteModel;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            modelNumber=itemView.findViewById(R.id.modelNumber);
            li_carwash=itemView.findViewById(R.id.li_carwash);
            iv_deleteModel=itemView.findViewById(R.id.iv_deleteModel);
            if(flag==1){
                iv_deleteModel.setVisibility(View.VISIBLE);
            }



        }
    }

}
