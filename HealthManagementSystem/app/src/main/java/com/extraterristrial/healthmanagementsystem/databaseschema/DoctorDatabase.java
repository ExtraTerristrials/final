package com.extraterristrial.healthmanagementsystem.databaseschema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.DoctorInformation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class DoctorDatabase{
    private DatabaseManager dManager;
    //tables
    private static final String DOCTOR_TABLE="doctor";
    private static final String PHONE_TABLE="contact";

    //Coloumns of tables
    /*1*/private static final String NAME="doctor_name";
    /*2*/private static final String SPECIALITY="speciality";
    /*3*/private static final String ADDRESS="address";
    /*4*/private static final String DOCTOR_PIC="doctor_pic";
    /*5*/private static final String DOCTOR_PHONE="doctor_contact";
    /*6*/private static final String APPOINTMENT="app_date";
    /*7*/private static final String USER_ID="user_id";
    /*8*/private static final String DOCTOR_MAIL="doctor_mail";

    //upgrade commands
    protected static final String UPGRADE_DOCTOR_TABLE="DROP TABLE IF EXISTS "+DOCTOR_TABLE;
    protected static final String UPGRADE_PHONE_TABLE="DROP TABLE IF EXISTS "+PHONE_TABLE;

    //table creations
    protected static final String CREATE_DOCTOR_TABLE="CREATE TABLE "+DOCTOR_TABLE+"("+NAME+" TEXT, "
            +DOCTOR_PIC+" blob, "+DOCTOR_MAIL+" TEXT, "+ADDRESS+" TEXT,"+APPOINTMENT+" TEXT, "+USER_ID+" INTEGER, "
            +SPECIALITY+" TEXT,PRIMARY KEY("
            +NAME+","+USER_ID+"));";
    protected static final String CREATE_PHONE_TABLE="CREATE TABLE "+PHONE_TABLE+"("+NAME+" TEXT, "+DOCTOR_PHONE+" TEXT, PRIMARY KEY("+NAME+
            ","+DOCTOR_PHONE+"));";


    protected DoctorDatabase(Context context) {
        dManager=new DatabaseManager(context,null,null,DatabaseManager.DATABASE_VERSION);
    }

    private byte[] getByteArray(Bitmap pic){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public boolean StoreDoctorInfo(DoctorInformation info){
        SQLiteDatabase db = dManager.getWritableInstance();
        ContentValues values = new ContentValues();

        values.put(NAME,info.getDoctorName());
        values.put(DOCTOR_MAIL,info.getDoctorMail());
        values.put(ADDRESS,info.getDoctorAdress());
        values.put(USER_ID,info.getDoctorUser());
        values.put(SPECIALITY,info.getDoctorSpeciality());
        values.put(DOCTOR_PIC,getByteArray(info.getDoctorPic()));
        values.put(APPOINTMENT,info.getDoctorName());

        int status=(int)db.insert(DOCTOR_TABLE,null,values);
        if(status>=0){
            values = new ContentValues();
            for(String phone:info.getDoctorPhoneList()){
                values.put(NAME, info.getDoctorName());
                values.put(DOCTOR_PHONE, phone);
                int status1=(int)db.insert(PHONE_TABLE,null,values);
                if(status<0){
                    delete(info.getDoctorName());
                    db.close();
                    return false;
                }
            }
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    private void delete(String name){
        String sql="DELETE FROM "+PHONE_TABLE+" WHERE "+NAME+" = '"+name+"';";
        SQLiteDatabase db = dManager.getWritableInstance();
        db.execSQL(sql);
        db.close();
    }
    public ArrayList<String> getDoctorList(int profile){
        String sql="SELECT "+NAME+" FROM "+DOCTOR_TABLE+" WHERE "+USER_ID+" = '"+profile+"';";
        SQLiteDatabase db=dManager.getWritableInstance();
        Cursor resultSet=db.rawQuery(sql, null);
        ArrayList<String> nameList=new ArrayList<>();

        resultSet.moveToFirst();
        while(!resultSet.isAfterLast()){
            nameList.add(resultSet.getString(resultSet.getColumnIndex(NAME)));
            resultSet.moveToNext();
        }
        resultSet.close();
        db.close();

        return nameList;
    }

}
