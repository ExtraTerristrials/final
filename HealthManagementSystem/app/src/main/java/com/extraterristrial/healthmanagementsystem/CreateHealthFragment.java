package com.extraterristrial.healthmanagementsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.extraterristrial.healthmanagementsystem.databaseschema.HealthDatabase;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.HealthInformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jewel on 12/20/2015.
 */
public class CreateHealthFragment extends Fragment{
    EditText edit_temperature,edit_bloodpressure,edit_hight,edit_weight,edit_bmi,edit_calori;
    Toolbar toolbar;
    int profile_id;
    String date;
    HealthInformation healthInformation;
    HealthDatabase healthDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.create_health_layout, container, false);
        profile_id=getArguments().getInt("profile_id");
        healthInformation=new HealthInformation();
        Date now=Calendar.getInstance().getTime();
        date=new SimpleDateFormat("yyyy-MM-dd").format(now);
        healthDatabase=new HealthDatabase(getContext());
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Add Daily Health");
        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                saveData(profile_id);
                HealthInfoFragment healthInfoFragment = new HealthInfoFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("profile_id", profile_id);
                healthInfoFragment.setArguments(bundle);
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.detail_page_layout, healthInfoFragment);
                ft.commit();
                return true;
            }
        });
        edit_temperature=(EditText)view.findViewById(R.id.edit_temperature);
        edit_bloodpressure=(EditText)view.findViewById(R.id.edit_bp);
        edit_hight=(EditText)view.findViewById(R.id.edit_hight);
        edit_weight=(EditText)view.findViewById(R.id.edit_weight);
        edit_bmi=(EditText)view.findViewById(R.id.edit_bmi);
        edit_calori=(EditText)view.findViewById(R.id.edit_calori);
        try {
            showData(profile_id);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
            edit_calori.setText("Not Set");
            edit_bmi.setText("Not Set");
            edit_weight.setText("Not Set");
            edit_hight.setText("Not Set");
            edit_bloodpressure.setText("Not Set");
            edit_temperature.setText("Not Set");
        }
        return view;
    }

    private void showData(int profile_id) {
        try {
            if (healthDatabase!=null) {
                ArrayList<HealthInformation> infoList = healthDatabase.getHealthData(profile_id);
                if (infoList.size()>0) {
                    edit_bloodpressure.setText(infoList.get(infoList.size() - 1).getBloodPressure());
                    edit_temperature.setText(infoList.get(infoList.size() - 1).getBloodGroup());
                    edit_weight.setText(infoList.get(infoList.size() - 1).getWeight());
                    edit_hight.setText(infoList.get(infoList.size() - 1).getHeight());
                    edit_bmi.setText(infoList.get(infoList.size() - 1).getBmi());
                    edit_calori.setText(infoList.get(infoList.size() - 1).getCalorie());
                }
            }
        }catch (NullPointerException e){

        }
    }

    private void saveData(int profile_id) {
        healthInformation.setBmi(edit_bmi.getText().toString());
        healthInformation.setCalorie(edit_calori.getText().toString());
        healthInformation.setBloodPressure(edit_bloodpressure.getText().toString());
        healthInformation.setHeight(edit_hight.getText().toString());
        if(edit_weight.getText().toString().equals("") || edit_weight.getText().toString().equals(null)){
            healthInformation.setWeight("Unknown");
        }else{
            healthInformation.setWeight(edit_weight.getText().toString());
        }
        healthInformation.setBloodGroup(edit_temperature.getText().toString());
        healthInformation.setUserId(profile_id);
        healthInformation.setDate(date);
        healthDatabase.InsertHealthInfo(healthInformation);
    }
}
