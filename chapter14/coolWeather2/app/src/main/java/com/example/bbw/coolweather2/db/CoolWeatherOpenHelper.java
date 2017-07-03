package com.example.bbw.coolweather2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 创建表
 * Created by bbw on 2017/7/3.
 */

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

    String TAG = "CoolWeatherOpenHelper";

    //Province建表语句
    public static final String CREATE_PROVINCE = "create table Province("
            +"id integer primary key autoincrement,"//自增加主键
            +"province_name text,"//省名
            +"province_code text)";//省级代号

    //City建表语句
    public static final String CREATE_CITY = "create table City("
            +"id integer primary key autoincrement,"//自增加主键
            +"city_name text,"//城市名
            +"city_code text,"//城市代号
            +"province_id integer)";//City表关联Province表的外键

    //county建表语句
    public static final String CREATE_COUNTY = "create table County("
            +"id integer primary key autoincrement,"//自增加主键
            +"county_name text,"//县级名称
            +"county_code text,"//县级代号
            +"city_id integer)";//County表关联City表的外键

    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //执行建表语句
        sqLiteDatabase.execSQL(CREATE_PROVINCE);
        Log.i(TAG,"province created");
        sqLiteDatabase.execSQL(CREATE_CITY);
        Log.i(TAG,"city created");
        sqLiteDatabase.execSQL(CREATE_COUNTY);
        Log.i(TAG,"county created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
