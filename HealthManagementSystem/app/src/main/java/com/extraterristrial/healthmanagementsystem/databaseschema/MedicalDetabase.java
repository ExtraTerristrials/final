package com.extraterristrial.healthmanagementsystem.databaseschema;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.DragEvent;

import com.extraterristrial.healthmanagementsystem.MedicalInfoFragment;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.MedicalInformation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MedicalDetabase {
    private DatabaseManager dManager;

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
            +MEDICAL_PIC+" blob, "+WEBPAGE+" TEXT, "+M_MAIL+" TEXT, "+ADDRESS+" TEXT,"+CONTACT+" TEXT, "+USER_ID+" INTEGER, PRIMARY KEY("
            +NAME+","+USER_ID+"));";

    public MedicalDetabase(Context context) {
        dManager=new DatabaseManager(context,null,null,DatabaseManager.DATABASE_VERSION);
    }
    private byte[] getByteArray(Bitmap pic) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public int InsertMedicalInfo(MedicalInformation mInfo){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dManager.getWritableInstance();

        values.put(NAME,mInfo.getName());
        values.put(ADDRESS,mInfo.getAddress());
        values.put(CONTACT,mInfo.getContacts());
        values.put(WEBPAGE,mInfo.getWebpage());
        values.put(USER_ID,mInfo.getProfile_id());
        values.put(MEDICAL_PIC, getByteArray(mInfo.getMedicalPic()));
        values.put(M_MAIL,mInfo.getEmail());
        int status=(int)db.insert(MEDICAL_TABLE,null,values);
        db.close();
        return status;
    }
    public MedicalInformation getMedicalData(int profile){
        String sql="SELECT * FROM "+MEDICAL_TABLE+" WHERE "+USER_ID+" = '"+profile+"';";
        SQLiteDatabase db=dManager.getWritableInstance();
        Cursor resultSet=db.rawQuery(sql, null);
        MedicalInformation item=new MedicalInformation();

        if(resultSet.moveToFirst()){
            item.setName(resultSet.getString(resultSet.getColumnIndex(NAME)));
            item.setAddress(resultSet.getString(resultSet.getColumnIndex(ADDRESS)));
            item.setContacts(resultSet.getString(resultSet.getColumnIndex(CONTACT)));
            item.setWebpage(resultSet.getString(resultSet.getColumnIndex(WEBPAGE)));
            item.setEmail(resultSet.getString(resultSet.getColumnIndex(M_MAIL)));
            byte[] arr=resultSet.getBlob(resultSet.getColumnIndex(MEDICAL_PIC));
            item.setMedicalPic(BitmapFactory.decodeByteArray(arr, 0, arr.length));
        }


        resultSet.close();
        db.close();

        return item;
    }

}
