package com.extraterristrial.healthmanagementsystem;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.extraterristrial.healthmanagementsystem.databaseschema.DietDatabase;

public class DietItemFragment extends Fragment {
    private String pageTitle;
    ListView dietList;
    TextView weekday;
    DietDatabase dietDatabase;
    DietListAdapter dietListAdapter;
    int profile;
    public static DietItemFragment newInstance(String page,int profile_id) {
        Bundle args = new Bundle();
        args.putString("page_title", page);
        args.putInt("profile_id",profile_id);
        DietItemFragment fragment = new DietItemFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diet_item_layout, container, false);
        dietList=(ListView)view.findViewById(R.id.diet_list);
        weekday=(TextView)view.findViewById(R.id.diet_weekday);
        setDietData();
        return view;
    }

    private void setDietData() {
        pageTitle = getArguments().getString("page_title");
        profile=getArguments().getInt("profile_id");
        dietDatabase=new DietDatabase(getActivity());
        dietListAdapter=new DietListAdapter(getActivity(),dietDatabase.retriveDietInfo(profile,pageTitle));
        dietList.setAdapter(dietListAdapter);
        weekday.setText(pageTitle);
        dietListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setDietData();
            }
        });
    }
}
