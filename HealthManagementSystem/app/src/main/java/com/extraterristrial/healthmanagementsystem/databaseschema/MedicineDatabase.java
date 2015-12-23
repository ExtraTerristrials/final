package com.extraterristrial.healthmanagementsystem.databaseschema;

public class MedicineDatabase {
    // tables
    /*1*/private static final String MEDICINE_TABLE="medicine";
    /*2*/private static final String SCHEDULE="schedule";
    /*3*/private static final String RELATION1="user_medicine";
    /*4*/private static final String RELATION2="medicine_schedule";

    //Coloumns of  tables
    /*1*/private static final String M_ID="medicine_id";
    /*2*/private static final String M_NAME="medicine_name";
    /*3*/private static final String COURSE="course";
    /*4*/private static final String M_PIC="medicine_pic";
    /*5*/private static final String DOSE="dose_per_day";
    /*6*/private static final String DOSE_COMPLETED="completed_dose";
    /*7*/private static final String M_QUANTITY="quantity";
    /*8*/private static final String FOOD="food_relation";
    /*9*/private static final String TIME="medicine_time";
    /*10*/private static final String USER_ID="user_id";

    //upgrade commands
    protected static final String UPGRADE_MEDICINE_TABLE="DROP TABLE IF EXIST "+MEDICINE_TABLE;
    protected static final String UPGRADE_SCHEDULE_TABLE="DROP TABLE IF EXIST "+SCHEDULE;
    protected static final String UPGRADE_RELATION1_TABLE="DROP TABLE IF EXIST "+RELATION1;
    protected static final String UPGRADE_RELATION2_TABLE="DROP TABLE IF EXIST "+RELATION2;

    //table creation
    protected static final String CREATE_MEDICINE="CREATE TABLE "+MEDICINE_TABLE+"("+M_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +M_NAME+" TEXT, "+M_PIC+" blob, "+COURSE+" TEXT, "+DOSE+" TEXT);";
    protected static final String CREATE_SCHEDULE="CREATE TABLE "+SCHEDULE+"("+TIME+" TEXT, "
            +M_QUANTITY+" TEXT, "+FOOD+" TEXT, PRIMARY KEY("+TIME+"));";
    protected static final String CREATE_RELATION1="CREATE TABLE "+RELATION1+"("+USER_ID+" INTEGER, "
            +M_ID+" INTEGER, "+DOSE_COMPLETED+" TEXT, PRIMARY KEY("+USER_ID+","+M_ID+"));";
    protected static final String CREATE_RELATION2="CREATE TABLE "+RELATION2+"("+M_ID+" INTEGER, "
            +TIME+" TEXT, PRIMARY KEY("+M_ID+","+TIME+"));";

}
