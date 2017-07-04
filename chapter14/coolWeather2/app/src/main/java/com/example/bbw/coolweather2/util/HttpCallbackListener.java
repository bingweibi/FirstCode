package com.example.bbw.coolweather2.util;

/**
 * Created by bbw on 2017/7/3.
 */

public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);
}
