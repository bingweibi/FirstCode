package com.example.bbw.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bbw on 2017/6/27.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

    private class Comfort {

        @SerializedName("txt")
        public String info;
    }

    private class CarWash {

        @SerializedName("txt")
        public String info;
    }

    private class Sport {

        @SerializedName("txt")
        public String info;
    }
}
