package com.example.lab4;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();
        TextView buffer;

        buffer = findViewById(R.id.textResult);
        buffer.setText(intent.getStringExtra("text"));

        buffer = findViewById(R.id.numberResult);
        int number = intent.getIntExtra("number", 0);
        buffer.setText(String.valueOf(number));

        buffer = findViewById(R.id.dataResult);

        if (intent.getBooleanExtra("showDate", false)){
            buffer.setVisibility(VISIBLE);
        } else {
            buffer.setVisibility(INVISIBLE);
        }

        Date currentTime = Calendar.getInstance().getTime();
        String dateText = getString(R.string.date_result, currentTime.toString());
        buffer.setText(dateText);
    }

    public void returnData(View view) {
        EditText textInput = findViewById(R.id.messageEdit);
        String message = textInput.getText().toString();

        if (message.isEmpty())
            return;

        Intent result = new Intent();
        result.putExtra("message", message);
        setResult(RESULT_OK, result);
        finish();
    }
}
