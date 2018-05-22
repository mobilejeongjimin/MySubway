package com.example.jeongjimin.mysubway;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentPathFind extends Fragment{

    private TextView startStation;
    private TextView destiStation;
    private TextView timeTxt;
    private TextView station;
    private TextView transferStation;

    private String startStationtxt;
    private String destiStationtxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pathfind_view, container, false);

        startStation = view.findViewById(R.id.startstationtxt);
        destiStation = view.findViewById(R.id.destistationtxt);

        Bundle bundle = getArguments();

        if(bundle != null) {
            startStationtxt = bundle.getString("FragStartStation");
            destiStationtxt = bundle.getString("FragDestiStation");

            startStation.setText(startStationtxt);
            destiStation.setText(destiStationtxt);
        }

        return view;
    }

}
