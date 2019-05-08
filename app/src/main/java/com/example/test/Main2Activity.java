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


public class Main2Activity extends AppCompatActivity{
    public static EditText nick;
    public static EditText ip;
    public static EditText port;
    public static Button connect;
    public static String SAVED_TEXT = "saved_text";
    public static String SAVED_IP="saved_ip";
    public static String SAVED_PORT="saved_port";
   public static SharedPreferences preferences;
    public static String NICK;
    public static String IP;
    public static String PORT;
public static TextView enter;
public static TextView eport;
public static TextView nickname;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ip=findViewById(R.id.ip);
        nick=findViewById(R.id.nick);
        port=findViewById(R.id.port);
        connect=findViewById(R.id.connect);

        enter=findViewById(R.id.enter);
        eport=findViewById(R.id.eport);
        nickname=findViewById(R.id.enick);
        final Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        final Animation animation1= AnimationUtils.loadAnimation(this,R.anim.anim1);
        final Animation animation2= AnimationUtils.loadAnimation(this,R.anim.anim2);
        final Animation animation3= AnimationUtils.loadAnimation(this,R.anim.anim3);
        final Animation animation4= AnimationUtils.loadAnimation(this,R.anim.anim4);
                enter.post(new Runnable() {
                    @Override
                    public void run() {
                        enter.startAnimation(animation1);
                        ip.startAnimation(animation1);
                    }
                });

                eport.post(new Runnable() {
                    @Override
                    public void run() {
                        eport.startAnimation(animation2);
                        port.startAnimation(animation2);
                    }
                });


                nickname.post(new Runnable() {
                    @Override
                    public void run() {
                        nickname.startAnimation(animation3);
                        nick.startAnimation(animation3);
                    }
                });
                connect.post(new Runnable() {
                    @Override
                    public void run() {
                        connect.startAnimation(animation4);
                    }
                });



        nick.post(new Runnable() {
            @Override
            public void run() {
           nick.setText(loadText());
            }
        });
        ip.post(new Runnable() {
            @Override
            public void run() {
           ip.setText(loadIP());
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
                final Intent intent=new Intent(Main2Activity.this,MainActivity.class);
              startActivity(intent);
            }
        });
    }
    public void saveText(){
        preferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString(SAVED_TEXT,nick.getText().toString());
        editor.putString(SAVED_IP,ip.getText().toString());
     editor.putString(SAVED_PORT,port.getText().toString());
        editor.apply();
    }
    public String loadText(){
        preferences=getPreferences(MODE_PRIVATE);
NICK=preferences.getString(SAVED_TEXT,"");
return NICK;
    }
    public String loadIP(){
        preferences=getPreferences(MODE_PRIVATE);
        IP=preferences.getString(SAVED_IP,"");
        return IP;
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