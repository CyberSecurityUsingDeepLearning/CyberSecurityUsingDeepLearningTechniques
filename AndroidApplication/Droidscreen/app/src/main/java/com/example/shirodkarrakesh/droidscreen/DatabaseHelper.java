package com.example.shirodkarrakesh.droidscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.password;
import static android.R.attr.version;
import static android.os.Build.ID;
import static android.provider.Contacts.SettingsColumns.KEY;
import static com.example.shirodkarrakesh.droidscreen.R.id.username;

/**
 * Created by shirodkarrakesh on 2/6/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

     SQLiteDatabase db;

     static final String DATABASE_NAME="ScanSchema.db";
     static final String TABLE1_NAME="user_profile";
     static final String col1_1="ID";
     static final String col1_2="name";
     static final String col1_3="username";
     static final String col1_4="email_address";
     static final String col1_5="password";

     static final String TABLE2_NAME="scan_info";
     static final String col2_1="scan_id";
     static final String col2_2="scan_app_name";
     static final String col2_3="scan_status";
     static final String col2_4="scan_date";
     static final String col2_5="scan_log";

    static final String CreatTable1 = "CREATE TABLE "+ TABLE1_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, " +
            "username TEXT, email_address TEXT, password TEXT );";

    static final String CreatTable2 = "CREATE TABLE "+ TABLE2_NAME +" (scan_id INTEGER PRIMARY KEY AUTOINCREMENT, scan_app_name TEXT, " +
            "scan_status TEXT , scan_date Text, scan_log TEXT);";

    //scan_date INTEGER,

    static final String DropTable1 ="DROP TABLE IF EXIST" + TABLE1_NAME;

    static final String DropTable2 ="DROP TABLE IF EXIST" + TABLE2_NAME;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
        db.execSQL(CreatTable1);
        db.execSQL(CreatTable2);

        this.db = db;
        }
        catch (Exception e)
        {
            System.out.println("Error while creating database" + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL(DropTable1);
        db.execSQL(DropTable2);
        this.onCreate(db);

    }

    //method for inserting data in user tables

    public boolean DataInsert(String name, String username ,String email_address, String password ) {

        db = this.getWritableDatabase();
        ContentValues ContVal = new ContentValues();

        //inserting values

        String CountQuery= "SELECT * FROM " + TABLE1_NAME;
        Cursor CountCursor = db.rawQuery(CountQuery,null);
        int count= CountCursor.getCount();

        ContVal.put(col1_1,count);
        ContVal.put(col1_2,name);
        ContVal.put(col1_3,username);
        ContVal.put(col1_4,email_address);
        ContVal.put(col1_5,password);

       long result=  db.insert(TABLE1_NAME, null, ContVal);

        db.close();

        if (result == -1)

            return false;
        else
            return true;


    }



    //method for inserting data in scan tables

    public boolean ScanInsert(String scan_app_name, String scan_status , String scan_log ) {

        db = this.getWritableDatabase();
        ContentValues ContVal = new ContentValues();

        //inserting values

        String CountQuery= "SELECT * FROM " + TABLE2_NAME;
        Cursor CountCursor = db.rawQuery(CountQuery,null);
        int count= CountCursor.getCount();


       //long date = new Date().getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContVal.put(col2_1,count);
        ContVal.put(col2_2,scan_app_name);
        ContVal.put(col2_3,scan_status);
        ContVal.put(col2_4,dateFormat.format(date));
        ContVal.put(col2_5,scan_log);

        long result=  db.insert(TABLE2_NAME, null, ContVal);

        db.close();

        if (result == -1)

            return false;
        else
            return true;


    }

//function to fetch password from username
    public String getpass (String usr_val)
    {
         db = this.getReadableDatabase();

        Cursor data=db.query(TABLE1_NAME, new String[]{col1_5 },
                col1_3+"=?", new String[]{usr_val}, null, null, null);

        String Result= "NoResult";

        if (data.moveToFirst())
        {
            Result = data.getString(0);


        }

        db.close();

        return Result;
    }



    public boolean getapp (String app_val)
    {
        db = this.getReadableDatabase();

        Cursor data=db.query(TABLE2_NAME, new String[]{col2_2},
                col2_2+"=?", new String[]{app_val}, null, null, null);

        //String Result= "NoResult";

        int count= data.getCount();

        if (count == 0)
        {
            return false;
        }
        else if(count != 0)
        {
            return true;
        }

        db.close();

        return false;
    }

//scan table update
public boolean  ScanUpdate (String scan_app_name, String scan_status , String scan_log)
    {
        db = this.getWritableDatabase();

        ContentValues ContVal = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();


        ContVal.put(col2_3,scan_status);
        ContVal.put(col2_4,dateFormat.format(date));
        ContVal.put(col2_5,scan_log);

        String[] args = new String[]{scan_app_name};

        long result= db.update(TABLE2_NAME, ContVal, col2_2 + "=?",args);

        db.close();

        if (result == -1)

            return false;
        else
            return true;


    }

    //function to fetch data from tables
    public Cursor GetRows()
    {
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select rowid _id,* from "+ TABLE2_NAME +  " ORDER BY "+col2_4 + " DESC", null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }


    //function to fetch data from tables
    public Cursor GetDetailRows(String name)
    {
        db = this.getReadableDatabase();
       Cursor cursor =  db.rawQuery( "select * from "+ TABLE2_NAME +  " WHERE TRIM("+col2_2 + ") = '"+name.trim()+"'",null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }


        db.close();

        return cursor;
    }


}