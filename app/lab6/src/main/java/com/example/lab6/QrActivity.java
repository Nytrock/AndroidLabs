package com.example.lab6;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class QrActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        Intent intent = getIntent();
        ImageView qrView = findViewById(R.id.qrView);

        byte[] byteArray = intent.getByteArrayExtra(MainActivity.QR_KEY);
        if (byteArray == null)
            return;

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        qrView.setImageBitmap(bitmap);
    }

    public void closeActivity(View view){
        finish();
    }
}
