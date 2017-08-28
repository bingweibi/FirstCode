package com.example.bbw.coolweather2.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.bbw.coolweather2.db.City;
import com.example.bbw.coolweather2.db.County;
import com.example.bbw.coolweather2.db.Province;
import com.example.bbw.coolweather2.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * json格式数据解析和处理
 * Created by bbw on 2017/7/3.
 */

public class Utility {

    static String TAG = "测试";

    //解析和处理服务器返回的省级数据,response:http请求的响应
    public static boolean handleProvinceResponse(String response){

        //响应非空
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvince = new JSONArray(response);//首先获取到所有的province
                for (int i = 0;i < allProvince.length();i++){
                    JSONObject provinceObject = allProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));//获取名
                    province.setProvinceCode(provinceObject.getInt("id"));//获取id
                    province.save();
                    Log.d(TAG," 成功");
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析和处理服务器返回的市级数据
    public static boolean handleCitiesResponse(String response,int provinceId){

        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析和处理服务器返回的县级数据
    public static boolean handleCountiesResponse(String response, int cityId){

        if (! TextUtils.isEmpty(response)){
            Log.d("utility response",response);
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i=0;i < allCounties.length();i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //将返回的JSON数据解析成Weather实体类
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
