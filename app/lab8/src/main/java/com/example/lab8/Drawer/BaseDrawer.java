package com.example.lab8.Drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lab8.Daytime.DaytimeManager;

public abstract class BaseDrawer {
    protected final Paint paint;

    public BaseDrawer(Paint paint){
        this.paint = paint;
    }

    public abstract void draw(Canvas canvas, DaytimeManager daytimeManager);
}
