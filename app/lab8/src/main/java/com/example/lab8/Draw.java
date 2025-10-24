package com.example.lab8;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;

class Point {
    private final float x, y;

    Point(float _x, float _y){
        x = _x;
        y = _y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

public class Draw extends View {
    private final Paint paint = new Paint();
    private final RectF rectF = new RectF();

    Path path = new Path();
    Point[] pointPath = {new Point(150, 1150), new Point(50, 1300),
            new Point(50, 1500), new Point(250, 1500),
            new Point(250, 1300), new Point(150, 1150)};

    public Draw(Context context){
        super(context);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.CYAN);
        canvas.drawPaint(paint);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        canvas.drawLine(10, 10, 800, 10, paint);

        paint.setTextSize(42);
        canvas.drawText("Две линии", 120, 60, paint);

        float cx = 120;
        float cy = 240;
        float a = 200;

        paint.setStyle(Paint.Style.FILL);
        drawFigure(cx, cy, a, canvas);
        cy += 250;

        paint.setStyle(Paint.Style.STROKE);
        drawFigure(cx, cy, a, canvas);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("Различные фигуры", 120, 660, paint);

        path.moveTo(pointPath[0].getX(), pointPath[0].getY());
        for (int i = 1; i < pointPath.length; i++)
            path.lineTo(pointPath[i].getX(), pointPath[i].getY());

        paint.setColor(Color.rgb(255, 125, 105));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

        paint.setColor(Color.BLACK);
        canvas.drawText("Домик", 120, 1560, paint);

        Resources res = this.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.golub);
        canvas.drawBitmap(bitmap, 350, 730, paint);
        canvas.drawText("Самолёт шестого поколения \"Голубь\"", 300, 1300, paint);
    }

    protected void drawFigure(float cx, float cy, float a, Canvas canvas){
        paint.setColor(Color.GREEN);
        canvas.drawCircle(cx, cy, a / 2, paint);
        cx += 250;

        paint.setColor(Color.RED);
        canvas.drawRect(cx - a / 2, cy - a / 2, cx + a / 2, cy + a / 2, paint);
        cx += 250;

        paint.setColor(Color.BLUE);
        rectF.set(cx - a / 2, cy - a / 2, cx + a / 2, cy + a / 2);
        canvas.drawRect(rectF, paint);
        cx += 250;

        paint.setColor(Color.YELLOW);
        rectF.set(cx - a / 2, cy - a / 2, cx + a / 2, cy + a / 2);
        canvas.drawArc(rectF, 135, 270, false, paint);
    }
}