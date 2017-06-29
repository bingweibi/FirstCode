package com.example.bbw.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bbw on 2017/6/27.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;

    private class More {

        @SerializedName("txt")
        public String info;
    }
}
