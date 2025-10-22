package com.example.lab2;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public TextView TextViewCounter;
    public TextView ErrorTextView;
    public Button ButtonCounter;
    public Button ButtonClear;
    public TableLayout ImagesContainer;
    public TableRow.LayoutParams ImagesParams;

    private MediaPlayer ErrorPlayer;
    private Random Randomizer;
    private int KidsCounter = 0;
    private int AdultsCounter = 0;
    private int[] KidsImages;
    private int[] AdultsImages;
    private boolean IsError;
    private int ImagesCount = 0;

    private final int MAX_PASSENGERS_COUNT = 15;
    private static final String KEY_KIDS_COUNT = "KIDS_COUNT";
    private static final String KEY_ADULTS_COUNT = "ADULTS_COUNT";
    private static final String KEY_IS_ERROR = "IS_ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextViewCounter = findViewById(R.id.textView);
        ErrorTextView = findViewById(R.id.errorTextView);
        ButtonCounter = findViewById(R.id.buttonKidsCounter);
        ButtonClear = findViewById(R.id.buttonClear);
        ImagesContainer = findViewById(R.id.imagesContainer);
        ImagesParams = new TableRow.LayoutParams(350,350);

        Randomizer = new Random();
        ErrorPlayer = MediaPlayer.create(this, R.raw.error);
        KidsImages = new int[] {R.drawable.kid_1, R.drawable.kid_2, R.drawable.kid_3, R.drawable.kid_4, R.drawable.kid_5};
        AdultsImages = new int[] {R.drawable.adult_1, R.drawable.adult_2, R.drawable.adult_3, R.drawable.adult_4, R.drawable.adult_5};

        ButtonClear.setOnClickListener(view -> {
            KidsCounter = 0;
            AdultsCounter = 0;
            updateText();
            ImagesContainer.removeAllViews();
            changeErrorState(false);
        });

        if (savedInstanceState != null) {
            KidsCounter = savedInstanceState.getInt(KEY_KIDS_COUNT);
            AdultsCounter = savedInstanceState.getInt(KEY_ADULTS_COUNT);
            IsError = savedInstanceState.getBoolean(KEY_IS_ERROR);

            for (int i = 0; i < KidsCounter; i++)
                addKidImage();
            for (int i = 0; i < AdultsCounter; i++)
                addAdultImage();
            changeErrorState(IsError);
        }
        updateText();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_KIDS_COUNT, KidsCounter);
        outState.putInt(KEY_ADULTS_COUNT, AdultsCounter);
        outState.putBoolean(KEY_IS_ERROR, IsError);
    }

    public void addKid(View view) {
        if (isAirplaneFull()){
            changeErrorState(true);
            return;
        }

        addKidImage();
        KidsCounter++;
        updateText();
    }

    private void addKidImage() {
        addImage(KidsImages[Randomizer.nextInt(KidsImages.length)]);
    }

    public void addAdult(View view) {
        if (isAirplaneFull()){
            changeErrorState(true);
            return;
        }

        addAdultImage();
        AdultsCounter++;
        updateText();
    }

    private void addAdultImage() {
        addImage(AdultsImages[Randomizer.nextInt(AdultsImages.length)]);
    }

    private boolean isAirplaneFull() {
        return KidsCounter + AdultsCounter >= MAX_PASSENGERS_COUNT;
    }

    private void changeErrorState(boolean isError) {
        IsError = isError;
        if (isError){
            ErrorTextView.setVisibility(VISIBLE);
            ErrorPlayer.start();
        } else {
            ErrorTextView.setVisibility(INVISIBLE);
        }
    }

    private void updateText(){
        int PassengersCount = KidsCounter + AdultsCounter;
        if (PassengersCount == 0){
            TextViewCounter.setText(R.string.counter_text_empty);
        } else {
            String counterText = getString(R.string.counter_text, PassengersCount, MAX_PASSENGERS_COUNT, AdultsCounter, KidsCounter);
            TextViewCounter.setText(counterText);
        }
    }

    private void addImage(int image) {
        int IMAGES_COLUMNS_COUNT = 3;
        int imageIndex = ImagesCount % IMAGES_COLUMNS_COUNT;
        ImagesCount++;
        TableRow Row;

        if (imageIndex == 0) {
            Row = new TableRow(this);
            ImagesContainer.addView(Row);
        } else {
            Row = (TableRow) ImagesContainer.getChildAt(ImagesContainer.getChildCount() - 1);
        }

        ImageView PassengerImage = new ImageView(this);
        PassengerImage.setImageResource(image);
        PassengerImage.setLayoutParams(ImagesParams);

        Row.addView(PassengerImage);
    }
}