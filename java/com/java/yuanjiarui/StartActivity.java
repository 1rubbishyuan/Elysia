package com.java.yuanjiarui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.TintableBackgroundView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class
StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Log.d("dsa","dsa");
        setContentView(R.layout.activity_start);
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this,MainActivity.class));
            }
        };
        timer.schedule(task,800);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}