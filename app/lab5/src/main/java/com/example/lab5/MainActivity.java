package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String socialStatus = "Студент";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextBirthday = findViewById(R.id.editTextBirthday);

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("Name", editTextName.getText().toString());
        intent.putExtra("Birthday", editTextBirthday.getText().toString());
        intent.putExtra("Status", socialStatus);

        startActivity(intent);
    }

    public void onRadioClick(View view) {
        int id = view.getId();
        if (id == R.id.radioStudent){
            socialStatus = "Студент";
        } else if (id == R.id.radioSchoolboy){
            socialStatus = "Школьник";
        } else if (id == R.id.radioEngineer){
            socialStatus = "Инженер";
        }
    }

    public void sendEmail(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"university@yandex.ru"});

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}