package com.example.lab1.SpaceBodies;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lab1.GameView;
import com.example.lab1.R;

import java.util.Random;

public class Bonus extends SpaceBody {
    public BonusType type;
    private boolean isActive;

    public Bonus(Context context) {
        Random random = new Random();

        size = 3;
        x = random.nextInt(GameView.maxX) - size / 2;
        y = -size;
        speed = 0.3f;
        isActive = true;

        int typeIndex = random.nextInt(3);
        type = BonusType.values()[typeIndex];

        switch (type){
            case BOMB: bitmapId = R.drawable.dynamite; break;
            case SHIELD: bitmapId = R.drawable.shield; break;
            case TIME_SLOWER: bitmapId = R.drawable.hourglasses; break;
        }

        init(context);
    }

    @Override
    public void update() {
        if (isActive)
            y += speed;
    }

    @Override
    public boolean isCollision(float otherBodyX, float otherBodyY, float otherBodySize) {
        if (!isActive)
            return false;
        return super.isCollision(otherBodyX, otherBodyY, otherBodySize);
    }

    @Override
    public void draw(Paint paint, Canvas canvas) {
        if (isActive)
            super.draw(paint, canvas);
    }

    public void deactivate() {
        isActive = false;
    }
}

