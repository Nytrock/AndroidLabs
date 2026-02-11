package com.example.lab1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private GameView gameView;
    private Handler handler;
    private LinearLayout gameLayout;
    private TextView scoreView;
    private LinearLayout gameOverLayout;
    private ImageButton bonusButton;

    private final String BEST_SCORE_KEY = "best_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(Looper.getMainLooper());
        gameLayout = findViewById(R.id.gameLayout);
        scoreView = findViewById(R.id.scoreView);
        gameOverLayout = findViewById(R.id.gameOverLayout);
        bonusButton = findViewById(R.id.bonusButton);

        Button leftButton = findViewById(R.id.leftButton);
        Button rightButon = findViewById(R.id.rightButton);
        leftButton.setOnTouchListener(this);
        rightButon.setOnTouchListener(this);

        startGame();
    }

    @Override
    public boolean onTouch(View button, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            gameView.changeDirection(0);
        } else {
            if (button.getId() == R.id.leftButton)
                gameView.changeDirection(-1);
            if (button.getId() == R.id.rightButton)
                gameView.changeDirection(1);
        }

        return true;
    }

    public void activateBonus(View view) {
        gameView.activateCurrentBonus();
    }

    public void restartGame(View view) {
        gameLayout.removeAllViews();
        startGame();
    }

    private void startGame() {
        gameOverLayout.setVisibility(View.INVISIBLE);
        gameView = new GameView(this, handler, scoreView, bonusButton);
        gameView.setOnGameOver(this::gameOver);

        gameLayout.addView(gameView);
        handler.post(gameView);
    }

    private void gameOver(int newScore) {
        int bestScore = loadBestScore();
        if (bestScore == 0 || bestScore < newScore){
            saveBestScore(newScore);
            bestScore = newScore;
        }

        gameOverLayout.setVisibility(View.VISIBLE);
        TextView bestScoreView = findViewById(R.id.bestScoreView);
        String bestScoreText = getString(R.string.best_score, bestScore);
        bestScoreView.setText(bestScoreText);
    }

    private void saveBestScore(int bestScore) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(BEST_SCORE_KEY, bestScore);
        editor.apply();
    }

    private int loadBestScore() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        return preferences.getInt(BEST_SCORE_KEY, 0);
    }
}