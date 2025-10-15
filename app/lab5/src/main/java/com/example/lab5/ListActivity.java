package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        fillList();
    }

    private void fillList() {
        LinearLayout postcardsContainer = findViewById(R.id.postcardContainer);
        Serializable rawData = getIntent().getSerializableExtra("postcards");
        ArrayList<?> postcards = (ArrayList<?>) rawData;
        if (postcards == null)
            return;

        for (int i = 0; i < postcards.size(); i++){
            PostcardData postcardData = (PostcardData) postcards.get(i);
            Button postcard = new Button(this);
            postcard.setOnClickListener((view) -> openPostcard(postcardData));
            postcard.setText(postcardData.getTitle());
            postcardsContainer.addView(postcard);
        }
    }

    private void openPostcard(PostcardData postcard){
        Intent intent = new Intent(ListActivity.this, PostcardActivity.class);
        intent.putExtra("postcard", postcard);
        startActivity(intent);
    }

    public void closeActivity(View view) {
        finish();
    }
}