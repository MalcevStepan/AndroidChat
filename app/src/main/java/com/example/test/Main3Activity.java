package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {
public static EditText newnick;
public Button change;
public String SAVED_TEXT=Main2Activity.SAVED_TEXT;
SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    newnick=findViewById(R.id.newnick);
    change=findViewById(R.id.change);
    change.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
saveText();
              finish();
        }
    });
    }
public void saveText(){
        Main2Activity.nick.setText(newnick.getText());
    preferences=getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor= preferences.edit();
    editor.putString(SAVED_TEXT,newnick.getText().toString());
}
}
