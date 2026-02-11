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
    private Context context;

    protected void init(Context context) {
        this.context = context;
        bitmap = loadBitmap(bitmapId);
    }

    protected Bitmap loadBitmap(int id) {
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), id);
        Bitmap newBitmap = Bitmap.createScaledBitmap(cBitmap, (int) (size * GameView.unitW),
                (int) (size * GameView.unitH), false);
        cBitmap.recycle();
        return newBitmap;
    }

    public void draw(Paint paint, Canvas canvas) {
        canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);
    }

    public boolean isCollision(float otherBodyX, float otherBodyY, float otherBodySize) {
        return !(((x + size) < otherBodyX) || (x > (otherBodyX + otherBodySize))
                || ((y + size) < otherBodyY) || (y > (otherBodyY + otherBodySize)));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public abstract void update();
}
