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

        SelectFragment(FragmentPathFindButton);
    }

    public void onClick(View view) {

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

//        SelectFragment(view);


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

