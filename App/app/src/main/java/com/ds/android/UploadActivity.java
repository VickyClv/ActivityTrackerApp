package com.ds.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class UploadActivity extends AppCompatActivity {

    ImageView upload_image;
    TextView upload_text;
    Button button_storage;
    Button button_return_home;
    private static final int REQUEST_CODE = 1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        upload_image = findViewById(R.id.imageUpload);
        upload_text = findViewById(R.id.upload_text);
        button_storage = findViewById(R.id.btn_storage);
        button_return_home = findViewById(R.id.btn_return_home);

    }

    @Override
    protected void onStart() {
        super.onStart();

        button_storage.setOnClickListener(view -> openDirectory());

        button_return_home.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    public void openDirectory() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData == null) {
                return;
            }
            Uri uri = resultData.getData();

            String contents;
            try {
                contents = readTextFromUri(uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            openDialog(getFileName(uri, getApplicationContext()), contents);
        }

    }

    @SuppressLint("Range")
    public String getFileName(Uri uri, Context context) {
        String res = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                assert cursor != null;
                cursor.close();
            }

            if (res == null) {
                res = uri.getPath();
                int cutT = res.lastIndexOf('/');
                if (cutT != -1) {
                    res = res.substring(cutT + 1);
                }
            }
        }
        return res;
    }

    public void openDialog(String filename, String readFile) {
        UploadDialog uploadDialog = new UploadDialog(filename, readFile);
        uploadDialog.show(getSupportFragmentManager(), "upload pop-up");
    }

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }
        return stringBuilder.toString();
    }

}
