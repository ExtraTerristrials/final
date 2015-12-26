package com.extraterristrial.healthmanagementsystem;

import android.content.Intent;
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
import android.widget.TextView;

import com.extraterristrial.healthmanagementsystem.databaseschema.HealthDatabase;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.HealthInformation;

import java.util.ArrayList;

public class HealthInfoFragment extends Fragment {
    EditText edit_temperature,edit_bloodpressure,edit_hight,edit_weight,edit_bmi,edit_calori;
    Toolbar toolbar;
    TextView date;
    int profile_id;
    HealthInformation healthInformation;
    HealthDatabase healthDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_info_layout, container, false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        healthDatabase=new HealthDatabase(getContext());
        healthInformation=new HealthInformation();
        date=(TextView)view.findViewById(R.id.date);
        edit_temperature=(EditText)view.findViewById(R.id.edit_temperature);
        edit_bloodpressure=(EditText)view.findViewById(R.id.edit_bloodpressure);
        edit_hight=(EditText)view.findViewById(R.id.edit_hight);
        edit_weight=(EditText)view.findViewById(R.id.edit_weight);
        edit_bmi=(EditText)view.findViewById(R.id.edit_bmi);
        edit_calori=(EditText)view.findViewById(R.id.edit_calori);
        try{
            profile_id=getArguments().getInt("profile_id");
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
                toolbar.setTitle("Health");
                toolbar.inflateMenu(R.menu.add_item_menu);
                toolbar.setNavigationIcon(R.mipmap.back_button);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DashBoardActivity.class);
                startActivity(intent);
            }
        });
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.add_item) {
                            CreateHealthFragment createHealthFragment = new CreateHealthFragment();
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            Bundle bundle = new Bundle();
                            bundle.putInt("profile_id", profile_id);
                            createHealthFragment.setArguments(bundle);
                            FragmentTransaction ft = fm.beginTransaction().replace(R.id.detail_page_layout, createHealthFragment);
                            ft.commit();
                        }
                        return true;
                    }
                });
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
                    date.setText(infoList.get(infoList.size() - 1).getDate());
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            edit_calori.setText("Not Set");
            edit_bmi.setText("Not Set");
            edit_weight.setText("Not Set");
            edit_hight.setText("Not Set");
            edit_bloodpressure.setText("Not Set");
            edit_temperature.setText("Not Set");
        }
    }
}
