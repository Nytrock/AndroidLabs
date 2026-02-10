package com.example.lab3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = "myLogs";
    private final String FILENAME = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnWrite)
            writeFile();
        else if (view.getId() == R.id.btnRead)
            readFile();
    }

    private void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)
            ));
            String str;
            while ((str = reader.readLine()) != null)
                Log.d(LOG_TAG, str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)
            ));
            writer.write("Содержимое файла");
            writer.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}