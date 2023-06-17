package com.ds.android;

import android.content.Context;
import android.content.Intent;
import  android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class ProgressBarAnimation extends Animation{

    private final Context context;
    private final ProgressBar progressbar;
    private final float from;
    private final float to;

    public ProgressBarAnimation(Context context, ProgressBar progressBar, float from, float to){
        this.context = context;
        this.progressbar = progressBar;
        this.from= from;
        this.to = to;
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressbar.setProgress((int) value);

        if (value == to) {
            context.startActivity(new Intent(context, MainActivity.class));
        }

    }
}