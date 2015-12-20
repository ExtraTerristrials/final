package com.extraterristrial.healthmanagementsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DietItemFragment extends Fragment {
    private String pageTitle;

    public static DietItemFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString("page_title", page);
        DietItemFragment fragment = new DietItemFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTitle = getArguments().getString("page_title");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diet_item_layout, container, false);
        return view;
    }
}
