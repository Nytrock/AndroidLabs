package com.example.lab9;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DecimalFormat;

public class LightActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensorLight;

    private TextView lightView;
    private SensorView sensorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        setupSensors();
        setupViews();
    }

    private void setupSensors() {
        sensorManager = getSystemService(SensorManager.class);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void setupViews() {
        ConstraintLayout rootView = (ConstraintLayout) findViewById(R.id.lightBackButton).getParent();
        sensorView = new SensorView(this);
        rootView.addView(sensorView);

        lightView = findViewById(R.id.lightText);
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
            updateLightValue(sensorEvent.values[0]);
        }
    };

    private void updateLightValue(float value) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String stringValue = df.format(value);

        lightView.setText(getString(R.string.light_text, stringValue));
        sensorView.updateValue(value);
    }

    public void finishActivity(View view) {
        finish();
    }
}
