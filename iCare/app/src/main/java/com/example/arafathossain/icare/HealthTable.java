package com.example.arafathossain.icare;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by HP on 1/2/2016.
 */
public class HealthTable {

    private DatabaseHelper dbHelper=ApplicationMain.getDatabase();

    private static final String HEALTH_TABLE="health";

    //Coloumns of user table
    /*2*/private static final String BLOOD_PRESSURE="blood_pressure";
    /*3*/private static final String HEART_RATE="heart_rate";
    /*4*/private static final String SLEEP="sleep";
    /*5*/private static final String BODY_MASS_INDEX="bmi";
    /*6*/private static final String CALORIE="calorie";
    /*7*/private static final String PROFILE_NAME="user_id";
    /*8*/private static final String DATE="date";
    /*9*/private static final String TEMPERATURE="temperature";

    //upgrade commands
    protected static final String UPGRADE_HEALTH_TABLE="DROP TABLE IF EXISTS "+HEALTH_TABLE;

    //table creation
    protected static final String CREATE_HEALTH_TABLE="CREATE TABLE "+HEALTH_TABLE+"("+PROFILE_NAME+" TEXT, "+DATE+" TEXT, "
            +BLOOD_PRESSURE+" TEXT, "+HEART_RATE+" TEXT, "+SLEEP+" TEXT,"+BODY_MASS_INDEX+
            " TEXT, "+TEMPERATURE+" TEXT, "+CALORIE+" TEXT, PRIMARY KEY("+PROFILE_NAME+"));";
    public int InsertHealthInfo(HealthInformation info){

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(PROFILE_NAME,info.getProfileName());
        values.put(BLOOD_PRESSURE,info.getBloodPressure());
        values.put(HEART_RATE,info.getHeart_Rate());
        values.put(SLEEP,info.getSleep());
        values.put(BODY_MASS_INDEX,info.getBmi());
        values.put(CALORIE,info.getCalorie());
        values.put(TEMPERATURE,info.getTemperature());

        int status=(int)db.insert(HEALTH_TABLE,null,values);
        db.close();
        return status;
    }


}
