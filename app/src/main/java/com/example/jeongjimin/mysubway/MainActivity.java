package com.example.jeongjimin.mysubway;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*메인 액티비티, 버튼3개가 각각의 id에 따라 역할 수행, 길찾기, 전체 노선도 보여주기, 나가기*/
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

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("종료 하시겠습니까??")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                builder.create();
                builder.show();
                break;
        }

    }

}

