package com.extraterristrial.healthmanagementsystem.databaseschema;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.HealthInformation;

import java.util.ArrayList;

public class HealthDatabase  {

    private DatabaseManager dManager;
    //table
   private static final String HEALTH_TABLE="health";

    //Coloumns of user table
    /*1*/private static final String BLOOD_GROUP="blood_group";
    /*2*/private static final String BLOOD_PRESSURE="blood_pressure";
    /*3*/private static final String HEIGHT="height";
    /*4*/private static final String WEIGHT="weight";
    /*5*/private static final String BODY_MASS_INDEX="bmi";
    /*6*/private static final String CALORIE="calorie";
    /*7*/private static final String USER_ID="user_id";
    /*8*/private static final String DATE="date";
    /*9*/private static final String TEMPERATURE="temperature";

    //upgrade commands
    protected static final String UPGRADE_HEALTH_TABLE="DROP TABLE IF EXISTS "+HEALTH_TABLE;

    //table creation
    protected static final String CREATE_HEALTH_TABLE="CREATE TABLE "+HEALTH_TABLE+"("+USER_ID+" INTEGER, "+DATE+" TEXT, "
            +BLOOD_GROUP+" TEXT, "+BLOOD_PRESSURE+" TEXT, "+HEIGHT+" TEXT, "+WEIGHT+" TEXT,"+BODY_MASS_INDEX+" TEXT, "+TEMPERATURE+" TEXT, "+CALORIE+" TEXT, PRIMARY KEY("+DATE+","+USER_ID+"));";

    protected HealthDatabase(Context context) {
        dManager=new DatabaseManager(context,null,null,DatabaseManager.DATABASE_VERSION);
    }
    public int InsertHealthInfo(HealthInformation info){

        SQLiteDatabase db=dManager.getWritableInstance();
        ContentValues values=new ContentValues();
        values.put(USER_ID,info.getUserId());
        values.put(BLOOD_GROUP,info.getBloodGroup());
        values.put(BLOOD_PRESSURE,info.getBloodPressure());
        values.put(HEIGHT,info.getHeight());
        values.put(WEIGHT,info.getWeight());
        values.put(BODY_MASS_INDEX,info.getBmi());
        values.put(CALORIE,info.getCalorie());
        values.put(TEMPERATURE,info.getTemperature());

        int status=(int)db.insert(HEALTH_TABLE,null,values);
        db.close();
        return status;
    }
    public HealthInformation getHealthData(int userID,String date){

       // ArrayList<HealthInformation> hList=new ArrayList<>();
        HealthInformation hInfo=null;
        String sql="select * from "+HEALTH_TABLE+" where "+USER_ID+" = '"+userID+"' and "+DATE+" = '"+date+"';";
        SQLiteDatabase db=dManager.getWritableInstance();
        Cursor cursor=db.rawQuery(sql,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            hInfo=new HealthInformation();
            hInfo.setBloodGroup(cursor.getString(cursor.getColumnIndex(BLOOD_GROUP)));
            hInfo.setBloodPressure(cursor.getString(cursor.getColumnIndex(BLOOD_PRESSURE)));
            hInfo.setBmi(cursor.getString(cursor.getColumnIndex(BODY_MASS_INDEX)));
            hInfo.setCalorie(cursor.getString(cursor.getColumnIndex(CALORIE)));
            hInfo.setHeight(cursor.getString(cursor.getColumnIndex(HEIGHT)));
            hInfo.setWeight(cursor.getString(cursor.getColumnIndex(WEIGHT)));
            hInfo.setTemperature(cursor.getString(cursor.getColumnIndex(TEMPERATURE)));

            //hList.add(hInfo);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return hInfo;
    }

    public int UpdateHealthData(HealthInformation info){
        ContentValues values=new ContentValues();
        values.put(BLOOD_PRESSURE,info.getBloodPressure());
        values.put(BODY_MASS_INDEX,info.getBmi());
        values.put(HEIGHT,info.getHeight());
        values.put(WEIGHT,info.getWeight());
        values.put(CALORIE,info.getCalorie());

        SQLiteDatabase db=dManager.getWritableInstance();
        int affectedRows=db.update(HEALTH_TABLE,values,USER_ID+" = "+info.getUserId()+" and "+DATE+" = "+info.getDate(),null);
        db.close();
        return affectedRows;
    }
}
