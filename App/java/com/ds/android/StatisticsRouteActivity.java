package com.ds.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StatisticsRouteActivity extends AppCompatActivity {
    ImageView imageRouteStats;
    TextView text_route_title;
    TextView text_user_title;
    TextView text_user_vs_users_title;
    TextView text_route_results;
    TextView text_user_results;
    TextView text_user_vs_users_results;
    Button button_return_home;
    Handler myHandler;
    String filename;
    String[] results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_route);

        imageRouteStats = findViewById(R.id.imageRouteResults);
        text_route_title = findViewById(R.id.text_route_title);
        text_route_results = findViewById(R.id.text_stats_route_results);
        text_user_title = findViewById(R.id.text_user_title);
        text_user_results = findViewById(R.id.text_stats_user_results);
        text_user_vs_users_title = findViewById(R.id.text_user_vs_users_title);
        text_user_vs_users_results = findViewById(R.id.text_stats_user_vs_users_results);
        button_return_home = findViewById(R.id.btn_home);

        Bundle bundle = getIntent().getExtras();
        filename = bundle.getString("filename", "Default");

        myHandler = new Handler(Looper.getMainLooper(), message -> {

            results = message.getData().getStringArray("Route and User results");

            String RouteText = createRouteText(results);
            String UserText = createUserText(results);
            String CompareUsersText = createCompareUsersText(results);

            text_route_results.setText(RouteText);
            text_user_results.setText(UserText);
            text_user_vs_users_results.setText(CompareUsersText);

            return true;
        });

        StatisticsRouteThread statisticsRouteThread = new StatisticsRouteThread(filename, myHandler, DefaultApplication.getUsername());
        statisticsRouteThread.start();
        try {
            statisticsRouteThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        button_return_home.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
    }


    private String createRouteText(String[] results) {
        return ("Your total distance: " + Math.floor((Double.parseDouble(results[0]) / 1000) * 100) / 100 + " km\n" +
                "Your average speed:" + (int) Double.parseDouble(results[1]) + " m/min\n" +
                "Your total ascent: " + Math.floor(Float.parseFloat(results[2]) * 100) / 100 + " m\n" +
                "Your total time: " + Long.parseLong(results[3]) / 1000 / 60 + " min");
    }

    private String createUserText(String[] results) {
        return ("Your total time: " + Long.parseLong(results[4]) / 1000 / 60 + " min\n" +
                "Your total distance: " + Math.floor((Double.parseDouble(results[5]) / 1000) * 100) / 100 + " km\n" +
                "Your total ascent: " + Math.floor(Float.parseFloat(results[6]) * 100) / 100 + " m\n" +
                "Your average time: " + Long.parseLong(results[7]) / 1000 / 60 + " min\n" +
                "Your average distance: " + Math.floor((Double.parseDouble(results[8]) / 1000) * 100) / 100 + " m\n" +
                "Your average ascent: " + Math.floor(Float.parseFloat(results[9]) * 100) / 100 + " m");
    }

    private String createCompareUsersText(String[] results) {
        String text = "";
        if (Integer.parseInt(results[10]) < 0) {
            text += ("You rαn " + Math.abs(Integer.parseInt(results[10])) + "% less minutes than the average\n");
        }
        else {
            text += ("You rαn " + Integer.parseInt(results[10]) + "% more minutes than the average\n");
        }
        if (Integer.parseInt(results[11]) < 0) {
            text += ("You rαn " + Math.abs(Integer.parseInt(results[11])) + "% less distance than the average\n");
        }
        else {
            text += ("You rαn " + Integer.parseInt(results[11]) + "% more distance than the average\n");
        }
        if (Integer.parseInt(results[12]) < 0) {
            text += ("You ascended " + Math.abs(Integer.parseInt(results[12])) + "% less than the average\n");
        }
        else {
            text += ("You ascended " + Integer.parseInt(results[12]) + "% more than the average");
        }
        return text;
    }
}