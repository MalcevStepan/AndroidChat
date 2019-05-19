package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class TCPUDP extends AppCompatActivity {
    Button next;
    RadioButton tcp;
    RadioButton udp;
    TextView choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpudp);
        next = findViewById(R.id.next);
        tcp = findViewById(R.id.tcp);
        udp = findViewById(R.id.udp);
        choose = findViewById(R.id.choose);
        udp.setChecked(true);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        next.post(new Runnable() {
            @Override
            public void run() {
                next.startAnimation(animation);
                tcp.startAnimation(animation);
                udp.startAnimation(animation);
                choose.startAnimation(animation);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tcp.isChecked()) {
                    Intent intent = new Intent(TCPUDP.this, TCP.class);
                    startActivity(intent);

                }
                if (udp.isChecked()) {
                    Intent intent = new Intent(TCPUDP.this, Main2Activity.class);
                    startActivity(intent);

                }
                finish();
            }
        });
    }
}
