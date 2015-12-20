package com.extraterristrial.healthmanagementsystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class DashBoardActivity extends AppCompatActivity {
    private int profile_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DashBoard");
        toolbar.inflateMenu(R.menu.edit_delete_menu);
        try {
            profile_id = getIntent().getExtras().getInt("profile_id");
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.edit_profile:
                    {
                        Fragment createProfileFragment = new CreateProfileFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        Bundle bundle=new Bundle();
                        bundle.putString("origin","edit");
                        bundle.putInt("profile_id",profile_id);
                        createProfileFragment.setArguments(bundle);
                        FragmentTransaction ft = fm.beginTransaction().add(R.id.dash_board_layout, createProfileFragment);
                        ft.commit();
                    }break;
                    case R.id.delete_profile:
                    {
                        //delete profile
                    }break;
                }
                return true;
            }
        });

    }
    public void profiledetail(View view)
    {
        switch (view.getId())
        {
            case R.id.diet_button:
            {
                Fragment dietFragment = new DietInfoFragment();
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putInt("profile_id",profile_id);
                dietFragment.setArguments(bundle);
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.detail_page_layout, dietFragment);
                ft.commit();
            }break;
            case R.id.health_button:
            {

            }break;
            case R.id.medical_button:
            {

            }break;
            case R.id.medicin_button:
            {

            }break;
            case R.id.vaccin_button:
            {

            }break;
            case R.id.doctor_button:
            {

            }break;
        }
    }

}
