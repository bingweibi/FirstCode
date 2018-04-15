package com.example.bbw.networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendRequestWithHttpConnection();
                sendRequestWithOkHttp3();
            }
        });
    }

    private void sendRequestWithOkHttp3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                //指定访问服务器地址是电脑本机,解析xml数据
//                Request request = new Request.Builder().url("http://10.0.2.2/get_data.xml").build();
                //解析Json数据
                Request request = new Request.Builder().url("http://10.0.2.2/get_data.json").build();
//                Request request = new Request.Builder().url("https://www.baidu.com").build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJsonWithGSON(responseData);
//                    parseJsonWithJSONObject(responseData);
//                    parseXMLWithSAX(responseData);
//                    parseXMLWithPull(responseData);
//                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJsonWithGSON(String responseData) {
        Gson gson = new Gson();
        List<App> applist = gson.fromJson(responseData,new TypeToken<List<App>>(){ }.getType());
        for (App app:applist){
            Log.d("MainActivity","id is " + app.getId());
            Log.d("MainActivity","name is "+ app.getName());
            Log.d("MainActivity","version is " + app.getVersion());
        }
    }

    private void parseJsonWithJSONObject(String responseData) {

        //首先将服务器返回的数据传入JSONArray对象中，遍历循环，取出的每个都是jsonObject对象
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("MainActivity","id is" + id);
                Log.d("MainActivity","name is " + name);
                Log.d("MainActivity","version is" + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithSAX(String responseData) {
        try {
            //首先获得saxParserFactory的实例，在得到xmlReader的对象
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();

            ContentHandler handler = new ContentHandler();
            //将contentHandler的实例设置到XMLReader中
            reader.setContentHandler(handler);
           // 开始解析
            reader.parse(new InputSource(new StringReader(responseData)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithPull(String responseData) {

        try {
            //首先获取到一个XmlPullParserFactory的实例，
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //再得到xmlPullParser的对象
            XmlPullParser xmlPullParser = factory.newPullParser();
            //再填入要解析的数据
            xmlPullParser.setInput(new StringReader(responseData));
            //得到当前的解析事件
            int eventType = xmlPullParser.getEventType();
            String name = "";
            String id = "";
            String version = "";
            //通过while循环进行解析
            while(eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点
                    case XmlPullParser.START_DOCUMENT: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        }
                        if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        }
                        if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成对某个节点的解析
                    case XmlPullParser.END_DOCUMENT:{
                        if ("app".equals(nodeName)){
                            Log.d("MainActivity","id is "+ id);
                            Log.d("MainActivity","name is  "+ name);
                            Log.d("MainActivity","version is "+ version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                //获取下一个解析事件
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRequestWithHttpConnection() {

        //开启线程发送网络请求
        new Thread(new Runnable(){

            @Override
            public void run() {
                HttpURLConnection httpUrlConnection = null;
                BufferedReader read = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    //获取HttpURLConnection实例
                    httpUrlConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方法
                    httpUrlConnection.setRequestMethod("GET");
                    //设置连接超时、读取超时
                    httpUrlConnection.setReadTimeout(8000);
                    httpUrlConnection.setConnectTimeout(8000);
                    //获取服务器返回的输入流
                    InputStream in = httpUrlConnection.getInputStream();
                    //对获取到的输入流进行读取
                    read = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = read.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (read != null){
                        try {
                            read.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (httpUrlConnection != null){
                        httpUrlConnection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(s);
            }
        });
    }
}
