package com.extraterristrial.healthmanagementsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DietPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 7;
    int profile_id;

    public DietPagerAdapter(FragmentManager fm, int profile_id) {
        super(fm);
        this.profile_id = profile_id;
    }

    private String tabTitles[] = new String[] { "Friday", "Saturday", "Sunday","Monday","Tuesday","Wednesday","Thursday" };

    @Override
    public Fragment getItem(int position) {
        return DietItemFragment.newInstance(tabTitles[position],profile_id);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
