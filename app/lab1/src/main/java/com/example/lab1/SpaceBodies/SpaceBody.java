package com.example.lab1.SpaceBodies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lab1.GameView;

public abstract class SpaceBody {
    public float x;
    public float y;
    public float size;

    protected float speed;
    protected int bitmapId;
    protected Bitmap bitmap;

    protected void init(Context context) {
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(cBitmap, (int) (size * GameView.unitW),
                (int) (size * GameView.unitH), false);
        cBitmap.recycle();
    }

    public void draw(Paint paint, Canvas canvas) {
        canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);
    }

    public abstract void update();
}
