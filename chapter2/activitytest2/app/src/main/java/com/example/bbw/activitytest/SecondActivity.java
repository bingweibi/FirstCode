package com.example.bbw.activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        //首先得到intent，随后获得传输过来的数据，并打印
        final Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        Log.d("SecondActivity", data);

        Button button = (Button) findViewById(R.id.button_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //向上传输数据，setResult（返回处理的结果，数据）
                Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                intent.putExtra("return_data","hello");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
