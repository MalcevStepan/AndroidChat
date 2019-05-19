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
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    Button send;
    EditText edit;
    TextView chat;
    String out;
    FloatingActionButton fab;
    DatagramSocket ds=Main2Activity.ds;
    DatagramPacket OUTpacket;
    byte[] OUTbytes;
FloatingActionButton editB;
    FloatingActionButton logout;
    FloatingActionButton shutwown;
    FloatingActionButton restartb;
    RelativeLayout layout;
    Animation animation;
    Animation animationClose;
    Animation animationOpen;
    Animation animationClose1;
    Animation animationOpen1;
    Animation animationClose2;
    Animation animationOpen2;
    Animation animationClose3;
    Animation animationOpen3;
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
        Toast toast=Toast.makeText(MainActivity.this,"Подключение установлено",Toast.LENGTH_LONG);
        toast.show();
        animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        animationClose=AnimationUtils.loadAnimation(this,R.anim.close);
        animationOpen=AnimationUtils.loadAnimation(this,R.anim.open);
         animationClose1=AnimationUtils.loadAnimation(this,R.anim.close1);
          animationOpen1=AnimationUtils.loadAnimation(this,R.anim.open1);
         animationClose2=AnimationUtils.loadAnimation(this,R.anim.close2);
          animationOpen2=AnimationUtils.loadAnimation(this,R.anim.open2);
         animationClose3=AnimationUtils.loadAnimation(this,R.anim.close3);
          animationOpen3=AnimationUtils.loadAnimation(this,R.anim.open3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                send.startAnimation(animation);
                edit.startAnimation(animation);
                chat.startAnimation(animation);
                editB.startAnimation(animationClose3);
                logout.startAnimation(animationClose1);
                shutwown.startAnimation(animationClose);
                restartb.startAnimation(animationClose2);
                editB.setClickable(false);
                logout.setClickable(false);
                shutwown.setClickable(false);
                restartb.setClickable(false);
            }
        }).start();


        View.OnClickListener onClickListener= new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   if(!editB.isClickable()){
                       editB.post(new Runnable() {
                           @Override
                           public void run() {
                               editB.startAnimation(animationOpen3);
                               logout.startAnimation(animationOpen1);
                               shutwown.startAnimation(animationOpen);
                               restartb.startAnimation(animationOpen2);
                               editB.setClickable(true);
                               logout.setClickable(true);
                               shutwown.setClickable(true);
                               restartb.setClickable(true);
                           }
                       });

                   }else {
                       editB.post(new Runnable() {
                           @Override
                           public void run() {
                               editB.startAnimation(animationClose3);
                               logout.startAnimation(animationClose1);
                               shutwown.startAnimation(animationClose);
                               restartb.startAnimation(animationClose2);
                               editB.setClickable(false);
                               logout.setClickable(false);
                               shutwown.setClickable(false);
                               restartb.setClickable(false);
                           }
                       });
                   }
               }
           }).start();

       }
   };
        fab.setOnClickListener(onClickListener);
layout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

            editB.post(new Runnable() {
                @Override
                public void run() {
                    if(editB.isClickable()) {
                        editB.startAnimation(animationClose3);
                        logout.startAnimation(animationClose1);
                        shutwown.startAnimation(animationClose);
                        restartb.startAnimation(animationClose2);
                        editB.setClickable(false);
                        logout.setClickable(false);
                        shutwown.setClickable(false);
                        restartb.setClickable(false);
                    }
                }
            });

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

                while (ds!=null) {
                    fab.post(new Runnable() {
                        @Override
                        public void run() {
                            if(ds.isConnected()){
                                fab.setImageDrawable(getResources().getDrawable(R.drawable.connected));
                            }else fab.setImageDrawable(getResources().getDrawable(R.drawable.disconnected));
                        }
                    });
                                                    try {
                                                       byte[] INbytes=new byte[128*1024];
                                                     final DatagramPacket  INpacket=new DatagramPacket(INbytes,INbytes.length);
                                                        ds.receive(INpacket);
                                                            chat.post(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if(!out.equals(new String(INpacket.getData(),0,INpacket.getLength())))
                                                                        chat.append("\n" + new String(INpacket.getData(),0,INpacket.getLength()));
                                                                }
                                                            });


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
                ds.close();
                finishAffinity();
                tr.stop();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ds.close();
                tr.stop();
                finish();
            }
        });
        restartb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ds.close();
                        try {
                            ds=new DatagramSocket(Integer.parseInt(Main2Activity.port.getText().toString()));
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        View.OnClickListener onClickListener3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ds!=null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                              out=Main2Activity.nick.getText().toString()+" : "+edit.getText().toString();
                                chat.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        chat.append("\n" + out);
                                    }
                                });
                                OUTbytes=out.getBytes();
                                OUTpacket=new DatagramPacket(OUTbytes,OUTbytes.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(Main2Activity.port.getText().toString()));
                                ds.send(OUTpacket);
                            } catch (UnknownHostException e) {
                                e.printStackTrace();
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

