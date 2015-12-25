package com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by Jewel on 12/25/2015.
 */
public class MedicalInformation {
    String name,address,contacts,email,webpage;
    byte[] medicalPic;
    int profile_id;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContacts() {
        return contacts;
    }

    public String getEmail() {
        return email;
    }

    public String getWebpage() {
        return webpage;
    }

    public byte[] getMedicalPic() {
        return medicalPic;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public void setMedicalPic(Bitmap medicalPic) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        if (medicalPic!=null) {
            medicalPic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            this.medicalPic = byteArrayOutputStream.toByteArray();
        }
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }
}
