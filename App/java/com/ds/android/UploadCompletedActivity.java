package com.ds.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UploadCompletedActivity extends AppCompatActivity {
    ImageView image_test;
    TextView text_completed_upload;
    Button button_new_upload;
    Button button_route_statistics;
    Button button_return_home;
    String filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_completed);

        image_test = findViewById(R.id.imageCompleted);
        text_completed_upload = findViewById(R.id.text_completed);
        button_new_upload = findViewById(R.id.btn_new_upload);
        button_route_statistics = findViewById(R.id.btn_route_statistics);
        button_return_home = findViewById(R.id.btn_back_home);

        Bundle bundle = getIntent().getExtras();
        filename = bundle.getString("filename", "Default");


        button_new_upload.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), UploadActivity.class);
            startActivity(intent);
        });

        button_route_statistics.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), StatisticsRouteActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("filename", filename);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        button_return_home.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}
