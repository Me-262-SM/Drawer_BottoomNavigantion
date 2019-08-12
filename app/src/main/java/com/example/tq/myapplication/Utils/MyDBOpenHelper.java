package com.example.tq.myapplication.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    private static MyDBOpenHelper instance;


    private MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static MyDBOpenHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        if(instance==null){
            instance=new MyDBOpenHelper(context, name, factory, version);
        }
        return instance;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table mission(" +
                "_id integer primary key autoincrement," +
                "info varchar(20)," +
                "status integer," +
                "prisoner text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新用
    }
}
