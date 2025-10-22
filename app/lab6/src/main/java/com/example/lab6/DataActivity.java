package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        TextView dataView = findViewById(R.id.dataView);
        dataView.setText(getIntent().getStringExtra(MainActivity.DATA_KEY));
    }

    public void closeActivity(View view){
        finish();
    }
}
