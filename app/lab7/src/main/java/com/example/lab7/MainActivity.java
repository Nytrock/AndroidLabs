package com.example.lab7;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.names, android.R.layout.simple_list_item_multiple_choice);
        listView.setAdapter(adapter);

        Button buttonCheck = findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(this);
        names = getResources().getStringArray(R.array.names);
    }


    @Override
    public void onClick(View v) {
        String LOG_TAG = "myLogs";
        Log.d(LOG_TAG, "checked: ");
        SparseBooleanArray checkArray = listView.getCheckedItemPositions();

        for (int i = 0; i < checkArray.size(); i++) {
            int key = checkArray.keyAt(i);
            if (checkArray.get(key))
                Log.d(LOG_TAG, names[key]);
        }
    }
}