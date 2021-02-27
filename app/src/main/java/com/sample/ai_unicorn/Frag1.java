package com.sample.ai_unicorn;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.broooapps.graphview.models.GraphData;
import com.broooapps.graphview.models.PointMap;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Frag1 extends Fragment {
    BarChart mBarChart;
    TextView tv1,tv2;

    Integer[] Color = {0xFF123456,0xFF343456,0xFF563456,0xFF873F56,0xFF56B7F1,0xFF343456,0xFF1FF4AC,0xFF1BA4E6,0xFF9708CC,0xFFD939CD};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag1,container,false);

        Bundle bundle = getArguments();

        mBarChart = (BarChart) v.findViewById(R.id.barchart);

        tv1 = (TextView) v.findViewById(R.id.frag1_tv1);
        tv2 = (TextView) v.findViewById(R.id.frag1_tv2);



//        mBarChart.addBar(new BarModel("1",2.3f, 0xFF123456));
//        mBarChart.addBar(new BarModel("2",2.f,  0xFF343456));
//        mBarChart.addBar(new BarModel("3",3.3f, 0xFF563456));
//        mBarChart.addBar(new BarModel("4",1.1f, 0xFF873F56));
//        mBarChart.addBar(new BarModel("5",2.7f, 0xFF56B7F1));
//        mBarChart.addBar(new BarModel("6",2.f,  0xFF343456));
//        mBarChart.addBar(new BarModel("7",0.4f, 0xFF1FF4AC));
//        mBarChart.addBar(new BarModel("8",4.f,  0xFF1BA4E6));

        for(int i=0; i<7; i++) {
            String s = bundle.getString("s"+i);
            Float f = bundle.getFloat("f"+i);

            mBarChart.addBar(new BarModel(s,f,Color[i]));
        }

        /*String s1 = bundle.getString("s0");
        String s2 = bundle.getString("s9");

        tv1.setText(s1);
        tv2.setText(s2);*/

        mBarChart.startAnimation();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
