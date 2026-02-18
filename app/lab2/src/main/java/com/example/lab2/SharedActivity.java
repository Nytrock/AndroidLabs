package com.example.lab2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SharedActivity extends AppCompatActivity {
    private EditText textEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        textEdit = findViewById(R.id.sharedTextEdit);
        findViewById(R.id.sharedSaveButton).setOnClickListener(view -> saveSharedData());
        findViewById(R.id.sharedCloseButton).setOnClickListener(view -> closeActivity());

        loadSharedData();
    }

    private void loadSharedData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_FILENAME, MODE_PRIVATE);
        String sharedString = sharedPreferences.getString(MainActivity.SHARED_KEY, "");
        textEdit.setText(sharedString);

        Toast.makeText(this, R.string.load_message, Toast.LENGTH_SHORT).show();
    }

    private void saveSharedData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MainActivity.SHARED_KEY, textEdit.getText().toString());
        editor.apply();
        Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();
    }

    private void closeActivity() {
        finish();
    }
}
