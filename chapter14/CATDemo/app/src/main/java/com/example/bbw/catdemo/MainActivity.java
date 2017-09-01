package com.example.bbw.catdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    public static final String DATA = "data";//不再提示存放data
    public static final String CONTENT = "content";
    public static final String LEARNDATA = "learndata";//不再提示存放data
    public static final String LEARNCONTENT = "learncontent";
    public static int nIsReminder = 1;//是否不再提示:0不再提示，1提示，首次进入需要提示
    public static int REMINDER_NO = 0;
    public static int REMINDER_YES = 1;
    public static int nISLEARN = 0;
    public static int DATASTRUCTER_NO = 0;//0没有学习过，1学习过
    public static int DATASTRUCTER_YES = 1;

    private RadioButton learnedDataStructure;
    private RadioButton learningDataStructure;
    private CheckBox tips;
    private Button continue_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        learnedDataStructure = (RadioButton) findViewById(R.id.learned);
        learningDataStructure = (RadioButton) findViewById(R.id.learning);
        tips = (CheckBox) findViewById(R.id.checkbox_tips);
        continue_text = (Button) findViewById(R.id.button_continue);

        Toolbar toolbar_cat = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_cat);
        ActionBar return_cat = getSupportActionBar();
        if ((return_cat != null)){
            return_cat.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);//去除默认显示的文字

        }

        //checkBox绑定监听器
        tips.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    nIsReminder = REMINDER_NO;
                }else{
                    nIsReminder = REMINDER_YES;
                }
            }
        });

        learningDataStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nISLEARN = DATASTRUCTER_NO;
            }
        });
        learnedDataStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nISLEARN = DATASTRUCTER_YES;
            }
        });

        continue_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //跳转至做题页面
                Intent intent = new Intent(getApplicationContext(),catText.class);
                startActivity(intent);
                MainActivity.this.finish();

                //保存是否学习过的数据
                SharedPreferences.Editor data = getSharedPreferences(LEARNDATA,0).edit();
                data.putInt(LEARNCONTENT,nISLEARN);
                data.commit();

                //保存提示数据
                SharedPreferences.Editor shareData = getSharedPreferences(DATA,0).edit();
                shareData.putInt(CONTENT,nIsReminder);
                shareData.commit();

            }
        });

        toolbar_cat.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),welcome.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}
