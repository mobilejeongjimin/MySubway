package com.example.jeongjimin.mysubway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PathFindActivity extends AppCompatActivity {

    private Button SearchButton;
    private EditText StartStationEdit;
    private EditText DestiStationEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_find);

        SearchButton = findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            //fragment로 데이터 보내주기
            }
        });

        StartStationEdit = findViewById(R.id.StartStationText);
        DestiStationEdit = findViewById(R.id.DestiStationText);
    }
}
