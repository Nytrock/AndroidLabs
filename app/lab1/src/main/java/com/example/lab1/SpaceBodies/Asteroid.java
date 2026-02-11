package com.example.lab1.SpaceBodies;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.lab1.GameView;
import com.example.lab1.R;

import java.util.Random;

public class Asteroid extends SpaceBody {
    private final Matrix rotator;
    private float rotateStep = 4f;
    private float nowAngle = 0;

    public Asteroid(Context context) {
        Random random = new Random();
        rotator = new Matrix();
        bitmapId = R.drawable.asteroid;

        float minRadius = 1.5f;
        float maxRadius = 4f;
        float radius = minRadius + (maxRadius - minRadius) * random.nextFloat();

        size = radius * 2;
        x = random.nextInt(GameView.maxX) - radius;
        y = -size;

        float minSpeed = 0.4f;
        float maxSpeed = 0.6f;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat();

        if (random.nextInt(2) == 0)
            rotateStep *= -1;
        init(context);
    }

    @Override
    public void draw(Paint paint, Canvas canvas) {
        canvas.drawBitmap(bitmap, rotator, paint);
    }

    @Override
    public void update() {
        y += speed;
        nowAngle += rotateStep * speed;

        float actualX = x * GameView.unitW;
        float actualY = y * GameView.unitH;
        float radius = size / 2;

        rotator.setTranslate(actualX, actualY);
        rotator.postRotate(nowAngle, actualX + radius * GameView.unitW,
                actualY + radius * GameView.unitH);
    }
}
