package com.example.lab9;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class BallActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    private int score = 0;
    private int screenWidth;
    private int screenHeight;
    private Random random;

    private TextView scoreView;
    private ImageView ballView;
    private ImageView holeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);

        getAllObjects();
        setupSensors();
        prepareGame();
    }

    private void prepareGame() {
        getResources();
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        setBallPositon(screenWidth / 2f, screenHeight / 2f);
        holeView.post(this::changeHolePosition);
        updateScore();
    }

    private void setupSensors() {
        sensorManager = getSystemService(SensorManager.class);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private void getAllObjects() {
        scoreView = findViewById(R.id.scoreView);
        ballView = findViewById(R.id.ballView);
        holeView = findViewById(R.id.holeView);
        random = new Random();
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
            moveBall(sensorEvent.values[0], sensorEvent.values[1]);
            checkIsBallInHole();
        }
    };

    private void setBallPositon(float x, float y) {
        float ballSize = ballView.getWidth();
        if (x < 0)
            x = 0;
        if (x > screenWidth - ballSize)
            x = screenWidth - ballSize;
        if (y < 0)
            y = 0;
        if (y > screenHeight - ballSize)
            y = screenHeight - ballSize;

        ballView.setX(x);
        ballView.setY(y);
    }

    private void moveBall(float x, float y) {
        float ballSpeed = 3f;
        float newX = ballView.getX() - x * ballSpeed;
        float newY = ballView.getY() + y * ballSpeed;
        setBallPositon(newX, newY);
    }

    private void checkIsBallInHole() {
        int ballSize = ballView.getWidth(), holeSize = holeView.getWidth();
        int accuracy = holeSize - ballSize;
        float ballX = ballView.getX() + ballSize / 2f, ballY = ballView.getY() + ballSize / 2f;
        float holeX = holeView.getX() + holeSize / 2f, holeY = holeView.getY() + holeSize / 2f;

        boolean xCheck = holeX - accuracy < ballX && ballX < holeX + accuracy;
        boolean yCheck = holeY - accuracy < ballY && ballY < holeY + accuracy;
        if (!xCheck || !yCheck)
            return;

        addScore();
        changeHolePosition();
    }

    private void addScore() {
        score++;
        updateScore();
    }

    private void updateScore(){
        scoreView.setText(String.valueOf(score));
    }

    private void changeHolePosition() {
        int holeSize = holeView.getWidth();
        float newX = random.nextFloat() * (screenWidth - holeSize);
        float newY = random.nextFloat() * (screenHeight - holeSize);
        holeView.setX(newX);
        holeView.setY(newY);
    }

    public void finishActivity(View view) {
        finish();
    }
}
