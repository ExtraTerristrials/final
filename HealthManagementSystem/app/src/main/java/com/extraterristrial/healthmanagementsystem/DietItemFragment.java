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
import android.widget.Toast;

import com.extraterristrial.healthmanagementsystem.databaseschema.DietDatabase;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.DietInformation;

import java.util.ArrayList;

public class DietItemFragment extends Fragment {
    String pageId;
    ListView dietList;
    TextView weekday;
    DietDatabase dietDatabase;
    DietListAdapter dietListAdapter;
    int profile;
    public static DietItemFragment newInstance(String page,int profile_id) {
        Bundle args = new Bundle();
        args.putString("page_id", page);
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
        dietDatabase=new DietDatabase(getActivity());
        pageId=getArguments().getString("page_id");
        profile=getArguments().getInt("profile_id");
        setDietData();
        return view;
    }

    private void setDietData() {
        ArrayList<DietInformation> al=dietDatabase.retriveDietInfo(profile,pageId);
        dietListAdapter=new DietListAdapter(getActivity(),al);
        dietList.setAdapter(dietListAdapter);
        weekday.setText(pageId);
        Toast.makeText(getActivity(),"Size Of List "+al.size(),Toast.LENGTH_SHORT).show();
        dietListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setDietData();
            }
        });
    }
}
