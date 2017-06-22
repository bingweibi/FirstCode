package com.example.bbw.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //button2回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            //判断请求码，随后获得传输过来的数据
            case 1:
                if (resultCode == RESULT_OK){
                    String returnData  = data.getStringExtra("return_data");
                    Log.d("MainActivity",returnData);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //makeText参数（上下文，展示的文字，展示的时间长短）
                Toast.makeText(MainActivity.this,"You click Button1", Toast.LENGTH_SHORT).show();

                //显示intent
                //第一个参数：上下文，第二个参数：目的活动
//                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
//                startActivity(intent);

                //隐式intent
                //可以到看到intent中的参数和androidMainfest中secondActivity设置的action一致
                //这样系统就知道启动哪一个活动了
//                Intent intent = new Intent("com.example.bbw.activitytest.ACTION_START");
//                startActivity(intent);

                //利用intent实现访问百度主页
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
//                startActivity(intent);

                //利用intent实现拨号功能
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:10086"));
//                startActivity(intent);

                //向下一个活动传输数据
                //首先创建intent,并向其中添加需要传输的数据，随后开始activity
                String data="hello";
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("extra_data",data);
                //第二个参数是请求码，用于在之后的回调中判断数据的来源
                startActivityForResult(intent,1);

                //销毁活动
//                finish();
            }
        });
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //调用inflate方法为当前活动创建菜单
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    //菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(MainActivity.this,"You click add_item",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(MainActivity.this,"You click remove_item",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
