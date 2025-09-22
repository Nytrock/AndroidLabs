package com.example.lab3;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editNumber1;
    private EditText editNumber2;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNumber1 = findViewById(R.id.edit_number1);
        editNumber2 = findViewById(R.id.edit_number2);
        textResult = findViewById(R.id.text_result);
    }

    public void onClick(View view) {
        String EditNumber1Text = editNumber1.getText().toString();
        String EditNumber2Text = editNumber2.getText().toString();
        if (TextUtils.isEmpty(EditNumber1Text) || TextUtils.isEmpty(EditNumber2Text)){
            return;
        }

        double Number1 = Float.parseFloat(EditNumber1Text);
        double Number2 = Float.parseFloat(EditNumber2Text);
        double Result;
        char Operation;

        if (view.getId() == R.id.button_plus) {
            Operation = '+';
            Result = Number1 + Number2;
        } else if (view.getId() == R.id.button_minus) {
            Operation = '-';
            Result = Number1 - Number2;
        } else if (view.getId() == R.id.button_mult) {
            Operation = '*';
            Result = Number1 * Number2;
        } else if (view.getId() == R.id.button_div) {
            Operation = '/';
            Result = Number1 / Number2;
        } else {
            Operation = '?';
            Result = 0;
        }

        String resultString = getString(R.string.result_text, Number1, Operation, Number2, Result);
        textResult.setText(resultString);
    }
}