package com.extraterristrial.healthmanagementsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.VaccinInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jewel on 12/27/2015.
 */
public class CreateVaccinFragment extends DialogFragment implements View.OnClickListener {
    TextView dn, vn,vdd;
    LinearLayout dl;
    CheckedTextView reminder;
    RadioGroup group;
    String time;
    int position;
    String[] diseasName;
    String[] vaccinName;
    String[] dose;
    static int profile_id;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.create_vaccin_layout, null);
        View dnView = view.findViewById(R.id.dn);
        profile_id=getArguments().getInt("profileId");
        position=getArguments().getInt("itemPosition");
        diseasName=getResources().getStringArray(R.array.diseaseNames);
        vaccinName=getResources().getStringArray(R.array.vaccineList);
        dose=getResources().getStringArray(R.array.doseMonths);
        View vnView = view.findViewById(R.id.vn);
        dnView.setBackgroundColor(Color.GRAY);
        vnView.setBackgroundColor(Color.GRAY);
        dn = (TextView) view.findViewById(R.id.diseaseName);
        vn = (TextView) view.findViewById(R.id.vaccineName);
        vdd = (TextView) view.findViewById(R.id.vaccineDate);
        dl = (LinearLayout) view.findViewById(R.id.vaccineDoses);
        reminder = (CheckedTextView) view.findViewById(R.id.reminder);
        reminder.setOnClickListener(this);
        view.findViewById(R.id.saveVaccineSchedule).setOnClickListener(this);
        view.findViewById(R.id.vdd).setOnClickListener(this);
        setValue();

        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }

    private void setValue() {
        dn.setText(diseasName[position]);
        vn.setText(vaccinName[position]);
        String[] doses = dose[position].split(",");
        group = new RadioGroup(getActivity());
        group.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        group.setOrientation(LinearLayout.VERTICAL);
        dl.addView(group);
        dl.invalidate();
        for (int i = 0; i < doses.length; i++) {
            RadioButton button = new RadioButton(getActivity());
            button.setId(i);
            button.setText(doses[i]);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setPadding(5, 5, 5, 5);
            group.addView(button);
            group.invalidate();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reminder:
                if (reminder.isChecked()) {
                    reminder.setChecked(false);
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                } else {
                    reminder.setChecked(true);
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                    showTimePicker();
                }
                break;
            case R.id.saveVaccineSchedule:
                createSchedule();
                break;
            case R.id.vdd:
                try {
                    showDatePicker();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    private void showDatePicker() throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        final DatePicker datePicker = new DatePicker(getActivity());
        if (vdd.getText()!=null&&!vdd.getText().toString().equalsIgnoreCase("not set")){
            datePicker.getCalendarView().setDate(format.parse(vdd.getText().toString()).getTime());
        }
        datePicker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(datePicker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(datePicker.getCalendarView().getDate());

                vdd.setText(format.format(calendar.getTime()));
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    private void createSchedule() {
        if (vdd.getText()==null||vdd.getText().toString().equalsIgnoreCase("not set")){
            Toast.makeText(getActivity(), "Date can not be empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (group.getCheckedRadioButtonId()==-1){
            Toast.makeText(getActivity(),"Please select a dose",Toast.LENGTH_LONG).show();
            return;
        }
        VaccinInformation detail = new VaccinInformation();
        detail.setDiseaseName(dn.getText().toString());
        detail.setVaccineName(vn.getText().toString());
        detail.setDate(vdd.getText().toString()+" at "+time);
        detail.setDoseNo(group.getCheckedRadioButtonId()+"");
        detail.setReminder(reminder.isChecked()?"yes":"no");
        detail.setStatus("pending");
        detail.setProfileId(profile_id);
            Toast.makeText(getActivity(),"Schedule creation complete",Toast.LENGTH_LONG).show();
            dismiss();
        //save in database
    }

    private void showTimePicker() {
        final TimePicker timePicker = new TimePicker(getActivity());
        if (time!=null){
            String[] times = time.split(":");
            timePicker.setCurrentHour(Integer.parseInt(times[0]));
            timePicker.setCurrentMinute(Integer.parseInt(times[1]));
        }
        timePicker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(timePicker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                time = timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                reminder.setChecked(false);
                reminder.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
            }
        });
        builder.create().show();
    }
}
