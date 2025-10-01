package com.example.lab4;

public class ArticleData {
    private final int title;
    private final int image;
    private final int text;

    public ArticleData(int title, int text, int image) {
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public int getTitle() {
        return title;
    }

    public int getText() {
        return text;
    }

    public int getImage() {
        return image;
    }
}
