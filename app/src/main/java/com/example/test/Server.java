package com.example.test;

import android.content.Intent;
import android.os.Bundle;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server extends AppCompatActivity {
    Button send;
    EditText edit;
    static TextView chat;
    FloatingActionButton fab;
    FloatingActionButton editB;
    FloatingActionButton logout;
    FloatingActionButton shutwown;
    FloatingActionButton restartb;
    ServerSocket serverSocket;
    Socket s;
    static byte[] b = new byte[64 * 1024];
    ArrayList<InputStream> ins = new ArrayList<>();
    static ArrayList<OutputStream> outs = new ArrayList<>();
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
        Toast toast = Toast.makeText(Server.this, "Установка соединения", Toast.LENGTH_LONG);
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
                Intent intent = new Intent(Server.this, Main3Activity.class);
                startActivity(intent);
            }
        });
        //RECEIVING STREAMS
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(Integer.parseInt(TCP.port.getText().toString()));
                    while (true) {
                        fab.post(new Runnable() {
                            @Override
                            public void run() {
                                if (s.isConnected()) {
                                    fab.setImageDrawable(getResources().getDrawable(R.drawable.connected));
                                } else
                                    fab.setImageDrawable(getResources().getDrawable(R.drawable.disconnected));
                            }
                        });
                        s = serverSocket.accept();
                        ins.add(s.getInputStream());
                        outs.add(s.getOutputStream());
                        if (s.isConnected()) {
                            ClientSocket clientSocket = new ClientSocket(s);
                            clientSocket.start();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //SENDING MESSAGE
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit.getText().toString().equals("")) {
                    final String s = TCP.nick.getText().toString() + ": " + edit.getText().toString();
                    Iterator<OutputStream> iterator = outs.iterator();
                    while (iterator.hasNext()) {
                        OutputStream out = iterator.next();
                        try {
                            out.write(s.getBytes());
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    chat.post(new Runnable() {
                        @Override
                        public void run() {
                            chat.append("\n" + s);
                            edit.setText("");
                        }
                    });
                }
            }
        });
    }
}

class ClientSocket extends Thread {
    Socket s;
    InputStream in;

    ClientSocket(final Socket s) {
        this.s = s;
    }

    public void run() {
        while (s.isConnected() & s != null) {
            try {
                in = s.getInputStream();
                final int r = in.read(Server.b);
                Server.chat.post(new Runnable() {
                    @Override
                    public void run() {
                        Server.chat.append("\n" + new String(Server.b, 0, r));
                    }
                });
                Iterator<OutputStream> outs = Server.outs.iterator();
                while (outs.hasNext()) {
                    OutputStream out = outs.next();
                    if (out != s.getOutputStream()) {
                        out.write(new String(Server.b, 0, r).getBytes());
                        out.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
