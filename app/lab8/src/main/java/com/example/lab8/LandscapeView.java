package com.example.lab8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.lab8.Daytime.DaytimeManager;
import com.example.lab8.Drawer.BaseDrawer;
import com.example.lab8.Drawer.CloudsDrawer;
import com.example.lab8.Drawer.ForegroundDrawer;
import com.example.lab8.Drawer.SkyDrawer;
import com.example.lab8.Drawer.SunDrawer;

public class LandscapeView extends View {
    private final BaseDrawer[] drawers;
    private final DaytimeManager daytimeManager;

    public LandscapeView(Context context) {
        super(context);
        Paint paint = new Paint();
        daytimeManager = new DaytimeManager();

        drawers = new BaseDrawer[4];
        drawers[0] = new SkyDrawer(paint);
        drawers[1] = new SunDrawer(paint);
        drawers[2] = new CloudsDrawer(paint);
        drawers[3] = new ForegroundDrawer(paint);
        this.postInvalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        daytimeManager.update();
        for (BaseDrawer drawer : drawers)
            drawer.draw(canvas, daytimeManager);
        this.postInvalidateDelayed( 1000 / 60);
    }
}