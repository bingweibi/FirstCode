package com.example.bbw.androidthreadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int UPDATE_TEXT = 1;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button  change_Text = (Button) findViewById(R.id.change_text);
        textView = (TextView) findViewById(R.id.text);

        //消息接受处理的handler
        final Handler handler = new Handler(){
            public void handleMessage(Message message){
                switch (message.what){
                    case UPDATE_TEXT:
                        //在这里进行操作
                        textView.setText("nice to meet you");
                        break;
                    default:
                        break;
                }
            }
        };

        change_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //在子线程中发送更新文字的message
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
