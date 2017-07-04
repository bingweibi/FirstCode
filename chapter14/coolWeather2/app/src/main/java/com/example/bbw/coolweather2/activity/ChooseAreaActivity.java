package com.example.bbw.coolweather2.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbw.coolweather2.R;
import com.example.bbw.coolweather2.db.CoolWeatherDB;
import com.example.bbw.coolweather2.model.City;
import com.example.bbw.coolweather2.model.County;
import com.example.bbw.coolweather2.model.Province;
import com.example.bbw.coolweather2.util.HttpCallbackListener;
import com.example.bbw.coolweather2.util.HttpUtil;
import com.example.bbw.coolweather2.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bbw on 2017/7/3.
 */

public class ChooseAreaActivity extends Activity {

    String TAG = "ChooseAreaActivity";

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVLE_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private CoolWeatherDB coolWeatherDB;
    private List<String> dataList = new ArrayList<>();

    //省级列表
    private List<Province> provinceList;
    //市级列表
    private List<City> cityList;
    //县级列表
    private List<County> countyList;
    //选中的省份
    private Province selectedProvince;
    //选中的城市
    private City selectedCity;
    //当前选中的级别
    private int currentLevel;
    //是否从WeatherActivity中跳转过来
    private boolean isFromWeatherActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity",false);
        Log.d(TAG,"isFromWeatherActivity = " + isFromWeatherActivity);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChooseAreaActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = findViewById(R.id.list_view);
        titleText  =findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        //设置item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {

                if (currentLevel == LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(index);
                    queryCities();//点击省，查询省下面所有的市
                }else if (currentLevel == LEVLE_CITY){
                    selectedCity = cityList.get(index);
                    queryCounties();//点击市，查询市下面所有的县
                }else if(currentLevel == LEVEL_COUNTY){
                    String countyCode = countyList.get(index).getCountyCode();
                    Log.d(TAG,countyCode + "h");
                    Intent intent = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
                    intent.putExtra("county_code",countyCode);
                    startActivity(intent);//点击县，进入weatherActivity
                    finish();
                }
            }
        });
        queryProvinces();//加载省级数据，遍历全国所有省
    }

    //查询全国所有的省，优先从数据库中进行查询，如果没有再去服务器上查询
    private void queryProvinces() {
        provinceList = coolWeatherDB.loadProvince();//从数据库中读取全国所有省份信息
        if (provinceList.size() > 0){
            dataList.clear();
            for (Province provinces : provinceList){
                dataList.add(provinces.getProvinceName());//遍历，将所有省份的名字加载到list里面
            }
            adapter.notifyDataSetChanged();//不用刷新Activity，通知activity动态刷新listView
            listView.setSelection(0);//设置第一个位置被点击
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        }else {
            queryFromServer(null,"province");//未读取到，将从服务器中读取省份信息
        }
    }

    //查询选中省份下面的所有城市，优先从数据库中进行查询，如果没有再去服务器上查询
    private void queryCities() {
        cityList = coolWeatherDB.loadCities(selectedProvince.getId());
        if (cityList.size() > 0){
            dataList.clear();
            for (City city : cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVLE_CITY;
        }else{
            queryFromServer(selectedProvince.getProvinceCode(),"city");
        }
    }

    //查询选中市内所有的县，优先从数据中查询，如果没有再去服务器中查询
    private void queryCounties() {
        Log.d(TAG,selectedCity.getId() + "");
        countyList = coolWeatherDB.loadCounties(selectedCity.getId());
        if (countyList.size() > 0){
            dataList.clear();
            for (County county : countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        }else{
            queryFromServer(selectedCity.getCityCode(),"county");
        }
    }

    //根据传入的代号和类型从服务器上查询市县数据,
    //当type为province是，没有code,type为city和county的时候，有code
    private void queryFromServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)){
            //code非空，这样拼装地址
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        }else {
            //code为空,这样拼装地址
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();//显示进度条
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handleProvinceResponse(coolWeatherDB,response);//将信息存入数据库
                }else if ("city".equals(type)){
                    result = Utility.handleCitiesResponse(coolWeatherDB,response,selectedProvince.getId());
                }else if ("county".equals(type)){
                    result = Utility.handleCountiesResponse(coolWeatherDB,response,selectedCity.getId());
                }
                if (result){
                    //通过runOnUiThread（）方法回到主线程处理逻辑
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();//关闭进度条

                            //根据type的类型，从数据库中读取响应的信息
                            if ("province".equals(type)){
                                queryProvinces();
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                //通过runOnUiThread（）方法回到主线程处理逻辑
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    //显示进度对话框
    private void showProgressDialog() {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载.....");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    //关闭进度对话框
    private void closeProgressDialog() {
        if (progressDialog !=null){
            progressDialog.dismiss();
        }
    }

    //捕获Back按键，根据当前的级别来判断，此时应该返回市列表，省列表，还是直接退出
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY){
            queryCounties();
        }else if (currentLevel == LEVLE_CITY){
            queryCities();
        }else {
            if (isFromWeatherActivity){
                Log.d(TAG,"issi");
                Intent intent = new Intent(this,WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
}
