package com.extraterristrial.healthmanagementsystem.medicine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.extraterristrial.healthmanagementsystem.DashBoardActivity;
import com.extraterristrial.healthmanagementsystem.R;
import com.extraterristrial.healthmanagementsystem.databaseschema.MedicineDatabase;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.MedicineInformation;

import java.util.ArrayList;

public class MedicineListFragment extends Fragment{
    ListView medicineList;
    MedicineAdapter adapter;
    MedicineDatabase mStorage;
    Toolbar lToolbar;

    protected static int profile_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragmentView=inflater.inflate(R.layout.medicine_list_fragment, container, false);
        medicineList=(ListView)listFragmentView.findViewById(R.id.medicine_list);
        mStorage=new MedicineDatabase(getActivity());
        ArrayList<MedicineInformation> mList=new ArrayList<>();
        lToolbar=(Toolbar)listFragmentView.findViewById(R.id.lToolbar);
        lToolbar.inflateMenu(R.menu.add_item_menu);
        lToolbar.setTitle("Medication");
        lToolbar.setNavigationIcon(R.mipmap.back_button);
        lToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(MedicineListFragment.this).commit();
            }
        });

        Bundle b=getArguments();
        if(b.getString("from").equals("DashBoard")){
            profile_id=b.getInt("profile_id");
        }
        mList=mStorage.getShortDescription(profile_id);
        adapter=new MedicineAdapter(getActivity(),mList);
        medicineList.setAdapter(adapter);
        
        ToolBarAction();

        return listFragmentView;
    }

    private void ToolBarAction(){
        lToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.add_item){
                    MedicineProfileFragment f=new MedicineProfileFragment();
                    getFragmentManager().beginTransaction().replace(R.id.detail_page_layout, f).commit();
                }
                return false;
            }
        });
    }

}
