package com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects;


import android.graphics.Bitmap;

import java.util.ArrayList;

public class DoctorInformation {
    private String doctorName;
    private ArrayList<String> doctorPhoneList;
    private String doctorAdress;
    private String doctorMail;
    private String doctorSpeciality;
    private Bitmap doctorPic;
    private int doctorUser;

    public void setDoctorPhoneList(ArrayList<String> doctorPhoneList) {
        this.doctorPhoneList = doctorPhoneList;
    }

    public ArrayList<String> getDoctorPhoneList() {
        return doctorPhoneList;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDoctorAdress(String doctorAdress) {
        this.doctorAdress = doctorAdress;
    }

    public void setDoctorMail(String doctorMail) {
        this.doctorMail = doctorMail;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        this.doctorSpeciality = doctorSpeciality;
    }

    public void setDoctorPic(Bitmap doctorPic) {
        this.doctorPic = doctorPic;
    }

    public void setDoctorUser(int doctorUser) {
        this.doctorUser = doctorUser;
    }

    public String getDoctorName() {
        return doctorName;
    }
    public String getDoctorAdress() {
        return doctorAdress;
    }

    public String getDoctorMail() {
        return doctorMail;
    }

    public String getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public Bitmap getDoctorPic() {
        return doctorPic;
    }

    public int getDoctorUser() {
        return doctorUser;
    }
}
