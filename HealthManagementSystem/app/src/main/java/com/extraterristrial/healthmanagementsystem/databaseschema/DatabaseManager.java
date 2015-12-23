package com.extraterristrial.healthmanagementsystem.databaseschema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.UserInformation;

import java.util.ArrayList;
import java.util.Currency;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=2;
    public static final String DATABASE_NAME="healthmanagement.db";

    //Coloumns of user table
    /*1*/public static final String ID="user_id";
    /*2*/public static final String NAME="user_name";
    /*3*/public static final String AGE="age";
    /*4*/public static final String PIC="use_pic";
    /*5*/public static final String RELATIONSHIP="relationship_status";
    /*6*/public static final String GENDER="gender";
    /*7*/public static final String PHONE="contact";
    /*8*/public static final String MAIL="e_mail";

    public static final String USER_TABLE="user";
    public static final String UPGRADE_USER_TABLE="DROP TABLE IF EXIST "+USER_TABLE;

    public static final String CREATE_TABLE_USER_INFO="create table "+USER_TABLE+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NAME+" TEXT, "+PIC+" blob, "+AGE+" TEXT, "+RELATIONSHIP+" TEXT, "+GENDER+" TEXT, "+PHONE+" TEXT, "+MAIL+" TEXT);";

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UPGRADE_USER_TABLE);
        onCreate(db);
    }

    public int Insert(UserInformation info){
        ContentValues values=new ContentValues();
        values.put(NAME,info.getUserName());
        values.put(AGE,info.getUserAge());
        values.put(GENDER,info.getUserGender());
        values.put(RELATIONSHIP,info.getUserRelationshipStatus());
        values.put(PIC,info.getUserPic());
        values.put(PHONE,info.getUserPhoneNo());
        values.put(MAIL,info.getUserEmail());
        SQLiteDatabase db=getWritableInstance();
        int status=(int)db.insert(USER_TABLE,null,values);
        db.close();
        return status;
    }
    public ArrayList<UserInformation> getUserList(){
        String sql="select "+NAME+", "+AGE+", "+MAIL+", "+GENDER+", "+PIC+" from "+USER_TABLE+";";
        SQLiteDatabase db=getWritableInstance();
        Cursor resultSet=db.rawQuery(sql, null);
        ArrayList<UserInformation> infoList=new ArrayList<>();
        UserInformation info;

        resultSet.moveToFirst();
        while(!resultSet.isAfterLast()){
            info=new UserInformation();
            info.setUserName(resultSet.getString(0));
            info.setUserAge(resultSet.getString(1));
            info.setUserEmail(resultSet.getString(2));
            info.setUserGender(resultSet.getString(3));

            byte[] arr=resultSet.getBlob(4);
            info.setUserPic(BitmapFactory.decodeByteArray(arr,0,arr.length));
            infoList.add(info);

            resultSet.moveToNext();
        }
        resultSet.close();
        db.close();

        return infoList;
    }
    public SQLiteDatabase getWritableInstance(){
        return getWritableDatabase();
    }
    public void DeleteAll(){
        String sql="delete from "+USER_TABLE;
        SQLiteDatabase db=getWritableInstance();
        db.execSQL(sql);
        db.close();
    }
}
