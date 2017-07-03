package com.example.bbw.coolweather2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bbw.coolweather2.model.City;
import com.example.bbw.coolweather2.model.County;
import com.example.bbw.coolweather2.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * 将一些常用的数据库操作封装起来，方便使用
 * Created by bbw on 2017/7/3.
 */

public class CoolWeatherDB {

    String TAG = "CoolWeatherDB";

    //数据库名
    public static final String DB_NAME = "cool_weather";

    //数据库版本
    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    //构造方法私有化
    private CoolWeatherDB(Context context){

        //创建数据库
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        Log.i(TAG,"dbHelper create success");
        db = dbHelper.getWritableDatabase();
        Log.i(TAG,"db create success");
    }

    //getInstance()为了获取CoolWeatherDB的实例,保证全局范围内只有一个CoolWeatherDB的实例
    public synchronized static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    //将province实例存储到数据库
    public void saveProvince(Province province){
        if (province != null){
            ContentValues values = new ContentValues();//键值对存储
            values.put("province_name",province.getProvinceName());//获得省份的名称
            values.put("province_code",province.getProvinceCode());//获得省份的代码
            db.insert("Province",null,values);//保存在数据库里面
        }
    }

    //从数据库中读取全国所有省份的数据,返回province类型的数据
    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);//遍历数据库，结果保存在游标cursor中
        if (cursor.moveToFirst()){//把游标放在最前面
            do{
                Province province = new Province();//new一个province实例
                //从游标中读取id,name,code
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            }while (cursor.moveToNext());//移动游标的位置
        }
        if (cursor != null){
            //关闭游标
            cursor.close();
        }
        return list;
    }

    //将City实例存储到数据库
    public void saveCity(City city){
        if (city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }

    //从数据库中读取某省下所有城市的信息
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    //将County实例存入数据库
    public void saveCounty(County county){
        if (county != null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }
    }

    //从数据库中读取某城市下所有县级的信息
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                Log.d(TAG,cursor.getString(cursor.getColumnIndex("county_name"))+"hell");
                Log.d(TAG,cursor.getString(cursor.getColumnIndex("county_code"))+"hell");
                county.setCityId(cityId);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

}
