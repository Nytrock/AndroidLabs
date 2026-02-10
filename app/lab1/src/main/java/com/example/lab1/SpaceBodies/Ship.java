package com.example.lab1.SpaceBodies;

import android.content.Context;

import com.example.lab1.GameView;
import com.example.lab1.MainActivity;
import com.example.lab1.R;

public class Ship extends SpaceBody {
    public Ship(Context context) {
        bitmapId = R.drawable.ship;
        size = 5;
        x = 7;
        y = GameView.maxY - size - 1;
        speed = 0.2f;
        init(context);
    }

    @Override
    public void update() {
        if (MainActivity.isLeftPressed && x >= 0)
            x -= speed;

        if (MainActivity.isRightPressed && x < GameView.maxX - size)
            x += speed;
    }
}
