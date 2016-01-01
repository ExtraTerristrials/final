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

public class DietInfoFragment extends Fragment implements ViewPager.OnPageChangeListener{
    public static int profile_id;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profile_id=getArguments().getInt("profile_id");
        View view = inflater.inflate(R.layout.diet_info_layout, container, false);
      final ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewPager);
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
                switch (item.getItemId()) {
                    case R.id.add_item: {
                        CreateDietFragment createDietFragment = new CreateDietFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("profileId", profile_id);
                        createDietFragment.setArguments(bundle);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        createDietFragment.show(fm, "diet");
                    }
                    break;
                }
                return true;
            }
        });
        TabLayout tab=(TabLayout)view.findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Friday"));
        tab.addTab(tab.newTab().setText("Saturday"));
        tab.addTab(tab.newTab().setText("Sunday"));
        tab.addTab(tab.newTab().setText("Monday"));
        tab.addTab(tab.newTab().setText("Tuesday"));
        tab.addTab(tab.newTab().setText("Wednesday"));
        tab.addTab(tab.newTab().setText("Thursday"));
        final DietPagerAdapter adapter=new DietPagerAdapter(getActivity().getSupportFragmentManager(),profile_id,tab.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
