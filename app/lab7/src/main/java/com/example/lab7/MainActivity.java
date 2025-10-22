package com.example.lab7;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer soundPlayer;
    private SwitchCompat themeSwitch;
    private CountDownTimer timer;
    private TextView timerView;
    private VideoView videoView;
    private CountDownTimer videoTimer;
    private ActivityResultLauncher<Intent> photoPickerLauncher;
    private ImageView photoView;

    public final String NOTIFICATIONS_CHANNEL = "NOTIFICATIONS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlayer = MediaPlayer.create(this, R.raw.meow);

        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.party);
        videoView.setVideoURI(videoUri);
        videoView.setVisibility(View.INVISIBLE);
        videoView.setOnPreparedListener((MediaPlayer mp) -> mp.setLooping(true));

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onFinish() {
                timerView.setText("");
            }

            @Override
            public void onTick(long l) {
                String text = getString(R.string.timer_text, l / 1000 + 1);
                timerView.setText(text);
            }
        };
        timerView = findViewById(R.id.timerView);

        videoTimer = new CountDownTimer(10000, 10000) {
            @Override
            public void onFinish() {
                videoView.setVisibility(View.INVISIBLE);
                videoView.stopPlayback();
            }

            @Override
            public void onTick(long l) {

            }
        };

        String name = getString(R.string.notifications_channel);
        NotificationChannel channel = new NotificationChannel(NOTIFICATIONS_CHANNEL, name, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::onPhotoSelected
        );
        photoView = findViewById(R.id.photoView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_theme_switch);
        View actionView = item.getActionView();
        if (actionView != null) {
            themeSwitch = actionView.findViewById(R.id.menu_switch);
            updateThemeSwitch();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_party)
            startParty();
        else if (id == R.id.menu_rotate)
            rotateScreen();
        else if (id == R.id.menu_sound)
            playSound();
        else if (id == R.id.menu_vibration)
            startVibration();
        else if (id == R.id.menu_window)
            openDialogueWindow();
        else if (id == R.id.menu_site)
            openSite();
        else if (id == R.id.menu_photo)
            takePhoto();
        else if (id == R.id.menu_timer)
            startTimer();
        else if (id == R.id.menu_notification)
            sendNotification();

        return super.onOptionsItemSelected(item);
    }

    private void startParty() {
        videoView.setVisibility(View.VISIBLE);
        videoView.start();

        videoTimer.cancel();
        videoTimer.start();
    }

    private void rotateScreen() {
        Log.i("ROTATION", "FUCK");
        View root = findViewById(R.id.main);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate360);
        root.startAnimation(rotate);
    }

    private void playSound() {
        soundPlayer.start();
    }

    private void startVibration() {
        Vibrator vibrator = getSystemService(Vibrator.class);
        VibrationEffect effect = VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(effect);
    }

    private void openDialogueWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_text);
        builder.setPositiveButton(R.string.dialog_yes, (DialogInterface dialog, int which) -> finish());
        builder.setNegativeButton(R.string.dialog_no, (DialogInterface dialog, int which) -> dialog.dismiss());
        builder.show();
    }

    private void openSite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/"));
        startActivity(intent);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoPickerLauncher.launch(intent);
    }

    private void onPhotoSelected(ActivityResult activityResult) {
        if (activityResult.getData() == null)
            return;

        if (activityResult.getData().getExtras() == null)
            return;

        Bitmap photo = (Bitmap) activityResult.getData().getExtras().get("data");
        photoView.setImageBitmap(photo);
    }

    private void startTimer() {
        timer.cancel();
        timer.start();
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATIONS_CHANNEL);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentTitle(getString(R.string.notification_title));
        builder.setContentText(getString(R.string.notification_text));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.notify(1, builder.build());
    }

    public void changeTheme(View view) {
        int currentMode = AppCompatDelegate.getDefaultNightMode();

        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        updateThemeSwitch();
    }

    private void updateThemeSwitch() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        themeSwitch.setChecked(currentMode == AppCompatDelegate.MODE_NIGHT_YES);
    }
}