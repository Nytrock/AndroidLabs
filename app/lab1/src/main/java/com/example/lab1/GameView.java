package com.example.lab1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lab1.SpaceBodies.Asteroid;
import com.example.lab1.SpaceBodies.Bonus;
import com.example.lab1.SpaceBodies.BonusType;
import com.example.lab1.SpaceBodies.Ship;
import com.example.lab1.SpaceBodies.SpaceBody;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    public static int maxX = 20;
    public static int maxY = 42;
    public static float unitW = 0;
    public static float unitH = 0;

    private final TextView scoreView;
    private final Handler handler;
    private final Paint paint;
    private final SurfaceHolder surfaceHolder;
    private final Random random;
    private final ImageButton bonusButton;

    private boolean firstTime = true;
    private boolean gameRunning = true;
    private OnGameOverListener listener;
    private Ship ship;
    private final ArrayList<SpaceBody> spaceBodies = new ArrayList<>();
    private int nextSpaceBodyTime;
    private int currentTime = 0;
    private int score = 0;

    private Bonus savedBonus;
    private BonusType currentBonus;
    private int bonusDuration;
    private float bonusCurrentTime;

    public interface OnGameOverListener {
        void onGameOver(int score);
    }


    public GameView(Context context, Handler handler, TextView scoreView, ImageButton bonusButton) {
        super(context);

        surfaceHolder = getHolder();
        paint = new Paint();
        random = new Random();

        this.handler = handler;
        this.scoreView = scoreView;
        this.bonusButton = bonusButton;

        updateScore();
        updateNextAsteroidTime();
    }

    public void setOnGameOver(OnGameOverListener listener) {
        this.listener = listener;
    }

    public void changeDirection(int newDirection) {
        if (ship == null)
            return;

        ship.setDirection(newDirection);
    }

    @Override
    public void run() {
        if (gameRunning) {
            update();
            checkCollision();
            draw();
            checkIfNewSpaceBody();
            checkCurrentBonus();
            removeOutOfBoundsSpaceBodies();
        }

        int delay = 16;
        if (currentBonus == BonusType.TIME_SLOWER)
            delay *= 2;
        handler.postDelayed(this, delay);
    }

    private void update() {
        if (firstTime)
            return;

        ship.update();
        for (SpaceBody spaceBody : spaceBodies)
            spaceBody.update();
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
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLACK);

        ship.draw(paint, canvas);
        for (SpaceBody spaceBody : spaceBodies)
            spaceBody.draw(paint, canvas);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void removeOutOfBoundsSpaceBodies() {
        for (int i = 0; i < spaceBodies.size(); i++) {
            if (spaceBodies.get(i).y >= maxY){
                if (spaceBodies.get(i) instanceof Asteroid)
                    score += 1;
                else if (spaceBodies.get(i) instanceof Bonus)
                    score -= 1;

                spaceBodies.remove(i);
                i--;
                updateScore();
            }
        }
    }

    private void updateScore() {
        scoreView.setText(String.valueOf(score));
    }

    private void checkCollision() {
        for (SpaceBody spaceBody : spaceBodies) {
            if (spaceBody.isCollision(ship.x, ship.y, ship.size)) {
                if (spaceBody instanceof Asteroid && currentBonus != BonusType.SHIELD)
                    gameOver();
                else if (spaceBody instanceof Bonus)
                    saveBonus((Bonus) spaceBody);
            }
        }
    }

    private void checkCurrentBonus() {
        if (currentBonus == null)
            return;

        bonusCurrentTime += 1;
        if (bonusCurrentTime >= bonusDuration)
            clearCurrentBonus();
    }

    private void gameOver() {
        gameRunning = false;

        MediaPlayer soundPlayer = MediaPlayer.create(getContext(), R.raw.explosion);
        soundPlayer.start();
        ship.explode();

        listener.onGameOver(score);
    }

    private void checkIfNewSpaceBody(){
        if (currentTime >= nextSpaceBodyTime) {
            boolean dropBonus = random.nextInt(10) == 1;

            SpaceBody spaceBody;
            if (!dropBonus)
                spaceBody = new Asteroid(getContext());
            else
                spaceBody = new Bonus(getContext());
            spaceBodies.add(spaceBody);

            currentTime = 0;
            updateNextAsteroidTime();
        } else {
            currentTime++;
        }
    }

    private void updateNextAsteroidTime() {
        float minAsteroidTime = 20;
        float maxAsteroidTime = 50;
        nextSpaceBodyTime = (int) (minAsteroidTime + (maxAsteroidTime - minAsteroidTime) * random.nextFloat());
    }

    private void saveBonus(Bonus bonus) {
        MediaPlayer soundPlayer = MediaPlayer.create(getContext(), R.raw.get_bonus);
        soundPlayer.start();

        savedBonus = bonus;
        updateBonusButton();
        savedBonus.deactivate();
    }

    public void activateCurrentBonus() {
        if (savedBonus == null)
            return;

        currentBonus = savedBonus.type;
        savedBonus = null;
        updateBonusButton();
        bonusCurrentTime = 0;

        switch (currentBonus) {
            case BOMB:
                destroyAllAsteroids();
                break;
            case SHIELD:
                ship.setShield(true);
                bonusDuration = 150;
                break;
            case TIME_SLOWER:
                bonusDuration = 100;
                break;
        }
    }

    private void updateBonusButton() {
        if (savedBonus == null)
            bonusButton.setImageBitmap(null);
        else
            bonusButton.setImageBitmap(savedBonus.getBitmap());
    }

    private void clearCurrentBonus() {
        if (currentBonus == BonusType.SHIELD)
            ship.setShield(false);
        currentBonus = null;
    }

    private void destroyAllAsteroids() {
        spaceBodies.clear();
        currentBonus = null;
    }
}
