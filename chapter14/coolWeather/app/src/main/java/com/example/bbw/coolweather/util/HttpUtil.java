package com.example.bbw.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by bbw on 2017/6/27.
 */

public class HttpUtil {

    public static void sendOkHtppRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
