package com.example.lab4;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        LayoutParams matchParentParam = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        LayoutParams wrapContentParam = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        setContentView(linearLayout, matchParentParam);

        TextView textView = new TextView(this);
        textView.setText("TextView");
        textView.setLayoutParams(wrapContentParam);
        linearLayout.addView(textView);

        Button button = new Button(this);
        button.setText("Button");
        linearLayout.addView(button, wrapContentParam);

        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftMarginParams.leftMargin = 50;
        Button button1 = new Button(this);
        button1.setText("Button 1");
        button1.callOnClick();
        linearLayout.addView(button1, leftMarginParams);

        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity = Gravity.RIGHT;

        Button button2 = new Button(this);
        button2.setText("Button 2");
        linearLayout.addView(button2, rightGravityParams);
    }
}