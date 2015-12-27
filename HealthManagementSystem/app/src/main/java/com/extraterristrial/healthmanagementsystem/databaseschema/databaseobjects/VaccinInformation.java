package com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects;

/**
 * Created by Jewel on 12/27/2015.
 */
public class VaccinInformation {
    private String diseaseName;
    private String vaccineName;
    private String doseNo;
    private String date;
    private String reminder;
    private String status;
    private int profileId;

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDoseNo() {
        return doseNo;
    }

    public void setDoseNo(String doseNo) {
        this.doseNo = doseNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
