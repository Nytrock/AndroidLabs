package com.example.lab9;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openBallActivity(View view) {
        Intent intent = new Intent(MainActivity.this, BallActivity.class);
        startActivity(intent);
    }

    public void openLightActivity(View view) {
        Intent intent = new Intent(MainActivity.this, LightActivity.class);
        startActivity(intent);
    }
}