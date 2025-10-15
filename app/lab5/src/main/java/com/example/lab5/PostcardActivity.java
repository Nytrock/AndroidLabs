package com.example.lab5;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostcardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postcard_activity);
        loadPostcardData();
    }

    private void loadPostcardData() {
        PostcardData postcard = (PostcardData) getIntent().getSerializableExtra("postcard");
        if (postcard == null)
            return;

        TextView textView;
        textView = findViewById(R.id.postcardTitle);
        textView.setText(postcard.getTitle());
        textView.setTextColor(postcard.getTextColor());

        textView = findViewById(R.id.postcardText);
        textView.setText(postcard.getText());
        textView.setTextColor(postcard.getTextColor());

        View mainLayout = findViewById(R.id.postcardLayout);
        mainLayout.setBackgroundResource(postcard.getBackground());

        Date currentDate = Calendar.getInstance().getTime();
        Locale locale = getResources().getConfiguration().getLocales().get(0);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy\n HH:mm:ss", locale);
        String dateString = formatter.format(currentDate);

        textView = findViewById(R.id.dateText);
        if (postcard.isShowDate()){
            textView.setVisibility(VISIBLE);
            textView.setTextColor(postcard.getTextColor());
            textView.setText(dateString);
        } else {
            textView.setVisibility(INVISIBLE);
        }

        ImageView imageView = findViewById(R.id.postcardImage);
        imageView.setImageURI(postcard.getGalleryImage());
    }

    public void closeActivity(View view) {
        finish();
    }
}
