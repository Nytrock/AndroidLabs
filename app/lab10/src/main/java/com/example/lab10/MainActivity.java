package com.example.lab10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private final Random random = new Random();

    private final int[] songs = {R.raw.lex, R.raw.birds, R.raw.decide, R.raw.citrus,
            R.raw.necromancin, R.raw.future, R.raw.world, R.raw.notion};
    private int nowSong;

    private ImageView songIcon;
    private TextView songTitle;
    private TextView songAuthor;
    private SeekBar songProgress;
    private TextView songProgressText;
    private TextView songDuration;
    private ImageView playButton;
    private ImageView repeatButton;
    private SeekBar songVolume;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            int milliseconds = mediaPlayer.getCurrentPosition();
            if (milliseconds > mediaPlayer.getDuration())
                milliseconds = mediaPlayer.getDuration();

            songProgress.setProgress(milliseconds);
            songProgressText.setText(millisecondsToString(milliseconds));

            if (milliseconds >= mediaPlayer.getDuration() && !mediaPlayer.isLooping())
                nextSong();
            handler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        setupProgressSlider();
        setupVolumeSlider();
        setNowSong(0);
    }

    private void setupViews() {
        songIcon = findViewById(R.id.songIcon);
        songTitle = findViewById(R.id.songTitle);
        songAuthor = findViewById(R.id.songAuthor);
        songProgress = findViewById(R.id.songProgress);
        songProgressText = findViewById(R.id.songProgressText);
        songDuration = findViewById(R.id.songDuration);
        playButton = findViewById(R.id.playButton);
        repeatButton = findViewById(R.id.repeatButton);
        songVolume = findViewById(R.id.songVolume);
    }

    private void setNowSong(int newNowSong) {
        nowSong = newNowSong;
        updateSong();
    }

    private void updateSong() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + songs[nowSong]);
        boolean isPlaying = false;
        if (mediaPlayer != null)
            isPlaying = mediaPlayer.isPlaying();

        try (MediaMetadataRetriever retriever = new MediaMetadataRetriever()) {
            retriever.setDataSource(this, uri);
            String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String author = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            songTitle.setText(title);
            songAuthor.setText(author);

            byte[] embeddedPicture = retriever.getEmbeddedPicture();
            Bitmap cover = BitmapFactory.decodeResource(getResources(), R.drawable.default_cover);
            if (embeddedPicture != null) {
                cover = BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.length);
            }
            songIcon.setImageBitmap(cover);
        } catch (Exception ex) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                changePlayState();
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, songs[nowSong]);
        songDuration.setText(millisecondsToString(mediaPlayer.getDuration()));
        songProgress.setMax(mediaPlayer.getDuration());
        if (isPlaying)
            changePlayState();
    }

    public void onPlayButtonClicked(View view) {
        changePlayState();
    }

    private void changePlayState() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.play);
        } else {
            mediaPlayer.start();
            playButton.setImageResource(R.drawable.pause);
        }
    }

    public void onNextSongButtonClicked(View view) {
        nextSong();
    }

    public void onPreviousSongButtonClicked(View view) {
        previousSong();
    }

    private void nextSong() {
        int newSong = (nowSong + 1) % songs.length;
        setNowSong(newSong);
    }

    private void previousSong() {
        int newSong = nowSong - 1;
        if (newSong < 0)
            newSong = songs.length - 1;
        setNowSong(newSong);
    }

    public void changeRepeatState(View view) {
        if (mediaPlayer.isLooping()) {
            mediaPlayer.setLooping(false);
            repeatButton.setImageResource(R.drawable.repeat);
        } else {
            mediaPlayer.setLooping(true);
            repeatButton.setImageResource(R.drawable.repeat_one);
        }
    }

    public void shuffleSongs(View view) {
        for (int i = songs.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = songs[i];
            songs[i] = songs[j];
            songs[j] = temp;
        }

        setNowSong(0);
    }

    private String millisecondsToString(int milliseconds) {
        int minutes = milliseconds / 1000 / 60;
        int seconds = milliseconds / 1000 % 60;
        return getString(R.string.timer_format, minutes, seconds);
    }

    private void setupProgressSlider() {
        handler.post(updateProgress);
        songProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;

                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });
    }

    private void setupVolumeSlider() {
        audioManager = getSystemService(AudioManager.class);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int nowVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        songVolume.setMax(maxVolume);
        songVolume.setProgress(nowVolume);

        songVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

}