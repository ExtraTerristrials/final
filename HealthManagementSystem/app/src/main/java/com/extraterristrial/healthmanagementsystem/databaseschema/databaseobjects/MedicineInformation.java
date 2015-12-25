package com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects;


import android.graphics.Bitmap;

import com.extraterristrial.healthmanagementsystem.medicine.TimeQuantity;

import java.util.ArrayList;

public class MedicineInformation {

    private String medicineName;
    private String course;
    private String dosePerDay;
    private Bitmap medicinePic;
    private String completedDose;
    private ArrayList<TimeQuantity> schedule;
    private int profile_id;

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {

        this.profile_id = profile_id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getCourse() {
        return course;
    }

    public String getDosePerDay() {
        return dosePerDay;
    }

    public Bitmap getMedicinePic() {
        return medicinePic;
    }

    public String getCompletedDose() {
        return completedDose;
    }

    public ArrayList<TimeQuantity> getSchedule() {
        return schedule;
    }

    public void setMedicineName(String medicineName) {

        this.medicineName = medicineName;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setDosePerDay(String dosePerDay) {
        this.dosePerDay = dosePerDay;
    }

    public void setMedicinePic(Bitmap medicinePic) {
        this.medicinePic = medicinePic;
    }

    public void setCompletedDose(String completedDose) {
        this.completedDose = completedDose;
    }

    public void setSchedule(ArrayList<TimeQuantity> schedule) {
        this.schedule = schedule;
    }
}
