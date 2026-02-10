package com.example.lab1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.lab1.SpaceBodies.Asteroid;
import com.example.lab1.SpaceBodies.Ship;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    public static int maxX = 20;
    public static int maxY = 28;
    public static float unitW = 0;
    public static float unitH = 0;

    private boolean firstTime = true;
    private boolean gameRunning = true;

    private Ship ship;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private final int ASTEROID_INTERVAL = 50;
    private int currentTime = 0;

    public GameView(Context context) {
        super(context);

        surfaceHolder = getHolder();
        paint = new Paint();
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameRunning) {
            update();
            draw();
            checkCollision();
            checkIfNewAsteroid();
            control();
        }
    }

    private void update() {
        if (firstTime)
            return;

        ship.update();
    }

    private void draw() {
        if (!surfaceHolder.getSurface().isValid())
            return;

        if (firstTime) {
            firstTime = false;
            unitW = (float) surfaceHolder.getSurfaceFrame().width() / maxX;
            unitH = (float) surfaceHolder.getSurfaceFrame().height() / maxY;
            ship = new Ship(getContext());
        }
;
        canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLACK);

        ship.draw(paint, canvas);
        for (Asteroid asteroid : asteroids)
            asteroid.draw(paint, canvas);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkCollision() {
        for (Asteroid asteroid : asteroids) {
            if (asteroid.isCollision(ship.x, ship.y, ship.size)){
                gameRunning = false;
            }
        }
    }

    private void checkIfNewAsteroid(){
        if (currentTime >= ASTEROID_INTERVAL){
            Asteroid asteroid = new Asteroid(getContext());
            asteroids.add(asteroid);
            currentTime = 0;
        } else {
            currentTime++;
        }
    }
}
