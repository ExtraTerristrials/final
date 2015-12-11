package com.extraterristrial.healthmanagementsystem;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.UserInformation;

import java.util.ArrayList;

/**
 * Created by Jewel on 12/9/2015.
 */
public class ProfileListAdapter extends ArrayAdapter<UserInformation>{

    ArrayList<UserInformation> userInformations;

    public ProfileListAdapter(Context context, ArrayList<UserInformation> userInformations) {
        super(context, R.layout.profile_list_layout,userInformations);
        this.userInformations=userInformations;
    }

    private class ViewHolder{
        ImageView userImage;
        TextView name,age,gender,email;

        public ViewHolder(View v){
            userImage= (ImageView) v.findViewById(R.id.imageView);
            name= (TextView) v.findViewById(R.id.name);
            age= (TextView) v.findViewById(R.id.age);
            gender= (TextView) v.findViewById(R.id.gender);
            email= (TextView) v.findViewById(R.id.email);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder myHolder;

        if(row==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            row=inflater.inflate(R.layout.profile_list_layout,parent,false);
            myHolder=new ViewHolder(row);
            row.setTag(myHolder);
        }
        else{
            myHolder=(ViewHolder)row.getTag();
        }
        byte[] arr=userInformations.get(position).getUserPic();
        myHolder.userImage.setImageBitmap(BitmapFactory.decodeByteArray(arr,0,arr.length));
        myHolder.name.setText(userInformations.get(position).getUserName());
        myHolder.age.setText(userInformations.get(position).getUserAge());
        myHolder.gender.setText(userInformations.get(position).getUserGender());
        myHolder.email.setText(userInformations.get(position).getUserEmail());

        return row;
    }
}
