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
    private String StartStationTxt;
    private EditText DestiStationEdit;
    private String DestiStationTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_find);

        SearchButton = findViewById(R.id.SearchButton);
        StartStationEdit = findViewById(R.id.StartStationText);
        DestiStationEdit = findViewById(R.id.DestiStationText);
    }

    public void onClick(View view){
        StartStationTxt = String.valueOf(StartStationEdit.getText());
        DestiStationTxt = String.valueOf(DestiStationEdit.getText());

        Intent Pathfindcomputeintent = new Intent(this, PathFindComputeActivity.class);
        Pathfindcomputeintent.putExtra("StartStation",StartStationTxt);
        Pathfindcomputeintent.putExtra("DestiStation",DestiStationTxt);
        startActivity(Pathfindcomputeintent);
    }
}
