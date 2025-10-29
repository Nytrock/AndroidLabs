package com.example.lab9;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LightActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        sensorManager = getSystemService(SensorManager.class);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorLight);
    }

    SensorEventListener listenerLight = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

        }
    };

    public void finishActivity(View view) {
        finish();
    }
}
