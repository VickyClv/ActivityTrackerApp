package com.ds.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class UploadDialog extends AppCompatDialogFragment {
    final String filename;
    Handler myHandler;
    final String readFile;
    UploadDialog(String filename, String readFile) {
        this.filename = filename;
        this.readFile = readFile;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Upload File").setMessage("Are you sure you want to upload " + filename + "?").setPositiveButton("YES", (dialog, which) -> {
            myHandler = new Handler(Looper.getMainLooper(), message -> {

                String username = message.getData().getString("username");
                DefaultApplication.setUsername(username);

                return true;
            });
            UploadThread uploadThread = new UploadThread(myHandler, filename, readFile);
            uploadThread.start();

            Intent intent = new Intent(getContext(), UploadCompletedActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("filename", filename);
            intent.putExtras(bundle);
            startActivity(intent);
        }).setNegativeButton("NO", (dialog, which) -> {

        });
        return builder.create();
    }
}
