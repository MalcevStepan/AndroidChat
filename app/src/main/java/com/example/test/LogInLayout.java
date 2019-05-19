package com.example.test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInLayout extends AppCompatActivity {
public static TextView welcome;
public static EditText login;
public static EditText password;
public Button loginB;
public static String loginText;
DBHelper dbHelper;
public static String passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_layout);
    welcome=findViewById(R.id.welcome);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        welcome.startAnimation(animation);
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        loginB=findViewById(R.id.loginbutton);
        dbHelper=new DBHelper(this);
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  loginText=login.getText().toString();
             passwordText=password.getText().toString();
             SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
             ContentValues contentValues=new ContentValues();
             contentValues.put(DBHelper.KEY_LOGIN,loginText);
             contentValues.put(DBHelper.KEY_PASSWORD,passwordText);
               sqLiteDatabase.insert(DBHelper.DATABASE_CONTACTS,null,contentValues);*/
                Intent intent=new Intent(LogInLayout.this,TCPUDP.class);
                startActivity(intent);
            }
        });
    }
}
