package com.extraterristrial.healthmanagementsystem.medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.extraterristrial.healthmanagementsystem.R;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.MedicineInformation;
import java.util.ArrayList;


public class MedicineAdapter extends ArrayAdapter<MedicineInformation> {
    private ArrayList<MedicineInformation> mInfo;
    public MedicineAdapter(Context context, ArrayList<MedicineInformation> mInfo) {
        super(context, R.layout.medicine_listview_row, mInfo);
        this.mInfo=mInfo;
    }

    private class ViewHolder{
        ImageView medicinePic;
        TextView mName,course,completedCourse,dosePerDay;
        ProgressBar medicineBar;
        public ViewHolder(View v){
            medicinePic= (ImageView) v.findViewById(R.id.list_image);
            mName= (TextView) v.findViewById(R.id.list_name);
            completedCourse= (TextView) v.findViewById(R.id.completed_course);
            dosePerDay= (TextView) v.findViewById(R.id.doseOfDay);
            course= (TextView) v.findViewById(R.id.list_course);
            medicineBar=(ProgressBar)v.findViewById(R.id.medicineBar);
        }
    }
    private int getMax(String course,String dpd){
        int c=Integer.parseInt(course);
        int d=Integer.parseInt(dpd);

        return c*d;
    }
    private int getTodaysDose(String cd,String dpd){
        int cD=Integer.parseInt(cd);
        int dPd=Integer.parseInt(dpd);

        if(cD%dPd!=0){
            return cD%dPd;
        }
        return dPd;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder myHolder;

        if(row==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            row=inflater.inflate(R.layout.medicine_listview_row,parent,false);
            myHolder=new ViewHolder(row);
            row.setTag(myHolder);
        }
        else{
            myHolder=(ViewHolder)row.getTag();
        }

        String courseStr=mInfo.get(position).getCourse();
        String doseStr=mInfo.get(position).getDosePerDay();
        String completedDose=mInfo.get(position).getCompletedDose();

        myHolder.medicinePic.setImageBitmap(mInfo.get(position).getMedicinePic());
        if(!courseStr.equals("1")){
            myHolder.course.setText(courseStr+" Days Course");
        }
        else {
            myHolder.course.setText(doseStr+" Dose Every Day");
        }
        myHolder.completedCourse.setText("Completed "+mInfo.get(position).getCompletedDose()+" Day/s");
        myHolder.dosePerDay.setText("Dose No "+getTodaysDose(completedDose,doseStr)+" To Be Completed");
        myHolder.medicineBar.setMax(getMax(courseStr,doseStr));
        return row;
    }
}
