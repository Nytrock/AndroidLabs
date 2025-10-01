package com.example.lab4;

public class AlbumData {
    private final int title;
    private final int[] images;

    public AlbumData(int title, int[] images) {
        this.title = title;
        this.images = images;
    }

    public int getTitle() {
        return title;
    }

    public int[] getImages() {
        return images;
    }
}
