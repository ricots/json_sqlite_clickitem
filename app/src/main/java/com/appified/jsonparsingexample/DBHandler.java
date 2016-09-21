package com.appified.jsonparsingexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by PP on 6/14/2015.
 */
public class DBHandler extends SQLiteOpenHelper implements KampusListener {
    //http://www.appifiedtech.net/2015/06/19/android-json-parsing-with-sqlite-example/
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CityDatabase.db";
    private static final String TABLE_NAME = "city_table";
    private static final String TABLE_NAME_JUR = "tb_jurusan";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "_name";
    private static final String KEY_STATE = "_state";
    private static final String KEY_DESCRIPTION = "_description";
    private static final String KEY_LOGO = "_logo";
    private static final String KEY_JURUSAN = "_jurusan";

    String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_NAME+" TEXT,"+KEY_STATE+" TEXT,"+KEY_DESCRIPTION+" TEXT,"+KEY_LOGO+" TEXT)";
    String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    String CREATE_TABLE_JUR = "CREATE TABLE "+TABLE_NAME_JUR+" ("+KEY_STATE+" TEXT,"+KEY_JURUSAN+" TEXT)";
    String DROP_TABLE_jur = "DROP TABLE IF EXISTS "+TABLE_NAME_JUR;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_JUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_TABLE_jur);
        onCreate(db);
    }

    @Override
    public void addCity(Kampus kampus) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, kampus.getName());
            values.put(KEY_STATE, kampus.getState());
            values.put(KEY_DESCRIPTION, kampus.getDescription());
            values.put(KEY_LOGO, kampus.getLogo());
            db.insert(TABLE_NAME, null, values);
            db.close();
        }catch (Exception e){
            Log.e("problem",e+"");
        }
    }

    @Override
    public void addjur(Jurusan jurnya) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        try{
            ContentValues jur = new ContentValues();
            jur.put(KEY_STATE, jurnya.getIdkampus());
            jur.put(KEY_JURUSAN,jurnya.getJurusan());
            db1.insert(TABLE_NAME_JUR, null, jur);
            db1.close();
        }catch (Exception e){
            Log.e("problem",e+"");
        }
    }

    @Override
    public ArrayList<Kampus> getAllCity() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Kampus> kampusList = null;
        try{
            kampusList = new ArrayList<Kampus>();
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    Kampus kampus = new Kampus();
                    kampus.setId(cursor.getInt(0));
                    kampus.setName(cursor.getString(1));
                    kampus.setState(cursor.getString(2));
                    kampus.setDescription(cursor.getString(3));
                    kampus.setLogo(cursor.getString(4));
                    kampusList.add(kampus);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }


        return kampusList;
    }

    @Override
    public ArrayList<Jurusan> getAlljur() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        ArrayList<Jurusan> jurursan_list = null;
        try{
            jurursan_list = new ArrayList<Jurusan>();
            String QUERY1 = "SELECT * FROM "+TABLE_NAME_JUR;
            Cursor cursor1 = db1.rawQuery(QUERY1, null);
            if(!cursor1.isLast())
            {
                while (cursor1.moveToNext())
                {
                    Jurusan jur = new Jurusan();
                    jur.setIdkampus(cursor1.getString(0));
                    jur.setJurusan(cursor1.getString(1));
                    jurursan_list.add(jur);
                }
            }
            db1.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }


        return jurursan_list;
    }

    @Override
    public int getCityCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return 0;
    }

    @Override
    public int getjur() {
        int num1 = 0;
        SQLiteDatabase db1 = this.getReadableDatabase();
        try{
            String QUERY1 = "SELECT * FROM "+TABLE_NAME_JUR;
            Cursor cursor1 = db1.rawQuery(QUERY1, null);
            num1 = cursor1.getCount();
            db1.close();
            return num1;
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return 0;
    }
}
