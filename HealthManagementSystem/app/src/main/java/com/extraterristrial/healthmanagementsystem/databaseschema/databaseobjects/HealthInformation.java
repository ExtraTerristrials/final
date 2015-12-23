package com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects;

/**
 * Created by HP on 12/21/2015.
 */
public class HealthInformation {

    private String bloodGroup;
    private String bloodPressure;
    private String bmi;
    private String height;
    private String weight;
    private String calorie;
    private String date;
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {

        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    private int userId;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public String getBmi() {
        return bmi;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getCalorie() {
        return calorie;
    }

    public int getUserId() {
        return userId;
    }

    public void setBloodGroup(String bloodGroup) {

        this.bloodGroup = bloodGroup;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
