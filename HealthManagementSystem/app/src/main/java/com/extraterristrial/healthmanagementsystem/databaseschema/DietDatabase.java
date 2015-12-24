package com.extraterristrial.healthmanagementsystem.databaseschema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.DietInformation;


import java.util.ArrayList;

public class DietDatabase {

    private DatabaseManager dManager;
    private Context context;

    //table
    private static final String DIET_TABLE="diet";

    //Coloumns of diet  table
    /*1*/private static final String FOOD_TITLE="title";
    /*2*/private static final String FOOD_ITEMS="items";
    /*3*/private static final String WEEK_DAY="day";
    /*4*/private static final String REMINDER="reminder";
    /*5*/private static final String TIME="time";
    /*6*/private static final String USER_ID="user_id";

    //table creation
      protected static final String CREATE_DIET_TABLE="create table "+DIET_TABLE+"("+USER_ID+" INTEGER, "+WEEK_DAY+" TEXT, "+
            FOOD_TITLE+" TEXT, "+TIME+" TEXT, "+FOOD_ITEMS+" TEXT, "+REMINDER+" TEXT,PRIMARY KEY("+USER_ID+","+WEEK_DAY+","+FOOD_TITLE
            +"));";

    //table upgradation
    protected static final String UPGRADE_DIET_TABLE="DROP TABLE IF EXISTS "+DIET_TABLE;

    public DietDatabase(Context context) {
        this.context=context;
        dManager=new DatabaseManager(context,null,null,DatabaseManager.DATABASE_VERSION);
    }
    public  int InsertDiet(DietInformation info){
        SQLiteDatabase db=dManager.getWritableInstance();
        ContentValues values=new ContentValues();
        values.put(USER_ID,info.getProfileId());
        values.put(WEEK_DAY,info.getWeekday());
        values.put(FOOD_TITLE,info.getFoodTitle());
        values.put(TIME,info.getFoodTime());
        values.put(FOOD_ITEMS,info.getFoodItem());
        values.put(REMINDER,info.getReminder());

        int status=(int)db.insert(DIET_TABLE,null,values);
        db.close();
        return status;
    }
    public ArrayList<DietInformation> retriveDietInfo(int profileId,String weekDay){
        ArrayList<DietInformation> list=new ArrayList<>();
        DietInformation info;
        String sql="select * from "+DIET_TABLE+" where "+USER_ID+" ='"+profileId+"' and "+WEEK_DAY+" = '"+weekDay+"';";
        SQLiteDatabase db=dManager.getWritableInstance();
        Cursor cursor=db.rawQuery(sql,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            try{
                info=new DietInformation();
                info.setFoodItem(cursor.getString(cursor.getColumnIndex(FOOD_ITEMS)));
                info.setFoodTime(cursor.getString(cursor.getColumnIndex(TIME)));
                info.setFoodTitle(cursor.getString(cursor.getColumnIndex(FOOD_TITLE)));
                info.setReminder(cursor.getString(cursor.getColumnIndex(REMINDER)));
                list.add(info);
            }catch(NullPointerException e){
                Toast.makeText(context,"Diet info Retrival throes null pointer exception",Toast.LENGTH_SHORT).show();
            }
            cursor.moveToNext();
        }

        return list;
    }

    //for update i want to know what information is provided
}
