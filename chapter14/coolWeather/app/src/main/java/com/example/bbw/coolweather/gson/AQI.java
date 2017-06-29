package com.example.bbw.coolweather.gson;

/**
 * Created by bbw on 2017/6/27.
 */

public class AQI {

    public AQICity city;

    private class AQICity {
        public String aqi;
        public String pm25;
    }
}
