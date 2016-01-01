package com.extraterristrial.healthmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Jewel on 12/27/2015.
 */
public class VaccinInfoFragment extends Fragment{
    public static int profile_id;
    Toolbar toolbar;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profile_id=getArguments().getInt("profile_id");
        View view = inflater.inflate(R.layout.vaccin_info_layout, container, false);
        listView=(ListView)view.findViewById(R.id.vaccin_schedule_list);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Vaccin");
        toolbar.setNavigationIcon(R.mipmap.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DashBoardActivity.class);
                startActivity(intent);
            }
        });
        toolbar.inflateMenu(R.menu.add_item_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_item: {
                        VaccinItemListFragment vaccinItemListFragment = new VaccinItemListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("profileId", profile_id);
                        vaccinItemListFragment.setArguments(bundle);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction().replace(R.id.detail_page_layout, vaccinItemListFragment);
                        ft.commit();
                    }
                    break;
                }
                return true;
            }
        });
        return view;
    }
}
