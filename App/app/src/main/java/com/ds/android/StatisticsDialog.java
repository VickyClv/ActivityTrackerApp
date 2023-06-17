package com.ds.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class StatisticsDialog extends AppCompatDialogFragment {
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("No user").setMessage("No data yet").setPositiveButton("OK", (dialog, which) -> {
        });
        return builder.create();
    }
}
