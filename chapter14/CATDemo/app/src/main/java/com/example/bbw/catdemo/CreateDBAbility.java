package com.example.bbw.catdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bbw on 2017/8/20.
 */

public class CreateDBAbility extends SQLiteOpenHelper {

    public static final String CREATE_ABILITY="create table Ability ("
            +"id integer primary key autoincrement, "
            +"discrimination text, "
            +"difficult text, "
            +"result integer)";

    private Context mContext;

    public CreateDBAbility(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ABILITY);
        Log.d("测试 ","ability表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
