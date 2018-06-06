package com.example.jeongjimin.mysubway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button PathfindButton;
    private Button SubwaymapButton;
    private Button ExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PathfindButton = findViewById(R.id.PathFindButton);
        SubwaymapButton = findViewById(R.id.SubwayMapButton);
        ExitButton = findViewById(R.id.ExitButton);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.PathFindButton:
                Intent Pathfindintent = new Intent(this, PathFindActivity.class);
                startActivity(Pathfindintent);
                break;

            case R.id.SubwayMapButton:
                Intent Mapintent = new Intent(this, SubwayMapActivity.class);
                startActivity(Mapintent);
                break;

            case R.id.ExitButton:
                finish();
                break;
        }

    }

}

