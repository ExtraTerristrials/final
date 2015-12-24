package com.extraterristrial.healthmanagementsystem;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.DietInformation;

import java.util.ArrayList;

public class DietListAdapter extends ArrayAdapter<DietInformation>{
    ArrayList<DietInformation> dietInformationArrayList;
    public DietListAdapter(Context context, ArrayList<DietInformation> dietInformationArrayList) {
        super(context, R.layout.diet_list_layout,dietInformationArrayList);
        this.dietInformationArrayList=dietInformationArrayList;
    }
    private class ViewHolder{

        TextView title,time,items;

        public ViewHolder(View v){

            title= (TextView) v.findViewById(R.id.diet_title);
            time= (TextView) v.findViewById(R.id.diet__time);
            items= (TextView) v.findViewById(R.id.diet_items);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder myHolder;

        if(row==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            row=inflater.inflate(R.layout.diet_list_layout,parent,false);
            myHolder=new ViewHolder(row);
            row.setTag(myHolder);
        }
        else{
            myHolder=(ViewHolder)row.getTag();
        }
        myHolder.title.setText(dietInformationArrayList.get(position).getFoodTitle());
        myHolder.time.setText("At "+dietInformationArrayList.get(position).getFoodTime());
        myHolder.items.setText(dietInformationArrayList.get(position).getFoodItem());
        return row;
    }
}
