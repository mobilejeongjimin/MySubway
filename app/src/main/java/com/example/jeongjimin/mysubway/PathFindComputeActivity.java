package com.example.jeongjimin.mysubway;

import android.app.Fragment;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.net.URLEncoder;

//PathFindActivity로부터 데이터를 전송 받고 최단거리를 계산하며 이를 화면으로 표현하는 기능 수행
//fragment를 표현해서 2개 영역으로 구분

public class PathFindComputeActivity extends AppCompatActivity {

    private String StartStation;
    private String DestiStation;
    private String data;
    private String Key = "704c42506963686f3231696c676469";

    private String TakeTime;                    //걸리는 시간 저장
    private String StationnNumber;              //거치는 역 갯수 저장
    private String TrensferStationnNm;          //환승역 갯수 저장
    private String StationName;                 //중간 역들의 이름 저장

    private Button FragmentPathFindButton;
    private Button FragmentMapButton;

    private TextView textview;

    private TextView StartStationTxt;
    private TextView DistiStationTxt;
    private TextView TakeTimeTxt;
    private TextView StationNumberTxt;
    private TextView TransferNumberTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_find_compute);

        StrictMode.enableDefaults();

        Intent intent = getIntent();
        StartStation = intent.getStringExtra("StartStation");
        DestiStation = intent.getStringExtra("DestiStation");

        FragmentPathFindButton = findViewById(R.id.view_pathfind);
        FragmentMapButton = findViewById(R.id.view_map);

        boolean inrow = false;
        boolean inshtStatnNm = false, inshtStatnCnt = false, inshtTravelTm = false, inshtTransferCnt = false;

        String shtStatnNm = null, shtStatnCnt = null, shtTravelTm = null, shtTransferCnt = null;

        try{

            URL url = new URL("http://swopenapi.seoul.go.kr/api/subway/72574f735463686f36344f666b6645" +
                    "/xml/shortestRoute/0/1/"+StartStation+"/"+DestiStation); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT){

                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행

                        if(parser.getName().equals("shtStatnNm")){ //가는 경로의 역 이름들
                            inshtStatnNm = true;
                        }

                        if(parser.getName().equals("shtStatnCnt")){ //최단시간거치는 역 개수
                            inshtStatnCnt = true;
                        }

                        if(parser.getName().equals("shtTravelTm")){ //최단시간 걸리는 시간
                            inshtTravelTm = true;
                        }

                        if(parser.getName().equals("shtTransferCnt")){ //최단시간 환승정보
                            inshtTransferCnt = true;
                        }

                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력

                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때

                        if(inshtStatnNm){ //가는 경로의 역 이름들 저장
                            shtStatnNm = parser.getText();
                            inshtStatnNm = false;
                        }

                        if(inshtStatnCnt){ //최단시간거치는 역 개수 저장
                            shtStatnCnt = parser.getText();
                            inshtStatnCnt = false;
                        }

                        if(inshtTravelTm){ //최단시간 걸리는 시간 저장
                            shtTravelTm = parser.getText();
                            inshtTravelTm = false;
                        }

                        if(inshtTransferCnt){  //최단시간 환승정보 저장
                            shtTransferCnt = parser.getText();
                            inshtTransferCnt = false;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("row")){
                            TakeTime = shtTravelTm.toString();
                            StationnNumber = shtStatnCnt.toString();
                            TrensferStationnNm = shtTransferCnt.toString();
                            StationName = shtStatnNm.toString();

                            inrow = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){

        }

        SelectFragment(FragmentPathFindButton);

    }

    public void onClick(View view) {

        SelectFragment(view);

    }

    public void onClickFinish(View view) {
        finish();
    }

    public void SelectFragment(View view) {

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

        /*파싱한 데이터 번들에 담아서 프래그먼트로 넘겨주기*/

        bundle.putString("FragStartStation", StartStation);
        bundle.putString("FragDestiStation", DestiStation);
        bundle.putString("FragTakeTime",TakeTime);
        bundle.putString("FragsationNumber",StationnNumber);
        bundle.putString("FragtransferStation",TrensferStationnNm);
        bundle.putString("FragStationName",StationName);

        fragment.setArguments(bundle);

        getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
