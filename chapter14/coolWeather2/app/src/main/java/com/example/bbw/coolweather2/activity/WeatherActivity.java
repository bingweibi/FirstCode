package com.example.bbw.coolweather2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbw.coolweather2.R;
import com.example.bbw.coolweather2.service.AutoUpdateService;
import com.example.bbw.coolweather2.util.HttpCallbackListener;
import com.example.bbw.coolweather2.util.HttpUtil;
import com.example.bbw.coolweather2.util.Utility;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "WeatherActivity";

    //记录上次按下返回键的系统时间
    private long lastBackTime = 0;
    //记录当前按下返回键的系统时间
    private long currentBckeTime = 0;

    private LinearLayout weatherInfoLayout;

    //显示城市名
    private TextView cityNameText;
    //显示发布时间
    private TextView publishText;
    //显示天气描述
    private TextView weatherdespText;
    //显示温度1
    private TextView temp1Text;
    //显示温度2
    private TextView temp2Text;
    //显示当前时间
    private TextView currentDateText;

    //选择城市
    private Button switchCity;
    //刷新城市
    private Button refreshWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);

        //初始化各控件
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherdespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
        Log.d(TAG,"hello");
        String countyCode = getIntent().getStringExtra("county_code");//从Intent中取出县级代号
        Log.d(TAG,"...." + countyCode + "....");
        if (!countyCode.isEmpty()){
            //有县级代号时就去查询天气
            publishText.setText("同步中");
            weatherInfoLayout.setVisibility(View.GONE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else {
            //没有县级代码就直接显示本地天气
            showWeather();
        }
    }

    //从sharedPreferences文件中读取存储的天气信息，并显示到界面上
    private void showWeather() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(sharedPreferences.getString("city_name",""));
        temp1Text.setText(sharedPreferences.getString("temp1",""));
        temp2Text.setText(sharedPreferences.getString("temp2",""));
        weatherdespText.setText(sharedPreferences.getString("weather_Desp",""));
        publishText.setText("今天" + sharedPreferences.getString("publish_time","") + "发布");
        currentDateText.setText(sharedPreferences.getString("current_date",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this,AutoUpdateService.class);
        startService(intent);
    }

    //查询县级代码所对应的天气代号
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address,"countyCode");
    }

    //根据传入的地址和类型去向服务器查询天气代号或者天气信息,type是县级代号
    private void queryFromServer(String address, final String type) {

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)){
                    if (!TextUtils.isEmpty(response)){
                        //从服务器返回的数据中解析出天气代号
                        String[] array = response.split("\\|");
                        if (array !=null && array.length == 2){
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);//查询天气代号所对应的天气信息
                        }
                    }
                }else if ("weatherCode".equals(type)){
                    //处理服务器返回的天气信息
                    Utility.handlerWeatherResponse(WeatherActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });

            }
        });
    }

    //查询天气代号对应的天气
    private void queryWeatherInfo(String weatherCode) {

        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode +  ".html";
        //10101010  http://www.weather.com.cn/data/cityinfo/10101010.html
        Log.d(TAG,address);
        queryFromServer(address,"weatherCode");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.switch_city:
                Intent intent = new Intent(this,ChooseAreaActivity.class);//点击切换城市按钮，切换到chooseAreaActivity界面
                Log.d(TAG,123+"");
                intent.putExtra("from_weather_activity",true);//为from_weather_Activity赋值true给chooseAreaActivity使用
                Log.d(TAG,1223+"");
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = sharedPreferences.getString("weather_code","");
                if (TextUtils.isEmpty(weatherCode)){
                    queryWeatherInfo(weatherCode);
                }
                break;
            default:
                break;
        }
    }

    //按两次返回键退出程序

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //捕获返回键按下的事件
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //获取当前系统时间的毫秒数
            currentBckeTime = System.currentTimeMillis();
            //比较上次按下返回键和当前按下返回键的时间差，若大于2秒，则提示再按一次退出
            if (currentBckeTime - lastBackTime >2*1000){
                Toast.makeText(this,"再按一次返回键退出",Toast.LENGTH_SHORT).show();
                lastBackTime = currentBckeTime;
            }else {
                //如果时间差小于2秒，就退出程序
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
