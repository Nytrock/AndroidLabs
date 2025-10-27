package com.example.lab8.Drawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.lab8.ColorUtility;
import com.example.lab8.Daytime.DaytimeManager;
import com.example.lab8.Point;

public class SunDrawer extends BaseDrawer {
    private final Point rotationCenter = new Point(1181, 932);
    private final Point startPoint = new Point(2162, 932);
    private final int sunColor = Color.parseColor("#e6d00b");
    private final float sunRadius = 30f;

    public SunDrawer(Paint paint) {
        super(paint);
    }

    @Override
    public void draw(Canvas canvas, DaytimeManager daytimeManager) {
        int morningTime = daytimeManager.getStartTimeOfDaytime(0);
        int nightTime = daytimeManager.getStartTimeOfDaytime(3);
        float nowTime = daytimeManager.getNowTime();

        float interval = 0;
        if (morningTime < nowTime && nowTime < nightTime) {
            interval = (nowTime - morningTime) / (nightTime - morningTime);
        }

        float startAngle = 180, endAngle = 0;
        float nowAngle = endAngle - (startAngle - endAngle) * interval;
        nowAngle = nowAngle * (float)Math.PI / 180f;

        Point positionPoint = startPoint.subtract(rotationCenter);
        positionPoint = positionPoint.rotateToAngle(nowAngle);
        positionPoint = positionPoint.add(rotationCenter);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ColorUtility.multiplyColors(daytimeManager.getNowObjectsColor(), sunColor));
        canvas.drawCircle(positionPoint.getX(), positionPoint.getY(), sunRadius, paint);
    }
}
