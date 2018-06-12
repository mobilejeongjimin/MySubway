package com.example.jeongjimin.mysubway;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*Path find compute에서 계산해서 나온 상세정보들을 보여주는 fragment*/

public class FragmentPathFind extends Fragment{

    private TextView startStation;
    private TextView destiStation;
    private TextView taketime;
    private TextView stationNum;
    private TextView transferStation;

    private String startStationtxt;
    private String destiStationtxt;
    private String taiet8jetxt;
    private String stationNumtxt;
    private String transferStationtxt;
    private String StationName;

    private ListView StationList;


    /*생성과 동시에 정보륿 받아와서 보여줌*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pathfind_view, container, false);

        startStation = view.findViewById(R.id.startstationtxt);
        destiStation = view.findViewById(R.id.destistationtxt);
        taketime = view.findViewById(R.id.taketimetxt);
        stationNum = view.findViewById(R.id.stationnumtxt);
        transferStation = view.findViewById(R.id.transstationtxt);

        StationList = view.findViewById(R.id.StationGruop);


        /*번들로 데이터 받아오고 그 데이터를 텍스트뷰에 배치*/
        Bundle bundle = getArguments();

        if(bundle != null) {
            startStationtxt = bundle.getString("FragStartStation");
            destiStationtxt = bundle.getString("FragDestiStation");
            taiet8jetxt = bundle.getString("FragTakeTime");
            stationNumtxt = bundle.getString("FragsationNumber");
            transferStationtxt = bundle.getString("FragtransferStation");
            StationName = bundle.getString("FragStationName");

            startStation.setText(startStationtxt);
            destiStation.setText(destiStationtxt);
            taketime.setText(taiet8jetxt + "분");
            stationNum.setText(stationNumtxt + "개");
            transferStation.setText(transferStationtxt + "개");
        }

        /*정거장의 정보를 하나의 스트링에 쭉 적어서 받아오는데 이를 ','를 기준으로 자르고 Array list에 담아 준 뒤 listview로 목록들을 보여줌*/
        String[] SplitArray = StationName.split(",");
        List<String> ArrStationNameGroup = new ArrayList<String>();
        for(int i = 0; i < SplitArray.length; i++){
            ArrStationNameGroup.add(SplitArray[i]);
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,ArrStationNameGroup);

        StationList.setAdapter(adapter);
        return view;
    }

}
