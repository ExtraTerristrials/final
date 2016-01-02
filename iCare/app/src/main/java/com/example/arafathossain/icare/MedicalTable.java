package com.example.arafathossain.icare;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by HP on 1/2/2016.
 */
public class MedicalTable {

    private DatabaseHelper dbHelper=ApplicationMain.getDatabase();

    //Table
    private static final String MEDICAL_TABLE="medical_info";

    /*1*/private static final String NAME="medical_name";
    /*2*/private static final String WEBPAGE="url";
    /*3*/private static final String ADDRESS="address";
    /*4*/private static final String MEDICAL_PIC="medical_pic";
    /*5*/private static final String CONTACT="contact";
    /*6*/private static final String USER_ID="user_id";
    /*7*/private static final String M_MAIL="mail";

    protected static final String UPGRADE_MEDICAL_TABLE="DROP TABLE IF EXISTS "+MEDICAL_TABLE;
    protected static final String CREATE_MEDICAL_TABLE="CREATE TABLE "+MEDICAL_TABLE+"("+NAME+" TEXT, "
            +MEDICAL_PIC+" blob, "+WEBPAGE+" TEXT, "+M_MAIL+" TEXT, "+ADDRESS+" TEXT,"+CONTACT+" TEXT, "+USER_ID+" TEXT, PRIMARY KEY("
            +NAME+","+USER_ID+"));";

    private byte[] getByteArray(Bitmap pic) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public int InsertMedicalInfo(MedicalInformation mInfo){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        values.put(NAME,mInfo.getMedicalName());
        values.put(ADDRESS,mInfo.getAdress());
        values.put(CONTACT,mInfo.getMedicalContact());
        values.put(WEBPAGE,mInfo.getMedicalWebPage());
        values.put(USER_ID,mInfo.getProfileNmame());
        values.put(MEDICAL_PIC, getByteArray(mInfo.getMedicalPic()));
        values.put(M_MAIL,mInfo.getMedicalEmail());
        int status=(int)db.insert(MEDICAL_TABLE,null,values);
        db.close();
        return status;
    }

    public MedicalInformation getMedicalData(String profileName){
        String sql="SELECT * FROM "+MEDICAL_TABLE+" WHERE "+USER_ID+" = '"+profileName+"';";
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor resultSet=db.rawQuery(sql, null);
        MedicalInformation item=new MedicalInformation();

        if(resultSet.moveToFirst()){
            item.setMedicalName(resultSet.getString(resultSet.getColumnIndex(NAME)));
            item.setAdress(resultSet.getString(resultSet.getColumnIndex(ADDRESS)));
            item.setMedicalContact(resultSet.getString(resultSet.getColumnIndex(CONTACT)));
            item.setMedicalWebPage(resultSet.getString(resultSet.getColumnIndex(WEBPAGE)));
            item.setMedicalEmail(resultSet.getString(resultSet.getColumnIndex(M_MAIL)));
            byte[] arr=resultSet.getBlob(resultSet.getColumnIndex(MEDICAL_PIC));
            item.setMedicalPic(BitmapFactory.decodeByteArray(arr, 0, arr.length));
        }

        resultSet.close();
        db.close();

        return item;
    }

    public boolean DeleteMedicalTable(MedicalInformation mInfo){

        SQLiteDatabase db =dbHelper.getWritableDatabase();
        int status=(int)db.delete(MEDICAL_TABLE,USER_ID+" = '"+mInfo.getProfileNmame()+"' and "+NAME+" = '"+mInfo.getMedicalName()+"';",null);
        if(status<0){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean UpdateMedicalTable(MedicalInformation mInfo){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        int status=(int)db.delete(MEDICAL_TABLE,USER_ID+" = '"+mInfo.getProfileNmame()+"';",null);
        if(status>=0){
            values.put(NAME,mInfo.getMedicalName());
            values.put(ADDRESS,mInfo.getMedicalName());
            values.put(CONTACT,mInfo.getMedicalName());
            values.put(WEBPAGE,mInfo.getMedicalName());
            values.put(USER_ID,mInfo.getMedicalName());
            values.put(M_MAIL,mInfo.getMedicalName());
            values.put(MEDICAL_PIC, getByteArray(mInfo.getMedicalPic()));
        }
        status=(int)db.insert(MEDICAL_TABLE,null,values);
        if(status>=0){
            db.close();
            return true;
        }
        db.close();
        return false;
    }

}
