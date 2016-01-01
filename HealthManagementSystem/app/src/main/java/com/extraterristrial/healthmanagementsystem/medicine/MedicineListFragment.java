package com.extraterristrial.healthmanagementsystem.medicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.extraterristrial.healthmanagementsystem.R;
import com.extraterristrial.healthmanagementsystem.databaseschema.MedicineDatabase;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.MedicineInformation;

import java.util.ArrayList;

public class MedicineListFragment extends Fragment{
    ListView medicineList;
    MedicineAdapter adapter;
    MedicineDatabase mStorage;
    Toolbar lToolbar;
    ArrayList<MedicineInformation> mList;

    protected static int profile_id;
    protected static Bitmap selectedImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragmentView=inflater.inflate(R.layout.medicine_list_fragment, container, false);
        medicineList=(ListView)listFragmentView.findViewById(R.id.medicine_list);
        mStorage=new MedicineDatabase(getActivity());
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
        ListViewAction();

        return listFragmentView;
    }

    private void ToolBarAction(){
        lToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.add_item){
                    MedicineProfileFragment f=new MedicineProfileFragment();
                    Bundle args=new Bundle();
                    args.putString("from","add");
                    f.setArguments(args);
                    getFragmentManager().beginTransaction().replace(R.id.detail_page_layout, f).commit();
                }
                return false;
            }
        });
    }
    private void ListViewAction(){
        medicineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicineProfileFragment f=new MedicineProfileFragment();
                Bundle args=new Bundle();
                args.putString("name",mList.get(position).getMedicineName());
                args.putString("course",mList.get(position).getCourse());
                args.putString("from","list");
                selectedImage=mList.get(position).getMedicinePic();
                f.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.detail_page_layout,f).commit();
            }
        });

        medicineList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog(mList.get(position).getMedicineName(),position);
                return true;
            }
        });
    }

    public void AlertDialog(String name, final int listPos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Html.fromHtml("<font color='#996600'>Dou You Want To Delete This Medicine?</font>"));
        final String mName=name;
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MedicineInformation mi=new MedicineInformation();
                mi.setMedicineName(mName);
                mi.setProfile_id(profile_id);
                if(new MedicineDatabase(getActivity()).DeleteUserRecord(mi)){
                    mList.remove(listPos);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
