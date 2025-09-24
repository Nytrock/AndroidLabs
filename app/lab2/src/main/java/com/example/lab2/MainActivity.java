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

    private final int MAX_PASSENGERS_COUNT = 15;
    private final int IMAGES_COLUMNS_COUNT = 3;

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
        updateText();
    }

    public void addKid(View view) {
        if (isAirplaneFull()){
            changeErrorState(true);
            return;
        }

        addImage(KidsImages[Randomizer.nextInt(KidsImages.length)]);
        KidsCounter++;
        updateText();
    }

    public void addAdult(View view) {
        if (isAirplaneFull()){
            changeErrorState(true);
            return;
        }

        addImage(AdultsImages[Randomizer.nextInt(AdultsImages.length)]);
        AdultsCounter++;
        updateText();
    }

    private boolean isAirplaneFull() {
        return KidsCounter + AdultsCounter >= MAX_PASSENGERS_COUNT;
    }

    private void changeErrorState(boolean isError) {
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
        int imageIndex = (KidsCounter + AdultsCounter) % IMAGES_COLUMNS_COUNT;
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