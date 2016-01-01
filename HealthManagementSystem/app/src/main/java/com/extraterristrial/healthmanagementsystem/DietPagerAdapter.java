package com.extraterristrial.healthmanagementsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DietPagerAdapter extends FragmentStatePagerAdapter{

    int PAGE_COUNT;
    int profile_id;

    public DietPagerAdapter(FragmentManager fm, int profile_id,int pageCount) {
        super(fm);
        this.profile_id = profile_id;
        this.PAGE_COUNT=pageCount;
    }
    static final String[] weekDay={"Friday","Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday"};
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position)
        {
            case 0:{
                fragment=DietItemFragment.newInstance(weekDay[position],profile_id);
                break;
            }
            case 1:{
                fragment=DietItemFragment.newInstance(weekDay[position],profile_id);
                break;
            }
            case 2:{
                fragment=DietItemFragment.newInstance(weekDay[position],profile_id);
                break;
            }
            case 3:{
                fragment=DietItemFragment.newInstance(weekDay[position],profile_id);
                break;
            }
            case 4:{
                fragment=DietItemFragment.newInstance(weekDay[position],profile_id);
                break;
            }
            case 5:{
                fragment=DietItemFragment.newInstance(weekDay[position],profile_id);
                break;
            }
            case 6:{
                fragment=DietItemFragment.newInstance(weekDay[position],profile_id);
                break;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
