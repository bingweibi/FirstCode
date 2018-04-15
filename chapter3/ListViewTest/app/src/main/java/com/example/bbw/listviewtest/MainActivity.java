package com.example.bbw.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bibingwei
 */
public class MainActivity extends AppCompatActivity {

//    private String[] data={"apple","banana","orange","watermelon","pear","grape","pineapple","strawberry","cherry","mango",
//            "apple","banana","orange","watermelon","pear","grape","pineapple","strawberry","cherry","mango"};
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //数组中的数据不能直接传递给ListView,需要借助适配器来完成
//        ArrayAdapter<String> fruit = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,data);

        initFruits();//初始化水果数据
        FruitAdapter fruitAdapter = new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruitList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(fruitAdapter);
    }

    private void initFruits() {
        for (int i =0;i<2;i++){
            Fruit apple = new Fruit("apple",R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit("banana",R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit("orange",R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit("watermelon",R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit("pear",R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit("grape",R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit("pineapple",R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("strawberry",R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit("cherry",R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit("mango",R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }
}
