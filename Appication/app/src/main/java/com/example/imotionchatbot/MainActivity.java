package com.example.imotionchatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button calendarButton, allButton, chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarButton = findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(this);
        allButton = findViewById(R.id.allButton);
        allButton.setOnClickListener(this);
        chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Intent intent;
        switch(b.getId()) {
            case R.id.calendarButton:
                intent = new Intent(this, CaledarActivity.class);
                startActivity(intent);
                break;

            case R.id.allButton:
                //Intent intent = new Intent(this, CaledarActivity.class);
                //startActivity(intent);
                break;
            case R.id.chatButton:
                intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
                break;
        }
    }
}