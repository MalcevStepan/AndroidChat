package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class TCP extends AppCompatActivity {
    TextView iptext;
    public static EditText ip;
    TextView portText;
    public static EditText port;
    TextView nickText;
    public static EditText nick;
    RadioButton server;
    RadioButton client;
    Animation animation;
    Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);
        iptext = findViewById(R.id.iptext);
        ip = findViewById(R.id.ip1);
        portText = findViewById(R.id.eport1);
        port = findViewById(R.id.port1);
        nickText = findViewById(R.id.enick1);
        nick = findViewById(R.id.nick1);
        connect = findViewById(R.id.connect1);
        server = findViewById(R.id.server);
        client = findViewById(R.id.client);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        iptext.post(new Runnable() {
            @Override
            public void run() {
                iptext.startAnimation(animation);
                ip.startAnimation(animation);
                portText.startAnimation(animation);
                port.startAnimation(animation);
                nickText.startAnimation(animation);
                nick.startAnimation(animation);
                server.startAnimation(animation);
                client.startAnimation(animation);
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (client.isChecked()) {
                    Intent intent = new Intent(TCP.this, Client.class);
                    startActivity(intent);
                }
                if (server.isChecked()) {
                    Intent intent = new Intent(TCP.this, Server.class);
                    startActivity(intent);
                }
            }
        });
    }
}
