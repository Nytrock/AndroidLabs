package com.example.lab4;

public class NewsData {
    private final int title;
    private final int text;

    public NewsData(int title, int text) {
        this.title = title;
        this.text = text;
    }

    public int getTitle() {
        return title;
    }

    public int getText() {
        return text;
    }
}
