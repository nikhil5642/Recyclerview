package com.example.nikhil.recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by nikhil on 22/5/17.
 */

public class dbhandler extends SQLiteOpenHelper {

    private static final int Database_version=1;
    private static final String Database_name="data";
    private static final String table_name="earthquake";

    private static final String Column_Range="range";
    private static final String Column_Location="location";
    private static final String Column_time="time";
    private static final String Column_url="url";
    private static final String Column_lat="lat";
    private static final String Column_lon="lon";


    private ArrayList<object> loaded_data;

    public dbhandler(Context context) {
        super(context, Database_name, null, Database_version);

          }

    public void getdata_for_sqlite(ArrayList<object> loaded_data){
        this.loaded_data=loaded_data;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table="create table if not exists "+table_name
                +"("+Column_Range+" text, "
                +Column_Location+" text, "
                +Column_time+" text, "
               +Column_url+" text,"
                +Column_lat+" text, "
                +Column_lon+" text, "
                +");";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+table_name);
    onCreate(db);
    }

    public void insert_data(String range0,String location0,String time0,String url0){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=getWritableDatabase();

        values.put(Column_Range,range0);
        values.put(Column_Location,location0);
        values.put(Column_time,time0);
        values.put(Column_url,url0);

        db.insert(table_name,null,values);

    }

    public ArrayList<object> databasetoArray(){
        ArrayList<object> mydata=new ArrayList<object>();

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+table_name,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            final object newdata=new object(cursor.getString(cursor.getColumnIndex(Column_Range)),
                    cursor.getString(cursor.getColumnIndex(Column_Location)),
                    cursor.getString(cursor.getColumnIndex(Column_url)),
                    cursor.getInt((int)cursor.getColumnIndex(Column_time)),
                    cursor.getInt(cursor.getColumnIndex(Column_url)),
                    cursor.getInt(cursor.getColumnIndex(Column_url)));
      mydata.add(newdata);
            cursor.moveToNext();
        }
        return mydata;
    }

    public void arraylisttodatabase(){
        int n=0;

        while (loaded_data.get(n) !=null){
            object obj=loaded_data.get(n);

            insert_data(obj.getRange(),obj.getLocation(),obj.getDetail(),obj.getUrl());
        }
    }
}
