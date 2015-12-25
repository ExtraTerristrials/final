package com.extraterristrial.healthmanagementsystem.medicine;

/**
 * Created by HP on 12/20/2015.
 */
public class TimeQuantity {
    private String time;
    private String Quantity;
    private String foodRelation;

    public TimeQuantity(String time, String quantity, String foodRelation) {
        this.time = time;
        Quantity = quantity;
        this.foodRelation = foodRelation;
    }

    public String getTime() {
        return time;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getFoodRelation() {
        return foodRelation;
    }
}
