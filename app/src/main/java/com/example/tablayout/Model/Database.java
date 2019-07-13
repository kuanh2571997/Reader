package com.example.tablayout.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    final String TAG = "ketqua";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    //public void InsertTitle(String Title, String Description,String TitleImg){
    public void InsertTitle(String Title, String Description){
        SQLiteDatabase database = getWritableDatabase();
        //String sql = "INSERT INTO tblTitle VALUES(null, ?, ?)";
        //String sql = "INSERT INTO tblTitle VALUES(null,'"+Title+"','"+Description+"','"+TitleImg+"')";
        String sql = "INSERT INTO tblTitle VALUES(null,'"+Title+"','"+Description+"')";
        Log.d(TAG, "InsertTitle: "+sql);
        QueryData(sql);
//        Log.d(TAG, "InsertTitle: "+sql);
//        Log.d(TAG, "InsertTitle: run");
    }

    public void InsertContent(int idTitle, String Content, String Status){
        //String sql = "INSERT INTO tblContent VALUES(null,"+idTitle+",\""+Content+"\",\""+Status+"\")";
        String sql = "INSERT INTO tblContent VALUES(null,"+idTitle+",'"+Content+"','"+Status+"')";
        Log.d(TAG, "InsertContent: "+sql);
        QueryData(sql);
//        Log.d(TAG, "InsertContent: đã lưu");
    }

    public void InsertHis(String TieuDe, String link, String linkimg, String date){
        String sql = "INSERT INTO tblHistory VALUES(null,'"+TieuDe+"','"+link+"','"+linkimg+"','"+date+"')";
        Log.d(TAG, "InsertContent: "+sql);
        QueryData(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
