package com.example.arafathossain.icare;

import android.graphics.Bitmap;

/**
 * Created by HP on 1/2/2016.
 */
public class MedicalInformation {
    private  String medicalName;
    private  String medicalWebPage;
    private  String adress;
    private Bitmap medicalPic;
    private  String medicalContact;
    private  String profileNmame;
    private  String medicalEmail;

    public String getMedicalName() {
        return medicalName;
    }

    public String getMedicalWebPage() {
        return medicalWebPage;
    }

    public String getAdress() {
        return adress;
    }

    public Bitmap getMedicalPic() {
        return medicalPic;
    }

    public String getMedicalContact() {
        return medicalContact;
    }

    public String getProfileNmame() {
        return profileNmame;
    }

    public String getMedicalEmail() {
        return medicalEmail;
    }

    public void setMedicalName(String medicalName) {
        this.medicalName = medicalName;
    }

    public void setMedicalWebPage(String medicalWebPage) {
        this.medicalWebPage = medicalWebPage;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setMedicalPic(Bitmap medicalPic) {
        this.medicalPic = medicalPic;
    }

    public void setMedicalContact(String medicalContact) {
        this.medicalContact = medicalContact;
    }

    public void setProfileNmame(String profileNmame) {
        this.profileNmame = profileNmame;
    }

    public void setMedicalEmail(String medicalEmail) {
        this.medicalEmail = medicalEmail;
    }
}
