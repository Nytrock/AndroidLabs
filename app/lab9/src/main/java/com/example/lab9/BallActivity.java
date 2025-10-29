package com.example.lab9;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class BallActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    private int score = 0;
    private Random random;

    private TextView scoreView;
    private ImageView ballView;
    private ImageView holeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);

        scoreView = findViewById(R.id.scoreView);
        ballView = findViewById(R.id.ballView);
        holeView = findViewById(R.id.holeView);

        random = new Random();

        sensorManager = getSystemService(SensorManager.class);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerAccelerometer, sensorAccelerometer);
    }

    SensorEventListener listenerAccelerometer = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

        }
    };

    private void moveBall(float x, float y) {

    }

    public void finishActivity(View view) {
        finish();
    }
}
