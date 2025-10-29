package com.example.lab8.Drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.lab8.Daytime.DaytimeManager;

import java.util.Random;

public class CloudDrawer extends BaseDrawer {
    private final int width;
    private final int height;
    private final Point position;
    private final float cloudSpeed;
    private final RectF rect;

    public CloudDrawer(Paint paint, Point position, int width, int height, Random random) {
        super(paint);
        this.position = position;
        this.width = width;
        this.height = height;
        cloudSpeed = (random.nextInt(10) + 5) / 10f;
        rect = new RectF();
    }

    @Override
    public void draw(Canvas canvas, DaytimeManager daytimeManager) {
        updatePosition(daytimeManager);
        canvas.drawRoundRect(rect, height / 2f, height / 2f, paint);
    }

    private void updatePosition(DaytimeManager daytimeManager) {
        float deltaTime = daytimeManager.getDeltaTime();
        position.addToX(deltaTime * cloudSpeed);
        rect.set(position.getX() - width / 2f, position.getY() - height / 2f,
                position.getX() + width / 2f, position.getY() + height / 2f);
    }

    public Point getPosition(){
        return position;
    }

    public int getWidth() {
        return width;
    }
}
