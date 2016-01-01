package com.extraterristrial.healthmanagementsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Jewel on 12/27/2015.
 */
public class VaccinItemListFragment extends Fragment {
    ListView vaccinlist;
    private String[] diseaseNames;
    static int profile;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.vaccin_item_list_layout, container, false);
        profile=getArguments().getInt("profile_id");
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Vaccin List");
        toolbar.setNavigationIcon(R.mipmap.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment vaccinFragment = new VaccinInfoFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putInt("profile_id",profile);
                vaccinFragment.setArguments(bundle);
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.detail_page_layout, vaccinFragment);
                ft.commit();
            }
        });
        diseaseNames = getResources().getStringArray(R.array.diseaseNames);
        vaccinlist=(ListView)view.findViewById(R.id.vaccin_item_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2, diseaseNames);
        vaccinlist.setAdapter(adapter);
        vaccinlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CreateVaccinFragment createVaccinFragment = new CreateVaccinFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("profileId", profile);
                bundle.putInt("itemPosition",position);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                createVaccinFragment.show(fm,"vaccin");
            }
        });
        return view;
    }
}
