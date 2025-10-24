package com.example.lab9;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvText;
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private Sensor sensorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = findViewById(R.id.tvText);
        sensorManager = getSystemService(SensorManager.class);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void onClickSensList(View view) {
        sensorManager.unregisterListener(listenerLight, sensorLight);
        StringBuilder sb = new StringBuilder();

        for (Sensor sensor : sensors){
            sb.append("name = ").append(sensor.getName())
                    .append("\n type = ").append(sensor.getType())
                    .append("\n vendor = ").append(sensor.getVendor())
                    .append("\n version = ").append(sensor.getVersion())
                    .append("\n max = ").append(sensor.getMaximumRange())
                    .append("\n resolution = ").append(sensor.getResolution())
                    .append("\n-------------------------------\n");
        }

        tvText.setText(sb);
    }

    public void onClickSensLight(View view) {
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorLight);
    }

    SensorEventListener listenerLight = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            String text = getString(R.string.light_text, sensorEvent.values[0]);
            tvText.setText(text);
        }
    };
}