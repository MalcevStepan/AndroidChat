package com.example.test;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Main2Activity extends AppCompatActivity{
    public static EditText nick;
    public static EditText port;
    public static Button connect;
    public static String SAVED_TEXT = "saved_text";
    public static String SAVED_PORT="saved_port";
   public static SharedPreferences preferences;
    public static String NICK;
    public static String PORT;
public static TextView eport;
public static DatagramSocket ds;
public static TextView nickname;
Animation animation;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nick=findViewById(R.id.nick);
        port=findViewById(R.id.port);
        connect=findViewById(R.id.connect);
        eport=findViewById(R.id.eport);
        nickname=findViewById(R.id.enick);
       animation= AnimationUtils.loadAnimation(this,R.anim.anim);


                eport.post(new Runnable() {
                    @Override
                    public void run() {
                        eport.startAnimation(animation);
                        port.startAnimation(animation);
                    }
                });


                nickname.post(new Runnable() {
                    @Override
                    public void run() {
                        nickname.startAnimation(animation);
                        nick.startAnimation(animation);
                    }
                });
                connect.post(new Runnable() {
                    @Override
                    public void run() {
                        connect.startAnimation(animation);
                    }
                });



        nick.post(new Runnable() {
            @Override
            public void run() {
           nick.setText(loadText());
            }
        });
        port.post(new Runnable() {
            @Override
            public void run() {
           port.setText(loadPort());
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveText();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ds=new DatagramSocket(Integer.parseInt(port.getText().toString()));
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                final Intent intent=new Intent(Main2Activity.this,MainActivity.class);
              startActivity(intent);
              finish();
            }
        });
    }
    public void saveText(){
        preferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString(SAVED_TEXT,nick.getText().toString());
     editor.putString(SAVED_PORT,port.getText().toString());
        editor.apply();
    }
    public String loadText(){
        preferences=getPreferences(MODE_PRIVATE);
NICK=preferences.getString(SAVED_TEXT,"");
return NICK;
    }
    public String loadPort(){
        preferences=getPreferences(MODE_PRIVATE);
        PORT=preferences.getString(SAVED_PORT,"");
        return PORT;
    }
public void onDestroy(){
        super.onDestroy();
        saveText();
}
}