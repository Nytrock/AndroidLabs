package com.example.lab6;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public final String FILE_NAME = "lab6";
    public static final String DATA_KEY = "TEXT";
    public static final String QR_KEY = "QR";
    public TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.SUCCESS)
                return;

            textToSpeech.setLanguage(Locale.getDefault());
        });
    }

    public void sendToOtherActivity(View view) {
        Intent intent = new Intent(MainActivity.this, DataActivity.class);
        intent.putExtra(DATA_KEY, getText());
        startActivity(intent);
    }

    public void writeToFile(View view) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, FILE_NAME);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        Uri uri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
        if (uri == null)
            return;

        try {
            OutputStream stream = getContentResolver().openOutputStream(uri);
            if (stream == null)
                return;

            stream.write(getText().getBytes());
            stream.close();
        } catch (IOException e){
            Log.e("File", e.toString());
        }
    }

    public void copyToClipboard(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("text", getText());
        clipboardManager.setPrimaryClip(data);
    }

    public void turnToQR(View view) {
        Bitmap bitmap = null;
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            BitMatrix bitMatrix = barcodeEncoder.encode(getText(), BarcodeFormat.QR_CODE, 500, 500);
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            Log.e("QR", e.toString());
        }

        if (bitmap == null)
            return;

        Intent intent = new Intent(MainActivity.this, QrActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] qr = stream.toByteArray();

        intent.putExtra(QR_KEY, qr);
        startActivity(intent);
    }

    public void addVoiceover(View view) {
        textToSpeech.speak(getText(), TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getText());

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    private String getText(){
        EditText textInput = findViewById(R.id.editText);
        return textInput.getText().toString();
    }
}