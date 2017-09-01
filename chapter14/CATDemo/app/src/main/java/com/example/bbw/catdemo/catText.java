package com.example.bbw.catdemo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.litepal.crud.DataSupport;


import java.util.Random;

public class catText extends AppCompatActivity {

    private TextView textContent;//题目信息
    private RadioButton radioButton_checked;
    private TextView textContent_A;
    private TextView textContent_B;
    private TextView textContent_C;
    private TextView textContent_D;
    private Button next_Question;
    private RadioGroup radioGroup_answer;
    private ProgressBar progress_text;
    private String corrent_answer = "";
    private int correctNum = 0;
    private CreateDBAbility dbHelper;
    Question question;
    private int initTextNumber;//初始题目的编号

    double y_cur = 0;
    double x = 0.0;//初始能力值
    double step = 0.01;//每次迭代的步长
    //double precision = 0.0001;//误差

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_text);

        //创建能力值数据库
        dbHelper = new CreateDBAbility(getApplicationContext(),"Ability.db",null,1);
        Log.d("测试 "," 创建数据库成功");

        textContent = (TextView) findViewById(R.id.textContent);
        textContent_A = (TextView) findViewById(R.id.content_A);
        textContent_B = (TextView) findViewById(R.id.content_B);
        textContent_C = (TextView) findViewById(R.id.content_C);
        textContent_D = (TextView) findViewById(R.id.content_D);
        next_Question = (Button) findViewById(R.id.next_question);
        progress_text = (ProgressBar) findViewById(R.id.progress_text);
        radioGroup_answer = (RadioGroup) findViewById(R.id.radioGroup_answer);

        Toolbar toolbar_cat = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_cat);
        ActionBar return_cat = getSupportActionBar();
        if ((return_cat != null)){
            return_cat.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);//去除toolbar默认显示的文字
        }

        SharedPreferences isLearnData = getSharedPreferences(MainActivity.LEARNDATA,0);
        int learnDataStructer= isLearnData.getInt(MainActivity.LEARNCONTENT,MainActivity.nISLEARN);//读取上个页面设置的是否学习过数据结构

        if (learnDataStructer == MainActivity.DATASTRUCTER_NO){
            //没有学习过
            initTextNumber = 1;
        }else if (learnDataStructer == MainActivity.DATASTRUCTER_YES){
            //学习过
            initTextNumber = 11;
        }

        initQuestion();

        next_Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int progress = progress_text.getProgress();
                progress = progress + 5;
                progress_text.setProgress(progress);
                if (progress >= 100){
                    theat th = new theat();
                    th.getmin();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(catText.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("你已经完成了测试，能力值：" + x);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }

                for (int i=0;i < radioGroup_answer.getChildCount();i++){

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();

                    radioButton_checked = (RadioButton) radioGroup_answer.getChildAt(i);
                    if (radioButton_checked.isChecked()){
                        if (radioButton_checked.getText().equals(corrent_answer)){

                            question= DataSupport.find(Question.class,initTextNumber);
                            values.put("discrimination",question.getDiscrimination());
                            values.put("difficult",question.getDifficult());
                            values.put("result",1);
                            db.insert("Ability",null,values);
                            values.clear();
                            Log.d("测试 ","区分度：" + question.getDiscrimination() );
                            correctNum++;//正确题目的数量
                            initTextNumber = initTextNumber + (new Random().nextInt(5)+5);//正确，难度增加
                        }else{

                            question= DataSupport.find(Question.class,initTextNumber);
                            values.put("discrimination",question.getDiscrimination());
                            values.put("difficult",question.getDifficult());
                            values.put("result",0);
                            db.insert("Ability",null,values);
                            values.clear();
                            initTextNumber = initTextNumber - (new Random().nextInt(5)+5);//错误，难度降低
                        }
                        //解决题目到达边界的情况
                        if (initTextNumber<1){
                            initTextNumber = new Random().nextInt(5)+1;
                        }else if (initTextNumber >30){
                            initTextNumber = new Random().nextInt(5)+25;
                        }
                    }
                }
                Log.d("测试","initTextNumber   "+ initTextNumber);
                Log.d("测试","correctNum      "+ correctNum);
                initQuestion();
                radioGroup_answer.clearCheck();
            }
        });

        toolbar_cat.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                catText.this.finish();
            }
        });
    }

    private void initQuestion() {

        //读取题目信息
        question= DataSupport.find(Question.class,initTextNumber);
        textContent.setText(question.getQuestion());
        textContent_A.setText(" " + question.getA());
        textContent_B.setText(" " + question.getB());
        textContent_C.setText(" " + question.getC());
        textContent_D.setText(" " + question.getD());
        corrent_answer = question.getCorrectAnswer();
    }

    class theat{

        public double derivative(double difficult, double discrimination, int result, double x){

            double temp=0.0;
            temp = temp + (0.25 - result + (0.75 * (1 / (1 + (Math.pow(Math.E,(-1.702 * discrimination) * (x - difficult)))))));
            return temp/20;
        }

        public double function(double difficult, double discrimination, double x){
            return 0.25 + 0.75 * (1 / (1 + (Math.pow(Math.E,(-1.702 * discrimination) * (x - difficult)))));
        }

        public void getmin(){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("Ability",null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                do {
                    Double disc = Double.valueOf(cursor.getString(cursor.getColumnIndex("discrimination")).toString());
                    Double dif = Double.valueOf(cursor.getString(cursor.getColumnIndex("difficult")).toString());
                    int result = cursor.getInt(cursor.getColumnIndex("result"));
                    Log.d("测试 ","dif: " + dif);
                    y_cur=function(dif,disc,x);
                    double y_div=function(dif,disc,x);//初始y值
                    for(int i=0;i<2000;i++){//下降梯度的幅度变化大于误差，继续迭代
                        x=x-step*derivative(dif,disc,result,x);//沿梯度负方向移动
                        //y_div=y_cur-function(dif,disc,x);//移动后计算y的变化幅度值
                        y_cur=function(dif,disc,x);  //y值跟着x移动变化，计算下一轮迭代
                        //Log.d("测试 " , "y_div " + y_div );
                        //Log.d("测试 ：","能力值 " + x);
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
    }
}
