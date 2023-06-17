package com.ds.android;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    ImageView load_image;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        initializeUser();

        load_image = findViewById(R.id.loading_image);
        progressBar = findViewById(R.id.loading_progressBar);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        load_image.startAnimation(animation);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressAnimation();

    }

    public void progressAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, 0f, 100f);
        anim.setDuration(3000);
        progressBar.setAnimation(anim);
    }

    void initializeUser(){
        DefaultApplication.setUsername("");
    }

}
