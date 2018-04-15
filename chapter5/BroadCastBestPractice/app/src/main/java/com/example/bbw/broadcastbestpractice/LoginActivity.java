package com.example.bbw.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private String name;
    private String password;
    private CheckBox isChecked;
    private SharedPreferences sharedPreferences;
    private EditText nameText;
    private EditText pwdText;

    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            isChecked = (CheckBox) findViewById(R.id.isChecked);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            nameText =(EditText) findViewById(R.id.edit_name);
            pwdText = (EditText)findViewById(R.id.edit_password);
            final Button login = (Button) findViewById(R.id.login);


            boolean isRemember = sharedPreferences.getBoolean("isChecked",false);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name = nameText.getText().toString();
                    password = pwdText.getText().toString();
                    if(name.equals("admin") && password.equals("123456")){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (isChecked.isChecked()){
                            editor.putBoolean("isChecked",true);
                            editor.putString("nameText",name);
                            editor.putString("pwdText",password);
                        }else{
                            editor.clear();
                        }
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"accountName or password is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        if (isRemember){
            String name = sharedPreferences.getString("nameText","");
            String password = sharedPreferences.getString("pwdText","");
            nameText.setText(name);
            pwdText.setText(password);
            isChecked.setChecked(true);
        }
        }
}
