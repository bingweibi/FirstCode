package com.example.bbw.catdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class welcome extends AppCompatActivity {

    private Button welcome;
    private Button createDB;//创建数据库，并添加数据，暂时演示
    List<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome = (Button) findViewById(R.id.button_welcome);
        createDB = (Button) findViewById(R.id.createDB);

        Toolbar toolbar_cat = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_cat);
        ActionBar return_cat = getSupportActionBar();
        if ((return_cat != null)) {
            return_cat.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }

        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //保存数据
                SharedPreferences shareData = getSharedPreferences(MainActivity.DATA,0);
                int nData = shareData.getInt(MainActivity.CONTENT,MainActivity.nIsReminder);
                if (MainActivity.REMINDER_YES == nData){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    com.example.bbw.catdemo.welcome.this.finish();
                }else if (MainActivity.REMINDER_NO == nData){
                    Intent intent = new Intent(getApplicationContext(),catText.class);
                    startActivity(intent);
                    com.example.bbw.catdemo.welcome.this.finish();
                }

            }
        });

        createDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.getDatabase();
                Question question = new Question();
                question.setDiscrimination("0.36");
                question.setDifficult("0.35");
                question.setQuestion("1+1=?");
                question.setA("3");
                question.setB("2");
                question.setC("4");
                question.setD("1");
                question.setCorrectAnswer("B");
                questionList.add(question);

                Question question1 = new Question();
                question1.setDiscrimination("0.42");
                question1.setDifficult("0.48");
                question1.setQuestion("1+2=?");
                question1.setA("3");
                question1.setB("2");
                question1.setC("4");
                question1.setD("1");
                question1.setCorrectAnswer("A");
                questionList.add(question1);

                Question question2 = new Question();
                question2.setDiscrimination("0.48");
                question2.setDifficult("0.57");
                question2.setQuestion("3+1=?");
                question2.setA("3");
                question2.setB("2");
                question2.setC("4");
                question2.setD("1");
                question2.setCorrectAnswer("C");
                questionList.add(question2);

                Question question3 = new Question();
                question3.setDiscrimination("0.53");
                question3.setDifficult("0.61");
                question3.setQuestion("1+4=?");
                question3.setA("3");
                question3.setB("2");
                question3.setC("4");
                question3.setD("5");
                question3.setCorrectAnswer("D");
                questionList.add(question3);

                Question question4 = new Question();
                question4.setDiscrimination("0.55");
                question4.setDifficult("0.62");
                question4.setQuestion("5+1=?");
                question4.setA("3");
                question4.setB("6");
                question4.setC("4");
                question4.setD("5");
                question4.setCorrectAnswer("B");
                questionList.add(question4);

                Question question5 = new Question();
                question5.setDiscrimination("0.57");
                question5.setDifficult("0.63");
                question5.setQuestion("1+6=?");
                question5.setA("3");
                question5.setB("6");
                question5.setC("4");
                question5.setD("7");
                question5.setCorrectAnswer("B");
                questionList.add(question5);

                Question question6 = new Question();
                question6.setDiscrimination("0.68");
                question6.setDifficult("0.63");
                question6.setQuestion("1+7=?");
                question6.setA("3");
                question6.setB("2");
                question6.setC("8");
                question6.setD("1");
                question6.setCorrectAnswer("B");
                questionList.add(question6);

                Question question7 = new Question();
                question7.setDiscrimination("0.70");
                question7.setDifficult("0.64");
                question7.setQuestion("8+1=?");
                question7.setA("3");
                question7.setB("2");
                question7.setC("4");
                question7.setD("9");
                question7.setCorrectAnswer("D");
                questionList.add(question7);

                Question question8 = new Question();
                question8.setDiscrimination("0.79");
                question8.setDifficult("0.66");
                question8.setQuestion("1+9=?");
                question8.setA("3");
                question8.setB("10");
                question8.setC("4");
                question8.setD("1");
                question8.setCorrectAnswer("B");
                questionList.add(question8);

                Question question9 = new Question();
                question9.setDiscrimination("0.84");
                question9.setDifficult("0.70");
                question9.setQuestion("1+10=?");
                question9.setA("3");
                question9.setB("2");
                question9.setC("4");
                question9.setD("11");
                question9.setCorrectAnswer("D");
                questionList.add(question9);

                Question question10 = new Question();
                question10.setDiscrimination("0.85");
                question10.setDifficult("0.76");
                question10.setQuestion("1+11=?");
                question10.setA("12");
                question10.setB("2");
                question10.setC("4");
                question10.setD("1");
                question10.setCorrectAnswer("A");
                questionList.add(question10);

                Question question11 = new Question();
                question11.setDiscrimination("0.92");
                question11.setDifficult("0.86");
                question11.setQuestion("1+12=?");
                question11.setA("13");
                question11.setB("2");
                question11.setC("4");
                question11.setD("1");
                question11.setCorrectAnswer("A");
                questionList.add(question11);

                Question question12 = new Question();
                question12.setDiscrimination("0.94");
                question12.setDifficult("0.96");
                question12.setQuestion("1+13=?");
                question12.setA("13");
                question12.setB("2");
                question12.setC("14");
                question12.setD("1");
                question12.setCorrectAnswer("C");
                questionList.add(question12);

                Question question13 = new Question();
                question13.setDiscrimination("1.26");
                question13.setDifficult("1.06");
                question13.setQuestion("1+14=?");
                question13.setA("13");
                question13.setB("2");
                question13.setC("4");
                question13.setD("15");
                question13.setCorrectAnswer("D");
                questionList.add(question13);

                Question question14 = new Question();
                question14.setDiscrimination("1.37");
                question14.setDifficult("1.13");
                question14.setQuestion("1+15=?");
                question14.setA("13");
                question14.setB("16");
                question14.setC("4");
                question14.setD("1");
                question14.setCorrectAnswer("B");
                questionList.add(question14);

                Question question15 = new Question();
                question15.setDiscrimination("1.43");
                question15.setDifficult("1.16");
                question15.setQuestion("1+16=?");
                question15.setA("13");
                question15.setB("2");
                question15.setC("4");
                question15.setD("17");
                question15.setCorrectAnswer("D");
                questionList.add(question15);

                Question question16 = new Question();
                question16.setDiscrimination("1.44");
                question16.setDifficult("1.19");
                question16.setQuestion("1+17=?");
                question16.setA("13");
                question16.setB("18");
                question16.setC("4");
                question16.setD("1");
                question16.setCorrectAnswer("B");
                questionList.add(question16);

                Question question17 = new Question();
                question17.setDiscrimination("1.33");
                question17.setDifficult("1.23");
                question17.setQuestion("1+18=?");
                question17.setA("13");
                question17.setB("2");
                question17.setC("4");
                question17.setD("19");
                question17.setCorrectAnswer("D");
                questionList.add(question17);

                Question question18 = new Question();
                question18.setDiscrimination("1.23");
                question18.setDifficult("1.25");
                question18.setQuestion("1+19=?");
                question18.setA("13");
                question18.setB("20");
                question18.setC("4");
                question18.setD("1");
                question18.setCorrectAnswer("B");
                questionList.add(question18);

                Question question19 = new Question();
                question19.setDiscrimination("1.12");
                question19.setDifficult("1.28");
                question19.setQuestion("1+20=?");
                question19.setA("13");
                question19.setB("21");
                question19.setC("4");
                question19.setD("1");
                question19.setCorrectAnswer("B");
                questionList.add(question19);

                Question question20 = new Question();
                question20.setDiscrimination("1.02");
                question20.setDifficult("1.29");
                question20.setQuestion("1+21=?");
                question20.setA("13");
                question20.setB("22");
                question20.setC("4");
                question20.setD("1");
                question20.setCorrectAnswer("B");
                questionList.add(question20);

                Question question21 = new Question();
                question21.setDiscrimination("0.93");
                question21.setDifficult("1.39");
                question21.setQuestion("1+22=?");
                question21.setA("23");
                question21.setB("2");
                question21.setC("4");
                question21.setD("1");
                question21.setCorrectAnswer("A");
                questionList.add(question21);

                Question question22 = new Question();
                question22.setDiscrimination("0.91");
                question22.setDifficult("1.41");
                question22.setQuestion("1+23=?");
                question22.setA("13");
                question22.setB("25");
                question22.setC("24");
                question22.setD("1");
                question22.setCorrectAnswer("C");
                questionList.add(question22);

                Question question23 = new Question();
                question23.setDiscrimination("0.84");
                question23.setDifficult("1.42");
                question23.setQuestion("1+24=?");
                question23.setA("13");
                question23.setB("2");
                question23.setC("4");
                question23.setD("25");
                question23.setCorrectAnswer("D");
                questionList.add(question23);

                Question question24 = new Question();
                question24.setDiscrimination("0.66");
                question24.setDifficult("1.53");
                question24.setQuestion("1+25=?");
                question24.setA("13");
                question24.setB("2");
                question24.setC("26");
                question24.setD("1");
                question24.setCorrectAnswer("C");
                questionList.add(question24);

                Question question25 = new Question();
                question25.setDiscrimination("0.59");
                question25.setDifficult("1.62");
                question25.setQuestion("1+26=?");
                question25.setA("13");
                question25.setB("27");
                question25.setC("4");
                question25.setD("1");
                question25.setCorrectAnswer("B");
                questionList.add(question25);

                Question question26= new Question();
                question26.setDiscrimination("0.44");
                question26.setDifficult("1.78");
                question26.setQuestion("1+27=?");
                question26.setA("13");
                question26.setB("2");
                question26.setC("4");
                question26.setD("28");
                question26.setCorrectAnswer("D");
                questionList.add(question26);

                Question question27 = new Question();
                question27.setDiscrimination("0.43");
                question27.setDifficult("1.76");
                question27.setQuestion("1+28=?");
                question27.setA("29");
                question27.setB("2");
                question27.setC("4");
                question27.setD("1");
                question27.setCorrectAnswer("A");
                questionList.add(question27);

                Question question28 = new Question();
                question28.setDiscrimination("0.38");
                question28.setDifficult("1.83");
                question28.setQuestion("1+29=?");
                question28.setA("13");
                question28.setB("2");
                question28.setC("4");
                question28.setD("30");
                question28.setCorrectAnswer("D");
                questionList.add(question28);

                Question question29= new Question();
                question29.setDiscrimination("0.35");
                question29.setDifficult("1.86");
                question29.setQuestion("1+30=?");
                question29.setA("13");
                question29.setB("2");
                question29.setC("4");
                question29.setD("31");
                question29.setCorrectAnswer("D");
                questionList.add(question29);

                Question question30 = new Question();
                question30.setDiscrimination("0.31");
                question30.setDifficult("1.94");
                question30.setQuestion("1+31=?");
                question30.setA("13");
                question30.setB("2");
                question30.setC("4");
                question30.setD("32");
                question30.setCorrectAnswer("D");
                questionList.add(question30);

                DataSupport.saveAll(questionList);

                List<Question> questions = DataSupport.findAll(Question.class);
                for (Question q : questions){
                    Log.d("测试  ","questionID  "+q.getId());
                    Log.d("测试  ","questionDis  "+q.getDiscrimination());
                    Log.d("测试  ","questionDif "+q.getDifficult());
                    Log.d("测试  ","questionQuestion  "+q.getQuestion());
                    Log.d("测试  ","questionA  "+q.getA());
                    Log.d("测试  ","questionB  "+q.getB());
                    Log.d("测试  ","questionC  "+q.getC());
                    Log.d("测试  ","questionD  "+q.getD());
                    Log.d("测试  ","questionCorrect  "+q.getCorrectAnswer());
                }
            }
        });
    }
}
