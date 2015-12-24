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

/**
 * Created by Jewel on 12/20/2015.
 */
public class HealthInfoFragment extends Fragment {
    EditText edit_temperature,edit_bloodpressure,edit_hight,edit_weight,edit_bmi,edit_calori;
    Toolbar toolbar;
    View temperature,bloodpressure,hight,weight,bmi,calori;
    TextView date;
    int profile_id;
    HealthInformation healthInformation;
    HealthDatabase healthDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_info_layout, container, false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        healthDatabase=new HealthDatabase(getActivity());
        healthInformation=new HealthInformation();
        temperature=view.findViewById(R.id.bloodGroup);
        bloodpressure=view.findViewById(R.id.bloodPressure);
        date=(TextView)view.findViewById(R.id.date);
        bmi=view.findViewById(R.id.bmi);
        hight=view.findViewById(R.id.hight);
        calori=view.findViewById(R.id.calori);
        weight=view.findViewById(R.id.weight);
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
                        Fragment createHealthFragment = new CreateHealthFragment();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        Bundle bundle=new Bundle();
                        bundle.putInt("profile_id",profile_id);
                        createHealthFragment.setArguments(bundle);
                        FragmentTransaction ft = fm.beginTransaction().replace(R.id.detail_page_layout, createHealthFragment);
                        ft.commit();
                        return true;
                    }
                });
        return view;
    }

    private void showData(int profile_id) {
        try {
          //  healthInformation=healthDatabase.getHealthData(profile_id);     //  remove date from method parameter
            edit_bloodpressure.setText(healthInformation.getBloodPressure());
            edit_temperature.setText(healthInformation.getBloodGroup());
            edit_weight.setText(healthInformation.getWeight());
            edit_hight.setText(healthInformation.getHeight());
            edit_bmi.setText(healthInformation.getBmi());
            edit_calori.setText(healthInformation.getCalorie());
            date.setText(healthInformation.getDate());
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
