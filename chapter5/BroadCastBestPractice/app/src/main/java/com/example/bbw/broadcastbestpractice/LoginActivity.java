package com.example.bbw.broadcastbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText nameText;
    private EditText pwdText;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            nameText =(EditText) findViewById(R.id.edit_name);
            pwdText = (EditText)findViewById(R.id.edit_password);
            final Button login = (Button) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameText.getText().toString();
                    String password = pwdText.getText().toString();
                    if(name.equals("admin") && password.equals("123456")){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"accountName or password is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
}
