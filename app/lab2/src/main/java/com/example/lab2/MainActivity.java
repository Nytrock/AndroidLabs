package com.example.lab2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText stringEdit;
    private EditText integerEdit;
    private CheckBox booleanEdit;
    private TextView sharedText;

    private final String STRING_KEY = "saved_string";
    private final String INTEGER_KEY = "saved_int";
    private final String BOOLEAN_KEY = "saved_bool";

    public final static String SHARED_FILENAME = "shared_data";
    public final static String SHARED_KEY = "saved_shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringEdit = findViewById(R.id.textEdit);
        integerEdit = findViewById(R.id.numberEdit);
        booleanEdit = findViewById(R.id.booleanEdit);
        sharedText = findViewById(R.id.sharedTextView);

        findViewById(R.id.saveButton).setOnClickListener(view -> saveData());
        findViewById(R.id.loadButton).setOnClickListener(view -> loadData());
        findViewById(R.id.resetButton).setOnClickListener(view -> cleanData());
        findViewById(R.id.sharedLoadButton).setOnClickListener(view -> loadSharedData());
        findViewById(R.id.sharedOpenButton).setOnClickListener(view -> openSharedActivity());
    }

    private void saveData() {
        String integer = integerEdit.getText().toString();
        if (integer.isEmpty())
            integer = "0";

        SharedPreferences mainPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mainPreferences.edit();
        editor.putString(STRING_KEY, stringEdit.getText().toString());
        editor.putInt(INTEGER_KEY, Integer.parseInt(integer));
        editor.putBoolean(BOOLEAN_KEY, booleanEdit.isChecked());
        editor.apply();

        Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        SharedPreferences mainPreferences = getPreferences(MODE_PRIVATE);
        String savedText = mainPreferences.getString(STRING_KEY, "");
        int savedInteger = mainPreferences.getInt(INTEGER_KEY, 0);
        boolean savedBoolean = mainPreferences.getBoolean(BOOLEAN_KEY, false);

        stringEdit.setText(savedText);
        integerEdit.setText(String.valueOf(savedInteger));
        booleanEdit.setChecked(savedBoolean);

        Toast.makeText(this, R.string.load_message, Toast.LENGTH_SHORT).show();
    }

    private void cleanData() {
        stringEdit.setText("");
        integerEdit.setText("0");
        booleanEdit.setChecked(false);

        Toast.makeText(this, R.string.reset_message, Toast.LENGTH_SHORT).show();
    }

    private void loadSharedData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_FILENAME, MODE_PRIVATE);
        String sharedString = sharedPreferences.getString(SHARED_KEY, "");
        sharedText.setText(sharedString);

        Toast.makeText(this, R.string.load_message, Toast.LENGTH_SHORT).show();
    }

    private void openSharedActivity() {
        Intent intent = new Intent(MainActivity.this, SharedActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }
}