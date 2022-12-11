package com.nhoserviceboy.carwash.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhoserviceboy.carwash.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflter;
   List<String> list ;
    public CustomAdapter(Context applicationContext, List<String> list ) {
        this.context = applicationContext;
        this.list = list;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        convertView = inflter.inflate(R.layout.algorithm_spinner, null);

        TextView names = (TextView) convertView.findViewById(R.id.text_view);

        names.setText(list.get(position));
        return convertView;

    }
}
