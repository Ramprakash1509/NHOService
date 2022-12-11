package com.nhoserviceboy.carwash.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhoserviceboy.carwash.Activity.WashTypeDetailActivity1;
import com.nhoserviceboy.carwash.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModelAdapter extends  RecyclerView.Adapter<ModelAdapter.ViewHolder>
{
    private FirebaseFirestore db;
    private Context context;
    private List<DocumentSnapshot> list = new ArrayList<>();

    public ModelAdapter(Context context, List<DocumentSnapshot> list)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {   LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_dropdown, parent, false);
        return  new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelAdapter.ViewHolder holder, int position)
    {

        DocumentSnapshot data=list.get(position);

        List<Boolean> booleanList=new ArrayList<Boolean>(Arrays.asList(new Boolean[position]));
        booleanList.add(false);

        holder.carname.setText("" + data.getString("carName")  );

        String url= "" + data.getString("car_detail_image");
        Glide.with(holder.itemView)
                .load(url)
                .placeholder(R.drawable.rnd_logo)
                .into(holder.carimage);



        db = FirebaseFirestore.getInstance();
        boolean isExpendable=true;



        holder.imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                holder.idProgressBar.setVisibility(View.VISIBLE);
              if(!booleanList.get(position))
              {

                  holder.ll_car_wash_detail.setVisibility(View.VISIBLE);
                  booleanList.set(position,true);
                  holder.imageView.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
              }
              else
              {     holder.idProgressBar.setVisibility(View.GONE);
                  holder.imageView.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                  holder.ll_car_wash_detail.setVisibility(View.GONE);
                  booleanList.set(position,false);
              }

                db.collection("CarModelDetails").whereEqualTo("carName",data.getString("carName")).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                        {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                            {

                                if (queryDocumentSnapshots != null)
                                {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                    holder.idProgressBar.setVisibility(View.GONE);
                                    holder.ll_car_wash_detail.removeAllViews();
                             /*       for (DocumentSnapshot d : list)
                                    {
                                        LayoutInflater inflater2 = null;
                                        inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View mLinearView = inflater2.inflate(R.layout.car_model_drop_down, null);
                                        final TextView carname, modelNumber;
                                        LinearLayout li_carwash;
                                        CircleImageView img;

                                        carname = mLinearView.findViewById(R.id.CarName);
                                        img = mLinearView.findViewById(R.id.carimage);
                                        modelNumber = mLinearView.findViewById(R.id.modelNumber);
                                        li_carwash = mLinearView.findViewById(R.id.li_carwash);

                                        Log.d("sguetiu", "OJH" + d.getString("carName"));
                                        carname.setText("" + d.getString("carName"));
                                        modelNumber.setText("" + d.getString("carModel"));
                                        Glide.with(mLinearView)
                                                .load(url)
                                                .placeholder(R.drawable.rnd_logo)
                                                .into(img);
                                        li_carwash.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                Toast.makeText(context, "JHgdgfhjsdhugd", Toast.LENGTH_SHORT).show();

                                                Intent i=new Intent(context, WashTypeDetailActivity1.class);
                                                i.putExtra("carId",data.getId());
                                                i.putExtra("carName",data.getString("carName"));
                                                i.putExtra("carModel", d.getString("carModel"));
                                                context.startActivity(i);

                                            }
                                        });

                                        holder.ll_car_wash_detail.addView(mLinearView);

                                    }*/

                                }
                                else
                                {
                                    Toast.makeText(context, "Not fond data in database", Toast.LENGTH_SHORT).show();
                                    holder.idProgressBar.setVisibility(View.GONE);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(context, "Fail to download", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });






          /* holder.isExpendable.setVisibility(isExpendable ? View.VISIBLE :View.GONE);
        if (isExpendable)
        { holder.imageView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        }
        else
        { holder.imageView.setImageResource(R.drawable.arrow_down_24);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

              *//*  data.setExpendable(!data.isExpendable());
                nestedlist=data.getData();
                notifyItemChanged(holder.getAdapterPosition());*//*
            }
        });*/


    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
         TextView carname   ;
        CircleImageView carimage;

        private RelativeLayout isExpendable;
        private RecyclerView recyclerView;
        private ImageView imageView,image;
        LinearLayout linearLayout;
        LinearLayout ll_car_wash_detail;
        ProgressBar idProgressBar;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carname =  itemView.findViewById(R.id.carwashname);
            carimage =  itemView.findViewById(R.id.carimage);
            isExpendable=itemView.findViewById(R.id.isExpendable);
            recyclerView=itemView.findViewById(R.id.recyclerview);
            imageView=itemView.findViewById(R.id.arrow);
            linearLayout=itemView.findViewById(R.id.linearlayout);
            image=itemView.findViewById(R.id.image);
            ll_car_wash_detail=itemView.findViewById(R.id.ll_car_wash_detail);
            idProgressBar=itemView.findViewById(R.id.idProgressBar);

        }
    }
}
