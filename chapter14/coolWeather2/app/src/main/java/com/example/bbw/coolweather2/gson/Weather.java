package com.example.bbw.coolweather2.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bbw on 2017/8/24.
 */

public class Weather {

    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
