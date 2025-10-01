package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        TextView title = findViewById(R.id.albumTitle);
        LinearLayout imagesContainer = findViewById(R.id.albumImages);

        title.setText(intent.getIntExtra("title", R.string.albums_title_default));
        int[] images = intent.getIntArrayExtra("images");
        if (images == null)
            return;

        LinearLayout.LayoutParams ImagesParams = new LinearLayout.LayoutParams(800,600);
        for (int image: images) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(image);
            imageView.setLayoutParams(ImagesParams);
            imagesContainer.addView(imageView);
        }
    }

    public void closeActivity(View view) {
        finish();
    }
}
