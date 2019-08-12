package com.example.tq.myapplication.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tq.myapplication.Model.Mission;

public class Table_Mission {
    private SQLiteDatabase database;
    private static String table = "mission";
    private static Table_Mission instance;

    private Table_Mission(SQLiteDatabase database) {
        this.database = database;
        this.database.execSQL("create table if not exists "+table+"(" +
                "_id integer primary key autoincrement," +
                "info varchar(20)," +
                "status integer," +
                "prisoner text)");
    }

    public static String getTableName() {
        return table;
    }

    /**
     * 单一实例
     * @param database
     * @return
     */
    public static Table_Mission getTable(SQLiteDatabase database){
        if(instance==null){
            instance=new Table_Mission(database);
        }
        return instance;
    }

    public void add(String info,Integer status,byte[] prisoner){
        database.execSQL("insert into "+table+"(info,status,prisoner) values(?,?,?)",
                new Object[]{info,status.toString(),prisoner});
    }

    public void delete(Integer id){
        database.execSQL("delete from "+table+" where _id=?",
                new String[]{id.toString()});
    }

    public void update(Integer status,Integer id){
        database.execSQL("update "+table+" set status=? where _id=?",
                new String[]{status.toString(),id.toString()});
    }

    public Cursor query(Integer id){
        Cursor cursor = database.rawQuery("select * from "+table+" WHERE _id= ?",
                new String[]{id.toString()});

        return cursor;
    }

    public Cursor query(String sql,String[] selectionArgs){
        Cursor cursor = database.rawQuery(sql,selectionArgs);

        return cursor;
    }

    public Cursor queryAll(){
        Cursor cursor = database.rawQuery("select * from "+table,null);
        return cursor;
    }

    public void deleteAll(){
        database.execSQL("delete from "+table);
    }

    public int getCount(){
        Cursor cursor = queryAll();
        return cursor.getCount();
    }
}
