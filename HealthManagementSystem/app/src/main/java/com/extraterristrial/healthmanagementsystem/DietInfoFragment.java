package com.extraterristrial.healthmanagementsystem;

import android.app.DialogFragment;
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

public class DietInfoFragment extends Fragment {
    int profile_id;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profile_id=getArguments().getInt("profile_id");
        View view = inflater.inflate(R.layout.diet_info_layout, container, false);
        ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Diet");
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
                switch (item.getItemId())
                {
                    case R.id.add_item:
                    {
                        CreateDietFragment createDietFragment=new CreateDietFragment();
                        Bundle bundle=new Bundle();
                        bundle.putInt("profileId",profile_id);
                        createDietFragment.setArguments(bundle);
                        FragmentManager fm=getActivity().getSupportFragmentManager();
                        createDietFragment.show(fm,"diet");


                    }break;
                }
                return true;
            }
        });
        TabLayout tab=(TabLayout)view.findViewById(R.id.tab);
        viewPager.setAdapter(new DietPagerAdapter(getActivity().getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
        return view;
    }
}
