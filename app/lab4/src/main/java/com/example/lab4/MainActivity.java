package com.example.lab4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> dataReturnLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataReturnLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::onResultFromDataActivity
        );
    }

    public void sendDataWithRotateAnimation(View view){
        sendData();
        overridePendingTransition(R.anim.rotate_in_right, R.anim.rotate_out_left);
    }

    public void sendDataWithSlideAnimation(View view){
        sendData();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void sendData(){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();

        EditText textEdit = findViewById(R.id.editText);
        EditText numberEdit = findViewById(R.id.editNumber);
        CheckBox dateCheckbox = findViewById(R.id.dataCheckbox);

        bundle.putString("text", textEdit.getText().toString());
        bundle.putInt("number", Integer.parseInt(numberEdit.getText().toString()));
        bundle.putBoolean("showDate", dateCheckbox.isChecked());
        intent.putExtras(bundle);

        dataReturnLauncher.launch(intent);
    }

    private void onResultFromDataActivity(ActivityResult result){
        if (result.getResultCode() != SecondActivity.RESULT_OK)
            return;

        Intent data = result.getData();
        if (data == null)
            return;

        String message = data.getStringExtra("message");
        TextView messageView = findViewById(R.id.messageView);
        String viewText = getString(R.string.message_text, message);
        messageView.setText(viewText);
    }

    public void openOtherApp(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage("com.android.chrome");
        startActivity(intent);
    }

    public void openWebsite(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/"));
        startActivity(intent);
    }
}