package com.example.jeongjimin.mysubway;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//PathFindActivity로부터 데이터를 전송 받고 최단거리를 계산하며 이를 화면으로 표현하는 기능 수행
//fragment를 표현해서 2개 영역으로 구분

public class PathFindComputeActivity extends AppCompatActivity {

    private String StartStation;
    private String DestiStation;
    private String data;
    private String Key = "704c42506963686f3231696c676469";

    private String TakeTime;
    private String StationnNm;
    private String TrensferStationnNm;
    private String StationName;

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

        Intent intent = getIntent();
        StartStation = intent.getStringExtra("StartStation");
        DestiStation = intent.getStringExtra("DestiStation");

        FragmentPathFindButton = findViewById(R.id.view_pathfind);
        FragmentMapButton = findViewById(R.id.view_map);

        textview = (TextView)findViewById(R.id.textView);

        new Thread(new Runnable() {

            @Override
            public void run() {
                data = getDate();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textview.setText(data);
                    }
                });
            }
        }).start();

        SelectFragment(FragmentPathFindButton);

        StrictMode.enableDefaults();

        boolean instatnFid = false, instatnTid = false, instatnFnm = false, instatnTnm = false, inshtStatnId = false, inrow = false;
        boolean inshtStatnNm = false, inshtTransferMsg = false, inshtTravelMsg = false, inshtStatnCnt = false, inshtTravelTm = false, inshtTransferCnt = false;

        String statnFid = null, statnTid = null, statnFnm = null, statnTnm = null, shtStatnId = null, shtStatnNm = null, shtTransferMsg =null, shtTravelMsg =null;
        String shtStatnCnt = null, shtTravelTm = null, shtTransferCnt = null;

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
//                        if(parser.getName().equals("statnFid")){ //출발역 아이디
//                            instatnFid = true;
//                        }

//                        if(parser.getName().equals("statnTid")){ //도착역 아이디
//                            instatnTid = true;
//                        }

                        if(parser.getName().equals("statnFnm")){ //출발역 이름
                            instatnFnm = true;
                        }

                        if(parser.getName().equals("statnTnm")){ //도착역 이름
                            instatnTnm = true;
                        }

//                        if(parser.getName().equals("shtStatnId")){ //최단시간 경유 전체 아이디
//                            inshtStatnId = true;
//                        }

                        if(parser.getName().equals("shtStatnNm")){ //최단시간 경유 전체 이름
                            inshtStatnNm = true;
                        }

//                        if(parser.getName().equals("shtTransferMsg")){ //환승 지하철역 몇분 걸리고 몇개 있습니다
//                            inshtTransferMsg = true;
//                        }

//                        if(parser.getName().equals("shtTravelMsg")){ //최단시간 지하철역 몇분 걸리고 몇개 있습니다.
//                            inshtTravelMsg = true;
//                        }

                        if(parser.getName().equals("shtStatnCnt")){ //경유 지하철 역 수
                            inshtStatnCnt = true;
                        }

                        if(parser.getName().equals("shtTravelTm")){ //도착 예정시간(분단위)
                            inshtTravelTm = true;
                        }

                        if(parser.getName().equals("shtTransferCnt")){ //환승 횟수
                            inshtTransferCnt = true;
                        }
//
//                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
//                            status1.setText(status1.getText()+"에러");
//                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
//
//                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때

//                        if(instatnFid){ //isTitle이 true일 때 태그의 내용을 저장.
//                            statnFid = parser.getText();
//                            instatnFid = false;
//                        }

//                        if(instatnTid){ //isAddress이 true일 때 태그의 내용을 저장.
//                            statnTid = parser.getText();
//                            instatnTid = false;
//                        }

                        if(instatnFnm){ //isMapx이 true일 때 태그의 내용을 저장.
                            statnFnm = parser.getText();
                            instatnFnm = false;
                        }

                        if(instatnTnm){ //isMapy이 true일 때 태그의 내용을 저장.
                            statnTnm = parser.getText();
                            instatnTnm = false;
                        }

//                        if(inshtStatnId){ //isMapy이 true일 때 태그의 내용을 저장.
//                            shtStatnId = parser.getText();
//                            inshtStatnId = false;
//                        }

                        if(inshtStatnNm){ //isMapy이 true일 때 태그의 내용을 저장.
                            shtStatnNm = parser.getText();
                            inshtStatnNm = false;
                        }

//                        if(inshtTransferMsg){ //isMapy이 true일 때 태그의 내용을 저장.
//                            shtTransferMsg = parser.getText();
//                            inshtTransferMsg = false;
//                        }

//                        if(inshtTravelMsg){ //isMapy이 true일 때 태그의 내용을 저장.
//                            shtTravelMsg = parser.getText();
//                            inshtTravelMsg = false;
//                        }

                        if(inshtStatnCnt){ //isMapy이 true일 때 태그의 내용을 저장.
                            shtStatnCnt = parser.getText();
                            inshtStatnCnt = false;
                        }

                        if(inshtTravelTm){ //isMapy이 true일 때 태그의 내용을 저장.
                            shtTravelTm = parser.getText();
                            inshtTravelTm = false;
                        }

                        if(inshtTransferCnt){ //isMapy이 true일 때 태그의 내용을 저장.
                            shtTransferCnt = parser.getText();
                            inshtTransferCnt = false;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("row")){
//                            status1.setText(status1.getText()+"출발지하철역ID : "+ statnFid +"\n 도착지하철역ID: "+ statnTid +"\n 출발지하철역명 : " + statnFnm+
//                                    "\n 도착지하철역명 : " + statnTnm +  "\n 최단시간 경유 지하철역ID : " + shtStatnId+ "\n 최단시간 경유 지하철역명 : " + shtStatnNm
//                                    +"\n 최단시간 환승 지하철역명: " +shtTransferMsg + "\n 최단시간 도착 예정메세지 : " + shtTravelMsg + "\n 최단시간 경유 지하철역수 : " +shtStatnCnt
//                                    +"\n 최단시간 도착 예정시간 : " +shtTravelTm +"\n 최단시간 환승횟수 : " +shtTransferCnt+"\n");
//
                            inrow = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
//            status1.setText("에러가..났습니다...");
        }



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
        fragment.setArguments(bundle);

        getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }


    String getDate(){

        String Key = "434c54584c6d796f38344c63697369";

        String str1 = "서울";
        String station1 = URLEncoder.encode(str1);

        String str2 = "천안";
        String station2 = URLEncoder.encode(str2);

        StringBuffer Buffer = new StringBuffer();

        boolean instatnFid = false, instatnTid = false, instatnFnm = false, instatnTnm = false, inshtStatnId = false, inrow = false;
        boolean inshtStatnNm = false, inshtTransferMsg = false, inshtTravelMsg = false, inshtStatnCnt = false, inshtTravelTm = false, inshtTransferCnt = false;

        String statnFid = null, statnTid = null, statnFnm = null, statnTnm = null, shtStatnId = null, shtStatnNm = null, shtTransferMsg = null, shtTravelMsg = null;
        String shtStatnCnt = null, shtTravelTm = null, shtTransferCnt = null;

        try {

            URL url = new URL("http://swopenapi.seoul.go.kr/api/subway/"+Key+"/xml/shortestRoute/0/1/"+station1+"/"+station2 );
            Log.d("TAG",url.toString());

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), "UTF-8");

            int parserEvent = parser.getEventType();
            String tag = parser.getName();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {

                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행

                        if(tag.equals("row"));
                        if (tag.equals("statnFid")) { //title 만나면 내용을 받을수 있게 하자
                            statnFid = parser.getText().toString();
                            parser.next();
                            Buffer.append(statnFid);
                            Buffer.append("\n");
                        }

                        if (tag.equals("statnTid")) { //address 만나면 내용을 받을수 있게 하자
                            statnTid = parser.getText().toString();
                            parser.next();
                            Buffer.append(statnTid);
                            Buffer.append("\n");
                        }

                        if (tag.equals("statnFnm")) { //mapx 만나면 내용을 받을수 있게 하자
                            statnFnm = parser.getText().toString();
                            parser.next();
                            Buffer.append(statnFnm);
                            Buffer.append("\n");
                        }

                        if (tag.equals("statnTnm")) { //mapy 만나면 내용을 받을수 있게 하자
                            statnTnm = parser.getText().toString();
                            parser.next();
                            Buffer.append(statnTnm);
                            Buffer.append("\n");
                        }

                        if (tag.equals("shtStatnId")) { //mapy 만나면 내용을 받을수 있게 하자
                            shtStatnId = parser.getText().toString();
                            parser.next();
                            Buffer.append(shtStatnId);
                            Buffer.append("\n");
                        }

                        if (tag.equals("shtStatnNm")) { //mapy 만나면 내용을 받을수 있게 하자
                            shtStatnNm = parser.getText().toString();
                            parser.next();
                            Buffer.append(shtStatnNm);
                            Buffer.append("\n");
                        }

                        if (tag.equals("shtTransferMsg")) { //mapy 만나면 내용을 받을수 있게 하자
                            shtTransferMsg = parser.getText().toString();
                            parser.next();
                            Buffer.append(shtTransferMsg);
                            Buffer.append("\n");
                        }

                        if (tag.equals("shtTravelMsg")) { //mapy 만나면 내용을 받을수 있게 하자
                            shtTravelMsg = parser.getText().toString();
                            parser.next();
                            Buffer.append(shtTravelMsg);
                            Buffer.append("\n");
                        }

                        if (tag.equals("shtStatnCnt")) { //mapy 만나면 내용을 받을수 있게 하자
                            shtStatnCnt = parser.getText().toString();
                            parser.next();
                            Buffer.append(shtStatnCnt);
                            Buffer.append("\n");
                        }

                        if (tag.equals("shtTravelTm")) { //mapy 만나면 내용을 받을수 있게 하자
                            shtTravelTm = parser.getText().toString();
                            parser.next();
                            Buffer.append(shtTravelTm);
                            Buffer.append("\n");
                        }

                        if (tag.equals("shtTransferCnt")) { //mapy 만나면 내용을 받을수 있게 하자
                            shtTransferCnt = parser.getText().toString();
                            parser.next();
                            Buffer.append(shtTransferCnt);
                            Buffer.append("\n");
                        }

                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        break;

                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.equals("row"))
                            Buffer.append("\n");
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {

        }
        return Buffer.toString();
    }


}