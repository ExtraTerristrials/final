package com.extraterristrial.healthmanagementsystem.medicine;

/**
 * Created by HP on 12/20/2015.
 */
public class TimeQuantity {
    private String time;
    private String Quantity;

    public TimeQuantity(String time, String quantity) {
        this.time = time;
        Quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public String getQuantity() {
        return Quantity;
    }
}
