package com.example.lab5;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private final String[] names = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь"};
    private final String[] positions = {"Программист", "Бухгалтер", "Программист", "Программист",
            "Бухгалтер", "Директор", "Программист", "Охранник"};
    private final int[] salaries = {130000, 80000, 130000, 130000, 80000, 40000, 130000, 80000};
    private final int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        LinearLayout linearLayout = findViewById(R.id.linLayout);
        LayoutInflater layoutInflater = getLayoutInflater();

        for (int i = 0; i < names.length; i++){
            Log.d("My logs", "i = " + i);

            View item = layoutInflater.inflate(R.layout.item, linearLayout, false);
            TextView nameView = findViewById(R.id.nameView);
            TextView positionView = findViewById(R.id.positionView);
            TextView salaryView = findViewById(R.id.salaryView);

            nameView.setText(names[i]);
            positionView.setText("Должность: " + positions[i]);
            salaryView.setText("Оклад: " + salaries[i]);

            item.setBackgroundColor(colors[i % 2]);
            linearLayout.addView(item);
        }
    }
}