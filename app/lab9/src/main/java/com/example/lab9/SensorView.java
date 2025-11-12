package com.example.lab9;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

public class SensorView extends View {
    private final Paint paint;
    private final RectF arcRect;
    private final int ARC_RADIUS = 400;
    private final float MIN_VALUE = 0;
    private final float MAX_VALUE = 10000;
    private final float START_ANGLE = -180;
    private final float END_ANGLE = 0;

    private float screenCenterX;
    private float screenCenterY;
    private float nowValue;

    public SensorView(Context context) {
        super(context);
        getScreenCenter();

        paint = new Paint();
        arcRect = new RectF();
        arcRect.set(screenCenterX - ARC_RADIUS, screenCenterY - ARC_RADIUS,
                screenCenterX + ARC_RADIUS, screenCenterY + ARC_RADIUS);
        nowValue = MIN_VALUE;
        this.postInvalidate();
    }

    private void getScreenCenter() {
        getResources();
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        screenCenterX = displayMetrics.widthPixels / 2f;
        screenCenterY = displayMetrics.heightPixels / 2f;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        drawMetrics(canvas);
        drawArrow(canvas);
        this.postInvalidateDelayed( 1000 / 60);
    }

    private void drawMetrics(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);
        canvas.drawArc(arcRect, 0, -180, false, paint);

        int textDistance = ARC_RADIUS + 60;
        paint.setStrokeWidth(4);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(36);

        int stepsCount = 10;
        for (int i = 0; i <= stepsCount; i++) {
            float angle = START_ANGLE + (END_ANGLE - START_ANGLE) / (float) stepsCount * i;
            float value = MIN_VALUE + (MAX_VALUE - MIN_VALUE) / (float) stepsCount * i;
            String valueText = String.valueOf((int)value);
            float x = getXOnCircle(angle, textDistance);
            float y = getYOnCircle(angle, textDistance);
            canvas.drawText(valueText, x, y, paint);
        }
    }

    public void drawArrow(Canvas canvas) {
        int arrowDistance = ARC_RADIUS - 20;
        float angle = START_ANGLE + (nowValue - MIN_VALUE) / (MAX_VALUE - MIN_VALUE) * (END_ANGLE - START_ANGLE);
        Log.i("Angle", String.valueOf(angle));

        float x = getXOnCircle(angle, arrowDistance);
        float y = getYOnCircle(angle, arrowDistance);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);

        canvas.drawLine(screenCenterX, screenCenterY, x, y, paint);
    }

    private float getXOnCircle(float angle, float distance) {
        return screenCenterX + (float) (Math.cos(angle * Math.PI / 180) * distance);
    }

    private float getYOnCircle(float angle, float distance){
        return screenCenterY +(float) (Math.sin(angle * Math.PI / 180) * distance);
    }

    public void updateValue(float value) {
        nowValue = value;
    }
}
