package com.example.lab1.SpaceBodies;

import android.content.Context;

import com.example.lab1.GameView;
import com.example.lab1.R;

import java.util.Random;

public class Asteroid extends SpaceBody {
    private int radius = 2;
    private float minSpeed = 0.1f;
    private float maxSpeed = 0.5f;

    public Asteroid(Context context) {
        Random random = new Random();
        bitmapId = R.drawable.asteroid;

        y = 0;
        x = random.nextInt(GameView.maxX) - radius;
        size = radius * 2;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat();

        init(context);
    }

    @Override
    public void update() {
        y += speed;
    }

    public boolean isCollision(float shipX, float shipY, float shipSize) {
        return !(((x + size) < shipX) || (x > (shipX + shipSize))
                || ((y + size) < shipY) || (y > (shipY + shipSize)));
    }
}
