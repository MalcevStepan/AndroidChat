package com.example.test;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
public class MainActivity extends AppCompatActivity {

    Socket s;
    public static InputStream in;
    public static OutputStream out;
    public static Button send;
    public static EditText edit;
    public static TextView chat;
    public static FloatingActionButton fab;
    String ips;
    int ports;
FloatingActionButton editB;
    FloatingActionButton logout;
    FloatingActionButton shutwown;
    FloatingActionButton restartb;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = findViewById(R.id.send);
        edit = findViewById(R.id.edit);
        fab=findViewById(R.id.fab);
        chat = findViewById(R.id.chat);
        editB=findViewById(R.id.editButton);
        logout=findViewById(R.id.logout);
        shutwown=findViewById(R.id.shutdown);
        restartb=findViewById(R.id.restart);
        layout=findViewById(R.id.layout);
        final Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        send.startAnimation(animation);
        edit.startAnimation(animation);
        chat.startAnimation(animation);
        final Animation animationClose1=AnimationUtils.loadAnimation(this,R.anim.close);
        final Animation animationOpen=AnimationUtils.loadAnimation(this,R.anim.open);
        editB.startAnimation(animationClose1);
           logout.startAnimation(animationClose1);
           shutwown.startAnimation(animationClose1);
           restartb.startAnimation(animationClose1);
           editB.setClickable(false);
        logout.setClickable(false);
        shutwown.setClickable(false);
        restartb.setClickable(false);
        View.OnClickListener onClickListener= new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           editB.setVisibility(View.VISIBLE);
           logout.setVisibility(View.VISIBLE);
           shutwown.setVisibility(View.VISIBLE);
           restartb.setVisibility(View.VISIBLE);
           if(!editB.isClickable()){
               editB.startAnimation(animationOpen);
               logout.startAnimation(animationOpen);
               shutwown.startAnimation(animationOpen);
               restartb.startAnimation(animationOpen);
               editB.setClickable(true);
               logout.setClickable(true);
               shutwown.setClickable(true);
               restartb.setClickable(true);
           }else {
               editB.startAnimation(animationClose1);
               logout.startAnimation(animationClose1);
               shutwown.startAnimation(animationClose1);
               restartb.startAnimation(animationClose1);
               editB.setClickable(false);
               logout.setClickable(false);
               shutwown.setClickable(false);
               restartb.setClickable(false);
           }
       }
   };
        fab.setOnClickListener(onClickListener);
layout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(editB.isClickable()){
            editB.startAnimation(animationClose1);
            logout.startAnimation(animationClose1);
            shutwown.startAnimation(animationClose1);
            restartb.startAnimation(animationClose1);
            editB.setClickable(false);
            logout.setClickable(false);
            shutwown.setClickable(false);
            restartb.setClickable(false);
        }
    }
});
editB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(MainActivity.this,Main3Activity.class);
        startActivity(intent);
    }
});

      final Thread tr =new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        ports = Integer.parseInt(Main2Activity.port.getText().toString());
                        ips = Main2Activity.ip.getText().toString();
                        s = new Socket(ips, ports);
                        fab.post(new Runnable() {
                            @Override
                            public void run() {
                                if(s.isConnected()){
                                    fab.setImageDrawable(getResources().getDrawable(R.drawable.connected));
                                }else fab.setImageDrawable(getResources().getDrawable(R.drawable.disconnected));
                            }
                        });

                        if (s.isConnected()) {
                            chat.post(new Runnable() {
                                @Override
                                public void run() {
                                    chat.append("\nПодключение с сервером установлено");
                                }
                            });
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (s.isConnected()) {
                                        try {
                                            in = s.getInputStream();
                                            final byte[] b = new byte[64 * 1024];
                                            final int r = in.read(b);
                                            chat.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    chat.append("\n" + new String(b, 0, r));
                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }).start();
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        tr.start();
        shutwown.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                finishAffinity();
                tr.stop();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tr.stop();
            }
        });
        restartb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    s = new Socket(ips, ports);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        View.OnClickListener onClickListener3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.isConnected()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                final String l = Main2Activity.nick.getText().toString()+": " + edit.getText().toString();
                                out = s.getOutputStream();
                                chat.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        chat.append("\n" + l);
                                    }
                                });
                                out.write(l.getBytes());
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    edit.setText("");
                }
            }
        };
        send.setOnClickListener(onClickListener3);
    }
}

