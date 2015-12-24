package com.extraterristrial.healthmanagementsystem.medicine;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.extraterristrial.healthmanagementsystem.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MedicineProfileFragment extends Fragment{

    EditText name,course,quantity;
    CheckBox courseChecker;
    Button timePicker;
    ListView timeQuantityList;
    Spinner spinner;

    ArrayAdapter<String> adapter;
    ArrayList<String> listElement=new ArrayList<>();
    ArrayList<TimeQuantity> databaseElement=new ArrayList<>();

    protected int hour,minutes,am_pmIdentifier,listPosition=-1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profileView=inflater.inflate(R.layout.medication_profile_fragment,container,false);

        name=(EditText)profileView.findViewById(R.id.medicine_name);
        course=(EditText)profileView.findViewById(R.id.medicine_course);
        quantity=(EditText)profileView.findViewById(R.id.quantity);
        timeQuantityList= (ListView) profileView.findViewById(R.id.time_quantity_list);
        timePicker= (Button) profileView.findViewById(R.id.timePicker);
        courseChecker=(CheckBox)profileView.findViewById(R.id.course_checker);
        spinner= (Spinner) profileView.findViewById(R.id.medicine_spinner);

        course.setVisibility(View.INVISIBLE);

        ArrayAdapter<CharSequence> medicineSpinneradapter=ArrayAdapter.createFromResource(getContext(), R.array.general_medicine_rule, R.layout.spinner_item_layout);
        medicineSpinneradapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner.setAdapter(medicineSpinneradapter);

        adapter=new ArrayAdapter<String>(getActivity(),R.layout.time_quantity_row,R.id.schedule_text,listElement);
        timeQuantityList.setAdapter(adapter);

        timeQuantityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPosition = position;
                quantity.setText(databaseElement.get(listPosition).getQuantity());

            }
        });
        timeQuantityList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), tListener, hour, minutes, false).show();
            }
        });

        courseChecker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    course.setVisibility(View.VISIBLE);
                }
            }
        });

        return profileView;
    }

    protected String getTime(){
        String am_pm[]={"AM","PM"};
        if(hour==0){
            return String.format("%02d : %02d "+am_pm[am_pmIdentifier],12,minutes);
        }
        return String.format("%02d : %02d "+am_pm[am_pmIdentifier],hour%12,minutes);
    }
    protected TimePickerDialog.OnTimeSetListener tListener= new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour=hourOfDay;
            minutes=minute;
            Calendar time=Calendar.getInstance();
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            am_pmIdentifier=time.get(Calendar.AM_PM);
            String listString;
            if(initDatabaseElement()){
                listString="Time : "+databaseElement.get(databaseElement.size()-1).getTime()+" Quantity : "+databaseElement.get(databaseElement.size()-1).getQuantity();
                listElement.add(listString);
                adapter.notifyDataSetChanged();
            }else if(listPosition>=0){
                listString="Time : "+databaseElement.get(listPosition).getTime()+" Quantity : "+databaseElement.get(listPosition).getQuantity();
                listElement.set(listPosition, listString);
                adapter.notifyDataSetChanged();
                listPosition=-1;
            }

        }
    };

    private boolean initDatabaseElement(){
        String q=quantity.getText().toString();
        String t=getTime();

        if(!q.equals("") && !t.equals("") && listPosition<0){

            databaseElement.add(new TimeQuantity(t,q));
            quantity.setText("");
            return true;
        }

        if(!q.equals("") && !t.equals("") && listPosition>=0){
            databaseElement.set(listPosition,new TimeQuantity(t,q));
            quantity.setText("");
            return false;
        }

        return false;
    }
}
