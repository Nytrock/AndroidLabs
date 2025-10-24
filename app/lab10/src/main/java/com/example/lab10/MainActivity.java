package com.example.lab10;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = findViewById(R.id.play);
        Button pause = findViewById(R.id.pause);
        Button stop = findViewById(R.id.stop);

        final MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music);

        play.setOnClickListener((View view) -> mediaPlayer.start());
        pause.setOnClickListener((View view) -> {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
        });
        stop.setOnClickListener((View view) -> {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
        });
    }
}