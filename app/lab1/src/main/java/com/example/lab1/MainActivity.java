package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView TextViewHello;
    private EditText EditTextName;
    private ImageView DogImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextViewHello = findViewById(R.id.textView);
        EditTextName = findViewById(R.id.editText);
        DogImageView = findViewById(R.id.imageView);
    }

    public void onClick(View view) {
        if (EditTextName.getText().length() == 0) {
            TextViewHello.setText(R.string.response_default);
        } else {
            String response = getString(R.string.response_with_name, EditTextName.getText());
            TextViewHello.setText(response);
        }
    }

    public void rotateImage(View view) {
        DogImageView.setRotation(DogImageView.getRotation() + 15);
    }
}