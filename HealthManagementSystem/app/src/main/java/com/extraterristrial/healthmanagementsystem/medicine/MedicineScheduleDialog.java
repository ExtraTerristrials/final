package com.extraterristrial.healthmanagementsystem.medicine;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.extraterristrial.healthmanagementsystem.R;

import java.util.Calendar;


public class MedicineScheduleDialog extends DialogFragment {

    Spinner unit,foodRelation;
    EditText quantity;
    Button timePicker;
    private int hour,minutes,am_pmIdentifier;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView=inflater.inflate(R.layout.medicine_schedule_dialog, container, false);

        quantity=(EditText)dialogView.findViewById(R.id.quantity);
        timePicker=(Button)dialogView.findViewById(R.id.time_picker);
        unit=(Spinner)dialogView.findViewById(R.id.unit);
        foodRelation=(Spinner)dialogView.findViewById(R.id.meal_relation);

        try{
            Bundle b=getArguments();
            quantity.setText(b.getString("Quantity"));
            timePicker.setText(b.getString("Time"));
            foodRelation.setSelection(b.getInt("FoodRelation"));
        }catch(NullPointerException e){}

        ArrayAdapter<CharSequence> medicineSpinneradapter= ArrayAdapter.createFromResource(getActivity(), R.array.general_medicine_rule, R.layout.spinner_item_layout);
        medicineSpinneradapter.setDropDownViewResource(R.layout.spinner_item_layout);
        foodRelation.setAdapter(medicineSpinneradapter);

        ArrayAdapter<CharSequence> unitAdapter= ArrayAdapter.createFromResource(getActivity(), R.array.quantity_unit, R.layout.spinner_item_layout);
        unitAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        unit.setAdapter(unitAdapter);

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(),tListener,hour,minutes,false).show();
            }
        });

        return dialogView;
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
            Calendar time = Calendar.getInstance();
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            am_pmIdentifier=time.get(Calendar.AM_PM);
            Intent i=new Intent();
            i.putExtra("Time",getTime());
            i.putExtra("Quantity",Clarification(quantity.getText().toString())+" "+unit.getSelectedItem().toString());
            i.putExtra("FoodRelation",FoodClarifier(foodRelation.getSelectedItem().toString()));
            getTargetFragment().onActivityResult(getTargetRequestCode(), 0, i);
            getDialog().dismiss();
        }
    };
    private String Clarification(String s){
        int i=s.indexOf(" ");
        if(i>0){
            return s.substring(0,i);
        }
        if(s.equals("") || s.equals(null)){
            s="Quantity Not Provided";
        }
        return s;
    }
    private String FoodClarifier(String s){
        if(s.equals("None of The Above")){
            return  "";
        }
        return s;
    }
}
