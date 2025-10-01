package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ArticleActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        TextView title = findViewById(R.id.articleTitle);
        TextView text = findViewById(R.id.articleText);
        ImageView image = findViewById(R.id.articleImage);

        title.setText(intent.getIntExtra("title", R.string.articles_title_default));
        text.setText(intent.getIntExtra("text", R.string.articles_text_default));
        image.setImageResource(intent.getIntExtra("image", R.drawable.article_default));
    }

    public void closeActivity(View view) {
        finish();
    }
}