package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsData news1 = new NewsData(R.string.news_title_1, R.string.news_text_1);
        NewsData news2 = new NewsData(R.string.news_title_2, R.string.news_text_2);
        findViewById(R.id.newsButton1).setOnClickListener(view -> openNews(news1));
        findViewById(R.id.newsButton2).setOnClickListener(view -> openNews(news2));

        ArticleData article1 = new ArticleData(R.string.articles_title_1, R.string.articles_text_1, R.drawable.article_1);
        ArticleData article2 = new ArticleData(R.string.articles_title_2, R.string.articles_text_2, R.drawable.article_2);
        findViewById(R.id.articlesButton1).setOnClickListener(view -> openArticle(article1));
        findViewById(R.id.articlesButton2).setOnClickListener(view -> openArticle(article2));

        int[] albumImages1 = new int[] {R.drawable.album_1_1, R.drawable.album_1_2};
        AlbumData album1 = new AlbumData(R.string.albums_title_1, albumImages1);
        int[] albumImages2 = new int[] {R.drawable.album_2_1, R.drawable.album_2_2, R.drawable.album_2_3};
        AlbumData album2 = new AlbumData(R.string.albums_title_2, albumImages2);
        findViewById(R.id.albumsButton1).setOnClickListener(view -> openAlbum(album1));
        findViewById(R.id.albumsButton2).setOnClickListener(view -> openAlbum(album2));
    }

    private void openNews(NewsData data) {
        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
        intent.putExtra("title", data.getTitle());
        intent.putExtra("text", data.getText());
        startActivity(intent);
    }

    private void openArticle(ArticleData data) {
        Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
        intent.putExtra("title", data.getTitle());
        intent.putExtra("image", data.getImage());
        intent.putExtra("text", data.getText());
        startActivity(intent);
    }

    private void openAlbum(AlbumData data) {
        Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
        intent.putExtra("title", data.getTitle());
        intent.putExtra("images", data.getImages());
        startActivity(intent);
    }

    public void sendEmail(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"university@yandex.ru"});

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}