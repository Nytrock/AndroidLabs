package com.example.lab2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private Button buttonLoad, buttonSave;

    private SharedPreferences preferences;
    private final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.etText);
        buttonLoad = findViewById(R.id.btnLoad);
        buttonSave = findViewById(R.id.btnSave);

        buttonSave.setOnClickListener(this);
        buttonLoad.setOnClickListener(this);

        loadText();
    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.btnSave)
            saveText();
        else if (button.getId() == R.id.btnLoad)
            loadText();
    }

    private void saveText() {
        preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_TEXT, editText.getText().toString());
        editor.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    private void loadText() {
        preferences = getPreferences(MODE_PRIVATE);
        String savedText = preferences.getString(SAVED_TEXT, "");
        editText.setText(savedText);
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        saveText();
        super.onDestroy();
    }
}