package com.extraterristrial.healthmanagementsystem.databaseschema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.MedicineInformation;
import com.extraterristrial.healthmanagementsystem.medicine.TimeQuantity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MedicineDatabase {

    private DatabaseManager dManager;

    public MedicineDatabase(Context context) {
        dManager=new DatabaseManager(context,null,null,DatabaseManager.DATABASE_VERSION);
    }

    // table
    /*1*/private static final String MEDICINE_TABLE="medicine";

    //Coloumns of  tables
    /*1*/private static final String M_NAME="medicine_name";
    /*2*/private static final String COURSE="course";
    /*3*/private static final String M_PIC="medicine_pic";
    /*4*/private static final String DOSE="dose_per_day";
    /*5*/private static final String DOSE_COMPLETED="completed_dose";
    /*6*/private static final String M_QUANTITY="quantity";
    /*7*/private static final String FOOD="food_relation";
    /*8*/private static final String TIME="medicine_time";
    /*9*/private static final String USER_ID="user_id";

    //upgrade commands
    protected static final String UPGRADE_MEDICINE_TABLE="DROP TABLE IF EXISTS "+MEDICINE_TABLE;


    //table creation
    protected static final String CREATE_MEDICINE="CREATE TABLE "+MEDICINE_TABLE+"("+USER_ID+" INTEGER, "+M_NAME+" TEXT, "+TIME+" TEXT, "+
            COURSE+" TEXT, "+DOSE+" TEXT, "+DOSE_COMPLETED+" TEXT, "+M_QUANTITY+" TEXT, "+FOOD+" TEXT, "+M_PIC+" BLOB, PRIMARY KEY("+USER_ID+
            ","+M_NAME+","+TIME+"));";

    private byte[] getByteArray(Bitmap pic){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public boolean StoreMedicine(MedicineInformation mInfo) {

        SQLiteDatabase db = dManager.getWritableInstance();
        ContentValues values = new ContentValues();

        for(TimeQuantity tQ: mInfo.getSchedule()){

            values.put(USER_ID,mInfo.getProfile_id());
            values.put(M_NAME,mInfo.getMedicineName());
            values.put(TIME,tQ.getTime());
            values.put(M_QUANTITY,tQ.getQuantity());
            values.put(FOOD,tQ.getFoodRelation());
            values.put(DOSE,mInfo.getDosePerDay());
            values.put(DOSE_COMPLETED,mInfo.getCompletedDose());
            values.put(COURSE,mInfo.getCourse());
            values.put(M_PIC,getByteArray(mInfo.getMedicinePic()));

            int status=(int)db.insert(MEDICINE_TABLE,null,values);
            if(status<0){
                db.close();
                return false;
            }

        }
        db.close();
        return true;
    }

    public boolean DeleteUserRecord(MedicineInformation mi){
        SQLiteDatabase db = dManager.getWritableInstance();
        int status=(int)db.delete(MEDICINE_TABLE,USER_ID+" = '"+mi.getProfile_id()+"' and "+M_NAME+" = '"+mi.getMedicineName()+"';",null);
        db.close();
        if(status<0){
            return false;
        }
        return true;
    }
    public boolean updateDoseStatus(MedicineInformation mi){
        SQLiteDatabase db = dManager.getWritableInstance();
        ContentValues values = new ContentValues();
        values.put(DOSE_COMPLETED, mi.getCompletedDose());
        int status=(int)db.update(MEDICINE_TABLE,values,USER_ID+" = '"+mi.getProfile_id()+"' and "+M_NAME+" = '"+mi.getMedicineName()+"';",null);
        if(status<0){
            return false;
        }
        return true;
    }

    public ArrayList<MedicineInformation> getShortDescription(int profileId){
        String sql="SELECT DISTINCT "+M_NAME+","+DOSE+","+DOSE_COMPLETED+","+COURSE+","+M_PIC+" FROM "+MEDICINE_TABLE+" WHERE "+USER_ID+" = '"+profileId+"';";
        SQLiteDatabase db = dManager.getWritableInstance();
        Cursor cursor=db.rawQuery(sql, null);
        ArrayList<MedicineInformation> list=new ArrayList<>();
        MedicineInformation mi;
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            mi=new MedicineInformation();
            mi.setMedicineName(cursor.getString(cursor.getColumnIndex(M_NAME)));
            mi.setDosePerDay(cursor.getString(cursor.getColumnIndex(DOSE)));
            mi.setCompletedDose(cursor.getString(cursor.getColumnIndex(DOSE_COMPLETED)));
            mi.setCourse(cursor.getString(cursor.getColumnIndex(COURSE)));
            byte[] arr=cursor.getBlob(cursor.getColumnIndex(M_PIC));
            mi.setMedicinePic(BitmapFactory.decodeByteArray(arr,0,arr.length));
            list.add(mi);

            cursor.moveToNext();
        }
        db.close();
        cursor.close();
        return list;

    }

    public ArrayList<TimeQuantity> getDetailDescripTion(int profileId,String medicineName){
        String[] coloumns={TIME,M_QUANTITY,FOOD};
        String[] value={String.valueOf(profileId),medicineName};
        TimeQuantity tq;
        ArrayList<TimeQuantity> schedule=new ArrayList<>();
        SQLiteDatabase db = dManager.getWritableInstance();
        Cursor cursor=db.query(MEDICINE_TABLE,coloumns,USER_ID+"=? and "+M_NAME+"=?",value,null,null,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String time=cursor.getString(cursor.getColumnIndex(TIME));
            String quantity=cursor.getString(cursor.getColumnIndex(M_QUANTITY));
            String food=cursor.getString(cursor.getColumnIndex(FOOD));
            tq=new TimeQuantity(time,quantity,food);
            schedule.add(tq);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return schedule;
    }

    public boolean Update(String name,MedicineInformation updated){
        MedicineInformation mi=new MedicineInformation();
        mi.setProfile_id(updated.getProfile_id());
        mi.setMedicineName(name);
        boolean d,r;
        d=DeleteUserRecord(mi);
        r=StoreMedicine(updated);
        if(d && r){
            return true;
        }
        return false;
    }

}
