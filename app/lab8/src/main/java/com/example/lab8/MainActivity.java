package com.example.lab8;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LandscapeView landscape = new LandscapeView(this);
        setContentView(landscape);
    }
}