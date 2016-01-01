package com.extraterristrial.healthmanagementsystem.medicine;


import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.extraterristrial.healthmanagementsystem.R;
import com.extraterristrial.healthmanagementsystem.databaseschema.MedicineDatabase;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.MedicineInformation;

import java.util.ArrayList;

public class MedicineProfileFragment extends Fragment{

    EditText name,course;
    CheckBox courseChecker;
    Button scheduleButton;
    ListView scheduleList;
    ImageButton mPic,camera;
    Toolbar toolbar;

    ArrayAdapter<String> adapter;
    ArrayList<String> listElement=new ArrayList<>();
    ArrayList<TimeQuantity> databaseElement=new ArrayList<>();
    Bitmap medicinePic;

    protected int listPos=-1;

    private String mName,mCourse,completedCourse,dosePerDay,indicator,previousName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profileView=inflater.inflate(R.layout.medication_profile_fragment, container, false);
        name=(EditText)profileView.findViewById(R.id.medicineName);
        course=(EditText)profileView.findViewById(R.id.medicine_course);
        courseChecker=(CheckBox)profileView.findViewById(R.id.course_checker);
        scheduleButton= (Button) profileView.findViewById(R.id.schedule_button);
        scheduleList=(ListView)profileView.findViewById(R.id.schedule_list);
        camera=(ImageButton)profileView.findViewById(R.id.camera);
        mPic=(ImageButton)profileView.findViewById(R.id.medicine_pic);

        toolbar = (Toolbar)profileView.findViewById(R.id.mToolbar);
        toolbar.setTitle("Medication");
        toolbar.inflateMenu(R.menu.save_menu);
        course.setVisibility(View.INVISIBLE);

        try{
            Bundle mBundle=getArguments();
            indicator=mBundle.getString("from");
            if(indicator.equals("list")){
                DetailInfo(mBundle.getString("name"),mBundle.getString("course"));
            }
        }catch(NullPointerException e){}

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 3);
                }
            }
        });
        courseChecker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    course.setVisibility(View.VISIBLE);
                } else {
                    course.setVisibility(View.INVISIBLE);
                }
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineScheduleDialog dialog = new MedicineScheduleDialog();
                dialog.setTargetFragment(MedicineProfileFragment.this, 4);
                dialog.show(getFragmentManager(), "ScheduleDialog");
            }
        });
        adapter=new ArrayAdapter<String>(getActivity(),R.layout.time_quantity_row,R.id.schedule_text,listElement);
        scheduleList.setAdapter(adapter);
        scheduleList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicineScheduleDialog dialog = new MedicineScheduleDialog();
                dialog.setTargetFragment(MedicineProfileFragment.this, 4);
                Bundle args=new Bundle();
                listPos=position;
                TimeQuantity tQ=databaseElement.get(position);
                int itemPosition;
                if(tQ.getFoodRelation().equals("Before meal")){
                    itemPosition=0;
                }else if(tQ.getFoodRelation().equals("After meal")){
                    itemPosition=1;
                }else{
                    itemPosition=2;
                }
                args.putString("Time", tQ.getTime());
                args.putString("Quantity", tQ.getQuantity());
                args.putInt("FoodRelation", itemPosition);

                dialog.setArguments(args);
                dialog.show(getFragmentManager(),"ScheduleDialog");
            }
        });
        ToolbarAction();
        return profileView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        if(requestCode==3 && resultCode==getActivity().RESULT_OK){
            medicinePic= (Bitmap) extras.get("data");
            mPic.setImageBitmap(medicinePic);
        }
        if(requestCode==4 && listPos<0){
            TimeQuantity tq=new TimeQuantity(extras.getString("Time"),extras.getString("Quantity"),extras.getString("FoodRelation"));
            listElement.add(tq.getTime()+" "+tq.getQuantity()+" "+tq.getFoodRelation());
            adapter.notifyDataSetChanged();
            databaseElement.add(tq);
        }
        if(requestCode==4 && listPos>=0){
            TimeQuantity tq=new TimeQuantity(extras.getString("Time"),extras.getString("Quantity"),extras.getString("FoodRelation"));
            listElement.set(listPos,tq.getTime()+" "+tq.getQuantity()+" "+tq.getFoodRelation());
            adapter.notifyDataSetChanged();
            databaseElement.set(listPos,tq);
            listPos=-1;
        }
    }

    private void ToolbarAction(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MedicineInformation mi=new MedicineInformation();

                if(item.getItemId()==R.id.save_profile && getValue()){
                    mi.setMedicineName(mName);
                    mi.setMedicinePic(medicinePic);
                    mi.setCourse(mCourse);
                    mi.setCompletedDose(completedCourse);
                    mi.setDosePerDay(dosePerDay);
                    mi.setSchedule(databaseElement);
                    mi.setProfile_id(MedicineListFragment.profile_id);
                    if(indicator.equals("add")){
                        Save(mi);
                    }
                    if(indicator.equals("list")){
                        UpdatePreviousData(mi);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private boolean getValue(){
        mName=name.getText().toString();
        mCourse=course.getText().toString();
        Toast.makeText(getActivity(),"Name = "+mName,Toast.LENGTH_SHORT).show();
        completedCourse="0";
        dosePerDay=String.valueOf(databaseElement.size());

        if(mName.equals(null) || mName.equals("")){
            return false;
        }
        if(dosePerDay.equals("0")){
            return false;
        }
        if(!courseChecker.isChecked()||mCourse.equals("") || mCourse.equals(null)){
            mCourse="1";
        }
        if(medicinePic==null){
            medicinePic= BitmapFactory.decodeResource(getResources(), R.mipmap.noimage);
        }
        return true;
    }
    private void DetailInfo(String medicineName,String medicineCourse){
        name.setText(medicineName);
        previousName=medicineName;
        mPic.setImageBitmap(MedicineListFragment.selectedImage);
        MedicineListFragment.selectedImage=null;
        if(!medicineCourse.equals("1")){
            courseChecker.setChecked(true);
            course.setVisibility(View.VISIBLE);
            course.setText(medicineCourse);
        }
        databaseElement=new MedicineDatabase(getActivity()).getDetailDescripTion(MedicineListFragment.profile_id,medicineName);
        Toast.makeText(getActivity(),"size "+databaseElement.size(),Toast.LENGTH_SHORT).show();

        for(TimeQuantity tq:databaseElement){
            listElement.add(tq.getTime()+" "+tq.getQuantity()+" "+tq.getFoodRelation());
        }
        adapter.notifyDataSetChanged();

    }
    private void Save(MedicineInformation mi){
        if(new MedicineDatabase(getActivity()).StoreMedicine(mi)){
            Bundle mBundle=new Bundle();
            mBundle.putString("from","Profile");
            MedicineListFragment f=new MedicineListFragment();
            f.setArguments(mBundle);
            getFragmentManager().beginTransaction().replace(R.id.detail_page_layout, f).commit();
        }
    }
    private void UpdatePreviousData(MedicineInformation mi) {
        if(new MedicineDatabase(getActivity()).Update(previousName,mi)){
            Toast.makeText(getActivity(),"Data Updated",Toast.LENGTH_SHORT).show();
            Bundle mBundle=new Bundle();
            mBundle.putString("from","Profile");
            MedicineListFragment f=new MedicineListFragment();
            f.setArguments(mBundle);
            getFragmentManager().beginTransaction().replace(R.id.detail_page_layout, f).commit();
        }
    }
}
