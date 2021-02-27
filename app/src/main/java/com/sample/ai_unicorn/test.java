package com.sample.ai_unicorn;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.broooapps.graphview.CurveGraphConfig;
import com.broooapps.graphview.CurveGraphView;
import com.broooapps.graphview.models.GraphData;
import com.broooapps.graphview.models.PointMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class test extends AppCompatActivity {
    SQLiteDatabase database;
    CurveGraphView curveGraphView;
    CurveGraphView curveGraphView2;
    CurveGraphView curveGraphView3;
    GraphData gd;
    GraphData gd2;
    GraphData gd3;

    GraphData gd4;
    GraphData gd5;
    GraphData gd6;

    GraphData gd7;
    GraphData gd8;
    GraphData gd9;

    //max값을 통해 y값 최대치 설정
    public int maxgd = 25;
    public int maxgd2 = 50;
    public int maxgd3 = 50;

    public String pos;
    public String one;
    public String two;
    public String three;

    public boolean g1;
    public boolean g2;

    LinearLayout ln_time;
    LinearLayout ln_old;

    public boolean check1;

    public static String[] cat_en = {"e-commerce business","home appliances","Furniture","Flower plant","soft goods","Toys","sports/sports supplies","Cosmetics","Phrase","Medication","Clock and precious metal","Glasses","Bags",
            "Shoes","General clothing","A side dish shop","Cell phone","Sales computers and peripherals","Convenience store","Supermarket","Skin care","Nail shop","Beauty parlor","Sports Club","Dentist","General Assembly",
            "Coffee-Drink","Snack restaurant","Fast Food Shop","The bakery","Food restaurant","a Japanese restaurant","Chinese restaurants","Korean restaurant","Interior","Books","Medical equipment","Singing room",
            "Gosiwon","inn","Laundry","Car repair","PC room","Golf Training Center","a billiard room","Han Chinese Medical Center, California","a sports lesson","Hope-Ganju branch","Chicken specialty store",
            "Lighting","a hardware store","Pettle","Fruit-in-the-wool","Sales of marine products","meat sales","Rice sales","Home Appliance Repair","Art Academy","a foreign language institute","General Teaching Academy",
            "Car beauty","Real estate brokerage business","Bicycles and other transportation equipment"};

    public static String[] cat_ko = {"전자상거래업","가전제품","가구","화초","섬유제품","완구","운동/경기용품","화장품","문구","의약품","시계및귀금속","안경","가방","신발","일반의류","반찬가게","핸드폰","컴퓨터및주변장치판매","편의점",
            "슈퍼마켓","피부관리실","네일숍","미용실","스포츠클럽","치과의원","일반의원","커피-음료","분식전문점","패스트푸드점","제과점","양식음식점","일식음식점","중식음식점","한식음식점","인테리어","서적","의료기기","노래방",
            "고시원","여관","세탁소","자동차수리","PC방","골프연습장","당구장","한의원","스포츠 강습","호프-간이주점","치킨전문점","조명용품","철물점","애완동물","청과상","수산물판매","육류판매","미곡판매","가전제품수리",
            "예술학원","외국어학원","일반교습학원","자동차미용","부동산중개업","자전거 및 기타운송장비"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        Intent intent = getIntent();

        //TextView tx1 = (TextView) findViewById(R.id.textView);
        //TextView tx2 = (TextView) findViewById(R.id.textView2);

        //String name = intent.getExtras().getString("name");
        //tx1.setText(name);

        //int age = intent.getExtras().getInt("age");
        //tx2.setText(String.valueOf(age));*/

        pos = intent.getExtras().getString("position");
        one = intent.getExtras().getString("first");
        two = intent.getExtras().getString("second");
        three = intent.getExtras().getString("third");
        g1 = intent.getExtras().getBoolean("graph1");
        g2 = intent.getExtras().getBoolean("graph2");

        Log.e("pos랑 one값", pos + " " + one);

        //그래프 범주 설정
        TextView tx1 = (TextView) findViewById(R.id.rank_tv);
        TextView tx2 = (TextView) findViewById(R.id.rank_tv2);
        TextView tx3 = (TextView) findViewById(R.id.rank_tv3);

        tx1.setText(one + " [1]");
        tx2.setText(two + " [2]");
        tx3.setText(three + " [3]");

        //graph 기초 설정
        curveGraphView = findViewById(R.id.cgv);

        curveGraphView.configure(
                new CurveGraphConfig.Builder(this)
                        .setAxisColor(R.color.Black)                                             // Set number of values to be displayed in X ax
                        .setVerticalGuideline(4)                                                // Set number of background guidelines to be shown.
                        .setHorizontalGuideline(2)
                        .setGuidelineColor(R.color.Black)                                         // Set color of the visible guidelines.
                        .setNoDataMsg(" No Data ")                                              // Message when no data is provided to the view.
                        .setxAxisScaleTextColor(R.color.Black)                                  // Set X axis scale text color.
                        .setyAxisScaleTextColor(R.color.Black)                                  // Set Y axis scale text color
                        .setAnimationDuration(2000)                                             // Set Animation Duration
                        .build()
        );

        curveGraphView2 = findViewById(R.id.cgv2);

        curveGraphView2.configure(
                new CurveGraphConfig.Builder(this)
                        .setAxisColor(R.color.Black)                                             // Set number of values to be displayed in X ax
                        .setVerticalGuideline(4)                                                // Set number of background guidelines to be shown.
                        .setHorizontalGuideline(2)
                        .setGuidelineColor(R.color.Black)                                         // Set color of the visible guidelines.
                        .setNoDataMsg(" No Data ")                                              // Message when no data is provided to the view.
                        .setxAxisScaleTextColor(R.color.Black)                                  // Set X axis scale text color.
                        .setyAxisScaleTextColor(R.color.Black)                                  // Set Y axis scale text color
                        .setAnimationDuration(2000)                                             // Set Animation Duration
                        .build()
        );

        curveGraphView3 = findViewById(R.id.cgv3);

        curveGraphView3.configure(
                new CurveGraphConfig.Builder(this)
                        .setAxisColor(R.color.Black)                                             // Set number of values to be displayed in X ax
                        .setVerticalGuideline(4)                                                // Set number of background guidelines to be shown.
                        .setHorizontalGuideline(2)
                        .setGuidelineColor(R.color.Black)                                         // Set color of the visible guidelines.
                        .setNoDataMsg(" No Data ")                                              // Message when no data is provided to the view.
                        .setxAxisScaleTextColor(R.color.Black)                                  // Set X axis scale text color.
                        .setyAxisScaleTextColor(R.color.Black)                                  // Set Y axis scale text color
                        .setAnimationDuration(2000)                                             // Set Animation Duration
                        .build()
        );

        boolean bResult = isCheckDatabase();    // DB가 있는지?
        boolean bResult2 = isCheckDatabase2();    // DB가 있는지?

        if (!bResult) {  // DB가 없으면 복사
            Log.d("check","database copy");
            copyDataBase();
        }
        if (!bResult2) {  // DB가 없으면 복사
            Log.d("check","database copy");
            copyDataBase2();
        }

        Cursor c;


        int len = 9800;
        if(pos.equals("강남구")) {

            openDatabase2();
            len = 5656;
            c = database.rawQuery("select * from re_mlml_aa0", null);

            c.move(1);

            for(int i=0; i<len; i++) {
                c.moveToNext();
                String s2 = c.getString(71);
                for(int j=0; j<63; j++) {
                    if(cat_en[j].equals(s2)) {
                        s2 = cat_ko[j];
                        break;
                    }
                }

                if(s2.equals(one) || i==5656) {

                    int mon = c.getInt(4);
                    if(mon > maxgd) maxgd = mon;
                    int tus = c.getInt(5);
                    if(tus > maxgd) maxgd = tus;
                    int wed = c.getInt(6);
                    if(wed > maxgd) maxgd = wed;
                    int thu = c.getInt(7);
                    if(thu > maxgd) maxgd = thu;
                    int fri = c.getInt(8);
                    if(fri > maxgd) maxgd = fri;
                    int sat = c.getInt(9);
                    if(sat > maxgd) maxgd = sat;
                    int sun = c.getInt(10);
                    if(sun > maxgd) maxgd = sun;

                    PointMap pointMap = new PointMap();
                    pointMap.addPoint(1,mon);
                    pointMap.addPoint(2,tus);
                    pointMap.addPoint(3,wed);
                    pointMap.addPoint(4,thu);
                    pointMap.addPoint(5,fri);
                    pointMap.addPoint(6,sat);
                    pointMap.addPoint(7,sun);

                    gd = GraphData.builder(this)
                            .setPointMap(pointMap)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor3,R.color.gradientEndColor3)
                            .animateLine(true)
                            .setPointColor(R.color.Red)
                            .setPointRadius(7)
                            .build();

                    int time06 = c.getInt(11);
                    if(time06 > maxgd2) maxgd2 = time06;
                    int time611 = c.getInt(12);
                    if(time611 > maxgd2) maxgd2 = time611;
                    int time1114 = c.getInt(13);
                    if(time1114 > maxgd2) maxgd2 = time1114;
                    int time1417 = c.getInt(14);
                    if(time1417 > maxgd2) maxgd2 = time1417;
                    int time1721 = c.getInt(15);
                    if(time1721 > maxgd2) maxgd2 = time1721;
                    int time2124 = c.getInt(16);
                    if(time2124 > maxgd2) maxgd2 = time2124;

                    PointMap pointMap2 = new PointMap();
                    pointMap2.addPoint(1,time06);
                    pointMap2.addPoint(2,time611);
                    pointMap2.addPoint(3,time1114);
                    pointMap2.addPoint(4,time1417);
                    pointMap2.addPoint(5,time1721);
                    pointMap2.addPoint(6,time2124);

                    gd4 = GraphData.builder(this)
                            .setPointMap(pointMap2)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor3,R.color.gradientEndColor3)
                            .animateLine(true)
                            .setPointColor(R.color.Red)
                            .setPointRadius(6)
                            .build();


                    int man = c.getInt(17);
                    int wman = c.getInt(18);

                    int y10 = c.getInt(19);
                    if(y10 > maxgd3) maxgd3 = y10;
                    int y20 = c.getInt(20);
                    if(y20 > maxgd3) maxgd3 = y20;
                    int y30 = c.getInt(21);
                    if(y30 > maxgd3) maxgd3 = y30;
                    int y40 = c.getInt(22);
                    if(y40 > maxgd3) maxgd3 = y40;
                    int y50 = c.getInt(23);
                    if(y50 > maxgd3) maxgd3 = y50;
                    int y60 = c.getInt(24);
                    if(y60 > maxgd3) maxgd3 = y60;

                    PointMap pointMap3 = new PointMap();
                    pointMap3.addPoint(1,y10);
                    pointMap3.addPoint(2,y20);
                    pointMap3.addPoint(3,y30);
                    pointMap3.addPoint(4,y40);
                    pointMap3.addPoint(5,y50);
                    pointMap3.addPoint(6,y60);

                    gd7 = GraphData.builder(this)
                            .setPointMap(pointMap3)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor3,R.color.gradientEndColor3)
                            .animateLine(true)
                            .setPointColor(R.color.Red)
                            .setPointRadius(6)
                            .build();

                } else if (s2.equals(two) || i==5656) {
                    int mon = c.getInt(4);
                    if(mon > maxgd) maxgd = mon;
                    int tus = c.getInt(5);
                    if(tus > maxgd) maxgd = tus;
                    int wed = c.getInt(6);
                    if(wed > maxgd) maxgd = wed;
                    int thu = c.getInt(7);
                    if(thu > maxgd) maxgd = thu;
                    int fri = c.getInt(8);
                    if(fri > maxgd) maxgd = fri;
                    int sat = c.getInt(9);
                    if(sat > maxgd) maxgd = sat;
                    int sun = c.getInt(10);
                    if(sun > maxgd) maxgd = sun;

                    PointMap pointMap = new PointMap();
                    pointMap.addPoint(1,mon);
                    pointMap.addPoint(2,tus);
                    pointMap.addPoint(3,wed);
                    pointMap.addPoint(4,thu);
                    pointMap.addPoint(5,fri);
                    pointMap.addPoint(6,sat);
                    pointMap.addPoint(7,sun);

                    gd2 = GraphData.builder(this)
                            .setPointMap(pointMap)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor2,R.color.gradientEndColor2)
                            .animateLine(true)
                            .setPointColor(R.color.Blue)
                            .setPointRadius(7)
                            .build();

                    int time06 = c.getInt(11);
                    if(time06 > maxgd2) maxgd2 = time06;
                    int time611 = c.getInt(12);
                    if(time611 > maxgd2) maxgd2 = time611;
                    int time1114 = c.getInt(13);
                    if(time1114 > maxgd2) maxgd2 = time1114;
                    int time1417 = c.getInt(14);
                    if(time1417 > maxgd2) maxgd2 = time1417;
                    int time1721 = c.getInt(15);
                    if(time1721 > maxgd2) maxgd2 = time1721;
                    int time2124 = c.getInt(16);
                    if(time2124 > maxgd2) maxgd2 = time2124;


                    PointMap pointMap2 = new PointMap();
                    pointMap2.addPoint(1,time06);
                    pointMap2.addPoint(2,time611);
                    pointMap2.addPoint(3,time1114);
                    pointMap2.addPoint(4,time1417);
                    pointMap2.addPoint(5,time1721);
                    pointMap2.addPoint(6,time2124);

                    gd5 = GraphData.builder(this)
                            .setPointMap(pointMap2)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor2,R.color.gradientEndColor2)
                            .animateLine(true)
                            .setPointColor(R.color.Blue)
                            .setPointRadius(6)
                            .build();

                    int man = c.getInt(17);
                    int wman = c.getInt(18);

                    int y10 = c.getInt(19);
                    if(y10 > maxgd3) maxgd3 = y10;
                    int y20 = c.getInt(20);
                    if(y20 > maxgd3) maxgd3 = y20;
                    int y30 = c.getInt(21);
                    if(y30 > maxgd3) maxgd3 = y30;
                    int y40 = c.getInt(22);
                    if(y40 > maxgd3) maxgd3 = y40;
                    int y50 = c.getInt(23);
                    if(y50 > maxgd3) maxgd3 = y50;
                    int y60 = c.getInt(24);
                    if(y60 > maxgd3) maxgd3 = y60;

                    PointMap pointMap3 = new PointMap();
                    pointMap3.addPoint(1,y10);
                    pointMap3.addPoint(2,y20);
                    pointMap3.addPoint(3,y30);
                    pointMap3.addPoint(4,y40);
                    pointMap3.addPoint(5,y50);
                    pointMap3.addPoint(6,y60);


                    gd8 = GraphData.builder(this)
                            .setPointMap(pointMap3)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor2,R.color.gradientEndColor2)
                            .animateLine(true)
                            .setPointColor(R.color.Blue)
                            .setPointRadius(6)
                            .build();

                } else if (s2.equals(three)|| i==5656) {
                    int mon = c.getInt(4);
                    if(mon > maxgd) maxgd = mon;
                    int tus = c.getInt(5);
                    if(tus > maxgd) maxgd = tus;
                    int wed = c.getInt(6);
                    if(wed > maxgd) maxgd = wed;
                    int thu = c.getInt(7);
                    if(thu > maxgd) maxgd = thu;
                    int fri = c.getInt(8);
                    if(fri > maxgd) maxgd = fri;
                    int sat = c.getInt(9);
                    if(sat > maxgd) maxgd = sat;
                    int sun = c.getInt(10);
                    if(sun > maxgd) maxgd = sun;

                    PointMap pointMap = new PointMap();
                    pointMap.addPoint(1,mon);
                    pointMap.addPoint(2,tus);
                    pointMap.addPoint(3,wed);
                    pointMap.addPoint(4,thu);
                    pointMap.addPoint(5,fri);
                    pointMap.addPoint(6,sat);
                    pointMap.addPoint(7,sun);

                    gd3 = GraphData.builder(this)
                            .setPointMap(pointMap)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor,R.color.gradientEndColor)
                            .animateLine(true)
                            .setPointColor(R.color.Yellow)
                            .setPointRadius(7)
                            .build();

                    int time06 = c.getInt(11);
                    if(time06 > maxgd2) maxgd2 = time06;
                    int time611 = c.getInt(12);
                    if(time611 > maxgd2) maxgd2 = time611;
                    int time1114 = c.getInt(13);
                    if(time1114 > maxgd2) maxgd2 = time1114;
                    int time1417 = c.getInt(14);
                    if(time1417 > maxgd2) maxgd2 = time1417;
                    int time1721 = c.getInt(15);
                    if(time1721 > maxgd2) maxgd2 = time1721;
                    int time2124 = c.getInt(16);
                    if(time2124 > maxgd2) maxgd2 = time2124;


                    PointMap pointMap2 = new PointMap();
                    pointMap2.addPoint(1,time06);
                    pointMap2.addPoint(2,time611);
                    pointMap2.addPoint(3,time1114);
                    pointMap2.addPoint(4,time1417);
                    pointMap2.addPoint(5,time1721);
                    pointMap2.addPoint(6,time2124);

                    gd6 = GraphData.builder(this)
                            .setPointMap(pointMap2)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor,R.color.gradientEndColor)
                            .animateLine(true)
                            .setPointColor(R.color.Yellow)
                            .setPointRadius(7)
                            .build();

                    int man = c.getInt(17);
                    int wman = c.getInt(18);

                    int y10 = c.getInt(19);
                    if(y10 > maxgd3) maxgd3 = y10;
                    int y20 = c.getInt(20);
                    if(y20 > maxgd3) maxgd3 = y20;
                    int y30 = c.getInt(21);
                    if(y30 > maxgd3) maxgd3 = y30;
                    int y40 = c.getInt(22);
                    if(y40 > maxgd3) maxgd3 = y40;
                    int y50 = c.getInt(23);
                    if(y50 > maxgd3) maxgd3 = y50;
                    int y60 = c.getInt(24);
                    if(y60 > maxgd3) maxgd3 = y60;

                    PointMap pointMap3 = new PointMap();
                    pointMap3.addPoint(1,y10);
                    pointMap3.addPoint(2,y20);
                    pointMap3.addPoint(3,y30);
                    pointMap3.addPoint(4,y40);
                    pointMap3.addPoint(5,y50);
                    pointMap3.addPoint(6,y60);

                    gd9 = GraphData.builder(this)
                            .setPointMap(pointMap3)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor,R.color.gradientEndColor)
                            .animateLine(true)
                            .setPointColor(R.color.Yellow)
                            .setPointRadius(6)
                            .build();
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curveGraphView.setData(7, maxgd, gd,gd2,gd3);
                }
            }, 250);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curveGraphView2.setData(6, maxgd2, gd4,gd5,gd6);
                }
            }, 250);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curveGraphView3.setData(6, maxgd3, gd7,gd8,gd9);
                }
            }, 250);

        } else {
            openDatabase();
            len = 9800;
            c = database.rawQuery("select * from train", null);

            c.move(1);

            for(int i=0; i<len; i++) {
                c.moveToNext();
                String s = c.getString(0);
                String s2 = c.getString(2);
                if(s.equals(pos) && s2.equals(one) || i==9799) {

                    int mon = c.getInt(7);
                    if(mon > maxgd) maxgd = mon;
                    int tus = c.getInt(8);
                    if(tus > maxgd) maxgd = tus;
                    int wed = c.getInt(9);
                    if(wed > maxgd) maxgd = wed;
                    int thu = c.getInt(10);
                    if(thu > maxgd) maxgd = thu;
                    int fri = c.getInt(11);
                    if(fri > maxgd) maxgd = fri;
                    int sat = c.getInt(12);
                    if(sat > maxgd) maxgd = sat;
                    int sun = c.getInt(13);
                    if(sun > maxgd) maxgd = sun;

                    PointMap pointMap = new PointMap();
                    pointMap.addPoint(1,mon);
                    pointMap.addPoint(2,tus);
                    pointMap.addPoint(3,wed);
                    pointMap.addPoint(4,thu);
                    pointMap.addPoint(5,fri);
                    pointMap.addPoint(6,sat);
                    pointMap.addPoint(7,sun);

                    gd = GraphData.builder(this)
                            .setPointMap(pointMap)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor3,R.color.gradientEndColor3)
                            .animateLine(true)
                            .setPointColor(R.color.Red)
                            .setPointRadius(7)
                            .build();

                    int time06 = c.getInt(14);
                    if(time06 > maxgd2) maxgd2 = time06;
                    int time611 = c.getInt(15);
                    if(time611 > maxgd2) maxgd2 = time611;
                    int time1114 = c.getInt(16);
                    if(time1114 > maxgd2) maxgd2 = time1114;
                    int time1417 = c.getInt(17);
                    if(time1417 > maxgd2) maxgd2 = time1417;
                    int time1721 = c.getInt(18);
                    if(time1721 > maxgd2) maxgd2 = time1721;
                    int time2124 = c.getInt(19);
                    if(time2124 > maxgd2) maxgd2 = time2124;

                    PointMap pointMap2 = new PointMap();
                    pointMap2.addPoint(1,time06);
                    pointMap2.addPoint(2,time611);
                    pointMap2.addPoint(3,time1114);
                    pointMap2.addPoint(4,time1417);
                    pointMap2.addPoint(5,time1721);
                    pointMap2.addPoint(6,time2124);


                    gd4 = GraphData.builder(this)
                            .setPointMap(pointMap2)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor3,R.color.gradientEndColor3)
                            .animateLine(true)
                            .setPointColor(R.color.Red)
                            .setPointRadius(6)
                            .build();


                    int man = c.getInt(20);
                    int wman = c.getInt(21);

                    int y10 = c.getInt(22);
                    if(y10 > maxgd3) maxgd3 = y10;
                    int y20 = c.getInt(23);
                    if(y20 > maxgd3) maxgd3 = y20;
                    int y30 = c.getInt(24);
                    if(y30 > maxgd3) maxgd3 = y30;
                    int y40 = c.getInt(25);
                    if(y40 > maxgd3) maxgd3 = y40;
                    int y50 = c.getInt(26);
                    if(y50 > maxgd3) maxgd3 = y50;
                    int y60 = c.getInt(27);
                    if(y60 > maxgd3) maxgd3 = y60;

                    PointMap pointMap3 = new PointMap();
                    pointMap3.addPoint(1,y10);
                    pointMap3.addPoint(2,y20);
                    pointMap3.addPoint(3,y30);
                    pointMap3.addPoint(4,y40);
                    pointMap3.addPoint(5,y50);
                    pointMap3.addPoint(6,y60);

                    gd7 = GraphData.builder(this)
                            .setPointMap(pointMap3)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor3,R.color.gradientEndColor3)
                            .animateLine(true)
                            .setPointColor(R.color.Red)
                            .setPointRadius(6)
                            .build();

                } else if (s.equals(pos) && s2.equals(two)|| i==9798) {
                    int mon = c.getInt(7);
                    if(mon > maxgd) maxgd = mon;
                    int tus = c.getInt(8);
                    if(tus > maxgd) maxgd = tus;
                    int wed = c.getInt(9);
                    if(wed > maxgd) maxgd = wed;
                    int thu = c.getInt(10);
                    if(thu > maxgd) maxgd = thu;
                    int fri = c.getInt(11);
                    if(fri > maxgd) maxgd = fri;
                    int sat = c.getInt(12);
                    if(sat > maxgd) maxgd = sat;
                    int sun = c.getInt(13);
                    if(sun > maxgd) maxgd = sun;

                    PointMap pointMap = new PointMap();
                    pointMap.addPoint(1,mon);
                    pointMap.addPoint(2,tus);
                    pointMap.addPoint(3,wed);
                    pointMap.addPoint(4,thu);
                    pointMap.addPoint(5,fri);
                    pointMap.addPoint(6,sat);
                    pointMap.addPoint(7,sun);

                    gd2 = GraphData.builder(this)
                            .setPointMap(pointMap)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor2,R.color.gradientEndColor2)
                            .animateLine(true)
                            .setPointColor(R.color.Blue)
                            .setPointRadius(7)
                            .build();

                    int time06 = c.getInt(14);
                    if(time06 > maxgd2) maxgd2 = time06;
                    int time611 = c.getInt(15);
                    if(time611 > maxgd2) maxgd2 = time611;
                    int time1114 = c.getInt(16);
                    if(time1114 > maxgd2) maxgd2 = time1114;
                    int time1417 = c.getInt(17);
                    if(time1417 > maxgd2) maxgd2 = time1417;
                    int time1721 = c.getInt(18);
                    if(time1721 > maxgd2) maxgd2 = time1721;
                    int time2124 = c.getInt(19);
                    if(time2124 > maxgd2) maxgd2 = time2124;


                    PointMap pointMap2 = new PointMap();
                    pointMap2.addPoint(1,time06);
                    pointMap2.addPoint(2,time611);
                    pointMap2.addPoint(3,time1114);
                    pointMap2.addPoint(4,time1417);
                    pointMap2.addPoint(5,time1721);
                    pointMap2.addPoint(6,time2124);

                    gd5 = GraphData.builder(this)
                            .setPointMap(pointMap2)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor2,R.color.gradientEndColor2)
                            .animateLine(true)
                            .setPointColor(R.color.Blue)
                            .setPointRadius(6)
                            .build();

                    int man = c.getInt(20);
                    int wman = c.getInt(21);

                    int y10 = c.getInt(22);
                    if(y10 > maxgd3) maxgd3 = y10;
                    int y20 = c.getInt(23);
                    if(y20 > maxgd3) maxgd3 = y20;
                    int y30 = c.getInt(24);
                    if(y30 > maxgd3) maxgd3 = y30;
                    int y40 = c.getInt(25);
                    if(y40 > maxgd3) maxgd3 = y40;
                    int y50 = c.getInt(26);
                    if(y50 > maxgd3) maxgd3 = y50;
                    int y60 = c.getInt(27);
                    if(y60 > maxgd3) maxgd3 = y60;

                    PointMap pointMap3 = new PointMap();
                    pointMap3.addPoint(1,y10);
                    pointMap3.addPoint(2,y20);
                    pointMap3.addPoint(3,y30);
                    pointMap3.addPoint(4,y40);
                    pointMap3.addPoint(5,y50);
                    pointMap3.addPoint(6,y60);


                    gd8 = GraphData.builder(this)
                            .setPointMap(pointMap3)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor2,R.color.gradientEndColor2)
                            .animateLine(true)
                            .setPointColor(R.color.Blue)
                            .setPointRadius(6)
                            .build();

                } else if (s.equals(pos) && s2.equals(three)|| i==9797) {
                    int mon = c.getInt(7);
                    if(mon > maxgd) maxgd = mon;
                    int tus = c.getInt(8);
                    if(tus > maxgd) maxgd = tus;
                    int wed = c.getInt(9);
                    if(wed > maxgd) maxgd = wed;
                    int thu = c.getInt(10);
                    if(thu > maxgd) maxgd = thu;
                    int fri = c.getInt(11);
                    if(fri > maxgd) maxgd = fri;
                    int sat = c.getInt(12);
                    if(sat > maxgd) maxgd = sat;
                    int sun = c.getInt(13);
                    if(sun > maxgd) maxgd = sun;

                    PointMap pointMap = new PointMap();
                    pointMap.addPoint(1,mon);
                    pointMap.addPoint(2,tus);
                    pointMap.addPoint(3,wed);
                    pointMap.addPoint(4,thu);
                    pointMap.addPoint(5,fri);
                    pointMap.addPoint(6,sat);
                    pointMap.addPoint(7,sun);

                    gd3 = GraphData.builder(this)
                            .setPointMap(pointMap)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor,R.color.gradientEndColor)
                            .animateLine(true)
                            .setPointColor(R.color.Yellow)
                            .setPointRadius(7)
                            .build();

                    int time06 = c.getInt(14);
                    if(time06 > maxgd2) maxgd2 = time06;
                    int time611 = c.getInt(15);
                    if(time611 > maxgd2) maxgd2 = time611;
                    int time1114 = c.getInt(16);
                    if(time1114 > maxgd2) maxgd2 = time1114;
                    int time1417 = c.getInt(17);
                    if(time1417 > maxgd2) maxgd2 = time1417;
                    int time1721 = c.getInt(18);
                    if(time1721 > maxgd2) maxgd2 = time1721;
                    int time2124 = c.getInt(19);
                    if(time2124 > maxgd2) maxgd2 = time2124;


                    PointMap pointMap2 = new PointMap();
                    pointMap2.addPoint(1,time06);
                    pointMap2.addPoint(2,time611);
                    pointMap2.addPoint(3,time1114);
                    pointMap2.addPoint(4,time1417);
                    pointMap2.addPoint(5,time1721);
                    pointMap2.addPoint(6,time2124);

                    gd6 = GraphData.builder(this)
                            .setPointMap(pointMap2)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor,R.color.gradientEndColor)
                            .animateLine(true)
                            .setPointColor(R.color.Yellow)
                            .setPointRadius(7)
                            .build();

                    int man = c.getInt(20);
                    int wman = c.getInt(21);

                    int y10 = c.getInt(22);
                    if(y10 > maxgd3) maxgd3 = y10;
                    int y20 = c.getInt(23);
                    if(y20 > maxgd3) maxgd3 = y20;
                    int y30 = c.getInt(24);
                    if(y30 > maxgd3) maxgd3 = y30;
                    int y40 = c.getInt(25);
                    if(y40 > maxgd3) maxgd3 = y40;
                    int y50 = c.getInt(26);
                    if(y50 > maxgd3) maxgd3 = y50;
                    int y60 = c.getInt(27);
                    if(y60 > maxgd3) maxgd3 = y60;

                    PointMap pointMap3 = new PointMap();
                    pointMap3.addPoint(1,y10);
                    pointMap3.addPoint(2,y20);
                    pointMap3.addPoint(3,y30);
                    pointMap3.addPoint(4,y40);
                    pointMap3.addPoint(5,y50);
                    pointMap3.addPoint(6,y60);

                    gd9 = GraphData.builder(this)
                            .setPointMap(pointMap3)
                            .setGraphStroke(R.color.Black)
                            .setGraphGradient(R.color.gradientStartColor,R.color.gradientEndColor)
                            .animateLine(true)
                            .setPointColor(R.color.Yellow)
                            .setPointRadius(6)
                            .build();
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curveGraphView.setData(7, maxgd, gd,gd2,gd3);
                }
            }, 250);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curveGraphView2.setData(6, maxgd2, gd4,gd5,gd6);
                }
            }, 250);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curveGraphView3.setData(6, maxgd3, gd7,gd8,gd9);
                }
            }, 250);

        }

        //사용자 체크시 그래프 표시 여부
        if(!g1) {
            ln_time = (LinearLayout) findViewById(R.id.linear_time);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, 0);
            ln_time.setLayoutParams(param);
        }
        if(!g2) {
            ln_old = (LinearLayout) findViewById(R.id.linear_old);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, 0);
            ln_old.setLayoutParams(param);
        }
    }

    public boolean isCheckDatabase() {
        String filePath = "/data/data/com.sample.ai_unicorn" + "/databases/" + "test.db";
        File file = new File(filePath);
        return file.exists();
    }

    public boolean isCheckDatabase2() {
        String filePath = "/data/data/com.sample.ai_unicorn" + "/databases/" + "gangnam.db";
        File file = new File(filePath);
        return file.exists();
    }

    public void copyDataBase() {
        Context mContext = getApplicationContext();
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/com.sample.ai_unicorn" + "/databases";
        String filePath = "/data/data/com.sample.ai_unicorn" + "/databases/" + "test.db";
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fileOut = null;
        BufferedOutputStream bufferOut = null;
        try {
            InputStream inputStr = manager.open("db/" + "test.db");
            BufferedInputStream bufferStr = new BufferedInputStream(inputStr);
            if (folder.exists()) {
            } else {
                folder.mkdirs();
            }

            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fileOut = new FileOutputStream(file);
            bufferOut = new BufferedOutputStream(fileOut);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bufferStr.read(buffer, 0, 1024)) != -1) {
                bufferOut.write(buffer, 0, read);
            }
            bufferOut.flush();
            bufferOut.close();
            fileOut.close();
            bufferStr.close();
            inputStr.close();
        } catch (IOException e) {
            Log.e("Error : ", e.getMessage());
        }
    }

    public void copyDataBase2() {
        Context mContext = getApplicationContext();
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/com.sample.ai_unicorn" + "/databases";
        String filePath = "/data/data/com.sample.ai_unicorn" + "/databases/" + "gangnam.db";
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fileOut = null;
        BufferedOutputStream bufferOut = null;
        try {
            InputStream inputStr = manager.open("db/" + "gangnam.db");
            BufferedInputStream bufferStr = new BufferedInputStream(inputStr);
            if (folder.exists()) {
            } else {
                folder.mkdirs();
            }

            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fileOut = new FileOutputStream(file);
            bufferOut = new BufferedOutputStream(fileOut);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bufferStr.read(buffer, 0, 1024)) != -1) {
                bufferOut.write(buffer, 0, read);
            }
            bufferOut.flush();
            bufferOut.close();
            fileOut.close();
            bufferStr.close();
            inputStr.close();
        } catch (IOException e) {
            Log.e("Error : ", e.getMessage());
        }
    }

    public void openDatabase() {
        database = openOrCreateDatabase("test.db", MODE_PRIVATE, null);
        if (database != null) {
            //      Toast.makeText(this.getApplicationContext(), "데이터베이스가 존재합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDatabase2() {
        database = openOrCreateDatabase("gangnam.db", MODE_PRIVATE, null);
        if (database != null) {
            //      Toast.makeText(this.getApplicationContext(), "데이터베이스가 존재합니다.", Toast.LENGTH_SHORT).show();
        }
    }
}