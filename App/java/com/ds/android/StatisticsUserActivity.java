package com.ds.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsUserActivity extends AppCompatActivity {
    ImageView imageRouteStats;
    TextView text_user_title;
    TextView text_user_vs_users_title;
    TextView text_user_results;
    TextView text_user_vs_users_results;
    Button button_return_home;
    Handler myHandler;
    String[] results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_user);

        imageRouteStats = findViewById(R.id.imageUserStats);
        text_user_title = findViewById(R.id.text_user_stat_title);
        text_user_results = findViewById(R.id.text_stats_user_stat_results);
        text_user_vs_users_title = findViewById(R.id.text_user_stat_vs_users_title);
        text_user_vs_users_results = findViewById(R.id.text_user_stat_vs_users_results);
        button_return_home = findViewById(R.id.btn_go_home);


        myHandler = new Handler(Looper.getMainLooper(), message -> {

            results = message.getData().getStringArray("User results");

            String UserText = createUserText(results);
            String CompareUsersText = createCompareUsersText(results);

            text_user_results.setText(UserText);
            text_user_vs_users_results.setText(CompareUsersText);

            return true;
        });

        StatisticsUserThread statisticsUserThread = new StatisticsUserThread(myHandler, DefaultApplication.getUsername());
        statisticsUserThread.start();
        try {
            statisticsUserThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        button_return_home.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    private String createUserText(String[] results) {
        return ("Your total time: " + Long.parseLong(results[0]) / 1000 / 60 + " min\n" +
                "Your total distance: " + Math.floor((Double.parseDouble(results[1]) / 1000) * 100) / 100 + " km\n" +
                "Your total ascent: " + Math.floor(Float.parseFloat(results[2]) * 100) / 100 + " m\n" +
                "Your average time: " + Long.parseLong(results[3]) / 1000 / 60 + " min\n" +
                "Your average distance: " + Math.floor((Double.parseDouble(results[4]) / 1000) * 100) / 100 + " m\n" +
                "Your average ascent: " + Math.floor(Float.parseFloat(results[5]) * 100) / 100 + " m");
    }

    private String createCompareUsersText(String[] results) {
        String text = "";
        if (Integer.parseInt(results[6]) < 0) {
            text += ("You rαn " + Math.abs(Integer.parseInt(results[6])) + "% less minutes than the average\n");
        }
        else {
            text += ("You rαn " + Integer.parseInt(results[6]) + "% more minutes than the average\n");
        }
        if (Integer.parseInt(results[7]) < 0) {
            text += ("You rαn " + Math.abs(Integer.parseInt(results[7])) + "% less distance than the average\n");
        }
        else {
            text += ("You rαn " + Integer.parseInt(results[7]) + "% more distance than the average\n");
        }
        if (Integer.parseInt(results[8]) < 0) {
            text += ("You ascended " + Math.abs(Integer.parseInt(results[8])) + "% less than the average\n");
        }
        else {
            text += ("You ascended " + Integer.parseInt(results[8]) + "% more than the average");
        }
        return text;
    }

}
