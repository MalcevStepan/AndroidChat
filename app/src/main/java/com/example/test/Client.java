package com.example.test;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends AppCompatActivity {
    Button send;
    EditText edit;
    TextView chat;
    FloatingActionButton fab;
    FloatingActionButton editB;
    FloatingActionButton logout;
    FloatingActionButton shutwown;
    FloatingActionButton restartb;
    Socket s;
    InputStream in;
    OutputStream out;
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
        fab = findViewById(R.id.fab);
        chat = findViewById(R.id.chat);
        editB = findViewById(R.id.editButton);
        logout = findViewById(R.id.logout);
        Toast toast = Toast.makeText(Client.this, "Установка соединения", Toast.LENGTH_LONG);
        toast.show();
        shutwown = findViewById(R.id.shutdown);
        restartb = findViewById(R.id.restart);
        layout = findViewById(R.id.layout);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        animationClose = AnimationUtils.loadAnimation(this, R.anim.close);
        animationOpen = AnimationUtils.loadAnimation(this, R.anim.open);
        animationClose1 = AnimationUtils.loadAnimation(this, R.anim.close1);
        animationOpen1 = AnimationUtils.loadAnimation(this, R.anim.open1);
        animationClose2 = AnimationUtils.loadAnimation(this, R.anim.close2);
        animationOpen2 = AnimationUtils.loadAnimation(this, R.anim.open2);
        animationClose3 = AnimationUtils.loadAnimation(this, R.anim.close3);
        animationOpen3 = AnimationUtils.loadAnimation(this, R.anim.open3);
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


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!editB.isClickable()) {
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

                        } else {
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
                        if (editB.isClickable()) {
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
                Intent intent = new Intent(Client.this, Main3Activity.class);
                startActivity(intent);
            }
        });
//RECIEVING MESSAGE
        final Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        s = new Socket(TCP.ip.getText().toString(), Integer.parseInt(TCP.port.getText().toString()));
                        fab.post(new Runnable() {
                            @Override
                            public void run() {
                                if (s.isConnected()) {
                                    fab.setImageDrawable(getResources().getDrawable(R.drawable.connected));
                                } else
                                    fab.setImageDrawable(getResources().getDrawable(R.drawable.disconnected));
                            }
                        });

                        if (s.isConnected()) {
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
                                            break;
                                        }
                                    }
                                }
                            }).start();
                        }
                        break;
                    } catch (IOException e) {
                        break;

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
                finish();
            }
        });
        restartb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }).start();

            }
        });
        View.OnClickListener onClickListener3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//SENDING MESSAGE
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (s.isConnected()) {
                                        final String l = TCP.nick.getText().toString() + ": " + edit.getText().toString();
                                        out = s.getOutputStream();
                                        chat.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                chat.append("\n" + l);
                                            }
                                        });
                                        out.write(l.getBytes());
                                        out.flush();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }).start();
                edit.setText("");
            }
        };
        send.setOnClickListener(onClickListener3);
    }
}

