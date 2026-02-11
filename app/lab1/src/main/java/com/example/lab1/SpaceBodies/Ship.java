package com.example.lab1.SpaceBodies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lab1.GameView;
import com.example.lab1.MainActivity;
import com.example.lab1.R;

public class Ship extends SpaceBody {
    private final Bitmap shield;

    private int direction;
    private boolean isShieldActive;

    public Ship(Context context) {
        bitmapId = R.drawable.ship;
        size = 3;
        x = 7;
        y = GameView.maxY - size - 1;
        speed = 0.25f;
        init(context);
        shield = loadBitmap(R.drawable.ship_shield);
    }

    @Override
    public void draw(Paint paint, Canvas canvas) {
        super.draw(paint, canvas);
        if (isShieldActive)
            canvas.drawBitmap(shield, x * GameView.unitW, y * GameView.unitH, paint);
    }

    @Override
    public void update() {
        float newX = x + speed * direction;
        if (newX >= 0 && newX < GameView.maxX - size)
            x = newX;
    }

    public void setDirection(int newDirection) {
        direction = newDirection;
    }

    public void setShield(boolean isShieldActive) {
        this.isShieldActive = isShieldActive;
    }

    public void explode() {
        bitmap = loadBitmap(R.drawable.ship_exploded);
    }
}
