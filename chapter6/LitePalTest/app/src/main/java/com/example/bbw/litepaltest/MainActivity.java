package com.example.bbw.litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createDatabase = (Button) findViewById(R.id.create_database);
        Button addData = (Button) findViewById(R.id.add_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);
        Button queryData = (Button) findViewById(R.id.query_data);

        //创建数据库
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.getDatabase();
            }
        });
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setName("dsdfa");
                book.setPages(153);
                book.setPress("sfasfafa");
                book.setPrice(14.53);
                book.setAuthor("asferqyerag");
                book.save();
            }
        });

        //更新数据库
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setName("dsgas");
                book.setPages(157);
                book.updateAll("name = ? and author = ?","dsdfa","asferqyerag");
            }
        });

        //删除数据
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(Book.class,"price < ?","15");
            }
        });

        //查询数据
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Book> bookList = DataSupport.findAll(Book.class);
                for (Book book:bookList){
                    Log.d("MainActivity","book name is "+book.getName());
                    //......
                }
            }
        });
    }
}
