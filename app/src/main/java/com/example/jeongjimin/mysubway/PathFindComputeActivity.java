package com.example.jeongjimin.mysubway;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.FrameMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

//PathFindActivity로부터 데이터를 전송 받고 최단거리를 계산하며 이를 화면으로 표현하는 기능 수행
//fragment를 표현해서 2개 영역으로 구분

public class PathFindComputeActivity extends AppCompatActivity {

    private String StartStation;
    private String DestiStation;

    private Button FragmentPathFindButton;
    private Button FragmentMapButton;

    private TextView StartStationTxt;
    private TextView DistiStationTxt;
    private TextView TakeTimeTxt;
    private TextView StationNumberTxt;
    private TextView TransferNumberTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_find_compute);

        Intent intent = getIntent();
        StartStation = intent.getStringExtra("StartStation");
        DestiStation = intent.getStringExtra("DestiStation");

        FragmentPathFindButton = findViewById(R.id.view_pathfind);
        FragmentMapButton = findViewById(R.id.view_map);

        SelectFragment(FragmentPathFindButton);
    }

    public void onClick(View view) {

        SelectFragment(view);
    }

    public void onClickFinish(View view){
        finish();
    }

    public void SelectFragment(View view){

        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.view_pathfind:
                fragment = new FragmentPathFind();
                break;

            case R.id.view_map:
                fragment = new FragmentMap();
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putString("FragStartStation",StartStation);
        bundle.putString("FragDestiStation",DestiStation);
        fragment.setArguments(bundle);

        getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }




}


