package com.ds.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imageHome;
    Button button_upload;
    Button button_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageHome = findViewById(R.id.imageHome);
        button_upload = findViewById(R.id.btn_upload);
        button_results = findViewById(R.id.btn_results);

        button_upload.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), UploadActivity.class);
            startActivity(intent);
        });

        button_results.setOnClickListener(view -> {
            if (DefaultApplication.getUsername().equals("")) {
                openDialog();
            }
            else {
                Intent intent = new Intent(view.getContext(), StatisticsUserActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openDialog() {
        StatisticsDialog statisticsDialog = new StatisticsDialog();
        statisticsDialog.show(getSupportFragmentManager(), "user_statistics pop-up");
    }

}