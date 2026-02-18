package com.example.lab3;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText textView;
    private ImageView photoView;
    private Bitmap photo;
    private ActivityResultLauncher<Intent> photoPickerLauncher;
    private ActivityResultLauncher<Intent> videoRecorderLauncher;

    private final String LOG_TAG = "myLogs";
    private final String FILENAME = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        findViewById(R.id.fileSaveButton).setOnClickListener(view -> saveFile());
        findViewById(R.id.fileLoadButton).setOnClickListener(view -> loadFile());
        findViewById(R.id.pdfSaveButton).setOnClickListener(view -> savePDF());
        findViewById(R.id.photoTakeButton).setOnClickListener(view -> takePhoto());
        findViewById(R.id.photoSaveButton).setOnClickListener(view -> savePhoto());
        findViewById(R.id.videoTakeButton).setOnClickListener(view -> takeVideo());

        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::onPhotoSelected
        );
        photoView = findViewById(R.id.photoView);
        videoRecorderLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::onVideoRecorded
        );
    }

    private void saveFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)
            ));
            writer.write(textView.getText().toString());
            writer.close();

            Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    private void loadFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)
            ));
            String text = reader.readLine();
            textView.setText(text);

            Toast.makeText(this, R.string.load_message, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    private void savePDF() {
        PdfDocument pdfDocument = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                595, 842, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setTextSize(16);

        int x = 40;
        int y = 50;
        canvas.drawText(textView.getText().toString(), x, y, paint);
        pdfDocument.finishPage(page);

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, FILENAME + ".pdf");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        ContentResolver resolver = getContentResolver();
        Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), values);
        if (uri == null)
            return;

        try {
            OutputStream stream = resolver.openOutputStream(uri);
            if (stream == null)
                return;

            pdfDocument.writeTo(stream);
            pdfDocument.close();
            stream.close();
            Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoPickerLauncher.launch(intent);
    }


    private void onPhotoSelected(ActivityResult activityResult) {
        if (activityResult.getData() == null)
            return;

        if (activityResult.getData().getExtras() == null)
            return;

        photo = (Bitmap) activityResult.getData().getExtras().get("data");
        photoView.setImageBitmap(photo);
    }

    private void savePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, FILENAME + ".png");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        ContentResolver resolver = getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri == null)
            return;

        try {
            OutputStream stream = resolver.openOutputStream(uri);
            if (stream == null)
                return;

            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Toast.makeText(this, R.string.photo_message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    private void takeVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        videoRecorderLauncher.launch(intent);
    }

    private void onVideoRecorded(ActivityResult activityResult) {
        Toast.makeText(this, R.string.video_message, Toast.LENGTH_SHORT).show();
    }
}