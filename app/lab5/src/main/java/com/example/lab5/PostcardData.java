package com.example.lab5;

import android.net.Uri;

import java.io.Serializable;

public class PostcardData implements Serializable {
    private final String title;
    private final String text;
    private final int background;
    private final int textColor;
    private final boolean showDate;
    private final String galleryImage;

    public PostcardData(String title, String text, int background, int textColor, boolean showDate, Uri galleryImage){
        this.text = text;
        this.title = title;
        this.background = background;
        this.textColor = textColor;
        this.showDate = showDate;
        this.galleryImage = galleryImage.toString();
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }

    public int getTextColor(){
        return textColor;
    }

    public int getBackground(){
        return background;
    }

    public boolean isShowDate(){
        return showDate;
    }

    public Uri getGalleryImage(){
        return Uri.parse(galleryImage);
    }
}
