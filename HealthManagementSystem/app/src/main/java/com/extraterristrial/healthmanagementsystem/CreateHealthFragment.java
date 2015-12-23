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

/**
 * Created by Jewel on 12/20/2015.
 */
public class CreateHealthFragment extends Fragment implements View.OnClickListener {
    EditText edit_bloodgroup,edit_bloodpressure,edit_hight,edit_weight,edit_bmi,edit_calori;
    Toolbar toolbar;
    View bloodgroup,bloodpressure,hight,weight,bmi,calori;
    int profile_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.health_info_layout,container,false);
        profile_id=getArguments().getInt("profile_id");
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Health");
        toolbar.inflateMenu(R.menu.save_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                saveData(profile_id);
                Fragment healthInfoFragment = new HealthInfoFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("profile_id", profile_id);
                healthInfoFragment.setArguments(bundle);
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.detail_page_layout, healthInfoFragment);
                ft.commit();
                return true;
            }
        });
        bloodgroup=view.findViewById(R.id.bloodGroup);
        bloodpressure=view.findViewById(R.id.bloodPressure);
        bmi=view.findViewById(R.id.bmi);
        hight=view.findViewById(R.id.hight);
        calori=view.findViewById(R.id.calori);
        weight=view.findViewById(R.id.weight);
        edit_bloodgroup=(EditText)view.findViewById(R.id.edit_bloodgroup);
        edit_bloodpressure=(EditText)view.findViewById(R.id.edit_bloodpressure);
        edit_hight=(EditText)view.findViewById(R.id.edit_hight);
        edit_weight=(EditText)view.findViewById(R.id.edit_weight);
        edit_bmi=(EditText)view.findViewById(R.id.edit_bmi);
        edit_calori=(EditText)view.findViewById(R.id.edit_calori);
        bloodgroup.setFocusable(true);
        bloodgroup.setClickable(true);
        bloodpressure.setFocusable(true);
        bloodpressure.setClickable(true);
        bmi.setFocusable(true);
        bmi.setClickable(true);
        weight.setFocusable(true);
        weight.setClickable(true);
        hight.setFocusable(true);
        hight.setClickable(true);
        calori.setFocusable(true);
        calori.setClickable(true);
        edit_bloodgroup.setClickable(true);
        edit_bloodgroup.setFocusable(true);
        edit_bloodpressure.setClickable(true);
        edit_bloodpressure.setFocusable(true);
        edit_bmi.setClickable(true);
        edit_bmi.setFocusable(true);
        edit_calori.setClickable(true);
        edit_calori.setFocusable(true);
        edit_hight.setClickable(true);
        edit_hight.setFocusable(true);
        edit_weight.setClickable(true);
        edit_weight.setFocusable(true);
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
            edit_bloodgroup.setText("Not Set");
        }
        return view;
    }

    private void showData(int profile_id) {

    }

    private void saveData(int profile_id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bloodGroup:
            {

            }break;
            case R.id.bloodPressure:
            {

            }break;
            case R.id.bmi:
            {

            }break;
            case R.id.hight:
            {

            }break;
            case R.id.weight:
            {

            }break;
            case R.id.calori:
            {

            }break;
        }
    }
}
