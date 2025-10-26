package com.example.lab8.Drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lab8.Daytime.DaytimeManager;

public class SkyDrawer extends BaseDrawer {
    public SkyDrawer(Paint paint) {
        super(paint);
    }

    @Override
    public void draw(Canvas canvas, DaytimeManager daytimeManager) {
        paint.setColor(daytimeManager.getNowSkyColor());
        canvas.drawPaint(paint);
    }
}
