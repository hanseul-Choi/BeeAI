package com.sample.ai_unicorn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class Frag3 extends Fragment {
    BarChart mBarChart;
    TextView tv1,tv2;

    Integer[] Color = {0xFF123456,0xFF343456,0xFF563456,0xFF873F56,0xFF56B7F1,0xFF343456,0xFF1FF4AC,0xFF1BA4E6,0xFF9708CC,0xFFD939CD};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag3,container,false);

        mBarChart = (BarChart) v.findViewById(R.id.barchart);

        tv1 = (TextView) v.findViewById(R.id.frag3_tv1);
        tv2 = (TextView) v.findViewById(R.id.frag3_tv2);

        Bundle bundle2 = getArguments();

        for(int i=0; i<7; i++) {
            String s = bundle2.getString("s"+i);
            Float f = bundle2.getFloat("f"+i);

            if(s.length() > 5) s = s.substring(0,5);

            mBarChart.addBar(new BarModel(s,f,Color[i]));
        }

        //String s1 = bundle2.getString("s0");
        //String s2 = bundle2.getString("s9");

        //tv1.setText(s1);
        //tv2.setText(s2);

        mBarChart.startAnimation();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
