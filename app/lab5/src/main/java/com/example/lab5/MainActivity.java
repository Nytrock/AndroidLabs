package com.example.lab5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<PostcardData> postcards;
    private int background;
    private int textColor;

    private ActivityResultLauncher<Intent> photoPickerLauncher;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postcards = new ArrayList<>();
        selectBackground(findViewById(R.id.backgroundWhite));
        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::onPhotoSelected
        );
    }

    public void selectBackground(View view){
        int id = view.getId();
        if (id == R.id.backgroundWhite){
            background = R.drawable.background2;
            textColor = R.color.textBlack;
        } else if (id == R.id.backgroundPurple) {
            background = R.drawable.background1;
            textColor = R.color.textPurple;
        } else if (id == R.id.backgroundBlue) {
            background = R.drawable.background3;
            textColor = R.color.textBlue;
        }
    }

    private void onPhotoSelected(ActivityResult activityResult) {
        if (activityResult.getData() == null)
            return;

        selectedImage = activityResult.getData().getData();
        ImageView galleryImageView = findViewById(R.id.galleryImage);
        galleryImageView.setImageURI(selectedImage);
    }

    public void startImageSelect(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerLauncher.launch(intent);
    }

    public void generatePostcard(View view) {
        Intent intent = new Intent(MainActivity.this, PostcardActivity.class);
        String title = getInputText(R.id.titleInput);
        String text = getInputText(R.id.textInput);

        CheckBox dateCheckbox = findViewById(R.id.dateCheckbox);
        boolean showDate = dateCheckbox.isChecked();

        PostcardData postcard = new PostcardData(title, text, background, textColor, showDate, selectedImage);
        postcards.add(postcard);
        intent.putExtra("postcard", postcard);

        startActivity(intent);
    }

    public void openList(View view) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("postcards", postcards);
        startActivity(intent);
    }

    public void sendEmail(View view){
        String title =getInputText(R.id.titleInput);
        String text = getInputText(R.id.textInput);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String getInputText(int id) {
        EditText titleInput = findViewById(id);
        return titleInput.getText().toString();
    }
}