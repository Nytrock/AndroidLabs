package com.example.lab5;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        String buffer;
        TextView textView;

        buffer = getIntent().getStringExtra("Name");
        textView = findViewById(R.id.nameResult);
        textView.setText(buffer);

        buffer = getIntent().getStringExtra("Birthday");
        textView = findViewById(R.id.birthdayResult);
        textView.setText(buffer);

        buffer = getIntent().getStringExtra("Status");
        textView = findViewById(R.id.statusResult);
        textView.setText(buffer);
    }

    public void closeActivity(View view) {
        finish();
    }
}
