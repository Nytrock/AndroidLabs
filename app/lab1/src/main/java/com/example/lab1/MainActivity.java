package com.example.lab1;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    public static boolean isLeftPressed = false;
    public static boolean isRightPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameView gameView = new GameView(this);
        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        gameLayout.addView(gameView);

        Button leftButton = findViewById(R.id.leftButton);
        Button rightButon = findViewById(R.id.rightButton);
        leftButton.setOnTouchListener(this);
        rightButon.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View button, MotionEvent event) {
        if (button.getId() == R.id.leftButton)
            isLeftPressed = event.getAction() == MotionEvent.ACTION_DOWN;
        else
            isRightPressed = event.getAction() == MotionEvent.ACTION_DOWN;

        return true;
    }
}