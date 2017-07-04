package com.example.bbw.coolweather2.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.example.bbw.coolweather2.db.CoolWeatherDB;
import com.example.bbw.coolweather2.model.City;
import com.example.bbw.coolweather2.model.County;
import com.example.bbw.coolweather2.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

/**
 * xml格式数据解析和处理
 * Created by bbw on 2017/7/3.
 */

public class Utility {

    static String TAG = "Utility";

    //解析和处理服务器返回的省级数据,response:http请求的响应
    public synchronized static boolean handleProvinceResponse(CoolWeatherDB coolWeatherDB,String response){

        //响应非空
        if (!TextUtils.isEmpty(response)){
            Log.d("utility response", response);
            String[] allProvinces = response.split(",");//分割逗号
            if (allProvinces != null && allProvinces.length > 0){
                for (String p :allProvinces){
                    String[] array = p.split("\\|");//分割单竖线
                    Province province = new Province();
                    province.setProvinceCode(array[0]);//array[0]是代号
                    province.setProvinceName(array[1]);//array[1]是省名
                    //将解析的数据存储到Province表中
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    //解析和处理服务器返回的市级数据
    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            Log.d("Utility response",response);
            String[] allcities = response.split(",");
            if (allcities != null && allcities.length > 0){
                for (String c : allcities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    //解析出来的数据存储到city表中
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    //解析和处理服务器返回的县级数据
    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,String response, int cityId){
        if (! TextUtils.isEmpty(response)){
            Log.d("utility response",response);
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0){
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    //解析出来的数据存储在county表中
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }return false;
    }

    //解析和处理服务器返回的JSON数据，并将解析出来的数据存储到本地
    public static void handlerWeatherResponse(Context context,String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //将服务器返回的天气信息存储到sharedPreferences中
    @TargetApi(Build.VERSION_CODES.N)
    private static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime) {

        SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyy年m月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("weather_code",weatherCode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_Desp",weatherDesp);
        editor.putString("publish_time",publishTime);
        editor.putString("current_date",simpleDateFormate.format(new Date()));
        editor.commit();
    }
}
