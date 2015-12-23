package com.extraterristrial.healthmanagementsystem.databaseschema;

import android.content.Context;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.DoctorInformation;


public class DoctorDatabase{
    private DatabaseManager dManager;
    //tables
    private static final String DOCTOR_TABLE="doctor";
    private static final String USER_DOCTOR_RELATIONSHIP_TABLE="user_doctor";

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
    protected static final String UPGRADE_DOCTOR_TABLE="DROP TABLE IF EXIST "+DOCTOR_TABLE;
    protected static final String UPGRADE_USER_DOCTOR_RELATIONSHIP_TABLE="DROP TABLE IF EXIST "+USER_DOCTOR_RELATIONSHIP_TABLE;

    //table creations
    protected static final String CREATE_DOCTOR_TABLE="CREATE TABLE "+DOCTOR_TABLE+"("+NAME+" TEXT, "
            +DOCTOR_PHONE+" TEXT, "+DOCTOR_PIC+" blob, "+DOCTOR_MAIL+" TEXT, "+ADDRESS+" TEXT,"+SPECIALITY+" TEXT,PRIMARY KEY("
            +NAME+","+DOCTOR_PHONE+"));";
    protected static final String CREATE_USER_DOCTOR_RELATIONSHIP_TABLE="CREATE TABLE "+USER_DOCTOR_RELATIONSHIP_TABLE+"("+NAME+" TEXT, "
            +DOCTOR_PHONE+" TEXT, "+USER_ID+" INTEGER, "+APPOINTMENT+" TEXT,PRIMARY KEY("
            +NAME+","+DOCTOR_PHONE+","+USER_ID+"));";

    protected DoctorDatabase(Context context) {
        dManager=new DatabaseManager(context,null,null,DatabaseManager.DATABASE_VERSION);
    }

    public int StoreDoctorInfo(DoctorInformation info){
        return 0;
    }

}
